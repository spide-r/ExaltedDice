package me.spider.commands.combat;

import me.spider.Main;
import me.spider.commands.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class AdvanceCombat extends Command {
    @Override
    public void OnCommand(SlashCommandInteractionEvent event) {
        Main.combatManager.advanceTicks(event.getChannelId());
        event.reply("Combat tick has advanced!\n" + Main.combatManager.getStatus(event.getChannelId())).queue();
    }
}
