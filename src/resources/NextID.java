package resources;

import domain.*;
import java.util.Map;

public final class NextID {

    private NextID() {}

    public static int initNextStudentID(Map<String, Student> students) {
        return maxNum(students.keySet(), "S") + 1;
    }

    public static int initNextSkillID(Map<String, Skill> skills) {
        return maxNum(skills.keySet(), "K") + 1;
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

    private static int maxNum(Iterable<String> keys, String prefix) {
        int max = 0;
        for (String id : keys) {
            if (id.startsWith(prefix)) {
                try {
                    int num = Integer.parseInt(id.substring(prefix.length()));
                    if (num > max) max = num;
                } catch (NumberFormatException ignored) {}
            }
        }
        return max;
    }
}