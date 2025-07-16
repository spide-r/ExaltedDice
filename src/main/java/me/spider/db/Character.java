package me.spider.db;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.text.Style;
import java.util.ArrayList;
import java.util.Arrays;

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
    @DatabaseField
    private String filledBoxesJSON;
    @DatabaseField
    private String healthLevelsJSON;

    private ArrayList<java.lang.Character> filledBoxes = null;
    private ArrayList<Integer> healthLevels = null;

    public String getFilledBoxesJSON() {
        return filledBoxesJSON;
    }

    public void setFilledBoxesJSON(String filledBoxesJSON) {
        this.filledBoxesJSON = filledBoxesJSON;
    }

    public String getHealthLevelsJSON() {
        return healthLevelsJSON;
    }

    public void setHealthLevelsJSON(String healthLevelsJSON) {
        this.healthLevelsJSON = healthLevelsJSON;
    }

    // 0, 1, 2, 4, Incapacitated (5)
    public String arrayListToJSON(ArrayList<java.lang.Character> arr){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(arr);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String arrayListToJSONInt(ArrayList<Integer> arr){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(arr);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public ArrayList<java.lang.Character> jsonToArrayList(String json){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public ArrayList<Integer> jsonToArrayListInt(String json){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
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

    public String getFancyBoxes(){
        ArrayList<Integer> healthLevels = getHealthLevels();
        ArrayList<java.lang.Character> filledBoxes = getFilledBoxes();
        StringBuilder boxBuilder = new StringBuilder();

        boxBuilder.append("Health Levels:\n");
        int currentBox = 0;
        for (int i = 0; i < healthLevels.size(); i++) {
            if (i == 3) {
                continue;
            }
            int boxes = healthLevels.get(i);
            StringBuilder rowBuilder = new StringBuilder();
            if (i == 5) {
                rowBuilder.append("Incap: ");
            } else if(i == 6){
                rowBuilder.append("Dying: ");
            } else {
                rowBuilder.append("-").append(i).append(": ");
            }
            for (int j = 0; j < boxes; j++) {
                char box;
                if (filledBoxes.size() <= currentBox) {
                    box = ' ';
                } else {
                    box = filledBoxes.get(currentBox);
                }
                rowBuilder.append("[").append(box).append("] ");
                currentBox++;
            }
            if (i == 6) {
                boxBuilder.append(rowBuilder);
            } else {
                boxBuilder.append(rowBuilder).append("\n");
            }
        }
        return boxBuilder.toString();
    }

    public ArrayList<java.lang.Character> getFilledBoxes() {
        if(filledBoxes == null){
            if(filledBoxesJSON == null || filledBoxesJSON.equalsIgnoreCase("null")){
                filledBoxes = new ArrayList<>();
            }else {
                filledBoxes = jsonToArrayList(filledBoxesJSON);
            }
        }
        if(filledBoxes == null){
            return new ArrayList<>();
        }
        return filledBoxes;
    }



    Logger LOG = LoggerFactory.getLogger(Character.class);
    public ArrayList<Integer> getHealthLevels() {
        if(healthLevels == null){
            if(healthLevelsJSON == null || healthLevelsJSON.equalsIgnoreCase("null")){
                healthLevels = new ArrayList<>();
                fillHealthLevels(healthLevels);
            } else {
                healthLevels = jsonToArrayListInt(healthLevelsJSON);
            }
        }
        if(healthLevels == null){
            ArrayList<Integer> ll = new ArrayList<>();
            fillHealthLevels(ll);
            return ll;
        }
        return healthLevels;
    }

    private void fillHealthLevels(ArrayList<Integer> ll){
        ll.add(1); //-0
        ll.add(1); //-1
        ll.add(1); //-2
        ll.add(-1); //-3 (nonexistent
        ll.add(1); //-4
        ll.add(1); //incap (-5)
        ll.add(1); //dying (-6)
    }

    private void saveLists(){ //todo: sloppy
        healthLevelsJSON = arrayListToJSONInt(healthLevels);
        filledBoxesJSON = arrayListToJSON(filledBoxes);
    }

    public boolean setHealthLevel(int level, int value){
        if(level > 6 || level < 0){
            return false;
        }
        setHealthLevel(level, value, getHealthLevels());
        return true;
    }

    private void setHealthLevel(int level, int value, ArrayList<Integer> list){
        list.set(level, value);
        saveLists();
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

    private void addBashing(){
        getFilledBoxes().add('B'); //bashing damage is the lowest priority
        trimList();
    }

    private void addLethal(){

        int earliestLethalBashing = 0;
        for (int i = 0; i < getFilledBoxes().size(); i++) {
            earliestLethalBashing = i;
            char c = getFilledBoxes().get(i);
            if(c == 'B' || c == 'L'){
                break;
            }
        }
        getFilledBoxes().add(earliestLethalBashing, 'L');
        trimList();
    }

    public void takeAggravated(int amt){
        if(amt <= 0){
            return;
        }
        for (int i = 0; i < amt; i++) {
            addAggravated();
        }
    }

    public void takeBashing(int amt){
        if(amt <= 0){
            return;
        }
        for (int i = 0; i < amt; i++) {
            addBashing();
        }
    }

    public void takeLethal(int amt){
        if(amt <= 0){
            return;
        }
        for (int i = 0; i < amt; i++) {
            addLethal();
        }
    }
    private void addAggravated(){
        getFilledBoxes().add(0, 'A'); //aggravated damage always takes priority
        trimList();
    }

    public void removeBashing(int amt){
        removeLevel('B', amt);
    }

    public void removeAggravated(int amt){
        removeLevel('A', amt);
    }

    public void removeLethal(int amt){
        removeLevel('L', amt);
    }

    public boolean removeLevel(char type, int amount){
        int amt = amount;
        if(amount < 1){
            return false;
        }
        for (int i = 0; i < getFilledBoxes().size(); i++) {
            char c = getFilledBoxes().get(i);
            if(amt <= 0){
                break;
            }
            if(c == type){
                getFilledBoxes().set(i, '_');
                amt--;
            }
        }
        trimList();
        return true;
    }


    public void trimList(){
       int totalBoxes = 0;
        ArrayList<Integer> levels = getHealthLevels();
        for (int i = 0; i < levels.size(); i++) {
            if(i == 3){
                continue;
            }
            Integer healthLevel = levels.get(i);
            totalBoxes += healthLevel;
        }
        if(getFilledBoxes().size() > totalBoxes){
            filledBoxes = new ArrayList<>(getFilledBoxes().subList(0, totalBoxes));
        }
        getFilledBoxes().removeIf(c -> c == '_');
        saveLists();
    }

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
