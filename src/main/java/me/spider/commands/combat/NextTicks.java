package me.spider.commands.combat;

import me.spider.Main;
import me.spider.commands.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class NextTicks extends Command {
    @Override
    public void OnCommand(SlashCommandInteractionEvent event) {
        String nextSix = Main.combatManager.getNextSixTicks(event.getChannelId());
        event.reply(nextSix).setEphemeral(true).queue();
    }
}
