package me.spider.commands;

import me.spider.dice.GenericRoll;

public class Roll extends GenericRoll {
    public Roll(int amt, int autoSuccesses, int successThreshold, String label, boolean modifiesEssence) {
        super(amt, autoSuccesses, successThreshold, false, label, modifiesEssence);
    }
    //10's count as 2 successes

}
