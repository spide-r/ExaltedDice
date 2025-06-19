package me.spider.commands.sheets;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import me.spider.Main;
import me.spider.db.Character;
import me.spider.db.ServerConfiguration;

import java.sql.SQLException;

public class Refresh extends SlashCommand {
    public Refresh(){
        this.name = "refresh";
        this.help = "Refreshes essence motes to their max value.";
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        ServerConfiguration serverConfiguration = Main.cc.getSettingsFor(event.getGuild());
        Character c = serverConfiguration.getCharacter(event.getUser().getId());
        c.setPeripheralMotes(c.getPeripheralMax());
        c.setPersonalMotes(c.getPersonalMax());
        c.setOtherMotes(c.getOtherMax());
        try {
            serverConfiguration.saveCharacter(c);
            event.reply("All essences have been refreshed.").queue();

        } catch (SQLException e) {
            e.printStackTrace();
            event.reply("Error refreshing essence!").queue();
        }

    }
}
