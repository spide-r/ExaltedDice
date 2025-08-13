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

public class SetHealthLevels extends SlashCommand {
    public SetHealthLevels(){
        this.name = "sethealthlevels";
        this.help = "This sets all of your health levels.";
        this.options.add(new OptionData(OptionType.INTEGER, "minuszero", "Minus Zero Health Levels"));
        this.options.add(new OptionData(OptionType.INTEGER, "minusone", "Minus One Health Levels"));
        this.options.add(new OptionData(OptionType.INTEGER, "minustwo", "Minus Two Health Levels"));
        this.options.add(new OptionData(OptionType.INTEGER, "minusfour", "Minus Four Health Levels"));
        this.options.add(new OptionData(OptionType.INTEGER, "incap", "Incap Health Levels"));
        this.options.add(new OptionData(OptionType.INTEGER, "dying", "Dying Health Levels"));
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        int minusZero = event.getOption("minuszero", -1, OptionMapping::getAsInt);
        int minusOne = event.getOption("minusone", -1, OptionMapping::getAsInt);
        int minusTwo = event.getOption("minustwo", -1, OptionMapping::getAsInt);
        int minusFour = event.getOption("minusfour", -1, OptionMapping::getAsInt);
        int incap = event.getOption("incap", -1, OptionMapping::getAsInt);
        int dying = event.getOption("dying", -1, OptionMapping::getAsInt);
        ServerConfiguration c = Main.cc.getSettingsFor(event.getGuild());
        Character ch = c.getCharacter(event.getUser().getId());
        StringBuilder builder = new StringBuilder();
        builder.append("The following changes have been made:\n");
        if(minusZero > 0){
            ch.setHealthLevel(0, minusZero);
            builder.append("-0 Boxes: ").append(minusZero).append("\n");
        }

        if(minusOne > 0){
            ch.setHealthLevel(1, minusOne);
            builder.append("-1 Boxes: ").append(minusOne).append("\n");

        }

        if(minusTwo > 0){
            ch.setHealthLevel(2, minusTwo);
            builder.append("-2 Boxes: ").append(minusTwo).append("\n");

        }

        if(minusFour > 0){
            ch.setHealthLevel(4, minusFour);
            builder.append("-4 Boxes: ").append(minusFour).append("\n");

        }
        if(incap > 0){
            ch.setHealthLevel(5, incap);
            builder.append("Incap Boxes: ").append(incap).append("\n");
        }

        if(dying > 0){
            ch.setHealthLevel(6, dying);
            builder.append("Dying Boxes: ").append(dying);
        }
        try {
            c.saveCharacter(ch);
        } catch (SQLException e) {
            event.reply("Issue setting attribute!").queue();
        }

        event.reply(builder.toString()).queue();
    }
}
