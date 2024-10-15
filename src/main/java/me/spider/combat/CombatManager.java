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
        return builder.toString().replace(", $", "");
    }

    public boolean advanceTicks(String channel){
        return combat.get(channel).advanceTicks();
    }

    public void newScene(String channel){
        combat.get(channel).newScene();
    }

    public void tickZero(String channel){
        combat.get(channel).tickZero();
    }

    public void delay(String channel, int amount, String actpr){
        combat.get(channel).delay(amount, actpr);
    }

    public void joinCombat(String channel, int successes, String actor){
        combat.get(channel).joinCombat(actor, successes);
    }

    public void removeFromCombat(String channel, String actor){
        combat.get(channel).removeFromCombat(actor);
    }
}
