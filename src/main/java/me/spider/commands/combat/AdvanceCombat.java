package me.spider.commands.combat;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import me.spider.Main;
import me.spider.db.Combat;
import me.spider.db.ServerConfiguration;

import java.sql.SQLException;

public class AdvanceCombat extends SlashCommand {
    public AdvanceCombat(){
        this.name = "advance";
        this.help = "Advances Combat to the next tick where a combatant moves.";
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        ServerConfiguration sc = Main.cc.getSettingsFor(event.getGuild());
        Combat combat = sc.getCombat(event.getChannelId());
        if(!sc.isCombatInactive(event.getChannelId())){
            event.reply("Combat has not started!").queue();
            return;
        }
        if(combat.isStartOfCombat()){
            combat.tickZero();
            String status = combat.getStatus();
            String nextTicksForAllActors = combat.getAllActorsNextTick();
            try {
                sc.saveCombat(combat);
                event.reply("## Combat Has Started!\n\n" + nextTicksForAllActors + "\n\n" + status).queue();
            } catch (SQLException e) {
                e.printStackTrace();
                event.reply("Issue starting combat!").queue();
            }
        } else {
            combat.advanceTicks();
            try {
                sc.saveCombat(combat);
                event.reply("Combat tick has advanced!\n" + combat.getStatus()).queue();
            } catch (SQLException e) {
                e.printStackTrace();
                event.reply("Issue advancing combat!").queue();
            }
        }

    }
}
