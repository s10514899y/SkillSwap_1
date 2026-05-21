package domain;

public class MatchResult {
    private final String offerId;
    private final String requestId;
    private final int score;
    private final String reason;
 
    public MatchResult(String offerId, String requestId, int score, String reason) {
        this.offerId = offerId;
        this.requestId = requestId;
        this.score = score;
        this.reason = reason;
    }
 
    public String getOfferId()   { return offerId; }
    public String getRequestId() { return requestId; }
    public int getScore()        { return score; }
    public String getReason()    { return reason; }
 
    @Override
    public String toString() {
        return "Match[offer=" + offerId + ", request=" + requestId +
               ", score=" + score + ", reason=" + reason + "]";
    }
}
 