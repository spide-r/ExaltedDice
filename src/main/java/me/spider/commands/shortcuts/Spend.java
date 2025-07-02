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

public class Spend extends SlashCommand {
    public Spend(){
        this.name = "spend";
        this.help = "Burns motes. It first reduces from your personal, then peripheral.";
        this.options.add(new OptionData(OptionType.INTEGER, "amount", "How much essence are you spending?", true));
        this.options.add(new OptionData(OptionType.STRING, "label", "Why are you using essence?"));
    }
    @Override
    protected void execute(SlashCommandEvent event) {
        ServerConfiguration c = Main.cc.getSettingsFor(event.getGuild());
        Character ch = c.getCharacter(event.getUser().getId());
        int amount = event.getOption("amount", Constants.ESSENCE, OptionMapping::getAsInt);
        String label = event.getOption("label", Constants.DEFAULT_LABEL, OptionMapping::getAsString);
        int spendResponse = ch.spendMotes(amount);

        try {
            c.saveCharacter(ch);
        } catch (SQLException e) {
            event.reply("Issue saving character!").queue();
            return;
        }


        if(spendResponse == -1){
            event.reply("You do not have enough peripheral and personal motes!").queue();
        } else if(spendResponse == 0){
            event.reply("Your essence has been reduced by `"+ amount +"` due to " + label + ". You now have `" + ch.getPersonalMotes() + "` personal motes.").queue();
        } else if(spendResponse == 1){
            event.reply("Your essence has been reduced by `"+ amount +"` due to "+ label +". You now have `" + ch.getPersonalMotes() + "` personal motes and `" + ch.getPeripheralMotes() + "` peripheral motes." +
                    "\nYour anima effect is now: `" + ch.getAnimaEffect() + "`.").queue();
        }

    }


}
