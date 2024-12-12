package me.spider.commands.combat;

import me.spider.Constants;
import me.spider.Main;
import me.spider.commands.Command;
import me.spider.dice.Roll;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

public class JoinCombat extends Command {
    @Override
    public void OnCommand(SlashCommandInteractionEvent event) {
        //todo if you join combat twice, the first successes is used - this needs to be changed
        int dv = event.getOption("joinbattle", Constants.DICE_AMOUNT, OptionMapping::getAsInt);
        int success = event.getOption("successes", Constants.SUCCESSES, OptionMapping::getAsInt);
        String name = event.getOption("name", event.getUser().getId(), OptionMapping::getAsString);
        try{
            Roll roll = new Roll(dv, success, Constants.SUCCESS_THRESHOLD, "Join Battle", false);
            int successes = roll.getHitsAndAutoSuccesses();
            Main.combatManager.joinCombat(event.getChannelId(), successes, name);
            if(name.matches("\\d+")){
                event.reply("<@" + name + "> joined combat with " + successes + " successes." ).queue();

            } else {
                event.reply(name + " joined combat with " + successes + " successes.").queue();
            }
        } catch (NullPointerException e){
            event.reply("Combat has not yet started!").queue();
        }
    }
}
