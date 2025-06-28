package me.spider.commands.shortcuts;

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

public class Stunt extends SlashCommand {
    public Stunt(){
        this.name = "stunt";
        this.help = "Automatically regenerates essence depending on the stunt level";
        this.options.add(new OptionData(OptionType.INTEGER, "level", "What level is the stunt?", true));
    }
    @Override
    protected void execute(SlashCommandEvent event) {
        int level = event.getOption("level", Constants.STUNT, OptionMapping::getAsInt);

        if(level > 3 || level < 0){
            event.reply("Stunts can only be levels 1, 2, 3.").queue();
            return;
        }

        ServerConfiguration c = Main.cc.getSettingsFor(event.getGuild());
        Character ch = c.getCharacter(event.getUser().getId());
        ch.recoverMotes(level *2);
        try {
            c.saveCharacter(ch);
        } catch (SQLException e) {
            event.reply("Issue saving character!").queue();
            return;
        }

        event.reply("Recovered `" + level *2 + "` motes.").queue();
    }
}
