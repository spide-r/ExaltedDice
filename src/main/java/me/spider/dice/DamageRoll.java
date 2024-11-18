package me.spider.dice;

public class DamageRoll extends GenericRoll {
    //10's count as 1 success
    public DamageRoll(int amt, int autoSuccesses, int successThreshold, String label, boolean privateRoll) {
        super(amt, autoSuccesses, successThreshold, true, label, privateRoll);
    }
}
