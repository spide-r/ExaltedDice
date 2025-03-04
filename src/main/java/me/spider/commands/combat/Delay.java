package me.spider.commands.combat;

import me.spider.Constants;
import me.spider.Main;
import me.spider.commands.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

public class Delay extends Command {
    @Override
    public void OnCommand(SlashCommandInteractionEvent event) {
        int ticksToDelay = event.getOption("delay", Constants.DEFAULT_TICK, OptionMapping::getAsInt);
        String name = event.getOption("name", event.getUser().getId(), OptionMapping::getAsString);
        int newTick = Main.combatManager.delay(event.getChannelId(), ticksToDelay, name);

        if(name.matches("\\d+")){
            event.reply("Delaying <@" + name + "> to Tick " + newTick).queue();
        } else {
            event.reply("Delaying " + name + " to Tick " + newTick).queue();
        }

    }
}
