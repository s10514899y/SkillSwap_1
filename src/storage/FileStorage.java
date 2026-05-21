package storage;
 
import domain.*;
import java.io.*;
import java.nio.file.*;
 
public final class FileStorage {
 
    private static final Path DATA_DIR      = Path.of("data");
    private static final Path studentsPath  = DATA_DIR.resolve("students.csv");
    private static final Path skillsPath    = DATA_DIR.resolve("skills.csv");
    private static final Path offersPath    = DATA_DIR.resolve("offers.csv");
    private static final Path requestsPath  = DATA_DIR.resolve("requests.csv");
    private static final Path exchangesPath = DATA_DIR.resolve("exchanges.csv");
    private static final Path reviewsPath   = DATA_DIR.resolve("reviews.csv");
 
    public static void caricaCSV(SkillSwapState state) {
        loadFile(studentsPath,  state::addStudent);
        loadFile(skillsPath,    state::addSkill);
        loadFile(offersPath,    state::addOffer);
        loadFile(requestsPath,  state::addRequest);
        loadFile(exchangesPath, state::addExchange);
        loadFile(reviewsPath,   state::addReview);
    }
 
    private static void loadFile(Path path, java.util.function.Consumer<String> parser) {
        if (!Files.exists(path)) return;
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isBlank()) {
                    try {
                        parser.accept(line);
                    } catch (Exception e) {
                        System.err.println("Errore parsing riga [" + path.getFileName() + "]: " + line);
                        System.err.println("  -> " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Impossibile leggere " + path + ": " + e.getMessage());
        }
    }
 
    public static void salvaCSV(SkillSwapState state) {
        try {
            Files.createDirectories(DATA_DIR);
            salvaAtomic(studentsPath,  buildStudents(state));
            salvaAtomic(skillsPath,    buildSkills(state));
            salvaAtomic(offersPath,    buildOffers(state));
            salvaAtomic(requestsPath,  buildRequests(state));
            salvaAtomic(exchangesPath, buildExchanges(state));
            salvaAtomic(reviewsPath,   buildReviews(state));
        } catch (IOException e) {
            System.err.println("Errore durante il salvataggio: " + e.getMessage());
        }
    }
 
    private static void salvaAtomic(Path target, String content) throws IOException {
        Path tmp = target.resolveSibling(target.getFileName() + ".tmp");
        Files.writeString(tmp, content);
        Files.move(tmp, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
    }
 
    private static String buildStudents(SkillSwapState state) {
        StringBuilder sb = new StringBuilder("student_id;name;class;email;rating_avg;rating_count\n");
        for (Student s : state.getStudents().values())
            sb.append(s.formattaCSV()).append("\n");
        return sb.toString();
    }
 
    private static String buildSkills(SkillSwapState state) {
        StringBuilder sb = new StringBuilder("skill_id;name;category\n");
        for (Skill k : state.getSkills().values())
            sb.append(k.formattaCSV()).append("\n");
        return sb.toString();
    }
 
    private static String buildOffers(SkillSwapState state) {
        StringBuilder sb = new StringBuilder("offer_id;student_id;skill_id;level;note;active\n");
        for (Offer o : state.getOffers().values())
            sb.append(o.formattaCSV()).append("\n");
        return sb.toString();
    }
 
    private static String buildRequests(SkillSwapState state) {
        StringBuilder sb = new StringBuilder("request_id;student_id;skill_id;min_level;note\n");
        for (Request r : state.getRequests().values())
            sb.append(r.formattaCSV()).append("\n");
        return sb.toString();
    }
 
    private static String buildExchanges(SkillSwapState state) {
        StringBuilder sb = new StringBuilder("exchange_id;offer_id;request_id;status;created_at;closed_at\n");
        for (Exchange e : state.getExchanges().values())
            sb.append(e.formattaCSV()).append("\n");
        return sb.toString();
    }
 
    private static String buildReviews(SkillSwapState state) {
        StringBuilder sb = new StringBuilder("review_id;exchange_id;reviewer_student_id;reviewee_student_id;stars;comment;created_at\n");
        for (Review v : state.getReviews().values())
            sb.append(v.formattaCSV()).append("\n");
        return sb.toString();
    }
}
