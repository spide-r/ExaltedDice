package me.spider.commands.sheets;

import me.spider.Main;
import me.spider.commands.GenericCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.sql.SQLException;
import java.util.HashMap;

public class GetEssences extends GenericCommand {
    @Override
    public void OnCommand(SlashCommandInteractionEvent event) {
        if(!event.isFromGuild()){
            return;
        }
        String serverID = event.getGuild().getId();
        String userID = event.getUser().getId();
        try {
            HashMap<String, Integer> motes = Main.jdbcManager.getAllEssences(serverID, userID);
            String personal = "Personal: " + motes.get("personalMotes") + "/" + motes.get("personalMax");
            String peripheral = "Peripheral: " + motes.get("peripheralMotes") + "/" + motes.get("peripheralMax");
            String other = "Other: " + motes.get("otherMotes") + "/" + motes.get("otherMax");
            event.reply(personal + "\n" + peripheral + "\n" + other).queue();
        } catch (SQLException e) {
            e.printStackTrace();
            event.reply("SQL Exception!").queue();
        }
    }
    //shows all essence

}
