package domain;

import java.util.regex.Pattern;

public class Student {
    private String id;
    private String name;
    private String schoolClass;
    private String email;
    private double ratingAvg;
    private int ratingCount;

    private static final Pattern EMAIL_PATTERN =
           Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");

    private static final Pattern CLASS_PATTERN =
           Pattern.compile("^[1-5][A-Z]{1,3}$"); 

    public Student(String id, String name, String schoolClass, String email, double ratingAvg, int ratingCount) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Nome non valido");
        }
        if (email == null || email.isEmpty() || !isValidEmail(email)) {
            throw new IllegalArgumentException("Email non valida");
        }
        if (!isValidClass(schoolClass)) {
            throw new IllegalArgumentException("Classe non valida");
        }

        this.id = id;
        this.name = name;
        this.schoolClass = schoolClass;
        this.email = email;
        this.ratingAvg = ratingAvg;
        this.ratingCount = ratingCount;
    }

    public String formattaCSV() {
        return new StringBuilder()
            .append(id).append(";")
            .append(name).append(";")
            .append(schoolClass).append(";")
            .append(email).append(";")
            .append(ratingAvg).append(";")
            .append(ratingCount)
            .toString();
    }

    public void addRating(int stars) {
        ratingCount++;
        ratingAvg = ((ratingAvg * (ratingCount - 1)) + stars) / ratingCount;
    }

    private boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    private boolean isValidClass(String studentClass) {
        return studentClass != null && CLASS_PATTERN.matcher(studentClass).matches();
    }

    public String getId()           { return id; }
    public String getName()         { return name; }
    public String getSchoolClass()  { return schoolClass; }
    public String getEmail()        { return email; }
    public double getRatingAvg()    { return ratingAvg; }
    public int getRatingCount()     { return ratingCount; }

    @Override
    public String toString() {
        return id + " - " + name + " (" + schoolClass + ") | rating: " +
            String.format("%.1f", ratingAvg) + " (" + ratingCount + " voti)";
    }
}
