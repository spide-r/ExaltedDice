package me.spider.commands;

import me.spider.dice.GenericRoll;

public class Roll extends GenericRoll {
    public Roll(int amt, int autoSuccesses, int successThreshold, String label, boolean privateRoll) {
        super(amt, autoSuccesses, successThreshold, false, label, privateRoll);
    }
    //10's count as 2 successes

}
