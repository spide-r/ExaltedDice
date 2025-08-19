package me.spider.db.book;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "hearthstones")
public class Hearthstone extends BookPage {
    @DatabaseField(defaultValue = "unknown", id = true)
    private String name;
    @DatabaseField
    private String aspect;
    @DatabaseField
    private int level;
    @DatabaseField
    private String description;

    public Hearthstone() {
    }

    @Override
    public String getFancyText() {
        return "### " + name + "\n**Aspect:**" + aspect + "\n**Level:** " + level + "\n" + description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAspect() {
        return aspect;
    }

    public void setAspect(String aspect) {
        this.aspect = aspect;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
