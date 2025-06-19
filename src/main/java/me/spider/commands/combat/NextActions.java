package me.spider.commands.combat;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import me.spider.Main;
import me.spider.db.Combat;
import me.spider.db.ServerConfiguration;

public class NextActions extends SlashCommand {
    public NextActions(){
        this.name = "actors";
        this.help = "Shows every actors next tick.";
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        ServerConfiguration sc = Main.cc.getSettingsFor(event.getGuild());
        Combat combat = sc.getCombat(event.getChannelId());
        String nextActions = combat.getAllActorsNextTick();
        event.reply(nextActions).setEphemeral(true).queue();

    }
}
