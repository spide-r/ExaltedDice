package me.spider.commands.combat;

import me.spider.Constants;
import me.spider.Main;
import me.spider.commands.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

public class Preview extends Command {
    @Override
    public void OnCommand(SlashCommandInteractionEvent event) {
        String data = event.getOption("data", Constants.COMBAT_DATA[0], OptionMapping::getAsString);
        if(data.equals("actions")){
            String allActorsAndActions = Main.combatManager.getAllActorsNextTick(event.getChannelId());
            event.reply("Next Actors and Actions:\n" + allActorsAndActions).setEphemeral(true).queue();
        } else {
            String nextSix = Main.combatManager.getNextSixTicks(event.getChannelId());
            event.reply("Next 6 Ticks:\n" + nextSix).setEphemeral(true).queue();
        }

    }
}
