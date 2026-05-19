package service;
import java.util.*;

class Offer {
    String offerId;
    String studentId;
    String skill;
    String level;
    String className;
}

class Request {
    String requestId;
    String studentId;
    String skill;
    String level;
    String className;
}

class MatchResult {
    String offerId;
    String requestId;
    int score;
    String reason;

    public MatchResult(String offerId, String requestId, int score, String reason) {
        this.offerId = offerId;
        this.requestId = requestId;
        this.score = score;
        this.reason = reason;
    }
}

public class MatchingService {

    private final String studentId;

    public String getStudentId() {
        return studentId;
    }

    public MatchingService(String studentId) {
        this.studentId = studentId;
    }

    public List<MatchResult> findOneWayMatches(List<Offer> offers, List<Request> requests) {
        List<MatchResult> results = new ArrayList<>();
        for (Offer offer : offers) {
            if (offer.studentId.equals(studentId)) continue;
            for (Request request : requests) {
                if (request.studentId.equals(studentId)) continue;
                int score = 0;
                List<String> reasons = new ArrayList<>();
                if (offer.skill.equals(request.skill)) {
                    score += 3;
                    reasons.add("Skill uguale");
                }
                if (isLevelSufficient(offer.level, request.level)) {
                    score += 2;
                    reasons.add("Livello sufficiente");
                }
                if (offer.className.equals(request.className)) {
                    score += 1;
                    reasons.add("Stessa classe");
                }
                if (score > 0) {
                    results.add(new MatchResult(
                        offer.offerId,
                        request.requestId,
                        score,
                        String.join(", ", reasons)
                    ));
                }
            }
        }
        return results;
    }

    private boolean isLevelSufficient(String offerLevel, String requestLevel) {
        if (offerLevel.equals("Avanzato") && requestLevel.equals("Base")) return true;
        return offerLevel.equals(requestLevel);
    }

        public void findSwipeMatches(List<Offer> offers, List<Request> requests) {
                List<MatchResult> results = findOneWayMatches(offers, requests);
                results.sort((a, b) -> Integer.compare(b.score, a.score));
                for (MatchResult result : results) {
                    System.out.println("Offerta " + result.offerId + " - Richiesta " + result.requestId + ": Score " + result.score + " (" + result.reason + ")");
                }
        }
    }
