package me.spider.combat;

import com.sun.source.tree.Tree;

import java.util.*;

public class TickTracker {
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

    private final String channelID;
    private int currentTick = 0;
    private boolean startOfCombat;
    private int highestSuccess;
    private final TreeMap<Integer, HashSet<String>> tickList = new TreeMap<>();
    private final TreeMap<Integer, HashSet<String>> joinCombat = new TreeMap<>();

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
        joinCombat.entrySet().forEach(hitsAndParticipants -> {
            int successes = hitsAndParticipants.getKey();
            int tick = Math.min(6, highestSuccess - successes);
            setParticipants(tick, hitsAndParticipants.getValue(), tickList);
        });
    }

    public int delay(int amount, String actor){
        int actionTick = amount + currentTick;
        addToTick(actionTick, actor);
        return actionTick;
    }

    public void addToTick(int tick, String actor){
        addParticipant(tick, actor, tickList);
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

/*    public HashSet<String> getTickActors(){
        return getTickActorsAt(currentTick);
    }*/


    public TreeMap<Integer, HashSet<String>> getNextSixTicks(){
        TreeMap<Integer, HashSet<String>> nextSixTicks = new TreeMap<>();
        for (int i = currentTick+1; i < currentTick+7 ; i++) {
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
        //todo this will need a special type of function so that we can overwrite duplicate "add to combat" commands
        if(startOfCombat){
            return addParticipant(successes, actor, joinCombat);
        } else {
            int tickDelay = Math.min(6, Math.max(highestSuccess - successes, 0)); //have to do an extra check since the person joining combat might have more successes than the highest success
            delay(tickDelay, actor);

        }
        return false;
    }

    private void setParticipants(int tick, HashSet<String> actors, TreeMap<Integer, HashSet<String>> list){
        list.put(tick, actors);
    }

    private boolean addParticipant(int tick, String actor, TreeMap<Integer, HashSet<String>> list){
        HashSet<String> participantsAtTick = new HashSet<>();
        if(list.containsKey(tick)){
            participantsAtTick = list.get(tick);
        }
       boolean result =  participantsAtTick.add(actor);

        if(result){
            list.put(tick, participantsAtTick);
        }
        return result;
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
