package me.spider.commands.combat;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import me.spider.Main;
import me.spider.db.Combat;
import me.spider.db.ServerConfiguration;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.sql.SQLException;

public class RemoveActor extends SlashCommand {

    public RemoveActor(){
        this.name = "remove";
        this.help = "Removes an actor from future ticks.";
        this.options.add(new OptionData(OptionType.STRING, "name", "The name of the actor (if it isn't yourself)."));
    }
    @Override
    protected void execute(SlashCommandEvent event) {
        String name = event.getOption("name", event.getUser().getId(), OptionMapping::getAsString).trim();
        ServerConfiguration sc = Main.cc.getSettingsFor(event.getGuild());
        if(!sc.isCombatInactive(event.getChannelId())){
            event.reply("Combat has not started!").queue();
            return;
        }
        if(name.matches("<@\\d+>")){
            name = name.substring(2, name.length()-1);
        }
        Combat combat = sc.getCombat(event.getChannelId());
        combat.removeFromCombat(name);
        try {
            if(name.matches("\\d+")){
                event.reply("<@" + name + "> removed from combat." ).queue();

            } else {
                event.reply(name + " removed from combat.").queue();
            }
            sc.saveCombat(combat);
        } catch (SQLException e) {
            e.printStackTrace();
            event.reply("Issue removing from combat!").queue();
        }

    }
}
