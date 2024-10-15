package me.spider.commands.combat;

import me.spider.Main;
import me.spider.commands.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class NextActions extends Command {
    @Override
    public void OnCommand(SlashCommandInteractionEvent event) {
        String nextSix = Main.combatManager.getAllActorsNextTick(event.getChannelId());
        event.reply(nextSix).setEphemeral(true).queue();
    }
}
