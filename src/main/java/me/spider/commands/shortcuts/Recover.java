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

public class Recover extends SlashCommand {
    public Recover(){
        this.name = "recover";
        this.help = "Recovers motes. It first increases your peripheral, then personal.";
        this.options.add(new OptionData(OptionType.INTEGER, "amount", "How much essence are you restoring?", true));
        this.options.add(new OptionData(OptionType.STRING, "label", "Why are you recovering essence?"));

    }
    @Override
    protected void execute(SlashCommandEvent event) {
        ServerConfiguration c = Main.cc.getSettingsFor(event.getGuild());
        Character ch = c.getCharacter(event.getUser().getId());
        int amount = event.getOption("amount", Constants.ESSENCE, OptionMapping::getAsInt);
        String label = event.getOption("label", Constants.DEFAULT_LABEL, OptionMapping::getAsString);
        int maxEssence = ch.getPeripheralMax() + ch.getPersonalMax();

        int recoverResponse = ch.recoverMotes(amount);
        try {
            c.saveCharacter(ch);
        } catch (SQLException e) {
            event.reply("Issue saving character!").queue();
            return;
        }

        if(recoverResponse == -1){
            event.reply("You are restoring too much essence! You can restore a maximum of `" + ch.getPersonalMax() + "` personal and `" + ch.getPeripheralMax() + "` peripheral, totalling `" + maxEssence + "`.").queue();
        } else if(recoverResponse == 0){
            event.reply("Your peripheral essence is now: `" + ch.getPeripheralMotes() + "` due to " + label + ".").queue();
        } else if(recoverResponse == 1){
            event.reply("Your peripheral essence is now `" + ch.getPeripheralMotes() + "` due to " + label + ". Your personal essence is now `" + ch.getPersonalMotes() + "`.").queue();
        }
    }
}
