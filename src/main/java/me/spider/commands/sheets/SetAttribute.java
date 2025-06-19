package me.spider.commands.sheets;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import me.spider.Constants;
import me.spider.Main;
import me.spider.commands.Command;
import me.spider.db.Character;
import me.spider.db.ServerConfiguration;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.sql.SQLException;

public class SetAttribute extends SlashCommand {
    public SetAttribute(){
        this.name = "set";
        this.help = "Sets attribute to a value.";
        this.options.add(new OptionData(OptionType.STRING, "attribute", "Which attribute?", true, true));
        this.options.add(new OptionData(OptionType.INTEGER, "value", "The number to set the attribute to.", true));
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        String attribute = event.getOption("attribute", Constants.ATTRIBUTE, OptionMapping::getAsString);
        int value = event.getOption("value", Constants.ESSENCE_MODIFIER, OptionMapping::getAsInt);

        if(!Constants.isValidAttribute(attribute)){
            event.reply("The attribute is invalid!").queue();
        }
        ServerConfiguration c = Main.cc.getSettingsFor(event.getGuild());
        Character ch = c.getCharacter(event.getId());

        if(attribute.equals("essences")){
            event.reply("Only set one attribute at a time, sorry!").queue();

        } else {

            ch.setInt(attribute, value);
            try {
                c.saveCharacter(ch);
                event.reply("Your " + attribute + " is now: " + value).queue();
            } catch (SQLException e) {
                e.printStackTrace();
                event.reply("Issue setting attribute!").queue();
            }
        }
    }
}
