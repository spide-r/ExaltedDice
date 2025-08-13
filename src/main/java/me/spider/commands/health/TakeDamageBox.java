package me.spider.commands.health;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import me.spider.Main;
import me.spider.db.Character;
import me.spider.db.ServerConfiguration;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.sql.SQLException;

public class TakeDamageBox extends SlashCommand {
    public TakeDamageBox(String type){
        this.name = type;
        this.help = "Adds " + type + " damage ";
        this.options.add(new OptionData(OptionType.INTEGER, "amount", "How much damage did you take?", true));
    }
    @Override
    protected void execute(SlashCommandEvent event) {
        int amount = event.getOption("amount", 1, OptionMapping::getAsInt);

        ServerConfiguration c = Main.cc.getSettingsFor(event.getGuild());
        Character ch = c.getCharacter(event.getUser().getId());
        switch (this.name){
            case "lethal":
                ch.takeLethal(amount);
                break;
            case "bashing":
                ch.takeBashing(amount);
                break;
            case "aggravated":
                ch.takeAggravated(amount);
                break;
        }
        event.reply("Took " + amount + " " + this.name + " damage.\n" + ch.getFancyDamageBoxes()).queue();
        try {
            c.saveCharacter(ch);
        } catch (SQLException e) {
            event.reply("Issue setting attribute!").queue();
        }
    }
}
