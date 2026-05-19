package app;

import domain.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import resources.Category;
import resources.Level;
import resources.NextID;
import storage.FileStorage;
import storage.SkillSwapState;

public class Main {
    private static int nextStudentID = 1;
    private static int nextOfferID = 1;
    private static int nextRequestID = 1;

    private static final Map<String, Runnable> comandi = new HashMap<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static final SkillSwapState state = new SkillSwapState();

    public static void main(String[] args) {
        FileStorage.caricaCSV(state);

        nextStudentID = NextID.initNextStudentID(state.getStudents());
        nextOfferID = NextID.initNextOfferID(state.getOffers());
        nextRequestID = NextID.initNextRequestID(state.getRequests());

        // Skill predefinite
        state.addSkill(new Skill("K1", "Programmazione C", Category.SUBJECT));
        state.addSkill(new Skill("K2", "Matematica", Category.SUBJECT));

        comandi.put("1", Main::createStudent);
        comandi.put("2", Main::addOffer);
        comandi.put("3", Main::addRequest);
        comandi.put("4", Main::printList);

        while (true) {
            System.out.println("\n=== SkillSwap ===");
            System.out.println("1. Crea studente");
            System.out.println("2. Aggiungi offer");
            System.out.println("3. Aggiungi request");
            System.out.println("4. Lista offer e request");
            System.out.println("0. Esci\n");

            System.out.print("> ");
            String input = scanner.nextLine().trim();
            String[] parts = input.split(" ");
            String cmd = parts[0];

            if (cmd.equals('0')) {
                break;
            }

            if (comandi.containsKey(cmd)) {
                comandi.get(cmd).run();
            } else {
                System.out.println("Comando sconosciuto");
            }
        }
        
        FileStorage.salvaCSV(state);
    }

    private static void createStudent() {
        String id = "S" + nextStudentID;

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Classe: ");
        String classe = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        state.addStudent(new Student(id, nome, classe, email, 0.0, 0));
    } 

    private static void addOffer() {
        String id = "O" + nextOfferID;

        System.out.print("Student ID: ");
        String sid = scanner.nextLine();

        System.out.print("Skill ID: ");
        String skid = scanner.nextLine();

        Student stud = state.getStudents().get(sid);
        Skill skill = state.getSkills().get(skid);

        if (stud == null || skill == null) {
            System.out.println("Errore: studente o skill non trovati");
            return;
        }

        Offer o = new Offer(id, stud, skill, Level.BEGINNER, "", true);
        state.addOffer(o);
    }

    private static void addRequest() {
        String id = "R" + nextRequestID;

        System.out.print("Student ID: ");
        String rsid = scanner.nextLine();

        System.out.print("Skill ID: ");
        String rskid = scanner.nextLine();

        Student rstud = state.getStudents().get(rsid);
        Skill rskill = state.getSkills().get(rskid);

        if (rstud == null || rskill == null) {
            System.out.println("Errore: studente o skill non trovati");
            return;
        }

        Request r = new Request(id, rstud, rskill, Level.BEGINNER, "");
        state.addRequest(r);
    }

    private static void printList() {
        System.out.println("\nOFFERS:");
        for (Offer off : state.getOffers().values()) {
            System.out.println(off);
        }

        System.out.println("\nREQUESTS:");
        for (Request req : state.getRequests().values()) {
            System.out.println(req);
        }
    }
}
