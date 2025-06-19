package me.spider.commands.combat;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import me.spider.Main;
import me.spider.db.Combat;
import me.spider.db.ServerConfiguration;

import java.util.HashSet;

public class Ready extends SlashCommand {
    public Ready(){
        this.name = "ready";
        this.help = "Shows who is ready for combat.";
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        ServerConfiguration sc = Main.cc.getSettingsFor(event.getGuild());
        Combat combat = sc.getCombat(event.getChannelId());
        HashSet<String> part = combat.getParticipantsThatJoinedCombat();
        StringBuilder builder = new StringBuilder();
        part.forEach(e -> builder.append((e.matches("\\d+") ? "<@" + e + ">" : e )).append("\n"));
        event.reply("The following actors have joined combat: " + builder).setEphemeral(true).queue();
    }
}
