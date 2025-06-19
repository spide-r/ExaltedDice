package me.spider.commands.combat;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import me.spider.Main;
import me.spider.db.ServerConfiguration;

public class CurrentTick extends SlashCommand {

    public CurrentTick(){
        this.name = "tick";
        this.help = "Shows the current Tick Data";
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        ServerConfiguration sc = Main.cc.getSettingsFor(event.getGuild());
        String status = sc.getCombat(event.getChannelId()).getStatus();
        event.reply(status).setEphemeral(true).queue();
    }
}
