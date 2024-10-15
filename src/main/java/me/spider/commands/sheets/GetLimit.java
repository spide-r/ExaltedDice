package me.spider.commands.sheets;

import me.spider.Main;
import me.spider.commands.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.sql.SQLException;

public class GetLimit extends Command {
    @Override
    public void OnCommand(SlashCommandInteractionEvent event) {
        if(!event.isFromGuild()){
            return;
        }
        String serverID = event.getGuild().getId();
        String userID = event.getUser().getId();
        try {
            int limit = Main.jdbcManager.getLimit(serverID, userID);
            event.reply("Limit: " + limit).queue();
        } catch (SQLException e) {
            e.printStackTrace();
            event.reply("SQL Exception!").queue();
        }
    }
}
