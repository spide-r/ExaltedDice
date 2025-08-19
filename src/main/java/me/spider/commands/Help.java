package me.spider.commands;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;

public class Help extends SlashCommand {
    public Help(){
        this.name = "help";
        this.help = "Learn about the bot!";
    }

    @Override
    protected void execute(SlashCommandEvent slashCommandEvent) {
        //todo
        /*
        /Roll and /damage
        Tick Tracking (redirect to /combat help)
        Sheets
        Health
        Shortcuts
        funny
         */
    }
}
