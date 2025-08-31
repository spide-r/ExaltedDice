package me.spider.dice;

import me.spider.Constants;

import java.util.ArrayList;

public class GenericRoll {

    //return hit on 7/8/9
    // return 2 successes on each 10
    ArrayList<Die> dice = new ArrayList<>();
    boolean tensAreOneHit;
    boolean privateRoll;
    String label;
    int autoSuccesses;
    int targetNumber;
    public GenericRoll(int amt, int autoSuccesses, int targetNumber, boolean tensAreOneHit, String label, boolean privateRoll){
        for (int i = 0; i < amt; i++) {
            Die d = new Die(Roller.rollDie(), targetNumber);
            dice.add(d);
        }
        this.autoSuccesses = autoSuccesses;
        this.tensAreOneHit = tensAreOneHit;
        this.targetNumber = targetNumber;
        this.label = label;
        this.privateRoll = privateRoll;
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
                    .append(", ");
        });

        String rolls = builder.toString();
        if(rolls.isEmpty()){
            return "";
        }
        rolls = rolls.substring(0, rolls.length()-2);
        return rolls;
    }

    public String getCompressedRolls(){
        StringBuilder builder = new StringBuilder();
        int[] amount = new int[11];
        dice.forEach( die -> amount[die.getValue()] += 1);
        for (int i = 1; i < amount.length ; i++) {
            boolean success = i >= targetNumber;
            boolean doubleSuccess = !tensAreOneHit && i == 10;
            builder
                    .append((doubleSuccess) ? "__" : "")
                    .append((success) ? "**" : "")
                    .append("`").append(amount[i]).append("` ").append(numberToWord(i))
                    .append((success) ? "**" : "")
                    .append((doubleSuccess) ? "__" : "")
                    .append(", ");
        }

        String rolls = builder.toString();
        if(rolls.isEmpty()){
            return "";
        }
        rolls = rolls.substring(0, rolls.length()-2);
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

    private String numberToWord(int i){
        return switch (i) {
            case 1 -> "Ones";
            case 2 -> "Twos";
            case 3 -> "Threes";
            case 4 -> "Fours";
            case 5 -> "Fives";
            case 6 -> "Sixes";
            case 7 -> "Sevens";
            case 8 -> "Eights";
            case 9 -> "Nines";
            case 10 -> "Tens";
            default -> "unknown";
        };
    }

    public int getHitsAndAutoSuccesses(){
        return getHits() + autoSuccesses;
    }

    public String getLabel(){
        return label;
    }

    public String getStringResult(){
        boolean compress = dice.size() > 50;
        String rolls = (compress) ? getCompressedRolls() : getRolls();
        int hits = getHits();
        int finalHits = hits + autoSuccesses;
        String hitStr = hits + ((autoSuccesses != 0) ? ((autoSuccesses>0) ? " + " : " - ") + Math.abs(autoSuccesses) + " :star: **" + finalHits +"** :star:" : "");
        String label = getLabel();

        String targetChanged = "";
        if(this.targetNumber != Constants.TARGET_NUMBER){
         targetChanged = "\n### :white_check_mark: Target Number: {" + this.targetNumber + "}";
        }

        return ((privateRoll) ? ":ghost: " : ":pencil: ")+ label + "\n:game_die: **(" + dice.size() + "):** " + rolls + " " + targetChanged + "\n:dart: " + hitStr + ((isBotch()) ? "\n:x: Botch!" : "");
    }


    public boolean isBotch(){
        if(tensAreOneHit){ //damage rolls do nothing on a botch, so dont bother highlighting them
            return false;
        }
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
