package me.spider.commands.combat;

import me.spider.Main;
import me.spider.combat.CombatManager;
import me.spider.combat.TickTracker;
import me.spider.commands.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.HashSet;

public class Ready extends Command {
    @Override
    public void OnCommand(SlashCommandInteractionEvent event) {
        TickTracker tracker = Main.combatManager.getCombat(event.getChannelId());
        HashSet<String> part = tracker.getParticipantsThatJoinedCombat();
        StringBuilder builder = new StringBuilder();
        part.forEach(e -> builder.append((e.matches("\\d+") ? "<@" + e + ">" : e )).append("\n"));
        event.reply("The following actors have joined combat: " + builder).setEphemeral(true).queue();
    }
}
