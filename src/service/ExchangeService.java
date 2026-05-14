package service;

import resources.Status;

public class ExchangeService {
    private String offer;
    private String request;
    private Status status;


    public void Propose() {
        if (offer == null || request == null) {
            throw new IllegalArgumentException("Offer and Request devono esseer fornite");
        } else if (offer.equals(request)) {
            throw new IllegalArgumentException("Offer and Request non possono essere uguali");
        } else if (offer.isEmpty() || request.isEmpty()) {
            throw new IllegalArgumentException("Offer and Request non possono essere vuote");
        } else {
            status = Status.PROPOSED;
        }
    }

    public void accept() {
        if (status != Status.PROPOSED) {
            throw new IllegalStateException("Exchange deve essere proposto prima di venire accettato");
        }
        status = Status.ACCEPTED;
    }

    public void complete() {
        if (status != Status.ACCEPTED) {
            throw new IllegalStateException("Exchange deve essere accettato prima di venire completato");
        }
        status = Status.COMPLETED;
    }

    public void cancel() {
        if (status == null) {
            throw new IllegalStateException("Non può essere cancellato un exchange non accettato");
        }
        status = Status.CANCELLED;
    }
}