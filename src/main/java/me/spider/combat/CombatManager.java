package me.spider.combat;

import java.util.HashMap;
import java.util.HashSet;

public class CombatManager {
    private final HashMap<String, TickTracker> combat = new HashMap<>();

    public void startCombat(String channel){
        combat.put(channel, new TickTracker(channel));
    }
    public TickTracker getCombat(String channel){
        return combat.get(channel);
    }

    public String getStatus(String channel){
        int tick = combat.get(channel).getCurrentTick();
        String actors = getActorsAt(tick, channel);
        return "## :crossed_swords: " + tick + "\nActors: " + actors;
    }

    public String getNextSixTicks(String channel){
        int currentTick = combat.get(channel).getCurrentTick();

        StringBuilder builder = new StringBuilder();
        for (int i = currentTick+1; i < currentTick+6; i++) {
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

    public void joinCombat(String channel, int successes, String actor){
        combat.get(channel).joinCombat(actor, successes);
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
}
