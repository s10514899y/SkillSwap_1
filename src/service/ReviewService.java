package service;

public class ReviewService {
    public void addReview(String exchangeId, String reviewerId, int stars, String comment) {
       /*  if (status = ExchangeService.COMPLETED) {
            throw new IllegalStateException("Lo scambio deve essere COMPLETED per lasciare una review.");
        }*/
        if (stars < 1 || stars > 5) {
            throw new IllegalArgumentException("Le stelle devono essere ra 1 e 5");
        }
        if (comment == null || comment.trim().isEmpty()) {
            throw new IllegalArgumentException("I commenti no npossono essere vuoti");
        }

        Review review = new Review(exchangeId, reviewerId, stars, comment);

        Database.saveReview(review);

        Database.updateAverageRating(exchangeId);
    }
}

