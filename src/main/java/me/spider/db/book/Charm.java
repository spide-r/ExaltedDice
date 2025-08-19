package me.spider.db.book;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "charms")
public class Charm extends BookPage{
    @DatabaseField(defaultValue = "unknown", id = true)
    private String name;
    @DatabaseField
    private String category;
    @DatabaseField
    private String subsection;
    @DatabaseField
    private String cost;
    @DatabaseField
    private String type;
    @DatabaseField
    private String keywords;
    @DatabaseField
    private String duration;
    @DatabaseField
    private String description;

    public Charm() {
    }

    @Override
    public String getFancyText() {
        return "## " + name + " (" + category + ")\n### " + subsection +
                "\n**Cost:** " + cost + "\n**Type:**" + type + "\n**Keywords:** " + keywords + "\n**Duration:** " + duration + "\n" + description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubsection() {
        return subsection;
    }

    public void setSubsection(String subsection) {
        this.subsection = subsection;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
