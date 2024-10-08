package me.spider.dice;

public class Die {
    private int value;
    private final int successThreshold;
    public Die(int value, int successThreshold){
        this.value = value;
        this.successThreshold = successThreshold;
    }

    public boolean success(){
        return value > successThreshold;
    }

    public int getValue(){
        return value;
    }

    public boolean doubleSuccess(){
        return value == 10;
    }
}
