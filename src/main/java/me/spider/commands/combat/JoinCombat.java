package me.spider.commands.combat;

import me.spider.Constants;
import me.spider.Main;
import me.spider.commands.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

public class JoinCombat extends Command {
    @Override
    public void OnCommand(SlashCommandInteractionEvent event) {
        int successes = event.getOption("successes", Constants.SUCCESSES, OptionMapping::getAsInt);
        String name = event.getOption("name", event.getUser().getId(), OptionMapping::getAsString);
        Main.combatManager.joinCombat(event.getChannelId(), successes, name);
        if(name.matches("\\d+")){
            event.reply("<@" + name + "> joined combat with  " + successes + " successes." ).queue();

        } else {
            event.reply(name + " joined combat with  " + successes + " successes.").queue();
        }
    }
}
