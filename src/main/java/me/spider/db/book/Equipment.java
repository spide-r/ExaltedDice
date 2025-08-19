package me.spider.db.book;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "equipment")
public class Equipment extends BookPage {
    @DatabaseField(defaultValue = "unknown", id = true)
    private String name;
    @DatabaseField
    private String description;
    @DatabaseField
    private int dots;
    @DatabaseField
    private int commitment;
    @DatabaseField
    private String type;

    public Equipment() {
    }

    @Override
    public String getFancyText() {
        return "### " + name + "\n**Dots:** " + dots + "\n**Commitment:** " + commitment + "\n**Type:** " + type + "\n" + description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDots() {
        return dots;
    }

    public void setDots(int dots) {
        this.dots = dots;
    }

    public int getCommitment() {
        return commitment;
    }

    public void setCommitment(int commitment) {
        this.commitment = commitment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
