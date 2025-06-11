package me.spider.commands.funny;

import me.spider.commands.Command;
import me.spider.dice.Roller;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;


public class BlowOnDice extends Command {
    @Override
    public void OnCommand(SlashCommandInteractionEvent event) {
        Roller.rollDie(); // Rolls a useless die to advance RNG state
        event.reply("You have blown on the dice. May Luck Favor you.").queue();
    }
}
