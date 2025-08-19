package me.spider.db.book;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "astrologycolleges")
public class AstrologyCollege extends BookPage{
    @DatabaseField(defaultValue = "unknown", id = true)
    private String name;
    @DatabaseField
    private String description;

    public AstrologyCollege() {
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

    @Override
    public String getFancyText() {
        return "### " + name + "\n" + description;
    }
}
