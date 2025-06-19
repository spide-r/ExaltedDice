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

public class Delay extends SlashCommand {

    public Delay(){
        this.name = "delay";
        this.help = "Delays your next action by a specified value";
        this.options.add(new OptionData(OptionType.INTEGER, "delay", "How Many ticks should the next action be delayed by?", true));
        this.options.add(new OptionData(OptionType.STRING, "name", "The name of the actor (if it isn't yourself)."));
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        ServerConfiguration sc = Main.cc.getSettingsFor(event.getGuild());
        Combat combat = sc.getCombat(event.getChannelId());

        int ticksToDelay = event.getOption("delay", Constants.DEFAULT_TICK, OptionMapping::getAsInt);
        String name = event.getOption("name", event.getUser().getId(), OptionMapping::getAsString);
        int newTick = combat.delay(ticksToDelay, name);

        try {
            sc.saveCombat(combat);
            if(name.matches("\\d+")){
                event.reply("Delaying <@" + name + "> to Tick " + newTick).queue();
            } else {
                event.reply("Delaying " + name + " to Tick " + newTick).queue();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            event.reply("Issue saving combat!").queue();
        }
    }
}
