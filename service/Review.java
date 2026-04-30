package service;

import java.util.ArrayList;
import java.util.List;

class Review {
    private String exchangeId;
    private String reviewerId;
    private int stars;
    private String comment;

    public void setExchangeId(String exchangeId) {
        this.exchangeId = exchangeId;
    }

    public String getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(String reviewerId) {
        this.reviewerId = reviewerId;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Review(String exchangeId, String reviewerId, int stars, String comment) {
        this.exchangeId = exchangeId;
        this.reviewerId = reviewerId;
        this.stars = stars;
        this.comment = comment;
    }

    public String getExchangeId() {
        return exchangeId;
    }

    public int getStars() {
        return stars;
    }
}

class Database {
    private static final List<Review> reviews = new ArrayList<>();

    public static void saveReview(Review review) {
        reviews.add(review);
        System.out.println("Review salvata");
    }

    public static void updateAverageRating(String exchangeId) {
        int totalStars = 0;
        int count = 0;
        for (Review review : reviews) {
            if (review.getExchangeId().equals(exchangeId)) {
                totalStars += review.getStars();
                count++;
            }
        }
        if (count > 0) {
                double average = (double) totalStars / count;
                System.out.println("Valutazione media aggiornata per lo scambio " + exchangeId + ": " + average);
            } else {
                System.out.println("Nessuna recensione trovata per lo scambio " + exchangeId);
            }
        }
    }
