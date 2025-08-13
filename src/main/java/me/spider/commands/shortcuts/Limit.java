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

public class Limit extends SlashCommand {
    public Limit(){
        this.name = "limit";
        this.help = "Spends/Restores willpower by the specified amount.";
        this.options.add(new OptionData(OptionType.INTEGER, "amount", "How much willpower?", true));
        this.options.add(new OptionData(OptionType.BOOLEAN, "restore", "Are you restoring it?"));
    }
    @Override
    protected void execute(SlashCommandEvent event) {
        int amount = event.getOption("amount", Constants.ESSENCE, OptionMapping::getAsInt);
        boolean restore = event.getOption("restore", false, OptionMapping::getAsBoolean);
        ServerConfiguration c = Main.cc.getSettingsFor(event.getGuild());
        Character ch = c.getCharacter(event.getUser().getId());
        try {
            if(restore){
                amount = amount * -1;
            }
            ch.setLimitbreak(ch.getLimitbreak() + amount);
            c.saveCharacter(ch);
            event.reply("Your limit is now:\n" + ch.getFancyLimitBreakBoxes()).queue();
        } catch (SQLException e) {
            event.reply("Issue saving character!").queue();
        }
    }
}
