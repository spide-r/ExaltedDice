package me.spider.commands.sheets;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import me.spider.Main;
import me.spider.db.Character;
import me.spider.db.ServerConfiguration;

public class GetAttributes extends SlashCommand {

    public GetAttributes(){
        this.name = "get";
        this.help = "Gets Sheet Attributes";
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        ServerConfiguration c = Main.cc.getSettingsFor(event.getGuild());
        Character ch = c.getCharacter(event.getUser().getId());

        event.reply(ch.getAllAttributes()).queue();

    }
}
