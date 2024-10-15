package me.spider.commands.sheets;

import me.spider.Main;
import me.spider.commands.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.sql.SQLException;
import java.util.HashMap;

public class ResetEssences extends Command {
    @Override
    public void OnCommand(SlashCommandInteractionEvent event) {
        try {
            HashMap<String, Integer> essences = Main.jdbcManager.getAllEssences(event.getGuild().getId(), event.getUser().getId());
            essences.put("personalMotes", (essences.get("personalMax") == null) ? 0 : essences.get("personalMax") );
            essences.put("peripheralMotes", (essences.get("peripheralMax") == null) ? 0 : essences.get("peripheralMax") );
            essences.put("otherMotes", (essences.get("otherMax") == null) ? 0 : essences.get("otherMax"));
            Main.jdbcManager.setAllEssences(event.getGuild().getId(), event.getUser().getId(), essences);
            event.reply("All essences have been reset.").queue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
