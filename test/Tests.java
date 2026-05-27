import domain.*;
import resources.*;
import service.*;
import storage.*;
 
import java.util.*;

public class Tests {
 
    private static int passed = 0;
    private static int failed = 0;
 
    public static void main(String[] args) {
        test_student_rating_update();
 
        test_oneway_match_basic();
        test_oneway_excludes_self();
        test_oneway_excludes_inactive_offer();
        test_oneway_score_calculation();
        test_swap_match_reciprocal();
 
        test_propose_ok();
        test_propose_inactive_offer_throws();
        test_accept_then_complete();
        test_invalid_transition_complete_before_accept();
 
        test_review_on_completed_exchange();
        test_review_on_non_completed_throws();
        test_duplicate_review_throws();

        test_formatta_csv_student();
        test_state_addOffer_correct_columns();
 
        System.out.println("\n=== Risultati: " + passed + " passati, " + failed + " falliti ===");
    }

    static void test_student_rating_update() {
        Student s = new Student("S1", "Anna", "4A", "a@b.it", 0.0, 0);

        s.addRating(4);
        s.addRating(2);

        assertEquals("rating avg", 3.0, s.getRatingAvg(), 0.001);
        assertEquals("rating count", 2, s.getRatingCount());
    }
 
    static void test_oneway_match_basic() {
        SkillSwapState state = buildMinimalState();
        MatchingService svc = new MatchingService();
        List<MatchResult> results = svc.findOneWayMatches("S1",
            state.getOffers().values(), state.getRequests().values());
        assertTrue("almeno un match", !results.isEmpty());
        assertEquals("offer nel match", "O1", results.get(0).getOfferId());
    }
 
    static void test_oneway_excludes_self() {
        SkillSwapState state = buildMinimalState();
        MatchingService svc = new MatchingService();
        List<MatchResult> results = svc.findOneWayMatches("S2",
            state.getOffers().values(), state.getRequests().values());
        boolean selfMatch = results.stream()
            .anyMatch(m -> m.getOfferId().equals("O1") && m.getRequestId().equals("R2"));
        assertTrue("nessun self-match su offer propria", !results.stream()
            .anyMatch(m -> {
                Offer o = state.getOffers().get(m.getOfferId());
                Request r = state.getRequests().get(m.getRequestId());
                return o != null && r != null && o.getStudent().getId().equals(r.getStudent().getId());
            }));
    }
 
    static void test_oneway_excludes_inactive_offer() {
        SkillSwapState state = buildMinimalState();
        state.getOffers().get("O1").setActive(false);
        MatchingService svc = new MatchingService();
        List<MatchResult> results = svc.findOneWayMatches("S1",
            state.getOffers().values(), state.getRequests().values());
        assertTrue("offerta inattiva esclusa", results.isEmpty());
    }
 
    static void test_oneway_score_calculation() {
        SkillSwapState state = buildMinimalState();
        MatchingService svc = new MatchingService();
        List<MatchResult> results = svc.findOneWayMatches("S1",
            state.getOffers().values(), state.getRequests().values());
        assertTrue("score corretto", results.get(0).getScore() >= 5);
    }
 
    static void test_swap_match_reciprocal() {
        SkillSwapState state = buildSwapState();
        MatchingService svc = new MatchingService();

        List<MatchResult> results = svc.findSwapMatches("S1",
            state.getOffers().values(), state.getRequests().values());

        assertTrue("swap trovato", !results.isEmpty());
    }
 
    static void test_propose_ok() {
        SkillSwapState state = buildMinimalState();
        ExchangeService svc = new ExchangeService(state);
        Exchange ex = svc.propose("E1", "O1", "R1");
        assertEquals("status PROPOSED", Status.PROPOSED, ex.getStatus());
    }
 
    static void test_propose_inactive_offer_throws() {
        SkillSwapState state = buildMinimalState();
        state.getOffers().get("O1").setActive(false);
        ExchangeService svc = new ExchangeService(state);
        assertThrows("offerta inattiva", IllegalStateException.class,
            () -> svc.propose("E1", "O1", "R1"));
    }
 
    static void test_accept_then_complete() {
        SkillSwapState state = buildMinimalState();
        ExchangeService svc = new ExchangeService(state);
        svc.propose("E1", "O1", "R1");
        svc.accept("E1");
        svc.complete("E1");
        assertEquals("COMPLETED", Status.COMPLETED, state.getExchanges().get("E1").getStatus());
        assertTrue("closedAt impostato", state.getExchanges().get("E1").getClosedAt() != null);
    }
 
    static void test_invalid_transition_complete_before_accept() {
        SkillSwapState state = buildMinimalState();
        ExchangeService svc = new ExchangeService(state);
        svc.propose("E1", "O1", "R1");
        assertThrows("complete senza accept", IllegalStateException.class,
            () -> svc.complete("E1"));
    }

    static void test_review_on_completed_exchange() {
        SkillSwapState state = buildMinimalState();
        ExchangeService exSvc = new ExchangeService(state);
        ReviewService   rvSvc = new ReviewService(state);
        exSvc.propose("E1", "O1", "R1");
        exSvc.accept("E1");
        exSvc.complete("E1");
        Review rv = rvSvc.addReview("V1", "E1", "S2", 5, "ottimo!");
        assertEquals("stars", 5, rv.getStars());
        assertEquals("reviewee è S1", "S1", rv.getReviewee().getId());
    }
 
    static void test_review_on_non_completed_throws() {
        SkillSwapState state = buildMinimalState();
        ExchangeService exSvc = new ExchangeService(state);
        ReviewService   rvSvc = new ReviewService(state);
        exSvc.propose("E1", "O1", "R1");
        assertThrows("review su PROPOSED", IllegalStateException.class,
            () -> rvSvc.addReview("V1", "E1", "S1", 4, "bello"));
    }
 
    static void test_duplicate_review_throws() {
        SkillSwapState state = buildMinimalState();
        ExchangeService exSvc = new ExchangeService(state);
        ReviewService   rvSvc = new ReviewService(state);
        exSvc.propose("E1", "O1", "R1");
        exSvc.accept("E1");
        exSvc.complete("E1");
        rvSvc.addReview("V1", "E1", "S2", 5, "prima");
        assertThrows("duplicato review", IllegalStateException.class,
            () -> rvSvc.addReview("V2", "E1", "S2", 3, "seconda"));
    }
 
    static void test_formatta_csv_student() {
        Student s = new Student("S1", "Anna", "4A", "a@b.it", 4.5, 3);
        String csv = s.formattaCSV();
        assertEquals("CSV studente", "S1;Anna;4A;a@b.it;4.5;3", csv);
    }
 
    static void test_state_addOffer_correct_columns() {
        SkillSwapState state = new SkillSwapState();
        state.addStudent(new Student("S1", "Anna", "4A", "a@b.it", 0, 0));
        state.addSkill(new Skill("K1", "Matematica", Category.SUBJECT));

        state.addOffer("O1;S1;K1;ADVANCED;ottima nota;true");

        Offer o = state.getOffers().get("O1");
        assertTrue("offerta caricata", o != null);
        assertEquals("student corretto", "S1", o.getStudent().getId());
        assertEquals("skill corretta", "K1", o.getSkill().getId());
        assertEquals("level corretto", Level.ADVANCED, o.getLevel());
        assertTrue("active true", o.isActive());
    }
 
    private static SkillSwapState buildMinimalState() {
        SkillSwapState state = new SkillSwapState();
        Student s1 = new Student("S1", "Anna", "4A", "anna@x.it", 0, 0);
        Student s2 = new Student("S2", "Luca", "4A", "luca@x.it", 0, 0);
        Skill k1 = new Skill("K1", "Matematica", Category.SUBJECT);
        Skill k2 = new Skill("K2", "Informatica", Category.SUBJECT);
        state.addStudent(s1); state.addStudent(s2);
        state.addSkill(k1);   state.addSkill(k2);

        state.addOffer(new Offer("O1", s2, k1, Level.ADVANCED, "", true));
        state.addRequest(new Request("R1", s1, k1, Level.BEGINNER, ""));

        state.addOffer(new Offer("O2", s1, k2, Level.INTERMEDIATE, "", true));
        state.addRequest(new Request("R2", s2, k2, Level.BEGINNER, ""));
        return state;
    }
 
    private static SkillSwapState buildSwapState() {
        SkillSwapState state = new SkillSwapState();
        Student s1 = new Student("S1", "Anna", "4A", "anna@x.it", 0, 0);
        Student s2 = new Student("S2", "Luca", "4A", "luca@x.it", 0, 0);
        Skill k1 = new Skill("K1", "Matematica", Category.SUBJECT);
        Skill k2 = new Skill("K2", "Informatica", Category.SUBJECT);
        state.addStudent(s1); state.addStudent(s2);
        state.addSkill(k1);   state.addSkill(k2);
        state.addOffer(new Offer("O1", s2, k1, Level.ADVANCED, "", true));  // S2 offre K1
        state.addOffer(new Offer("O2", s1, k2, Level.ADVANCED, "", true));  // S1 offre K2
        state.addRequest(new Request("R1", s1, k1, Level.BEGINNER, ""));    // S1 cerca K1
        state.addRequest(new Request("R2", s2, k2, Level.BEGINNER, ""));    // S2 cerca K2
        return state;
    }
 
    static void assertTrue(String name, boolean condition) {
        if (condition) { System.out.println("✓ " + name); passed++; }
        else           { System.out.println("✗ " + name + " → FAIL"); failed++; }
    }
    static void assertEquals(String name, Object expected, Object actual) {
        if (Objects.equals(expected, actual)) { System.out.println("✓ " + name); passed++; }
        else { System.out.println("✗ " + name + " → expected=" + expected + " actual=" + actual); failed++; }
    }
    static void assertEquals(String name, double expected, double actual, double delta) {
        if (Math.abs(expected - actual) <= delta) { System.out.println("✓ " + name); passed++; }
        else { System.out.println("✗ " + name + " → expected=" + expected + " actual=" + actual); failed++; }
    }
    static void assertEquals(String name, int expected, int actual) {
        if (expected == actual) { System.out.println("✓ " + name); passed++; }
        else { System.out.println("✗ " + name + " → expected=" + expected + " actual=" + actual); failed++; }
    }
    static <T extends Throwable> void assertThrows(String name, Class<T> expected, Runnable action) {
        try { action.run(); System.out.println("✗ " + name + " → eccezione non lanciata"); failed++; }
        catch (Throwable t) {
            if (expected.isInstance(t)) { System.out.println("✓ " + name); passed++; }
            else { System.out.println("✗ " + name + " → eccezione sbagliata: " + t.getClass().getSimpleName()); failed++; }
        }
    }
}
 