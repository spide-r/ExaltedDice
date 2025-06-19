package me.spider.commands.funny;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import me.spider.dice.Roller;


public class BlowOnDice extends SlashCommand {
    public BlowOnDice(){
        this.name = "blowondice";
        this.help = "Blows on the dice. This may or may not increase your luck.";
    }
    @Override
    protected void execute(SlashCommandEvent event) {
        Roller.rollDie(); // Rolls a useless die to advance RNG state
        event.reply("You have blown on the dice. May Luck Favor you.").queue();
    }
}
