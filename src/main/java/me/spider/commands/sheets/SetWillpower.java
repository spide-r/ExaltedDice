package me.spider.commands.sheets;

import me.spider.Constants;
import me.spider.Main;
import me.spider.commands.GenericCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.sql.SQLException;

public class SetWillpower extends GenericCommand {
    @Override
    public void OnCommand(SlashCommandInteractionEvent event) {
        if(!event.isFromGuild()){
            return;
        }
        String serverID = event.getGuild().getId();
        String userID = event.getUser().getId();
        int willpower = event.getOption("value", Constants.WILLPOWER, OptionMapping::getAsInt);
        try {
            Main.jdbcManager.setWillpower(serverID, userID, willpower);
            event.reply("Your willpower is now: " + willpower).queue();
        } catch (SQLException e) {
            e.printStackTrace();
            event.reply("SQL Exception!").queue();
        }
    }
}
