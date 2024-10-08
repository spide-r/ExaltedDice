package me.spider.commands;

import me.spider.dice.GenericRoll;

public class DamageRoll extends GenericRoll {
    //10's count as 1 success
    public DamageRoll(int amt, int autoSuccesses, int successThreshold, String label) {
        super(amt, autoSuccesses, successThreshold, true, label);
    }
}
