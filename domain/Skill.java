package SkillSwap.domain;

public class Skill {
    private String id;
    private String name;
    private String category;

    public Skill(String id, String name, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    public String getId() { return id; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return id + " - " + name;
    }
}