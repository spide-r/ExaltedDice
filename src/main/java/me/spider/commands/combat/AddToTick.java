package me.spider.commands.combat;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import me.spider.Constants;
import me.spider.Main;
import me.spider.db.Combat;
import me.spider.db.ServerConfiguration;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.sql.SQLException;

public class AddToTick extends SlashCommand {

    public AddToTick(){
        this.name = "add";
        this.help = "Adds a specific actor to participate at a certain tick. !!! DIFFERENT FROM DELAYING !!!";
        this.options.add(new OptionData(OptionType.INTEGER, "tick", "The tick the actor should be added to.", true));
        this.options.add(new OptionData(OptionType.STRING, "name", "The name of the actor (if it isn't yourself)."));
    }
    @Override
    protected void execute(SlashCommandEvent event) {
        String name = event.getOption("name", event.getUser().getId(), OptionMapping::getAsString);
        int tick = event.getOption("tick", Constants.DEFAULT_TICK, OptionMapping::getAsInt);
        ServerConfiguration sc = Main.cc.getSettingsFor(event.getGuild());
        if(sc.isCombatInactive(event.getChannelId())){
            event.reply("Combat has not started!").queue();
        }

        try {
            Combat combat = sc.getCombat(event.getChannelId());
            combat.addToTick(tick, name);

            sc.saveCombat(combat);
            if(name.matches("\\d+")){
                event.reply("Adding <@" + name + "> to Tick " + tick).queue();
            } else {
                event.reply("Adding " + name + " to Tick " + tick).queue();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            event.reply("Error adding to combat!").queue();
        }

    }
}
