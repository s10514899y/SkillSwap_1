package domain;

import java.time.LocalDateTime;

public class Review {
    private String id;
    private Exchange exchange;
    private Student reviewer;
    private Student reviewee;
    private int stars;
    private String comment;
    private LocalDateTime createdAt;

    public Review(String id, Exchange exchange, Student reviewer, Student reviewee, int stars, String comment, LocalDateTime createdAt) {
        if (stars < 1 || stars > 5) {
            throw new IllegalArgumentException("Le stelle devono essere tra 1 e 5");
        }

        this.id = id;
        this.exchange = exchange;
        this.reviewer = reviewer;
        this.reviewee = reviewee;
        this.stars = stars;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public String formattaCSV() {
        return new StringBuilder()
            .append(id).append(";")
            .append(exchange.getId()).append(";")
            .append(reviewer.getId()).append(";")
            .append(reviewee.getId()).append(";")
            .append(stars).append(";")
            .append(comment).append(";")
            .append(createdAt)
            .toString();
    }

    public String getId() { return id; }
    public Exchange getExchange() { return exchange; }
    public Student getReviewer() { return reviewer; }
    public Student getReviewee() { return reviewee; }
    public int getStars() { return stars; }
    public String getComment() { return comment; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    @Override
    public String toString() {
        return id + " - " + reviewer.getName() + " -> " + reviewee.getName() + " (" + stars + " stelle)";
    }
}
