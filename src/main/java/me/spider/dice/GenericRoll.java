package me.spider.dice;

import me.spider.Main;

import java.util.ArrayList;

public class GenericRoll {

    //return hit on 7/8/9
    // return 2 successes on each 10
    ArrayList<Die> dice = new ArrayList<>();
    boolean tensAreOneHit;
    String label;
    int autoSuccesses;
    public GenericRoll(int amt, int autoSuccesses, int successThreshold, boolean tensAreOneHit, String label){
        for (int i = 0; i < amt; i++) {
            Die d = new Die(Roller.rollDie(), successThreshold);
            dice.add(d);
        }
        this.autoSuccesses = autoSuccesses;
        this.tensAreOneHit = tensAreOneHit;
        this.label = label;
    }


    public String getRolls(){
        StringBuilder builder = new StringBuilder();
        dice.forEach( die -> {
            boolean success = die.success();
            boolean doubleSuccess = die.doubleSuccess();
            builder.append((doubleSuccess) ? "__" : "")
                    .append((success) ? "**" : "")
                    .append(die.getValue()).append((success) ? "**" : "")
                    .append((doubleSuccess) ? "__" : "")
                    .append(", "); //I need to get better at this situation.
        });

        String rolls = builder.toString();
        rolls = rolls.replaceFirst(", $", "");
        return rolls;
    }

    public int getHits(){
        int hits = 0;
        for (Die die : dice) {
            if (die.success()) {
                if (die.doubleSuccess() && !tensAreOneHit) {
                    hits += 2;
                } else {
                    hits++;
                }

            }
        }
        return hits;
    }

    public String getLabel(){
        return label;
    }

    public String getStringResult(){
        String rolls = getRolls();
        int hits = getHits();
        int finalHits = hits + autoSuccesses;
        String hitStr = hits + ((autoSuccesses > 0) ? " + " + autoSuccesses + " :star: **" + finalHits +"** :star:" : "");
        String label = getLabel();

        return ":pencil: " + label + ":\n:game_die: " + rolls + "\n:dart: " + hitStr + ((isBotch()) ? "\n:x: Botch!" : "");
    }

    public boolean isBotch(){
        int amountOf1s = 0;
        for(Die d : dice){
            if(d.success() || autoSuccesses > 0){
                return false;
            }
            if(d.getValue() == 1){
                amountOf1s++;
            }
        }
        return amountOf1s > 0;
    }
}
