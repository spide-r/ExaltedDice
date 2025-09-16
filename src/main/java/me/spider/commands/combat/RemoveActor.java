package me.spider.commands.combat;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
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

public class RemoveActor extends SlashCommand {

    public RemoveActor(){
        this.name = "remove";
        this.help = "Removes an actor from future ticks.";
        this.options.add(new OptionData(OptionType.STRING, "name", "The name of the actor (if it isn't yourself).", false, true));
    }
    @Override
    protected void execute(SlashCommandEvent event) {
        String name = event.getOption("name", event.getUser().getId(), OptionMapping::getAsString).trim();
        ServerConfiguration sc = Main.cc.getSettingsFor(event.getGuild());
        if(!sc.isCombatActive(event.getChannelId())){
            event.reply("Combat has not started!").queue();
            return;
        }
        if(name.matches("<@\\d+>")){
            name = name.substring(2, name.length()-1);
        }
        Combat combat = sc.getCombat(event.getChannelId());
        if(combat.isStartOfCombat()){
            combat.removeFromJoinCombat(name);
        } else {
            combat.removeFromCombat(name);

        }
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

    @Override
    public void onAutoComplete(CommandAutoCompleteInteractionEvent event) {
        if(!event.getFocusedOption().getName().equals("name")){
            return;
        }
        List<Command.Choice> options = null;
        ServerConfiguration sc = Main.cc.getSettingsFor(event.getGuild());

        Combat combat = sc.getCombat(event.getChannelId());

        options = combat.getAllActors().stream().map(w -> {
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
        options = options.stream().filter(c -> c.getName().toLowerCase().contains(event.getFocusedOption().getValue().toLowerCase())).toList();
        if(options.size() > 25){
            options = options.subList(0,24);
        }
        event.replyChoices(options).queue();
    }
}
