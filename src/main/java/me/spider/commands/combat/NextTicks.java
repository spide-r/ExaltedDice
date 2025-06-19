package me.spider.commands.combat;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import me.spider.Main;
import me.spider.db.Combat;
import me.spider.db.ServerConfiguration;

public class NextTicks extends SlashCommand {
    public NextTicks(){
        this.name = "ticks";
        this.help = "Shows the next 6 ticks of action.";
    }
    @Override
    protected void execute(SlashCommandEvent event) {
        ServerConfiguration sc = Main.cc.getSettingsFor(event.getGuild());
        Combat combat = sc.getCombat(event.getChannelId());
        event.reply(combat.getNextSixTicks()).setEphemeral(true).queue();
    }
}
