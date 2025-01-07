package me.spider.combat;

import me.spider.Main;

import java.io.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class CombatManager implements Serializable {

    private final HashMap<String, TickTracker> combat = new HashMap<>();

    public void startCombat(String channel){
        combat.put(channel, new TickTracker(channel));
    }
    public TickTracker getCombat(String channel){
        return combat.get(channel);
    }

    public HashMap<String, String> serialize() throws IOException{
        HashMap<String, String> map = new HashMap<>();
        for (Map.Entry<String, TickTracker> entry : combat.entrySet()) {
            String channel = entry.getKey();
            TickTracker combat = entry.getValue();
            if(combat == null){
                continue;
            }
            map.put(channel, combat.toBLOB());
        }
        return map;
    }

    public void startupTask(HashMap<String, String> combatStringMap) throws IOException, ClassNotFoundException {
        for (Map.Entry<String, String> entry : combatStringMap.entrySet()) {
            String channel = entry.getKey();
            String blob = entry.getValue();
            if(blob == null){
                continue;
            }
            TickTracker tt = TickTracker.fromBLOB(blob);
            combat.put(channel, tt);
        }
    }

    public String getStatus(String channel){
        int tick = combat.get(channel).getCurrentTick();
        String actors = getActorsAt(tick, channel);
        return "## :crossed_swords: " + tick + "\nActors: " + actors;
    }

    public String getNextSixTicks(String channel){
        int currentTick = combat.get(channel).getCurrentTick();

        StringBuilder builder = new StringBuilder();
        for (int i = currentTick+1; i < currentTick+7; i++) {
            builder.append(":crossed_swords: ").append(i).append("\n");
            builder.append(getActorsAt(i, channel)).append("\n");
        }
        return builder.toString();
    }

    public String getAllActorsNextTick(String channel){
        HashMap<String, Integer> nextTicks = combat.get(channel).getAllActorsNextTick();
        StringBuilder builder = new StringBuilder();
        nextTicks.forEach((actor, tick ) -> {
            if(actor.matches("\\d+")){ //user ID
                builder.append("<@").append(actor).append(">");
            } else {
                builder.append("`").append(actor).append("`");
            }
            builder.append(" :stopwatch: ").append(tick).append("\n");

        });
        return builder.toString();
    }

    public String getActors(String channel){
        TickTracker tracker = getCombat(channel);
        return getActorsAt(tracker.getCurrentTick(), channel);
    }

    public String getActorsAt(int tick, String channel){
        TickTracker tracker = getCombat(channel);
        HashSet<String> participants = tracker.getTickActorsAt(tick);
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

    public int advanceTicks(String channel){
        return combat.get(channel).advanceTicks();
    }

    public void newScene(String channel){
        try {
            combat.get(channel).newScene();
        } catch (NullPointerException e){
            startCombat(channel);
        }
    }

    public void tickZero(String channel){
        combat.get(channel).tickZero();
    }

    public int delay(String channel, int amount, String actor){
        return combat.get(channel).delay(amount, actor);
    }

    public boolean joinCombat(String channel, int successes, String actor){
        return combat.get(channel).joinCombat(actor, successes);
    }

    public void removeFromCombat(String channel, String actor){
        combat.get(channel).removeFromCombat(actor);
    }

    public void addToTick(String channel, int tick, String actor){
        combat.get(channel).addToTick(tick, actor);
    }

    public int getTick(String channel){
        return combat.get(channel).getCurrentTick();
    }

    public void saveCombat(){
        try {
            HashMap<String, String> combat = serialize();
            Main.jdbcManager.setAllCombat(combat);
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
    }
}
