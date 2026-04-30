package SkillSwap.domain;

public class Request {
    private String id;
    private Student student;
    private Skill skill;
    private String minLevel;
    private String note;

    public Request(String id, Student student, Skill skill, String minLevel, String note) {
        this.id = id;
        this.student = student;
        this.skill = skill;
        this.minLevel = minLevel;
        this.note = note;
    }

    public String getId() { return id; }
    public Student getStudent() { return student; }
    public Skill getSkill() { return skill; }

    @Override
    public String toString() {
        return id + " - " + student.getName() + " cerca " + skill.getName();
    }
}
