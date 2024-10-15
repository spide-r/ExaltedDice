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

public class ModifyEssence extends Command {

    @Override
    public void OnCommand(SlashCommandInteractionEvent event) {
        if(!event.isFromGuild()){
            return;
        }
        String serverID = event.getGuild().getId();
        String userID = event.getUser().getId();
        String essence = event.getOption("essence", Constants.ESSENCE_TYPE, OptionMapping::getAsString);
        int valueToChange = event.getOption("value", Constants.ESSENCE_MODIFIER, OptionMapping::getAsInt);

        try {
            int oldNumber = Main.jdbcManager.getEssenceValue(serverID,userID,essence);
            int newEssence = oldNumber + valueToChange;
            Main.jdbcManager.setEssence(serverID, userID, essence, newEssence);
            event.reply("Your " + essence + " is now: " + newEssence).queue();
        } catch (SQLException e) {
            e.printStackTrace();
            event.reply("SQL Exception!").queue();
        }
    }

    @Override
    public void OnCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {
        if(event.getName().equals("modifyessence") && event.getFocusedOption().getName().equals("essence")){
            List<net.dv8tion.jda.api.interactions.commands.Command.Choice> options = Stream.of(Constants.ESSENCE_LIST).filter(w -> w.startsWith(event.getFocusedOption().getValue()))
                    .map(w -> new net.dv8tion.jda.api.interactions.commands.Command.Choice(w, w)).collect(Collectors.toList());
            event.replyChoices(options).queue();
        }
    }
}
