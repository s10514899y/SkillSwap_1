package domain;

import resources.Category;

public class Skill {
    private String id;
    private String name;
    private Category category;

    public Skill(String id, String name, Category category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    public String formattaCSV() {
        return new StringBuilder()
            .append(id).append(";")
            .append(name).append(";")
            .append(category)
            .toString();
    }

    public String getId()           { return id; }
    public String getName()         { return name; }
    public Category getCategory()   { return category; }

    @Override
    public String toString() {
        return id + " - " + name + " [" + category + "]";
    }
}