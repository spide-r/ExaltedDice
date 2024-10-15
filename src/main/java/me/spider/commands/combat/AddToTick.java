package me.spider.commands.combat;

import me.spider.Constants;
import me.spider.Main;
import me.spider.commands.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

public class AddToTick extends Command {
    @Override
    public void OnCommand(SlashCommandInteractionEvent event) {
        String channel = event.getChannelId();
        String name = event.getOption("name", event.getUser().getId(), OptionMapping::getAsString);
        int tick = event.getOption("tick", Constants.DEFAULT_TICK, OptionMapping::getAsInt);
        Main.combatManager.addToTick(channel, tick, name);
        if(name.matches("\\d+")){
            event.reply("Adding <@" + name + "> to Tick " + tick).queue();
        } else {
            event.reply("Adding " + name + " to Tick " + tick).queue();
        }

    }
}
