package domain;

public class Student {
    private String id;
    private String name;
    private String schoolClass;
    private String email;
    private double ratingAvg;
    private int ratingCount;

    public Student(String id, String name, String schoolClass, String email, double ratingAvg, int ratingCount) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Nome non valido");
        }
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email non valida");
        }

        this.id = id;
        this.name = name;
        this.schoolClass = schoolClass;
        this.email = email;
        this.ratingAvg = ratingAvg;
        this.ratingCount = ratingCount;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getSchoolClass() { return schoolClass; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return id + " - " + name + " (" + schoolClass + ")";
    }
}
