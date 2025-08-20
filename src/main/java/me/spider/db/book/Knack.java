package me.spider.db.book;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "knacks")
public class Knack extends BookPage {
    @DatabaseField(defaultValue = "unknown", id = true)
    private String name;
    @DatabaseField
    private String type;
    @DatabaseField
    private String description;

    public Knack() {
    }

    @Override
    public String getFancyText() {
        return "### " + name + "\n**Type:** " + type + "\n" + description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
