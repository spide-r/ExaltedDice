package me.spider.commands.shortcuts;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import me.spider.Main;
import me.spider.db.Character;
import me.spider.db.ServerConfiguration;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.sql.SQLException;

public class HealDamage extends SlashCommand {
    public HealDamage(String type){
        this.name = type;
        this.help = "Heals " + type + " damage ";
        this.options.add(new OptionData(OptionType.INTEGER, "amount", "How much damage did you heal?", true));
    }
    @Override
    protected void execute(SlashCommandEvent event) {
        int amount = event.getOption("amount", 1, OptionMapping::getAsInt);

        ServerConfiguration c = Main.cc.getSettingsFor(event.getGuild());
        Character ch = c.getCharacter(event.getUser().getId());
        switch (this.name){
            case "lethal":
                ch.removeLethal(amount);
                break;
            case "bashing":
                ch.removeBashing(amount);
                break;
            case "aggravated":
                ch.removeAggravated(amount);
                break;
        }
        event.reply("Healed " + amount + " " + this.name + " damage.\n" + ch.getFancyBoxes()).queue();
        try {
            c.saveCharacter(ch);
        } catch (SQLException e) {
            e.printStackTrace();
            event.reply("Issue saving!").queue();
        }
    }
}
