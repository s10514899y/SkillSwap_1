// Java
package app;

import domain.Exchange;
import domain.Offer;
import domain.Request;
import domain.Student;
import domain.Match; 

public class ConsoleReportPrinter {
    public void printStudentProfile(Student student, List<Offer> offers, List<Request> requests){
        System.out.println("Student Profile:");
        System.out.println("Name: " + student.getName());
        System.out.println("Email: " + student.getEmail());
        System.out.println("\nOffers:");
        for (Offer offer : offers) {
            System.out.println("- " + offer.getSkill() + " (ID: " + offer.getId() + ")");
        }
        System.out.println("\nRequests:");
        for (Request request : requests) {
            System.out.println("- " + request.getSkill() + " (ID: " + request.getId() + ")");
        }
    }
    
    public void printMatches(List<Match> matches){
        System.out.println("Matches:");
        for (Match match : matches) {
            System.out.println("- " + match.getOffer().getSkill() + " matched with " + match.getRequest().getSkill());
        }
    }

    public void printExchangeDetails(Exchange exchange) {
        System.out.println("Exchange Details:");
        System.out.println("Offer: " + exchange.getOffer().getSkill() + " (ID: " + exchange.getOffer().getId() + ")");
        System.out.println("Request: " + exchange.getRequest().getSkill() + " (ID: " + exchange.getRequest().getId() + ")");
        System.out.println("Status: " + exchange.getStatus());
    }

    public void printLeaderboard(List<Student> students) {
        System.out.println("Leaderboard:");
        for (Student student : students) {
            System.out.println("- " + student.getName() + " (Exchanges: " + student.getExchangesCount() + ")");
        }   
    }
}