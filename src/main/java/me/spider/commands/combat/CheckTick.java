package me.spider.commands.combat;

import me.spider.Constants;
import me.spider.Main;
import me.spider.commands.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

public class CheckTick extends Command {
    @Override
    public void OnCommand(SlashCommandInteractionEvent event) {
        int tick = event.getOption("tick", Constants.DEFAULT_TICK, OptionMapping::getAsInt);
        String tickStatus = Main.combatManager.getActorsAt(tick, event.getChannelId());
        event.reply(tickStatus).setEphemeral(true).queue();
    }
}
