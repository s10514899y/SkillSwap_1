package storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.DateTimeException;

public final class FileStorage {
    public static void aggiungiDaCSV(SkillSwapState state) {
        Path studentsPath = Path.of("data", "students.csv");
        Path skillsPath = Path.of("data", "skills.csv");
        Path offersPath = Path.of("data", "offers.csv");
        Path requestsPath = Path.of("data", "requests.csv");
        Path exchangesPath = Path.of("data", "exchanges.csv");
        Path reviewsPath = Path.of("data", "reviews.csv");

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
}
