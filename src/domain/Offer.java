package domain;

import resources.*;

public class Offer {
    private String id;
    private Student student;
    private Skill skill;
    private Level level;
    private String note;
    private boolean active;

    public Offer(String id, Student student, Skill skill, Level level, String note, boolean  active) {
        this.id = id;
        this.student = student;
        this.skill = skill;
        this.level = level;
        this.note = note;
        this.active = active;
    }

    public String formattaCSV() {
        return new StringBuilder()
            .append(id).append(";")
            .append(student.getId()).append(";")
            .append(skill.getId()).append(";")
            .append(level).append(";")
            .append(note).append(";")
            .append(active)
            .toString();
    }

    public Level getLevel() { return level; }
    public String getId() { return id; }
    public Student getStudent() { return student; }
    public Skill getSkill() { return skill; }

    @Override
    public String toString() {
        return id + " - " + student.getName() + " offre " + skill.getName();
    }
}