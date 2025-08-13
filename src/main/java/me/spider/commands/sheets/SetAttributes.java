package me.spider.commands.sheets;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import me.spider.Constants;
import me.spider.Main;
import me.spider.db.Character;
import me.spider.db.ServerConfiguration;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.sql.SQLException;

public class SetAttributes extends SlashCommand {
    public SetAttributes(){
        this.name = "set";
        this.help = "This sets all of your sheet attributes.";
        for (String s : Constants.ATTRIBUTE_LIST) {
            this.options.add(new OptionData(OptionType.INTEGER, s.toLowerCase(), "Sets your " + s + " to the specified value"));
        }

    }
    @Override
    protected void execute(SlashCommandEvent event) {
        ServerConfiguration c = Main.cc.getSettingsFor(event.getGuild());
        Character ch = c.getCharacter(event.getUser().getId());
        StringBuilder builder = new StringBuilder();
        builder.append("The following changes have been made:\n");
        for (OptionMapping option : event.getOptionsByType(OptionType.INTEGER)) {
            String attribute = option.getName();
            int amount = option.getAsInt();
            ch.setInt(attribute, amount);
            builder.append("Your ").append(attribute).append(" is now: ").append(amount).append(".\n");

        }
        try {
            c.saveCharacter(ch);
            event.reply(builder.substring(0, builder.length()-1)).queue();
        } catch (SQLException e) {
            event.reply("Issue setting attribute!").queue();
        }
    }
}
