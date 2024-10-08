package me.spider.dice;

import java.util.Random;

public class Roller {
    private static Random r;
    public Roller(){
        r = new Random();
    }

    public static int rollDie() {
        return r.nextInt(1, 11);
    }

}
