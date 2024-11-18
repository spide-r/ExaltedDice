package me.spider.commands.sheets;

import me.spider.commands.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class HelpAttribute extends Command {
    @Override
    public void OnCommand(SlashCommandInteractionEvent event) {
        event.reply("Ask spider for help lol come back soon.").queue();
    }
}
