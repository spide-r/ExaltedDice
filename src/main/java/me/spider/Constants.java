package me.spider;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

import java.util.Arrays;

public class Constants {
    public static final int DICE_AMOUNT = 1; // Default dice amount
    public static final int SUCCESS_THRESHOLD = 7; // Any dice higher than this value count as a success
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
    public static final String[] ATTRIBUTE_LIST = new String[]{"essences", "personalMotes", "personalMax", "peripheralMotes", "peripheralMax", "otherMotes", "otherMax", "willpower", "limitbreak"};
    public static final String[] COMBAT_DATA = new String[]{"ticks","actions"};
    public static final int DEFAULT_TICK = 0;
    public static final CommandData[] commandData = new CommandData[]{Commands.slash("roll", "Rolls The Dice - 10's count as 2 successes")
            .addOption(OptionType.INTEGER, "amount", "Amount Of Dice", true)
            .addOption(OptionType.STRING, "label", "Describe what this dice roll is for", false, false)
            .addOption(OptionType.INTEGER, "successes", "Amount of Automatic Successes", false, false)
            .addOption(OptionType.INTEGER, "threshold", "If the success threshold needs to be changed, change it here.", false, false)
            .addOption(OptionType.INTEGER, "essencemodification", "If the dice roll will modify your essence.", false, false)
            .addOption(OptionType.BOOLEAN, "private", "If the dice roll should be shown only to you.", false, false),
            Commands.slash("damage", "Rolls The Dice - 10's count as 1 success")
                    .addOption(OptionType.INTEGER, "amount", "Amount Of Dice", true)
                    .addOption(OptionType.STRING, "label", "Describe what this dice roll is for", false, false)
                    .addOption(OptionType.INTEGER, "successes", "Amount of Automatic Successes", false, false)
                    .addOption(OptionType.INTEGER, "threshold", "If the success threshold needs to be changed, change it here.", false, false)
                    .addOption(OptionType.INTEGER, "essencemodification", "If the dice roll will modify your essence, change it here.", false, false)
                    .addOption(OptionType.BOOLEAN, "private", "If the dice roll should be shown only to you.", false, false),
            Commands.slash("sheet", "Character Sheet Management").addSubcommands(
                    new SubcommandData("get", "Gets Attribute")
                            .addOption(OptionType.STRING, "attribute", "Which Attribute?", true, true),
                    new SubcommandData("set", "Sets Attribute")
                            .addOption(OptionType.STRING, "attribute", "Which Attribute?", true, true)
                            .addOption(OptionType.INTEGER, "value", "The number to set the attribute at.", true),
                    new SubcommandData("modify", "Modifies Attribute")
                            .addOption(OptionType.STRING, "attribute", "Which Attribute?", true, true)
                            .addOption(OptionType.INTEGER, "value", "The number to set the attribute at.", true),
                    new SubcommandData("help", "Is this thing on?"),
                    new SubcommandData("refresh", "Resets all your essences back to their max value.")
            ),


            //combat start|join|advance|delay|add|remove|check|preview|help

            Commands.slash("combat", "Combat Commands").addSubcommands(
                    new SubcommandData("start", "Resets Current Combat and Creates a new Scene. Be very sure about this!")
                            .addOption(OptionType.BOOLEAN, "areyousure", "Are you sure you wish to start a new scene?", true),
                    new SubcommandData("join", "Joins the current combat")
                            .addOption(OptionType.INTEGER, "joinbattle", "What is your join battle dv?", true)
                            .addOption(OptionType.INTEGER, "successes", "Amount of automatic successes (if applicable)")
                            .addOption(OptionType.STRING, "name", "The name of the actor (if it isn't yourself).")
                            .addOption(OptionType.STRING, "label", "The label of the join battle roll."),
                    new SubcommandData("advance", "Advances Combat to the next tick where a combatant moves."),
                    new SubcommandData("delay", "Delays your next action by a specified value")
                            .addOption(OptionType.INTEGER, "delay", "How Many ticks should the next action be delayed by?", true)
                            .addOption(OptionType.STRING, "name", "The name of the actor (if it isn't yourself)."),
                    new SubcommandData("add", "Adds a specific actor to participate at a certain tick. !!! DIFFERENT FROM DELAYING !!!")
                            .addOption(OptionType.INTEGER, "tick", "The tick the actor should be added to.", true)
                            .addOption(OptionType.STRING, "name", "The name of the actor (if it isn't yourself)."),
                    new SubcommandData("remove", "Removes an actor from future combat ticks. (RIP)")
                            .addOption(OptionType.STRING, "name", "The name of the actor (if it isn't yourself)."),
                    new SubcommandData("check", "Checks Actions at a given tick")
                            .addOption(OptionType.INTEGER, "tick", "Which tick should be examined?", true),
                    new SubcommandData("preview", "Shows a preview of whats to come in combat.")
                            .addOption(OptionType.STRING, "data", "What Combat Data would you like to see? Next Actions or Next 6 Ticks?", true, true),
                    new SubcommandData("tick", "Shows the current Tick Data"),
                    new SubcommandData("ready", "Shows who is ready for combat."),
                    new SubcommandData("help", "I don't know how to computer???"),
                    new SubcommandData("cheatsheet", "I don't know how to combat???")


            ),
            Commands.slash("blowondice", "Blows on the dice which may or may not increase your luck."),
/*            Commands.slash("reducemotes", "Quick Command to reduce your motes. It first reduces from your peripheral, then personal, then other")
                    .addOption(OptionType.INTEGER, "amount", "How many motes to reduce.", true),
            Commands.slash("addmotes", "Quick Command to increase your motes. It first increases your peripheral, then personal, then other")
                    .addOption(OptionType.INTEGER, "amount", "How many motes to add.", true),*/
            /* Commands.slash("addtotick", "Adds a specific actor to participate at a certain tick. !!! DIFFERENT FROM DELAYING !!!")
                     .addOption(OptionType.INTEGER, "tick", "The tick the actor should be added to.", true)
                     .addOption(OptionType.STRING, "name", "The name of the actor (if it isn't yourself)."),
             Commands.slash("advancecombat", "Advances Combat to the next tick where a combatant moves."),
             Commands.slash("currenttick", "Shows the current tick and all participants."),
             Commands.slash("delay", "Delays your next action by a specified value")
                     .addOption(OptionType.INTEGER, "delay", "How Many ticks should the next action be delayed by?", true)
                     .addOption(OptionType.STRING, "name", "The name of the actor (if it isn't yourself)."),
             Commands.slash("joincombat", "Joins the current combat")
                     .addOption(OptionType.INTEGER, "successes", "How Many successes did you get on your Join Combat Roll?", true)
                     .addOption(OptionType.STRING, "name", "The name of the actor (if it isn't yourself)."),
             Commands.slash("startcombat", "Resets Current Combat and Creates a new Scene. Be very sure about this!")
                     .addOption(OptionType.BOOLEAN, "areyousure", "Are you sure you wish to start a new scene?", true),
             Commands.slash("removeactor", "Removes an actor from future combat ticks. (RIP)")
                     .addOption(OptionType.STRING, "name", "The name of the actor (if it isn't yourself)." ),
             Commands.slash("tickzero", "Finalizes all combat and officially starts the scene at tick zero."),
             Commands.slash("checktick", "Checks Actions at a given tick")
                     .addOption(OptionType.INTEGER, "tick", "Which tick should be examined?", true),
             Commands.slash("nextticks", "Shows a preview of the next 6 ticks."),
             Commands.slash("nextactions", "Shows a list each actor and the next tick they act on.")*/
    };
    public static boolean isValidAttribute(String s){
        for (String string : ATTRIBUTE_LIST) {
            if(string.equals(s)){
                return true;
            }
        }
        return false;
    }

}
