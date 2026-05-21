package resources;

import java.time.DateTimeException;
import java.time.LocalDateTime;

public final class ParserUtils {
    public static Status parseStatus(String s) throws IllegalArgumentException {
        return Status.valueOf(s.trim());
    }

    public static LocalDateTime parseLocalDate(String s) throws DateTimeException {
        return LocalDateTime.parse(s.trim());
    }

    public static boolean parseActive(String s) throws IllegalArgumentException {
        return Boolean.parseBoolean(s.trim());
    }

    public static Level parseLevel(String s) throws IllegalArgumentException {
        return Level.valueOf(s.trim());
    }

    public static Category parseCategory(String s) throws IllegalArgumentException {
        return Category.valueOf(s.trim());
    }

    public static double parseAvgRating(String s) throws NumberFormatException {
        return Double.parseDouble(s.trim());
    }

    public static int parseRatingCount(String s) throws NumberFormatException {
        return Integer.parseInt(s.trim());
    }
    
    public static int parseStars(String s) throws NumberFormatException {
        return Integer.parseInt(s.trim());
    }
}
