package me.spider.combat;

import java.io.*;
import java.util.*;

public class TickTracker implements Serializable {
    @Serial
    private static final long serialVersionUID = 2522314465556487L;
    /*
    Have a command to start a new fight scene (this should clear any active combatants from the fight tracker)
A command that lets people roll dice for join battle (or that just has them enter a number)
Then a command for Tick 0
When carrying out tick 0, it assigns anyone who achieved the highest number of hits to act on that tick
Then it determines peoples initial ticks by subtracting their successes from the highest successes
To a maximum of 6
Then you have an option to Set Delay, in which people will enter the speed of an action they take, and it will add that number to the current tick in order to determine when they should act again
Then you have a command that advances to the next tick that anyone is active on, and announces who acts on the current tick as well as how many ticks passed during that
And then I suppose a command to remove specific people from the tracker (like when they die)
     */

    private String channelID;
    private int currentTick;
    private boolean startOfCombat;
    private int highestSuccess;
    private TreeMap<Integer, HashSet<String>> tickList = new TreeMap<>();
    private TreeMap<Integer, HashSet<String>> joinCombat = new TreeMap<>();

    private TickTracker(String channelID, boolean startOfCombat, int currentTick, int highestSuccess, TreeMap<Integer, HashSet<String>> tickList, TreeMap<Integer, HashSet<String>> joinCombat){
        this.channelID = channelID;
        this.startOfCombat = startOfCombat;
        this.currentTick = currentTick;
        this.highestSuccess = highestSuccess;
        this.tickList = tickList;
        this.joinCombat = joinCombat;

    }

    @Override
    public String toString() {
        return "TickTracker{" +
                "channelID='" + channelID + '\'' +
                ", currentTick=" + currentTick +
                ", startOfCombat=" + startOfCombat +
                ", highestSuccess=" + highestSuccess +
                ", tickList=" + tickList +
                ", joinCombat=" + joinCombat +
                '}';
    }

    public static TickTracker fromBLOB(String blob) throws IOException, ClassNotFoundException {
        byte[] data = Base64.getDecoder().decode(blob);
        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(data));
        return (TickTracker) objectInputStream.readObject();
    }

    public String toBLOB() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(this);
        objectOutputStream.close();
        return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
    }

    @Serial
    private void writeObject(ObjectOutputStream o) throws IOException {
        o.defaultWriteObject();
/*        o.writeObject(channelID);
        o.writeObject(currentTick);
        o.writeObject(startOfCombat);
        o.writeObject(highestSuccess);
        o.writeObject(tickList);
        o.writeObject(joinCombat);*/
    }

    @Serial
    private void readObject(ObjectInputStream i) throws ClassNotFoundException, IOException{
        i.defaultReadObject();
/*        String channelID = (String) i.readObject();
        Integer currentTick = (Integer) i.readObject();
        Boolean startOfCombat = (Boolean) i.readObject();
        Integer highestSuccess = (Integer) i.readObject();
        TreeMap<Integer, HashSet<String>> tickList= (TreeMap<Integer, HashSet<String>>) i.readObject();
        TreeMap<Integer, HashSet<String>> joinCombat= (TreeMap<Integer, HashSet<String>>) i.readObject();

        setChannelID(channelID);
        setCurrentTick(currentTick);
        setStartOfCombat(startOfCombat);
        setHighestSuccess(highestSuccess);
        setTickList(tickList);
        setJoinCombat(joinCombat);*/
    }

    @Serial
    private void readObjectNoData(ObjectInputStream i){

    }


    public void setChannelID(String channelID) {
        this.channelID = channelID;
    }

    public void setCurrentTick(int currentTick) {
        this.currentTick = currentTick;
    }

    public void setStartOfCombat(boolean startOfCombat) {
        this.startOfCombat = startOfCombat;
    }

    public void setHighestSuccess(int highestSuccess) {
        this.highestSuccess = highestSuccess;
    }

    public void setTickList(TreeMap<Integer, HashSet<String>> tickList) {
        this.tickList = tickList;
    }

    public void setJoinCombat(TreeMap<Integer, HashSet<String>> joinCombat) {
        this.joinCombat = joinCombat;
    }

    public TickTracker(String channelID){
        this.channelID = channelID;
        startOfCombat = true;
        currentTick = 0;
        highestSuccess = 0;
    }

    public void newScene(){
        startOfCombat = true;
        currentTick = 0;
        highestSuccess = 0;
        tickList.clear();
        joinCombat.clear();
    }

    public void tickZero(){
        startOfCombat = false;
        highestSuccess = joinCombat.lastKey();
        setParticipants(0, joinCombat.lastEntry().getValue(), tickList);
        joinCombat.forEach((key, value) -> {
            int successes = key;
            int tick = Math.min(6, highestSuccess - successes);
            setParticipants(tick, value, tickList);
        });
    }

    public int delay(int amount, String actor){
        int actionTick = amount + currentTick;
        addToTick(actionTick, actor);
        return actionTick;
    }

    public void addToTick(int tick, String actor){
        addToTreeMap(tick, actor, tickList);
    }

    public int advanceTicks(){
        if(tickList.ceilingKey(currentTick + 1) == null){
            return -1;
        }
        currentTick = tickList.ceilingKey(currentTick + 1);
        return currentTick;
    }

    public HashSet<String> getTickActorsAt(int tick){
        HashSet<String> tt = tickList.get(tick);
        if(tt == null){
            tt = new HashSet<>();
        }
        return tt;
    }


    public TreeMap<Integer, HashSet<String>> getNextSixTicks(){
        TreeMap<Integer, HashSet<String>> nextSixTicks = new TreeMap<>();
        for (int i = currentTick+1; i <= currentTick+7 ; i++) {
            if(tickList.get(i) == null){
                nextSixTicks.put(i, new HashSet<>());
            } else {
                nextSixTicks.put(i, tickList.get(i));
            }
        }
        return nextSixTicks;
    }

    public HashMap<String, Integer> getAllActorsNextTick(){
        HashMap<String, Integer> actorsAndTicks = new HashMap<>();
        tickList.tailMap(currentTick).forEach((tick, actors) -> {
            actors.forEach(actor -> {
                actorsAndTicks.putIfAbsent(actor, tick);
            });
        });
        return actorsAndTicks;
    }

    public boolean joinCombat(String actor, int successes){
        if(startOfCombat){
            addParticipantToJoinCombat(successes, actor, joinCombat);
            return true;
        } else {
            int tickDelay = Math.min(6, Math.max(highestSuccess - successes, 0)); //have to do an extra check since the person joining combat might have more successes than the highest success
            delay(tickDelay, actor);
        }
        return false;
    }

    private void setParticipants(int tick, HashSet<String> actors, TreeMap<Integer, HashSet<String>> list){
        list.compute(tick, (tt, aa) -> {
            if(aa == null){
                aa = actors;
            } else {
                aa.addAll(actors);
            }
            return aa;
        });
        //list.put(tick, actors); //this overwrites the tick
    }

    private void addParticipantToJoinCombat(int successes, String actor, TreeMap<Integer, HashSet<String>> list){
        list.forEach((suc, participants) -> {
            participants.remove(actor);
        });
        addToTreeMap(successes, actor, list);

    }

    public HashSet<String> getParticipantsThatJoinedCombat(){
        HashSet<String> participants = new HashSet<>();
        joinCombat.forEach((tick, pp) -> {
            participants.addAll(pp);
        });
        return participants;
    }

    private void addToTreeMap(int index, String actor, TreeMap<Integer, HashSet<String>> list){
        HashSet<String> itemsAtIndex = new HashSet<>();
        list.compute(index, (idx, ll) -> {
            if(ll == null){
                ll = new HashSet<>();
                ll.add(actor);
            }
            ll.add(actor);
            return ll;
        });
    }

    public void removeFromCombat(String actor){
        tickList.forEach((tick, hashSet) -> {
            if(tick > currentTick){
                hashSet.remove(actor);
            }
        });
    }

/*    public boolean removeParticipant(int tick, String actor, TreeMap<Integer, HashSet<String>> list){
        HashSet<String> participantsAtTick = new HashSet<>();
        if(list.containsKey(tick)){
            participantsAtTick = list.get(tick);
        }
        boolean result =  participantsAtTick.remove(actor);

        if(result){
            list.put(tick, participantsAtTick);
        }
        return result;
    }*/

    public int getCurrentTick() {
        return currentTick;
    }

    public boolean isStartOfCombat() {
        return startOfCombat;
    }

    public String getChannelID() {
        return channelID;
    }
}
