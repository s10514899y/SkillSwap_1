package resources;

import domain.Exchange;
import domain.Offer;
import domain.Request;
import domain.Review;
import domain.Student;
import java.util.Map;

public final class NextID {
    public static int initNextStudentID(Map<String, Student> students) {
        return maxNum(students.keySet(), "S") + 1;
    }
 
    public static int initNextOfferID(Map<String, Offer> offers) {
        return maxNum(offers.keySet(), "O") + 1;
    }
 
    public static int initNextRequestID(Map<String, Request> requests) {
        return maxNum(requests.keySet(), "R") + 1;
    }
 
    public static int initNextExchangeID(Map<String, Exchange> exchanges) {
        return maxNum(exchanges.keySet(), "E") + 1;
    }
 
    public static int initNextReviewID(Map<String, Review> reviews) {
        return maxNum(reviews.keySet(), "V") + 1;
    }
 
    private static int maxNum(Iterable<String> keys, String prefisso) {
        int max = 0;
        for (String id : keys) {
            if (id.startsWith(prefisso)) {
                try {
                    int num = Integer.parseInt(id.substring(prefisso.length()));
                    if (num > max) max = num;
                } catch (NumberFormatException E) {}
            }
        }
        return max;
    }
}
