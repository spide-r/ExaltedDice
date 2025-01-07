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
        int dv = event.getOption("joinbattle", Constants.DICE_AMOUNT, OptionMapping::getAsInt);
        int success = event.getOption("successes", Constants.SUCCESSES, OptionMapping::getAsInt);
        String name = event.getOption("name", event.getUser().getId(), OptionMapping::getAsString).trim();
        try{
            Roll roll = new Roll(dv, success, Constants.SUCCESS_THRESHOLD, "Join Battle", false);
            int successes = roll.getHitsAndAutoSuccesses();
            boolean result = Main.combatManager.joinCombat(event.getChannelId(), successes, name);
            if(result){
                if(name.matches("\\d+")){
                    event.reply("<@" + name + "> joined combat with " + successes + " successes." ).queue();
                } else {
                    event.reply(name + " joined combat with " + successes + " successes.").queue();
                }
            } else {
                event.reply("Error Adding to Join Battle Has combat started already? If so, manually add the actor to a tick!").queue();
            }

        } catch (NullPointerException e){
            event.reply("Combat has not yet started!").queue();
        }
    }
}
