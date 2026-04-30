package domain;

public class Exchange {
    private String id;
    private Offer offer;
    private Request request;
    private String status;

    public Exchange(String id, Offer offer, Request request) {
        this.id = id;
        this.offer = offer;
        this.request = request;
        this.status = "PROPOSED";
    }
}

