package domain;

import resources.Level;

public class Request {
    private String id;
    private Student student;
    private Skill skill;
    private Level minLevel;
    private String note;

    public Request(String id, Student student, Skill skill, Level minLevel, String note) {
        this.id = id;
        this.student = student;
        this.skill = skill;
        this.minLevel = minLevel;
        this.note = note;
    }

    public String formattaCSV() {
        return new StringBuilder()
            .append(id).append(";")
            .append(student.getId()).append(";")
            .append(skill.getId()).append(";")
            .append(minLevel).append(";")
            .append(note)
            .toString();
    }

    public String getId()         { return id; }
    public Student getStudent()   { return student; }
    public Skill getSkill()       { return skill; }
    public Level getMinLevel()    { return minLevel; }
    public String getNote()       { return note; }

    @Override
    public String toString() {
        return id + " - " + student.getName() + " cerca " + skill.getName() +
            " [min: " + minLevel + "]";
    }
}
