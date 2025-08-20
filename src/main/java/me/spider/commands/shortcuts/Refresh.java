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

public class Refresh extends SlashCommand {
    public Refresh(){
        this.name = "refresh";
        this.help = "Refreshes essence motes to their max value. This also restores willpower.";
        this.options.add(new OptionData(OptionType.BOOLEAN, "wp", "Restore Willpower as well?"));
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        ServerConfiguration serverConfiguration = Main.cc.getSettingsFor(event.getGuild());
        boolean wp = event.getOption("wp", true, OptionMapping::getAsBoolean);

        Character c = serverConfiguration.getCharacter(event.getUser().getId());
        c.setPeripheralMotes(c.getPeripheralMax());
        c.setPersonalMotes(c.getPersonalMax());
        c.setOtherMotes(c.getOtherMax());
        if(wp){
            c.setWillpower(10);
            //c.setLimitbreak(0);
        }

        try {
            serverConfiguration.saveCharacter(c);
            event.reply("All essences have been refreshed." + ((wp) ? "Willpower has been restored." : "")).queue();

        } catch (SQLException e) {
            event.reply("Error refreshing essence!").queue();
        }

    }
}
