package me.spider.dice;

public class Roll extends GenericRoll {
    public Roll(int amt, int autoSuccesses, int targetNumber, String label, boolean privateRoll) {
        super(amt, autoSuccesses, targetNumber, false, label, privateRoll);
    }
    //10's count as 2 successes

}
