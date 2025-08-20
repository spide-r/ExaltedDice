package me.spider.db.book;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "anima")
public class Anima extends BookPage{
    @DatabaseField(defaultValue = "unknown", id = true)
    private String caste;
    @DatabaseField
    private String power;
    @DatabaseField
    private String otherInfo;

    public Anima() {
    }

    public String getCaste() {
        return caste;
    }

    public void setCaste(String caste) {
        this.caste = caste;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }

    @Override
    public String getFancyText() {
        return "### " + caste + "\n" + power +
                ((otherInfo == null || otherInfo.equalsIgnoreCase("null") || otherInfo.equalsIgnoreCase("")) ? "" : "\n**Other Info:**\n" + otherInfo);
    }
}
