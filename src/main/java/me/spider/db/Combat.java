package me.spider.db;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@DatabaseTable(tableName = "combat")
public class Combat {
    //this solution works for now - but if this bot somehow explodes in popularity it might be worth looking at caching
    @DatabaseField(id = true)
    private String channelID;
    @DatabaseField
    private boolean startOfCombat;
    @DatabaseField
    private int currentTick;
    @DatabaseField
    private int highestSuccess;
    @DatabaseField
    private String tickListJSON;
    @DatabaseField
    private String joinCombatJSON;

    private TreeMap<Integer, HashSet<String>> tickList = null;
    private TreeMap<Integer, HashSet<String>> joinCombat = null;

    public Combat(){

    }
    Logger LOG = LoggerFactory.getLogger(Combat.class);
    public String treeMapToJSON(TreeMap<Integer, HashSet<String>> map){
        ObjectMapper mapper = new ObjectMapper();
        try {
            String s = mapper.writeValueAsString(map);
            LOG.info(s);
            return s;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public TreeMap<Integer, HashSet<String>> jsonToTreeMap(String json){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new TreeMap<>();
        }
    }

    private void writeJSON(){ //todo sloppy
        joinCombatJSON = treeMapToJSON(joinCombat);
        tickListJSON = treeMapToJSON(tickList);

    }

    private Combat(String channelID, boolean startOfCombat, int currentTick, int highestSuccess, TreeMap<Integer, HashSet<String>> tickList, TreeMap<Integer, HashSet<String>> joinCombat){
        this.channelID = channelID;
        this.startOfCombat = startOfCombat;
        this.currentTick = currentTick;
        this.highestSuccess = highestSuccess;
        this.tickList = tickList;
        this.joinCombat = joinCombat;
    }

    public Combat(String channelID){
        this.channelID = channelID;
        startOfCombat = true;
        currentTick = 0;
        highestSuccess = 0;
    }

    public String getChannelID() {
        return channelID;
    }

    public boolean isStartOfCombat() {
        return startOfCombat;
    }

    public void setStartOfCombat(boolean startOfCombat) {
        this.startOfCombat = startOfCombat;
    }

    public int getCurrentTick() {
        return currentTick;
    }

    public void setCurrentTick(int currentTick) {
        this.currentTick = currentTick;
    }

    public int getHighestSuccess() {
        return highestSuccess;
    }

    public void setHighestSuccess(int highestSuccess) {
        this.highestSuccess = highestSuccess;
    }

    public String getTickListJSON() {
        return tickListJSON;
    }

    public void setTickListJSON(String tickListJSON) {
        this.tickListJSON = tickListJSON;
    }

    public String getJoinCombatJSON() {
        return joinCombatJSON;
    }

    public void setJoinCombatJSON(String joinCombatJSON) {
        this.joinCombatJSON = joinCombatJSON;
    }

    public TreeMap<Integer, HashSet<String>> getTickList() {
        if(tickList == null){
            if(tickListJSON == null || tickListJSON.equalsIgnoreCase("null")){
                tickList = new TreeMap<>();
            }else {
                tickList = jsonToTreeMap(tickListJSON);
            }
        }
        return tickList;
    }

    public void setTickList(TreeMap<Integer, HashSet<String>> tickList) {
        this.tickList = tickList;
    }

    public TreeMap<Integer, HashSet<String>> getJoinCombat() {
        if(joinCombat == null){
            if(joinCombatJSON == null || joinCombatJSON.equalsIgnoreCase("null")){
                joinCombat = new TreeMap<>();
            } else {
                joinCombat = jsonToTreeMap(joinCombatJSON);

            }
        }
        if(joinCombat == null){
            return new TreeMap<>();
        }
        return joinCombat;
    }

    public void setJoinCombat(TreeMap<Integer, HashSet<String>> joinCombat) {
        this.joinCombat = joinCombat;
        writeJSON();
    }

    public void newScene(){
        startOfCombat = true;
        currentTick = 0;
        highestSuccess = 0;
        getTickList().clear();
        getJoinCombat().clear();
    }

    public void tickZero(){
        startOfCombat = false;
        highestSuccess = getJoinCombat().lastKey();
        setParticipants(0, getJoinCombat().lastEntry().getValue(), getTickList());
        getJoinCombat().forEach((key, value) -> {
            int successes = key;
            int tick = Math.min(6, highestSuccess - successes);
            setParticipants(tick, value, getTickList());
        });
    }

    public int delay(int amount, String actor){
        int actionTick = amount + currentTick;
        addToTick(actionTick, actor);
        return actionTick;
    }

    public void addToTick(int tick, String actor){
        addToTreeMap(tick, actor, getTickList());
    }

    public void advanceTicks(){
        if(getTickList().ceilingKey(currentTick + 1) == null){
            return;
        }
        currentTick = getTickList().ceilingKey(currentTick + 1);
    }

    public HashSet<String> getTickActorsAt(int tick){
        HashSet<String> tt = getTickList().get(tick);
        if(tt == null){
            tt = new HashSet<>();
        }
        return tt;
    }

    public TreeMap<Integer, HashSet<String>> getAllActorsNextTickTreeMap(){
        TreeMap<Integer, HashSet<String>> actorsAndTicks = new TreeMap<>();
        getTickList().tailMap(currentTick).forEach((t, a) -> actorsAndTicks.compute(t, (i, aa) -> {
            if(aa == null){
                aa = new HashSet<>();
            }
            aa.addAll(a);
            return aa;
        }));
        return actorsAndTicks;
    }

    public boolean joinCombat(String actor, int successes){
        if(startOfCombat){
            addParticipantToJoinCombat(successes, actor, getJoinCombat());
            return true;
        } else {
            return false;
        }
    }

    private void setParticipants(int tick, HashSet<String> actors, TreeMap<Integer, HashSet<String>> list){
        list.compute(tick, (tt, aa) -> {
            if(aa == null){
                aa = actors;
            } else {
                aa.addAll(actors);
            }
            writeJSON();
            return aa;
        });
        //list.put(tick, actors); //this overwrites the tick
    }

    private void addParticipantToJoinCombat(int successes, String actor, TreeMap<Integer, HashSet<String>> list){
        list.forEach((suc, participants) -> participants.remove(actor));
        addToTreeMap(successes, actor, list);
    }

    public HashSet<String> getParticipantsThatJoinedCombat(){
        HashSet<String> participants = new HashSet<>();
        getJoinCombat().forEach((tick, pp) -> participants.addAll(pp));
        return participants;
    }

    private void addToTreeMap(int index, String actor, TreeMap<Integer, HashSet<String>> list){
        list.compute(index, (idx, ll) -> {
            if(ll == null){
                ll = new HashSet<>();
            }
            ll.add(actor);
            return ll;
        });
        writeJSON();
    }

    public void removeFromCombat(String actor){
        HashSet<Integer> ticksToRemove = new HashSet<>();
        getTickList().forEach((tick, hashSet) -> {
            if(tick > currentTick){
                hashSet.remove(actor);
            }
            if(hashSet.isEmpty()){
                ticksToRemove.add(tick); //if ticks are empty after actor removal - no need to save this tick since nothing happens
            }
        });

        ticksToRemove.forEach(t -> {
            getTickList().remove(t);
        });

        writeJSON();
    }

    public String getActorsAt(int tick){
        if(isStartOfCombat()){
            return "Combat has not started! Did you mean to run `/combat ready`?";
        }
        HashSet<String> participants = getTickActorsAt(tick);
        StringBuilder builder = new StringBuilder();
        participants.forEach(p -> {
            if(p.matches("\\d+")){ //user ID
                builder.append("<@").append(p).append(">").append(", ");
            } else {
                builder.append(p).append(", ");
            }
        });
        return builder.toString().replaceFirst(", $", "");
    }


    public String getAllActorsNextTick(){
        if(isStartOfCombat()){
            return "Combat has not started! Did you mean to run `/combat ready`?";
        }
        TreeMap<Integer, HashSet<String>> nextTicks = getAllActorsNextTickTreeMap();
        StringBuilder builder = new StringBuilder();
        nextTicks.forEach((tick, actors) -> {
            builder.append("### :stopwatch: ").append(tick).append("\n");
            actors.forEach(actor -> {
                if(actor.matches("\\d+")){ //user ID
                    builder.append("<@").append(actor).append("> ");
                } else {
                    builder.append("`").append(actor).append("` ");
                }
            });
            builder.append("\n");


        });
        return builder.toString();
    }
    public String getNextSixTicks(){
        if(isStartOfCombat()){
            return "Combat has not started! Did you mean to run `/combat ready`?";
        }
        int currentTick = getCurrentTick();
        StringBuilder builder = new StringBuilder();
        for (int i = currentTick+1; i < currentTick+7; i++) {
            builder.append(":crossed_swords: ").append(i).append("\n");
            builder.append(getActorsAt(i)).append("\n");
        }
        return builder.toString();
    }

    public String getStatus(){
        int tick = getCurrentTick();
        String actors = getActorsAt(tick);
        return "## :crossed_swords: " + tick + "\nActors: " + actors;
    }
}
