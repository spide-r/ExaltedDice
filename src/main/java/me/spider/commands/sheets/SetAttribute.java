package me.spider.commands.sheets;

import me.spider.Constants;
import me.spider.Main;
import me.spider.commands.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.sql.SQLException;

public class SetAttribute extends Command {
    @Override
    public void OnCommand(SlashCommandInteractionEvent event) {
        if(!event.isFromGuild()){
            return;
        }
        String attribute = event.getOption("attribute", Constants.ATTRIBUTE, OptionMapping::getAsString);
        int newValue = event.getOption("value", Constants.ESSENCE_MODIFIER, OptionMapping::getAsInt);
        if(!Constants.isValidAttribute(attribute)){
            event.reply("The attribute is invalid!").queue();
        }
        String serverID = event.getGuild().getId();
        String userID = event.getUser().getId();

        try{
            if(attribute.equals("essences")){
                event.reply("Only modify one attribute at once, sorry!").queue();
            } else {
                Main.jdbcManager.setInt(serverID, userID, attribute, newValue);
                event.reply("Your " + attribute + " is now: " + newValue).queue();
            }
        } catch (SQLException e){
            e.printStackTrace();
            event.reply("Sqlexception!").queue();
        }

    }
}
