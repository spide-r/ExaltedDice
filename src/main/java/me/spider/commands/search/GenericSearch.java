package me.spider.commands.search;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import me.spider.Main;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class GenericSearch extends SlashCommand {
    public GenericSearch(String toSearch){
        this.name = toSearch;
        this.help = "Searches for a specified " + toSearch;
        this.options.add(new OptionData(OptionType.STRING, "name", "What are you trying to search?", true, true));
        this.options.add(new OptionData(OptionType.BOOLEAN, "hide", "Do you wish to hide the search?", false));
    }
    @Override
    protected void execute(SlashCommandEvent event) {
        String toSearch = event.getOption("name", "unknown", OptionMapping::getAsString);
        boolean hide = event.getOption("hide", false, OptionMapping::getAsBoolean);


        try {
            String s = Main.bookManager.getPage(this.name, toSearch).getFancyText();
            if(s.length() > 2000){
                s = s.substring(0, 1950) + "**[Description cut to fit Discord's limits]**";
            }
            event.reply(s).setEphemeral(hide).queue();
        } catch (SQLException | NullPointerException e) {
            event.reply("Unable to find `" + toSearch + "`!").setEphemeral(hide).queue();
        }
    }

    @Override
    public void onAutoComplete(CommandAutoCompleteInteractionEvent event) {
        if(event.getFocusedOption().getValue().length() < 2){
            return;
        }
        List<Command.Choice> options = null;
        try {
            options = Main.bookManager.getKeys(this.name).stream().filter(w -> w.contains(event.getFocusedOption().getValue()))
                    .map(w -> new Command.Choice(w, w)).collect(Collectors.toList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        event.replyChoices(options).queue();
    }
}
