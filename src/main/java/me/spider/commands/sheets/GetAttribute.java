package me.spider.commands.sheets;

import me.spider.Constants;
import me.spider.Main;
import me.spider.commands.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.sql.SQLException;
import java.util.HashMap;

public class GetAttribute extends Command {
    @Override
    public void OnCommand(SlashCommandInteractionEvent event) {
        if(!event.isFromGuild()){
            return;
        }
        String attribute = event.getOption("attribute", Constants.ATTRIBUTE, OptionMapping::getAsString);
        if(!Constants.isValidAttribute(attribute)){
            event.reply("The attribute is invalid!").queue();
        }
        String serverID = event.getGuild().getId();
        String userID = event.getUser().getId();

        try{
            if(attribute.equals("essences")){
                HashMap<String, Integer> motes = Main.jdbcManager.getAllEssences(serverID, userID);
                String personal = "Personal: " + motes.get("personalMotes") + "/" + motes.get("personalMax");
                String peripheral = "Peripheral: " + motes.get("peripheralMotes") + "/" + motes.get("peripheralMax");
                String other = "Other: " + motes.get("otherMotes") + "/" + motes.get("otherMax");
                event.reply(personal + "\n" + peripheral + "\n" + other).queue();
            } else {
                int attributeInt = 0;
                attributeInt = Main.jdbcManager.getInt(serverID, userID, attribute);
                event.reply(attribute + ": " + attributeInt).queue();
            }
        } catch (SQLException e){
            e.printStackTrace();
            event.reply("Sqlexception!").queue();
        }





    }
}
