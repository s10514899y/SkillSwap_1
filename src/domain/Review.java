package domain;

public class Review {
    private String id;
    private Exchange exchange;
    private Student reviewer;
    private Student reviewee;
    private int stars;
    private String comment;

    public Review(String id, Exchange exchange, Student reviewer, Student reviewee, int stars, String comment) {
        if (stars < 1 || stars > 5) {
            throw new IllegalArgumentException("Le stelle devono essere tra 1 e 5");
        }

        this.id = id;
        this.exchange = exchange;
        this.reviewer = reviewer;
        this.reviewee = reviewee;
        this.stars = stars;
        this.comment = comment;
    }
}
