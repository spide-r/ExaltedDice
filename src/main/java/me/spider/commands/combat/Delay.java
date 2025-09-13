package me.spider.commands.combat;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import me.spider.Constants;
import me.spider.Main;
import me.spider.db.Combat;
import me.spider.db.ServerConfiguration;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.UserSnowflake;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class Delay extends SlashCommand {

    public Delay(){
        this.name = "delay";
        this.help = "Delays your next action by a specified value";
        this.options.add(new OptionData(OptionType.INTEGER, "delay", "How Many ticks should the next action be delayed by?", true));
        this.options.add(new OptionData(OptionType.STRING, "name", "The name of the actor (if it isn't yourself).", false, true));
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        ServerConfiguration sc = Main.cc.getSettingsFor(event.getGuild());

        if(!sc.isCombatInactive(event.getChannelId())){
            event.reply("Combat has not started!").queue();
            return;
        }
        Combat combat = sc.getCombat(event.getChannelId());

        if(combat.isStartOfCombat()){
            event.reply("Combat has not yet started! Did you mean to join combat with `/combat join`?").queue();
            return;
        }


        int ticksToDelay = event.getOption("delay", Constants.DEFAULT_TICK, OptionMapping::getAsInt);
        String name = event.getOption("name", event.getUser().getId(), OptionMapping::getAsString).trim();
        if(name.matches("<@\\d+>")){
            name = name.substring(2, name.length()-1);
        }
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

    @Override
    public void onAutoComplete(CommandAutoCompleteInteractionEvent event) {
        if(!event.getFocusedOption().getName().equals("name")){
            return;
        }
        List<Command.Choice> options = null;
        ServerConfiguration sc = Main.cc.getSettingsFor(event.getGuild());

        Combat combat = sc.getCombat(event.getChannelId());

        options = combat.getAllActors().stream().filter(w -> w.toLowerCase().contains(event.getFocusedOption().getValue().toLowerCase()))
                .map(w -> {

                    if(w.length() > 100){
                        w = w.substring(0, 99);
                    }
                    String key = w;
                    String value = w;
                    if(w.matches("\\d+")){
                        if(event.getGuild() != null){
                            Member m = event.getGuild().getMember(UserSnowflake.fromId(w));
                            if(m != null){
                                key = m.getEffectiveName();
                            }
                        }
                    }
                    return new Command.Choice(key, value);

                }).collect(Collectors.toList());
        if(options.size() > 25){
            options = options.subList(0,24);
        }
        event.replyChoices(options).queue();
    }
}
