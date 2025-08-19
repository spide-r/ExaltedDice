package me.spider.db.book;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "martialarts")
public class MartialArts extends BookPage{
    @DatabaseField(defaultValue = "unknown", id = true)
    private String name;
    @DatabaseField
    private String style;
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
    @DatabaseField
    private String level;

    @Override
    public String getFancyText() {
        return "### " + name + " (" + style + ")" +
                "\n**Cost:** " + cost + "\n**Type:** " + type + "\n**Keywords:** " + keywords + "\n**Duration:** " + duration + "\n**Level:** " + level+ "\n" + description;
    }


    public MartialArts() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
