package me.spider.commands.combat;

import me.spider.Main;
import me.spider.commands.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class TickZero extends Command {
    @Override
    public void OnCommand(SlashCommandInteractionEvent event) {
        Main.combatManager.tickZero(event.getChannelId());
        String status = Main.combatManager.getStatus(event.getChannelId());
        event.reply("## Combat Has Started!\n\n" + status).queue();
    }
}
