package app;

import domain.*;
import java.util.*;
import resources.*;
import service.*;
import storage.*;

public class Main {

    private static int nextStudentID;
    private static int nextSkillID;
    private static int nextOfferID;
    private static int nextRequestID;
    private static int nextExchangeID;
    private static int nextReviewID;

    private static final Scanner            scanner = new Scanner(System.in);
    private static final SkillSwapState     state   = new SkillSwapState();
    private static final MatchingService    matchSvc    = new MatchingService();
    private static final ExchangeService    exchangeSvc = new ExchangeService(state);
    private static final ReviewService      reviewSvc   = new ReviewService(state);
    private static final ConsoleReportPrinter printer   = new ConsoleReportPrinter();

    public static void main(String[] args) {
        FileStorage.caricaCSV(state);

        nextStudentID  = NextID.initNextStudentID(state.getStudents());
        nextSkillID    = NextID.initNextSkillID(state.getSkills());
        nextOfferID    = NextID.initNextOfferID(state.getOffers());
        nextRequestID  = NextID.initNextRequestID(state.getRequests());
        nextExchangeID = NextID.initNextExchangeID(state.getExchanges());
        nextReviewID   = NextID.initNextReviewID(state.getReviews());

        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            switch (input) {
                case "0"  -> running = false;
                case "1"  -> createStudent();
                case "2"  -> addSkill();
                case "3"  -> addOffer();
                case "4"  -> addRequest();
                case "5"  -> printList();
                case "6"  -> findMatches();
                case "7"  -> proposeExchange();
                case "8"  -> changeExchangeStatus();
                case "9"  -> addReview();
                case "10" -> showLeaderboard();
                case "11" -> showStudentProfile();
                default   -> System.out.println("Comando sconosciuto: " + input);
            }
        }

        FileStorage.salvaCSV(state);
        System.out.println("Dati salvati. Arrivederci!");
    }

    private static void printMenu() {
        System.out.println("\n=== SkillSwap School ===");
        System.out.println(" 1.  Crea studente");
        System.out.println(" 2.  Aggiungi skill");
        System.out.println(" 3.  Aggiungi offerta");
        System.out.println(" 4.  Aggiungi richiesta");
        System.out.println(" 5.  Lista offerte e richieste");
        System.out.println(" 6.  Trova match");
        System.out.println(" 7.  Proponi exchange");
        System.out.println(" 8.  Cambia stato exchange");
        System.out.println(" 9.  Aggiungi recensione");
        System.out.println("10.  Leaderboard");
        System.out.println("11.  Profilo studente");
        System.out.println(" 0.  Esci");
    }

    private static void createStudent() {
        String id = "S" + nextStudentID;
        System.out.print("Nome: ");    String nome    = scanner.nextLine();
        System.out.print("Classe: "); String classe  = scanner.nextLine();
        System.out.print("Email: ");  String email   = scanner.nextLine();
        try {
            state.addStudent(new Student(id, nome, classe, email, 0.0, 0));
            nextStudentID++;
            System.out.println("Studente creato: " + id);
        } catch (IllegalArgumentException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    private static void addSkill() {
        String id = "K" + nextSkillID;
        System.out.print("Nome skill: "); String nome = scanner.nextLine();
        System.out.println("Categoria (" +
            java.util.Arrays.stream(Category.values())
                .map(Enum::name).reduce((a, b) -> a + "/" + b).orElse("") + "): ");
        System.out.print("> "); String cat = scanner.nextLine();
        try {
            Category categoria = Category.valueOf(cat.toUpperCase());
            state.addSkill(new Skill(id, nome, categoria));
            nextSkillID++;
            System.out.println("Skill creata: " + id + " - " + nome);
        } catch (IllegalArgumentException e) {
            System.out.println("Categoria non valida: " + cat);
        }
    }

    private static void addOffer() {
        String id = "O" + nextOfferID;
        System.out.print("Student ID: ");  String sid  = scanner.nextLine();
        System.out.print("Skill ID: ");    String skid = scanner.nextLine();
        System.out.print("Livello (BEGINNER/INTERMEDIATE/ADVANCED): "); String lvl = scanner.nextLine();
        System.out.print("Nota: ");        String note = scanner.nextLine();
 
        Student stud = state.getStudents().get(sid);
        Skill   skill = state.getSkills().get(skid);
        if (stud == null || skill == null) { System.out.println("Studente o skill non trovati."); return; }
 
        try {
            Level level = Level.valueOf(lvl.toUpperCase());
            state.addOffer(new Offer(id, stud, skill, level, note, true));
            nextOfferID++;
            System.out.println("Offerta creata: " + id);
        } catch (IllegalArgumentException e) {
            System.out.println("Livello non valido: " + lvl);
        }
    }

    private static void addRequest() {
        String id = "R" + nextRequestID;
        System.out.print("Student ID: ");  String sid  = scanner.nextLine();
        System.out.print("Skill ID: ");    String skid = scanner.nextLine();
        System.out.print("Livello minimo (BEGINNER/INTERMEDIATE/ADVANCED): "); String lvl = scanner.nextLine();
        System.out.print("Nota: ");        String note = scanner.nextLine();

        Student stud = state.getStudents().get(sid);
        Skill   skill = state.getSkills().get(skid);
        if (stud == null || skill == null) { System.out.println("Studente o skill non trovati."); return; }

        try {
            Level level = Level.valueOf(lvl.toUpperCase());
            state.addRequest(new Request(id, stud, skill, level, note));
            nextRequestID++;
            System.out.println("Richiesta creata: " + id);
        } catch (IllegalArgumentException e) {
            System.out.println("Livello non valido: " + lvl);
        }
    }

    private static void printList() {
        System.out.println("\n--- OFFERTE ---");
        state.getOffers().values().forEach(System.out::println);
        System.out.println("\n--- RICHIESTE ---");
        state.getRequests().values().forEach(System.out::println);
        System.out.println("\n--- SKILL ---");
        state.getSkills().values().forEach(System.out::println);
    }

    private static void findMatches() {
        System.out.print("Student ID: "); String sid = scanner.nextLine();
        System.out.println("1. One-way  2. Swap reciproco");
        System.out.print("> "); String tipo = scanner.nextLine();
 
        List<MatchResult> results = tipo.equals("2")
            ? matchSvc.findSwapMatches(sid, state.getOffers().values(), state.getRequests().values())
            : matchSvc.findOneWayMatches(sid, state.getOffers().values(), state.getRequests().values());
 
        System.out.print(printer.printMatches(sid, results));
    }

    private static void proposeExchange() {
        String id = "E" + nextExchangeID;
        System.out.print("Offer ID: ");   String oid = scanner.nextLine();
        System.out.print("Request ID: "); String rid = scanner.nextLine();
        try {
            Exchange ex = exchangeSvc.propose(id, oid, rid);
            nextExchangeID++;
            System.out.println("Exchange proposto: " + ex.getId());
        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    private static void changeExchangeStatus() {
        System.out.print("Exchange ID: "); String eid = scanner.nextLine();
        System.out.println("1. Accept  2. Complete  3. Cancel");
        System.out.print("> "); String op = scanner.nextLine();
        try {
            switch (op) {
                case "1" -> exchangeSvc.accept(eid);
                case "2" -> exchangeSvc.complete(eid);
                case "3" -> exchangeSvc.cancel(eid);
                default  -> { System.out.println("Opzione non valida."); return; }
            }
            System.out.print(printer.printExchangeDetails(state.getExchanges().get(eid)));
        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    private static void addReview() {
        String id = "V" + nextReviewID;
        System.out.print("Exchange ID: ");  String eid = scanner.nextLine();
        System.out.print("Reviewer ID: ");  String rid = scanner.nextLine();
        System.out.print("Stelle (1-5): "); String stars = scanner.nextLine();
        System.out.print("Commento: ");     String comment = scanner.nextLine();
        try {
            Review rv = reviewSvc.addReview(id, eid, rid, Integer.parseInt(stars), comment);
            nextReviewID++;
            System.out.println("Recensione aggiunta: " + rv.getId());
        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    private static void showLeaderboard() {
        System.out.print(printer.printLeaderboard(state.getStudents().values()));
    }

    private static void showStudentProfile() {
        System.out.print("Student ID: "); String sid = scanner.nextLine();
        Student s = state.getStudents().get(sid);
        if (s == null) { System.out.println("Studente non trovato."); return; }
        System.out.print(printer.printStudentProfile(s, state));
    }
}