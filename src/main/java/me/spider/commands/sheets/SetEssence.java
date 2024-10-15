package me.spider.commands.sheets;

import me.spider.Constants;
import me.spider.Main;
import me.spider.commands.Command;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SetEssence extends Command {
    @Override
    public void OnCommand(SlashCommandInteractionEvent event) {
        if(!event.isFromGuild()){
            return;
        }
        String serverID = event.getGuild().getId();
        String userID = event.getUser().getId();
        /*
            motes.put("personalMotes", resultSet.getInt("personalMotes"));
            motes.put("personalMax", resultSet.getInt("personalMax"));
            motes.put("peripheralMotes", resultSet.getInt("peripheralMotes"));
            motes.put("peripheralMax", resultSet.getInt("peripheralMax"));
            motes.put("otherMotes", resultSet.getInt("otherMotes"));
            motes.put("otherMax", resultSet.getInt("otherMax"));
         */
        String essence = event.getOption("essence", Constants.ESSENCE_TYPE, OptionMapping::getAsString);
        int value = event.getOption("value", Constants.LIMIT_BREAK, OptionMapping::getAsInt);
        try {
            Main.jdbcManager.setEssence(serverID, userID,essence, value);
            event.reply("Your " + essence + " is now: " + value).queue();
        } catch (SQLException e) {
            event.reply("SQL Exception!").queue();
        }
    }

    @Override
    public void OnCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {
        if(event.getName().equals("setessence") && event.getFocusedOption().getName().equals("essence")){
            List<net.dv8tion.jda.api.interactions.commands.Command.Choice> options = Stream.of(Constants.ESSENCE_LIST).filter(w -> w.startsWith(event.getFocusedOption().getValue()))
                    .map(w -> new net.dv8tion.jda.api.interactions.commands.Command.Choice(w, w)).collect(Collectors.toList());
            event.replyChoices(options).queue();
        }
    }
}
