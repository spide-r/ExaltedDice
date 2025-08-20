package me.spider.db.book;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "submodules")
public class Submodule extends BookPage {
    @DatabaseField(defaultValue = "unknown", id = true)
    private String name;
    @DatabaseField
    private String charm;
    @DatabaseField
    private String description;

    public Submodule() {
    }

    @Override
    public String getFancyText() {
        return "### " + name + "\n**Charm:** " + charm + "\n" + description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharm() {
        return charm;
    }

    public void setCharm(String charm) {
        this.charm = charm;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
