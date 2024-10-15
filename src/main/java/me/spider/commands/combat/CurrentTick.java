package me.spider.commands.combat;

import me.spider.Main;
import me.spider.commands.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class CurrentTick extends Command {
    @Override
    public void OnCommand(SlashCommandInteractionEvent event) {
        String status = Main.combatManager.getStatus(event.getChannelId());
        event.reply(status).queue();
    }
}
