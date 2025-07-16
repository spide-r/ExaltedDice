package me.spider.commands.sheets;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import me.spider.Main;
import me.spider.db.Character;
import me.spider.db.ServerConfiguration;

public class Health extends SlashCommand {
    public Health(){
        this.name = "health";
        this.help = "Shows your current health levels";
    }
    @Override
    protected void execute(SlashCommandEvent event) {
        ServerConfiguration c = Main.cc.getSettingsFor(event.getGuild());
        Character ch = c.getCharacter(event.getUser().getId());
        event.reply(ch.getFancyBoxes()).queue();
    }
}
