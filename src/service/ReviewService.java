package service;
 
import domain.Exchange;
import domain.Review;
import domain.Student;
import java.time.LocalDateTime;
import resources.Status;
import storage.SkillSwapState;
 
public class ReviewService {
 
    private final SkillSwapState state;
 
    public ReviewService(SkillSwapState state) {
        this.state = state;
    }
 
    public Review addReview(String reviewId, String exchangeId,
                            String reviewerId, int stars, String comment) {
 
        if (stars < 1 || stars > 5)
            throw new IllegalArgumentException("Le stelle devono essere tra 1 e 5");
        if (comment == null || comment.isBlank())
            throw new IllegalArgumentException("Il commento non può essere vuoto");
 
        Exchange exchange = state.getExchanges().get(exchangeId);
        if (exchange == null)
            throw new IllegalArgumentException("Exchange non trovato: " + exchangeId);
        if (exchange.getStatus() != Status.COMPLETED)
            throw new IllegalStateException("La recensione è possibile solo quando lo scambio è COMPLETED");
 
        Student reviewer = state.getStudents().get(reviewerId);
        if (reviewer == null)
            throw new IllegalArgumentException("Recensore non trovato: " + reviewerId);
 
        Student offerStudent   = exchange.getOffer().getStudent();
        Student requestStudent = exchange.getRequest().getStudent();
 
        Student reviewee;
        if (reviewerId.equals(offerStudent.getId())) {
            reviewee = requestStudent;
        } else if (reviewerId.equals(requestStudent.getId())) {
            reviewee = offerStudent;
        } else {
            throw new IllegalStateException("Il recensore non ha partecipato a questo scambio");
        }
 
        boolean alreadyReviewed = state.getReviews().values().stream()
            .anyMatch(r -> r.getExchange().getId().equals(exchangeId)
                       && r.getReviewer().getId().equals(reviewerId));
        if (alreadyReviewed)
            throw new IllegalStateException("Hai già lasciato una recensione per questo scambio");
 
        Review review = new Review(reviewId, exchange, reviewer, reviewee, stars, comment, LocalDateTime.now());
        state.addReview(review);
 
        reviewee.addRating(stars);
 
        return review;
    }
}