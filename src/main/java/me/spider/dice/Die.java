package me.spider.dice;

public class Die {
    private int value;
    private final int targetNumber;
    public Die(int value, int targetNumber){
        this.value = value;
        this.targetNumber = targetNumber;
    }

    public boolean success(){
        return value >= targetNumber;
    }

    public int getValue(){
        return value;
    }

    public boolean doubleSuccess(){
        return value == 10;
    }
}
