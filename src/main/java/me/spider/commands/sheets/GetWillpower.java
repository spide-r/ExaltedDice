package me.spider.commands.sheets;

import me.spider.Main;
import me.spider.commands.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.sql.SQLException;

public class GetWillpower extends Command {
    @Override
    public void OnCommand(SlashCommandInteractionEvent event) {
        if(!event.isFromGuild()){
            return;
        }
        String serverID = event.getGuild().getId();
        String userID = event.getUser().getId();
        try {
            int limit = Main.jdbcManager.getWillpower(serverID, userID);
            event.reply("Willpower: " + limit).queue();
        } catch (SQLException e) {
            event.reply("SQL Exception!").queue();
        }
    }
}
