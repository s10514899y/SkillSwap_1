package storage;

import domain.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.DateTimeException;

public final class FileStorage {
    private static final Path studentsPath = Path.of("data", "students.csv");
    private static final Path skillsPath = Path.of("data", "skills.csv");
    private static final Path offersPath = Path.of("data", "offers.csv");
    private static final Path requestsPath = Path.of("data", "requests.csv");
    private static final Path exchangesPath = Path.of("data", "exchanges.csv");
    private static final Path reviewsPath = Path.of("data", "reviews.csv");

    public static void caricaCSV(SkillSwapState state) {
        try (
            BufferedReader brStudents = Files.newBufferedReader(studentsPath);
            BufferedReader brSkills = Files.newBufferedReader(skillsPath);
            BufferedReader brOffers = Files.newBufferedReader(offersPath);
            BufferedReader brRequests = Files.newBufferedReader(requestsPath);
            BufferedReader brExchanges = Files.newBufferedReader(exchangesPath);
            BufferedReader brReviews = Files.newBufferedReader(reviewsPath)
        ) {
            String riga;
            while((riga = brStudents.readLine()) != null) {
                try {
                    state.addStudent(riga);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                } 
            }

            while((riga = brSkills.readLine()) != null) {
                try {
                    state.addSkill(riga);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }

            while((riga = brOffers.readLine()) != null) {
                try {
                    state.addOffer(riga);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }

            while((riga = brRequests.readLine()) != null) {
                try {
                    state.addRequest(riga);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }

            while((riga = brExchanges.readLine()) != null) {
                try {
                    state.addExchanges(riga);
                } catch(IllegalArgumentException | DateTimeException e) {
                    e.printStackTrace();
                }
            }

            while((riga = brReviews.readLine()) != null) {
                try {
                    state.addReview(riga);
                } catch(NumberFormatException | DateTimeException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void salvaCSV(SkillSwapState state) {
        try (BufferedWriter sbw = Files.newBufferedWriter(studentsPath);
            BufferedWriter kbw = Files.newBufferedWriter(skillsPath);
            BufferedWriter obw = Files.newBufferedWriter(offersPath);
            BufferedWriter rbw = Files.newBufferedWriter(requestsPath);
            BufferedWriter ebw = Files.newBufferedWriter(exchangesPath);
            BufferedWriter vbw = Files.newBufferedWriter(reviewsPath)) {
            
            sbw.append("student_id;name;class;email;rating_avg;rating_count");
            for (Student s : state.getStudents().values()) {
                sbw.newLine();
                sbw.append(s.formattaCSV());
            }

            kbw.append("skill_id;name;category");
            for (Skill k : state.getSkills().values()) {
                kbw.newLine();
                kbw.append(k.formattaCSV());
            }

            obw.append("offer_id;student_id;skill_id;level;note;active");
            for (Offer o : state.getOffers().values()) {
                obw.newLine();
                obw.append(o.formattaCSV());
            }

            rbw.append("request_id;student_id;skill_id;min_level;note");
            for (Request r : state.getRequests().values()) {
                rbw.newLine();
                rbw.append(r.formattaCSV());
            }

            ebw.append("exchange_id;offer_id;request_id;status;created_at;closed_at");
            for (Exchange e : state.getExchanges().values()) {
                ebw.newLine();
                ebw.append(e.formattaCSV());
            }

            vbw.append("review_id;exchange_id;reviewer_student_id;reviewee_student_id;stars;comment;created_at");
            for (Review v : state.getReviews().values()) {
                vbw.newLine();
                vbw.append(v.formattaCSV());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
