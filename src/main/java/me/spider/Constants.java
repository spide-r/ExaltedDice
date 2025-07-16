package me.spider;

public class Constants {
    public static final int DICE_AMOUNT = 1; // Default dice amount
    public static final int TARGET_NUMBER = 7; // Any dice higher than this value count as a success
    public static final int SUCCESSES = 0; // Minimum Guaranteed Successes if not specified
    public static final String DEFAULT_LABEL = "Roll"; // Minimum Guaranteed Successes if not specified
    public static final boolean ESSENCE_MODIFIED = false; // Does a dice roll modify essence?
    public static final boolean PRIVATE_ROLL = false;
    public static final int LIMIT_BREAK = 0; // Default limit break amount
    public static final int WILLPOWER = 5; // Default willpower amount
    public static final int ESSENCE = 0; // Default essence amount
    public static final int ESSENCE_MODIFIER = -1; // Default essence amount
    public static final String ESSENCE_TYPE = "personal"; // Default essence amount
    public static final String ATTRIBUTE = "invalid-attribute";
    public static final String[] ESSENCE_LIST = new String[]{"personalMotes", "personalMax", "peripheralMotes", "peripheralMax", "otherMotes", "otherMax"};
    public static final String[] ATTRIBUTE_LIST = new String[]{"personalMotes", "personalMax", "peripheralMotes", "peripheralMax", "otherMotes", "otherMax", "willpower", "limitbreak"};
    public static final String[] COMBAT_DATA = new String[]{"ticks","actions"};
    public static final int DEFAULT_TICK = 0;
    public static final int STUNT = 1;

    public static boolean isValidAttribute(String s){
        for (String string : ATTRIBUTE_LIST) {
            if(string.equals(s)){
                return true;
            }
        }
        return false;
    }

}
