package service;
 
import domain.Student;
import domain.MatchResult;
import domain.Exchange;
import java.util.*;
import storage.SkillSwapState;
 
public class ConsoleReportPrinter {
 
    private static final String SEP   = "─".repeat(50);
    private static final String SEP_S = "·".repeat(50);

    public String printStudentProfile(Student s, SkillSwapState state) {
        StringBuilder sb = new StringBuilder();
        sb.append(SEP).append("\n");
        sb.append("  STUDENTE: ").append(s.getName()).append("\n");
        sb.append(SEP).append("\n");
        sb.append("  ID      : ").append(s.getId()).append("\n");
        sb.append("  Classe  : ").append(s.getSchoolClass()).append("\n");
        sb.append("  Email   : ").append(s.getEmail()).append("\n");
        sb.append(String.format("  Rating  : %.1f / 5.0  (%d voti)%n",
                  s.getRatingAvg(), s.getRatingCount()));
        sb.append(SEP_S).append("\n");
 
        sb.append("  Offerte:\n");
        state.getOffers().values().stream()
            .filter(o -> o.getStudent().getId().equals(s.getId()))
            .forEach(o -> sb.append("    ").append(o).append("\n"));
 
        sb.append("  Richieste:\n");
        state.getRequests().values().stream()
            .filter(r -> r.getStudent().getId().equals(s.getId()))
            .forEach(r -> sb.append("    ").append(r).append("\n"));
 
        sb.append(SEP).append("\n");
        return sb.toString();
    }
 
    public String printMatches(String studentId, List<MatchResult> matches) {
        StringBuilder sb = new StringBuilder();
        sb.append(SEP).append("\n");
        sb.append("  MATCH per studente: ").append(studentId).append("\n");
        sb.append(SEP).append("\n");
        if (matches.isEmpty()) {
            sb.append("  Nessun match trovato.\n");
        } else {
            int i = 1;
            for (MatchResult m : matches) {
                sb.append(String.format("  %2d. Offer: %-5s | Request: %-5s | Score: %d | %s%n",
                          i++, m.getOfferId(), m.getRequestId(), m.getScore(), m.getReason()));
            }
        }
        sb.append(SEP).append("\n");
        return sb.toString();
    }
 
    public String printExchangeDetails(Exchange ex) {
        StringBuilder sb = new StringBuilder();
        sb.append(SEP).append("\n");
        sb.append("  EXCHANGE: ").append(ex.getId()).append("\n");
        sb.append(SEP).append("\n");
        sb.append("  Stato     : ").append(ex.getStatus()).append("\n");
        sb.append("  Offerta   : ").append(ex.getOffer()).append("\n");
        sb.append("  Richiesta : ").append(ex.getRequest()).append("\n");
        sb.append("  Creato    : ").append(ex.getCreatedAt()).append("\n");
        if (ex.getClosedAt() != null)
            sb.append("  Chiuso    : ").append(ex.getClosedAt()).append("\n");
        sb.append(SEP).append("\n");
        return sb.toString();
    }

    public String printLeaderboard(Collection<Student> students) {
        StringBuilder sb = new StringBuilder();
        sb.append(SEP).append("\n");
        sb.append("  LEADERBOARD – Top studenti per rating\n");
        sb.append(SEP).append("\n");
 
        students.stream()
            .filter(s -> s.getRatingCount() > 0)
            .sorted(Comparator.comparingDouble(Student::getRatingAvg).reversed())
            .forEach(s -> sb.append(String.format("  %-20s  %.1f ★  (%d voti)%n",
                      s.getName(), s.getRatingAvg(), s.getRatingCount())));
 
        sb.append(SEP).append("\n");
        return sb.toString();
    }
}