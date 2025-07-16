package me.spider.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "characters")
public class Character {
    @DatabaseField(defaultValue = "0", id = true)
    private String serverUserID;
    @DatabaseField(defaultValue = "0")
    private String serverID;
    @DatabaseField(defaultValue = "0")
    private String userID;
    @DatabaseField
    private int personalMotes = 0;
    @DatabaseField
    private int peripheralMotes = 0;
    @DatabaseField
    private int otherMotes = 0;
    @DatabaseField
    private int personalMax = 0;
    @DatabaseField
    private int peripheralMax = 0;
    @DatabaseField
    private int otherMax = 0;
    @DatabaseField
    private int willpower = 0;
    @DatabaseField
    private int limitbreak = 0;

    public int getInt(String type){
        return switch (type){
            case "personalMotes" -> getPersonalMotes();
            case "peripheralMotes" -> getPeripheralMotes();
            case "otherMotes" -> getOtherMotes();
            case "personalMax" -> getPersonalMax();
            case "peripheralMax" -> getPeripheralMax();
            case "otherMax" -> getOtherMax();
            case "willpower" -> getWillpower();
            case "limitbreak" -> getLimitbreak();
            default -> 0;
        };
    }

    public void setInt(String type, int value){
        switch (type){
            case "personalMotes" -> setPersonalMotes(value);
            case "peripheralMotes" -> setPeripheralMotes(value);
            case "otherMotes" -> setOtherMotes(value);
            case "personalMax" -> setPersonalMax(value);
            case "peripheralMax" -> setPeripheralMax(value);
            case "otherMax" -> setOtherMax(value);
            case "willpower" -> setWillpower(value);
            case "limitbreak" -> setLimitbreak(value);
        };
    }
/*
@DatabaseField
private char[] filledBoxes; //todo later - might need to represent them in a different way

@DatabaseField
private char[][] healthLevels;*/
    public Character(){
    }

    public Character(String serverID, String userID) {
        this.serverID = serverID;
        this.userID = userID;
        this.serverUserID = serverID + userID;
    }

    public String getServerID() {
        return serverID;
    }

    public String getUserID() {
        return userID;
    }

    public int getPersonalMotes() {
        return personalMotes;
    }

    public void setPersonalMotes(int personalMotes) {
        this.personalMotes = personalMotes;
    }

    public int getPeripheralMotes() {
        return peripheralMotes;
    }

    public void setPeripheralMotes(int peripheralMotes) {
        this.peripheralMotes = peripheralMotes;
    }

    public int getOtherMotes() {
        return otherMotes;
    }

    public void setOtherMotes(int otherMotes) {
        this.otherMotes = otherMotes;
    }

    public int getPersonalMax() {
        return personalMax;
    }

    public void setPersonalMax(int personalMax) {
        this.personalMax = personalMax;
    }

    public int getPeripheralMax() {
        return peripheralMax;
    }

    public void setPeripheralMax(int peripheralMax) {
        this.peripheralMax = peripheralMax;
    }

    public int getOtherMax() {
        return otherMax;
    }

    public void setOtherMax(int otherMax) {
        this.otherMax = otherMax;
    }

    public int getWillpower() {
        return willpower;
    }

    public void setWillpower(int willpower) {
        this.willpower = willpower;
    }

    public int getLimitbreak() {
        return limitbreak;
    }

    public void setLimitbreak(int limitbreak) {
        this.limitbreak = limitbreak;
    }


    public int spendMotes(int amount){
        int totalEssences = getPeripheralMotes() + getPersonalMotes();
        if(amount > totalEssences){
            return -1;
        }

        int personalMotes = getPersonalMotes();
        if(personalMotes >= amount){
            personalMotes = personalMotes - amount;
            setPersonalMotes(personalMotes);
            return 0;
        } else {
            amount = amount - personalMotes;
            setPersonalMotes(0);

            int peripheralMotes = getPeripheralMotes();
            peripheralMotes = peripheralMotes - amount;
            setPeripheralMotes(Math.max(0, peripheralMotes));
            return 1;
        }
    }

    public int spendMotesPeripheralFirst(int amount){
        int totalEssences = getPeripheralMotes() + getPersonalMotes();
        if(amount > totalEssences){
            return -1;
        }

        int peripheral = getPeripheralMotes();
        if(peripheral >= amount){
            peripheral = peripheral - amount;
            setPeripheralMotes(peripheral);
            return 1;
        } else {
            amount = amount - peripheral;
            setPeripheralMotes(0);
            int personal = getPersonalMotes();
            personal = personal - amount;
            setPersonalMotes(Math.max(0, personal));
            return 0;
        }
    }

    public int recoverMotes(int amount){
        int maxEssence = getPeripheralMax() + getPersonalMax();
        if(amount > maxEssence){
            return -1;
        }

        if(getPersonalMax() >= amount + getPersonalMotes()){
            setPersonalMotes(amount + getPersonalMotes());
            return 0;
        } else {
            amount = amount - (getPersonalMax() - getPersonalMotes());
            setPersonalMotes(getPersonalMax());
            setPeripheralMotes(Math.min(getPeripheralMax(), getPeripheralMotes() + amount));
            return 1;
        }

    }

    public String getAnimaEffect(){
        int totalSpend = getPeripheralMax() - getPeripheralMotes();
        if(totalSpend <= 3){
            return "glittering (1-3)";
        }
        if(totalSpend <= 7){
            return "burning (4-7)";
        }
        if(totalSpend <= 10){
            return "coruscant (8-10)";
        }

        if(totalSpend <= 15){
            return "bonfire (11-15)";
        }

        return "totemic (16+)";
    }


}
