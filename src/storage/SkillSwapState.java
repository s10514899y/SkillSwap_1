package storage;

import domain.*;
import java.time.DateTimeException;
import java.util.*;
import resources.Category;
import resources.Level;
import resources.ParserUtils;

public class SkillSwapState {
    private Map<String, Student> students = new HashMap<>();
    private Map<String, Skill> skills = new HashMap<>();
    private Map<String, Offer> offers = new HashMap<>();
    private Map<String, Request> requests = new HashMap<>();
    private Map<String, Exchange> exchanges = new HashMap<>();
    private Map<String, Review> reviews = new HashMap<>();

    public void addStudent(String st) throws NumberFormatException {
        String[] dati = st.split(";");
        if (dati.length == 6) {
            double avgRating = ParserUtils.parseAvgRating(dati[4]);
            int ratingCount = ParserUtils.parseRatingcount(dati[5]);
   
            Student s = new Student(dati[0], dati[1], dati[2], dati[3], avgRating, ratingCount);
            students.put(s.getId(), s);
        } else {
            System.out.println("Dati insufficienti: " + st);
        }
    }

    public void addStudent(Student s) {
        students.put(s.getId(), s);
    }

    public void addSkill(String st) throws IllegalArgumentException {
        String[] dati = st.split(";");
        if (dati.length == 3) {
            Category category = ParserUtils.parseCategory(dati[2]);
        
            Skill sk = new Skill(dati[0], dati[1], category.name());
            skills.put(sk.getId(), sk);
        } else {
            System.out.println("Dati insufficienti: " + st);
        }
    }

    public void addSkill(Skill k) {
        skills.put(k.getId(), k);
    }

    public void addOffer(String st) throws IllegalArgumentException {
        String[] dati = st.split(";");
        if (dati.length == 6) {
            Level level = ParserUtils.parseLevel(dati[2]);
            boolean active = ParserUtils.parseActive(dati[3]);
            Student s = students.get(dati[4]);
            Skill sk = skills.get(dati[5]);

            if(s == null || sk == null) {
                System.out.println("Student o skill non trovato: " + st);
                return;
            }
            
            Offer o = new Offer(dati[0], s, sk, level, dati[1]);
            offers.put(o.getId(), o);
        } else {
            System.out.println("Dati insufficienti: " + st);
        }
    }

    public void addOffer(Offer o) {
        offers.put(o.getId(), o);
    }

    public void addRequest(String st) throws IllegalArgumentException {
        String[] dati = st.split(";");
        if (dati.length == 5) {
            Student s = students.get(dati[1]);
            Skill sk = skills.get(dati[2]);
            Level level = ParserUtils.parseLevel(dati[3]);

            if(s == null || sk == null) {
                System.out.println("Student o skill non trovato: " + st);
                return;
            }
            
            Request r = new Request(dati[0], s, sk, level.name(), dati[4]);
            requests.put(r.getId(), r);
        } else {
            System.out.println("Dati insufficienti: " + st);
        }
    }

    public void addRequest(Request r) {
        requests.put(r.getId(), r);
    }

    public void addExchanges(String st) throws IllegalArgumentException, DateTimeException {
        String[] dati = st.split(";");
        if (dati.length == 4) {
            Offer o = offers.get(dati[1]);
            Request r = requests.get(dati[2]);

            if(o == null || r == null) {
                System.out.println("Offer o request non trovato: " + st);
                return;
            }

            Exchange ex = new Exchange(dati[0], o, r);
            exchanges.put(ex.getId(), ex);
        } else {
            System.out.println("Dati insufficienti: " + st);
        }
    }

    public void addExchange(Exchange e) {
        exchanges.put(e.getId(), e);
    }

    public void addReview(String st) throws NumberFormatException, DateTimeException {
        String[] dati = st.split(";");
        if (dati.length >= 6) {
            Exchange ex = exchanges.get(dati[1]);
            Student s1 = students.get(dati[2]);
            Student s2 = students.get(dati[3]);

            if(ex == null || s1 == null || s2 == null) {
                System.out.println("Exchange o student non trovato: " + st);
                return;
            }

            int stars = (int) ParserUtils.parseStars(dati[4]);
            
            Review rv = new Review(dati[0], ex, s1, s2, stars, dati[5]); 
            reviews.put(rv.getId(), rv);
        } else {
            System.out.println("Dati insufficienti: " + st);
        }
    }

    public void addReview(Review v) {
        reviews.put(v.getId(), v);
    }

    public Map<String, Student> getStudents() {
        return students;
    }

    public Map<String, Skill> getSkills() {
        return skills;
    }

    public Map<String, Offer> getOffers() {
        return offers;
    }

    public Map<String, Request> getRequests() {
        return requests;
    }

    public Map<String, Exchange> getExchanges() {
        return exchanges;
    }

    public Map<String, Review> getReviews() {
        return reviews;
    }
}
