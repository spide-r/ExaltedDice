package me.spider.commands.combat;

import me.spider.Main;
import me.spider.commands.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class AdvanceCombat extends Command {
    @Override
    public void OnCommand(SlashCommandInteractionEvent event) {
        if(Main.combatManager.getCombat(event.getChannelId()).isStartOfCombat()){
            Main.combatManager.tickZero(event.getChannelId());
            String status = Main.combatManager.getStatus(event.getChannelId());
            event.reply("## Combat Has Started!\n\n" + status).queue();
        } else {
            Main.combatManager.advanceTicks(event.getChannelId());
            event.reply("Combat tick has advanced!\n" + Main.combatManager.getStatus(event.getChannelId())).queue();
        }

    }
}
