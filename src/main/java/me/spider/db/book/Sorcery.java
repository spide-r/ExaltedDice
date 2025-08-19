package me.spider.db.book;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "sorcery")
public class Sorcery extends BookPage{
    @DatabaseField(defaultValue = "unknown", id = true)
    private String name;
    @DatabaseField
    private String level;
    @DatabaseField
    private String cost;
    @DatabaseField
    private String target;
    @DatabaseField
    private String description;

    @Override
    public String getFancyText() {
        return "### " + name + "(" + level + ")" +
                "\n**Cost:** " + cost + "\n**Target:**" + target + "\n" + description;
    }


    public Sorcery() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
