package me.spider.commands.combat;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import me.spider.Constants;
import me.spider.Main;
import me.spider.db.Combat;
import me.spider.db.ServerConfiguration;
import me.spider.dice.Roll;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.sql.SQLException;

public class JoinCombat extends SlashCommand {
    public JoinCombat(){
        this.name = "join";
        this.help = "Joins the current combat.";
        this.options.add(new OptionData(OptionType.INTEGER, "joinbattle", "What is your join battle dv?", true));
        this.options.add(new OptionData(OptionType.INTEGER, "successes", "Amount of automatic successes (if applicable)"));
        this.options.add(new OptionData(OptionType.STRING, "name", "The name of the actor (if it isn't yourself)."));
        this.options.add(new OptionData(OptionType.STRING, "label", "The label of the join battle roll."));
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        int dv = event.getOption("joinbattle", Constants.DICE_AMOUNT, OptionMapping::getAsInt);
        int success = event.getOption("successes", Constants.SUCCESSES, OptionMapping::getAsInt);
        String name = event.getOption("name", event.getUser().getId(), OptionMapping::getAsString).trim();
        String label = event.getOption("label", "Join Battle", OptionMapping::getAsString).trim();
        ServerConfiguration sc = Main.cc.getSettingsFor(event.getGuild());
        Combat combat = sc.getCombat(event.getChannelId());
        try{
            Roll roll = new Roll(dv, success, Constants.SUCCESS_THRESHOLD, label, false);
            int successes = roll.getHitsAndAutoSuccesses();
            boolean result = combat.joinCombat(name, successes);
            if(result){
                String newName = name;
                if(name.matches("\\d+")){
                    newName = "<@" + name + ">";
                }
                sc.saveCombat(combat);
                event.reply(roll.getStringResult() + "\n" + newName + " joined combat with " + successes + " successes.").queue();

            } else {
                event.reply("Error Adding to Join Battle. Has combat started already? If so, manually add the actor to a tick!").queue();
            }

        } catch (NullPointerException e){
            event.reply("Combat has not yet started!").queue();
        } catch (SQLException e) {
            e.printStackTrace();
            event.reply("Error joining combat!").queue();
        }
    }
}
