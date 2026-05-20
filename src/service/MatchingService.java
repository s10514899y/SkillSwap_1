package service;
 
import domain.MatchResult;
import domain.Offer;
import domain.Request;
import java.util.*;

public class MatchingService {
 
    public List<MatchResult> findOneWayMatches(String studentId,
                                               Collection<Offer>   allOffers,
                                               Collection<Request> allRequests) {
        List<MatchResult> results = new ArrayList<>();
 
        List<Request> myRequests = allRequests.stream()
            .filter(r -> r.getStudent().getId().equals(studentId))
            .toList();
 
        for (Request myRequest : myRequests) {
            for (Offer offer : allOffers) {
                if (offer.getStudent().getId().equals(studentId)) continue;
                if (!offer.isActive()) continue;
 
                int score = 0;
                List<String> reasons = new ArrayList<>();
 
                if (offer.getSkill().getId().equals(myRequest.getSkill().getId())) {
                    score += 3;
                    reasons.add("skill uguale");
                }
                if (offer.getLevel().isSufficient(myRequest.getMinLevel())) {
                    score += 2;
                    reasons.add("livello sufficiente");
                }
                if (offer.getStudent().getSchoolClass().equals(myRequest.getStudent().getSchoolClass())) {
                    score += 1;
                    reasons.add("stessa classe");
                }
 
                if (score > 0) {
                    results.add(new MatchResult(
                        offer.getId(),
                        myRequest.getId(),
                        score,
                        String.join(", ", reasons)
                    ));
                }
            }
        }
 
        results.sort(Comparator.comparingInt(MatchResult::getScore).reversed());
        return results;
    }
 
    public List<MatchResult> findSwapMatches(String studentId,
                                             Collection<Offer>   allOffers,
                                             Collection<Request> allRequests) {
        List<MatchResult> results = new ArrayList<>();
 
        List<Offer> myOffers = allOffers.stream()
            .filter(o -> o.getStudent().getId().equals(studentId) && o.isActive())
            .toList();
        List<Request> myRequests = allRequests.stream()
            .filter(r -> r.getStudent().getId().equals(studentId))
            .toList();
 
        for (Offer myOffer : myOffers) {
            for (Request myRequest : myRequests) {
                for (Offer theirOffer : allOffers) {
                    String theirId = theirOffer.getStudent().getId();
                    if (theirId.equals(studentId)) continue;
                    if (!theirOffer.isActive()) continue;
                    if (!theirOffer.getSkill().getId().equals(myRequest.getSkill().getId())) continue;
                    if (!theirOffer.getLevel().isSufficient(myRequest.getMinLevel())) continue;

                    boolean theyWantMySkill = allRequests.stream()
                        .anyMatch(r -> r.getStudent().getId().equals(theirId)
                                    && r.getSkill().getId().equals(myOffer.getSkill().getId())
                                    && myOffer.getLevel().isSufficient(r.getMinLevel()));
 
                    if (theyWantMySkill) {
                        int score = 6;
                        if (myOffer.getStudent().getSchoolClass()
                                .equals(theirOffer.getStudent().getSchoolClass())) score += 1;
 
                        results.add(new MatchResult(
                            theirOffer.getId(),
                            myRequest.getId(),
                            score,
                            "swap reciproco"
                        ));
                    }
                }
            }
        }
 
        results.sort(Comparator.comparingInt(MatchResult::getScore).reversed());
        return results;
    }
}