package service;
 
import domain.Exchange;
import domain.Offer;
import domain.Request;
import java.time.LocalDateTime;
import resources.Status;
import storage.SkillSwapState;

public class ExchangeService {
 
    private final SkillSwapState state;
 
    public ExchangeService(SkillSwapState state) {
        this.state = state;
    }

    public Exchange propose(String exchangeId, String offerId, String requestId) {
        Offer offer = state.getOffers().get(offerId);
        if (offer == null)
            throw new IllegalArgumentException("Offerta non trovata: " + offerId);
        if (!offer.isActive())
            throw new IllegalStateException("L'offerta " + offerId + " non è attiva");
 
        Request request = state.getRequests().get(requestId);
        if (request == null)
            throw new IllegalArgumentException("Richiesta non trovata: " + requestId);
 
        if (offer.getStudent().getId().equals(request.getStudent().getId()))
            throw new IllegalStateException("Uno studente non può fare match con sé stesso");
 
        Exchange ex = new Exchange(exchangeId, offer, request, LocalDateTime.now());
        state.addExchange(ex);
        return ex;
    }
 
    public void accept(String exchangeId) {
        Exchange ex = getExchange(exchangeId);
        if (ex.getStatus() != Status.PROPOSED)
            throw new IllegalStateException("L'exchange deve essere PROPOSED per essere accettato (attuale: " + ex.getStatus() + ")");
        ex.setStatus(Status.ACCEPTED);
    }
 
    public void complete(String exchangeId) {
        Exchange ex = getExchange(exchangeId);
        if (ex.getStatus() != Status.ACCEPTED)
            throw new IllegalStateException("L'exchange deve essere ACCEPTED per essere completato (attuale: " + ex.getStatus() + ")");
        ex.setStatus(Status.COMPLETED);
        ex.setClosedAt(LocalDateTime.now());
        ex.getOffer().setActive(false);
    }
 
    public void cancel(String exchangeId) {
        Exchange ex = getExchange(exchangeId);
        if (ex.getStatus() == Status.COMPLETED)
            throw new IllegalStateException("Un exchange COMPLETED non può essere cancellato");
        ex.setStatus(Status.CANCELLED);
        ex.setClosedAt(LocalDateTime.now());
    }
 
    private Exchange getExchange(String exchangeId) {
        Exchange ex = state.getExchanges().get(exchangeId);
        if (ex == null)
            throw new IllegalArgumentException("Exchange non trovato: " + exchangeId);
        return ex;
    }
}
