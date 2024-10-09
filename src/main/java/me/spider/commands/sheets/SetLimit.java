package me.spider.commands.sheets;

import me.spider.Constants;
import me.spider.Main;
import me.spider.commands.GenericCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.sql.SQLException;

public class SetLimit extends GenericCommand {
    @Override
    public void OnCommand(SlashCommandInteractionEvent event) {
        if(!event.isFromGuild()){
            return;
        }
        String serverID = event.getGuild().getId();
        String userID = event.getUser().getId();
        int limit = event.getOption("limitbreak", Constants.LIMIT_BREAK, OptionMapping::getAsInt);
        try {
            Main.jdbcManager.setLimit(serverID, userID, limit);
            event.reply("Your limit is now: " + limit).queue();
        } catch (SQLException e) {
            event.reply("SQL Exception!").queue();
        }
    }
}