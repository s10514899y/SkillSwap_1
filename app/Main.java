package SkillSwap.app;

import SkillSwap.domain.Level;
import SkillSwap.domain.Offer;
import SkillSwap.domain.Request;
import SkillSwap.domain.Skill;
import SkillSwap.domain.Student;
import SkillSwap.state.SkillSwapState;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SkillSwapState state = new SkillSwapState();

        // Skill predefinite
        state.skills.put("K1", new Skill("K1", "Programmazione C", "SUBJECT"));
        state.skills.put("K2", new Skill("K2", "Matematica", "SUBJECT"));

        while (true) {
            System.out.println("\n=== SkillSwap ===");
            System.out.println("1. Crea studente");
            System.out.println("2. Aggiungi offer");
            System.out.println("3. Aggiungi request");
            System.out.println("4. Lista offer e request");
            System.out.println("0. Esci");

            int scelta = scanner.nextInt();
            scanner.nextLine();

            switch (scelta) {
                case 1:
                    System.out.print("ID: ");
                    String id = scanner.nextLine();

                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();

                    System.out.print("Classe: ");
                    String classe = scanner.nextLine();

                    System.out.print("Email: ");
                    String email = scanner.nextLine();

                    Student s = new Student(id, nome, classe, email);
                    state.students.put(id, s);
                    break;

                case 2:
                    System.out.print("ID Offer: ");
                    String oid = scanner.nextLine();

                    System.out.print("Student ID: ");
                    String sid = scanner.nextLine();

                    System.out.print("Skill ID: ");
                    String skid = scanner.nextLine();

                    Student stud = state.students.get(sid);
                    Skill skill = state.skills.get(skid);

                    if (stud == null || skill == null) {
                        System.out.println("Errore: studente o skill non trovati");
                        break;
                    }

                    Offer o = new Offer(oid, stud, skill, Level.BEGINNER, "");
                    state.offers.put(oid, o);
                    break;

                case 3:
                    System.out.print("ID Request: ");
                    String rid = scanner.nextLine();

                    System.out.print("Student ID: ");
                    String rsid = scanner.nextLine();

                    System.out.print("Skill ID: ");
                    String rskid = scanner.nextLine();

                    Student rstud = state.students.get(rsid);
                    Skill rskill = state.skills.get(rskid);

                    if (rstud == null || rskill == null) {
                        System.out.println("Errore: studente o skill non trovati");
                        break;
                    }

                    Request r = new Request(rid, rstud, rskill, "BASE", "");
                    state.requests.put(rid, r);
                    break;

                case 4:
                    System.out.println("\nOFFERS:");
                    for (Offer off : state.offers.values()) {
                        System.out.println(off);
                    }

                    System.out.println("\nREQUESTS:");
                    for (Request req : state.requests.values()) {
                        System.out.println(req);
                    }
                    break;

                case 0:
                    return;
            }
        }
    }
}