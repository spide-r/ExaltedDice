package me.spider.commands.shortcuts;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import me.spider.Main;
import me.spider.db.Character;
import me.spider.db.ServerConfiguration;

public class Essence extends SlashCommand {
    public Essence(){
        this.name = "essence";
        this.help = "Shows all of your essence";
    }
    @Override
    protected void execute(SlashCommandEvent event) {
        ServerConfiguration c = Main.cc.getSettingsFor(event.getGuild());
        Character ch = c.getCharacter(event.getUser().getId());

        String personal = "Personal: " + ch.getPersonalMotes() + "/" + ch.getPersonalMax();
        String peripheral = "Peripheral: " + ch.getPeripheralMotes() + "/" + ch.getPeripheralMax();
        String other = "Other: " + ch.getOtherMotes() + "/" + ch.getOtherMax();
        event.reply(personal + "\n" + peripheral + "\n" + other).queue();
    }
}
