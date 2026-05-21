package storage;

import domain.*;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.*;
import resources.Category;
import resources.Level;
import resources.ParserUtils;
import resources.Status;

public class SkillSwapState {
    private Map<String, Student> students = new HashMap<>();
    private Map<String, Skill> skills = new HashMap<>();
    private Map<String, Offer> offers = new HashMap<>();
    private Map<String, Request> requests = new HashMap<>();
    private Map<String, Exchange> exchanges = new HashMap<>();
    private Map<String, Review> reviews = new HashMap<>();

    public void addStudent(String st) throws NumberFormatException {
        if (st.startsWith("student_id")) return;
        String[] dati = st.split(";", -1);
        if (dati.length < 6) { System.out.println("Student: dati insufficienti: " + st); return; }
        double avg   = ParserUtils.parseAvgRating(dati[4]);
        int    count = ParserUtils.parseRatingCount(dati[5]);
        Student s = new Student(dati[0], dati[1], dati[2], dati[3], avg, count);
        students.put(s.getId(), s);
    }

    public void addStudent(Student s) { students.put(s.getId(), s); }

    public void addSkill(String st) throws IllegalArgumentException {
        if (st.startsWith("skill_id")) return;
        String[] dati = st.split(";", -1);
        if (dati.length < 3) { System.out.println("Skill: dati insufficienti: " + st); return; }
        Category category = ParserUtils.parseCategory(dati[2]);
        Skill sk = new Skill(dati[0], dati[1], category);
        skills.put(sk.getId(), sk);
    }

    public void addSkill(Skill k) { skills.put(k.getId(), k); }

    public void addOffer(String st) throws IllegalArgumentException {
        if (st.startsWith("offer_id")) return;
        String[] dati = st.split(";", -1);
        if (dati.length < 6) { System.out.println("Offer: dati insufficienti: " + st); return; }
 
        Student s  = students.get(dati[1]);
        Skill   sk = skills.get(dati[2]);
        if (s == null || sk == null) { System.out.println("Offer: student o skill non trovato: " + st); return; }
 
        Level   level  = ParserUtils.parseLevel(dati[3]);
        String  note   = dati[4];
        boolean active = ParserUtils.parseActive(dati[5]);
 
        Offer o = new Offer(dati[0], s, sk, level, note, active);
        offers.put(o.getId(), o);
    }

    public void addOffer(Offer o) { offers.put(o.getId(), o); }

    public void addRequest(String st) {
        if (st.startsWith("request_id")) return;
        String[] dati = st.split(";", -1);
        if (dati.length < 5) { System.out.println("Request: dati insufficienti: " + st); return; }
 
        Student s  = students.get(dati[1]);
        Skill   sk = skills.get(dati[2]);
        if (s == null || sk == null) { System.out.println("Request: student o skill non trovato: " + st); return; }
 
        Level level = ParserUtils.parseLevel(dati[3]);
        Request r = new Request(dati[0], s, sk, level, dati[4]);
        requests.put(r.getId(), r);
    }

    public void addRequest(Request r) { requests.put(r.getId(), r); }

    public void addExchange(String st) throws IllegalArgumentException, DateTimeException {
        if (st.startsWith("exchange_id")) return;
        String[] dati = st.split(";", -1);
        if (dati.length < 6) { System.out.println("Exchange: dati insufficienti: " + st); return; }
 
        Offer   o = offers.get(dati[1]);
        Request r = requests.get(dati[2]);
        if (o == null || r == null) { System.out.println("Exchange: offer o request non trovato: " + st); return; }
 
        Status        status    = ParserUtils.parseStatus(dati[3]);
        LocalDateTime createdAt = ParserUtils.parseLocalDate(dati[4]);
        LocalDateTime closedAt  = (dati.length > 5 && !dati[5].isBlank())
                                  ? ParserUtils.parseLocalDate(dati[5]) : null;
 
        Exchange ex = new Exchange(dati[0], o, r, createdAt);
        ex.setStatus(status);
        ex.setClosedAt(closedAt);
        exchanges.put(ex.getId(), ex);
    }

    public void addExchange(Exchange e) { exchanges.put(e.getId(), e); }

    public void addReview(String st) throws NumberFormatException, DateTimeException {
        if (st.startsWith("review_id")) return;
        String[] dati = st.split(";", -1);
        if (dati.length < 7) { System.out.println("Review: dati insufficienti: " + st); return; }
 
        Exchange ex = exchanges.get(dati[1]);
        Student  s1 = students.get(dati[2]);
        Student  s2 = students.get(dati[3]);
        if (ex == null || s1 == null || s2 == null) { System.out.println("Review: entità non trovata: " + st); return; }
 
        int           stars     = ParserUtils.parseStars(dati[4]);
        LocalDateTime createdAt = ParserUtils.parseLocalDate(dati[6]);
 
        Review rv = new Review(dati[0], ex, s1, s2, stars, dati[5], createdAt);
        reviews.put(rv.getId(), rv);
    }

    public void addReview(Review v) { reviews.put(v.getId(), v); }

    public Map<String, Student> getStudents()   { return students; }
    public Map<String, Skill> getSkills()       { return skills; }
    public Map<String, Offer> getOffers()       { return offers; }
    public Map<String, Request> getRequests()   { return requests; }
    public Map<String, Exchange> getExchanges() { return exchanges; }
    public Map<String, Review> getReviews()     { return reviews; }
}
