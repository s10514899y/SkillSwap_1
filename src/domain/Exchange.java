package domain;

import java.time.LocalDateTime;
import resources.Status;

public class Exchange {
    private String id;
    private Offer offer;
    private Request request;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime closedAt;

    public Exchange(String id, Offer offer, Request request, LocalDateTime createdAt) {
        this.id = id;
        this.offer = offer;
        this.request = request;
        this.status = Status.PROPOSED;
        this.createdAt = createdAt;
        this.closedAt = null;
    }

    public String formattaCSV() {
        return new StringBuilder()
            .append(id).append(";")
            .append(offer.getId()).append(";")
            .append(request.getId()).append(";")
            .append(status).append(";")
            .append(createdAt).append(";")
            .append(closedAt != null ? closedAt : "")
            .toString();
    }

    public String getId()                 { return id; }
    public Offer getOffer()               { return offer; }
    public Request getRequest()           { return request; }
    public Status getStatus()             { return status; }
    public LocalDateTime getCreatedAt()   { return createdAt; }
    public LocalDateTime getClosedAt()    { return closedAt; }
    public void setStatus(Status status)  { this.status = status; }
    public void setClosedAt(LocalDateTime closedAt) { this.closedAt = closedAt; }

    @Override
    public String toString() {
        return id + " [" + status + "] " + offer.getId() + " <-> " + request.getId();
    }
}
