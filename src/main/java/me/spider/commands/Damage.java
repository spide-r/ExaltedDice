package me.spider.commands;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import me.spider.Constants;
import me.spider.dice.DamageRoll;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class Damage extends SlashCommand {
    public Damage(){
        this.name = "damage";
        this.help = "Rolls a die. 10's count as 1 hit.";
        this.options.add(new OptionData(OptionType.INTEGER, "amount", "Amount Of Dice", true));
        this.options.add(new OptionData(OptionType.STRING, "label", "Describe what this dice roll is for", false, false));
        this.options.add(new OptionData(OptionType.INTEGER, "successes", "Amount of Automatic Successes", false, false));
        this.options.add(new OptionData(OptionType.INTEGER, "target", "If the target number needs to be changed, change it here.", false, false));
        this.options.add(new OptionData(OptionType.BOOLEAN, "private", "If the dice roll should be shown only to you.", false, false));
    }
    @Override
    protected void execute(SlashCommandEvent event) {
        int amount = event.getOption("amount", Constants.DICE_AMOUNT, OptionMapping::getAsInt);
        int successes = event.getOption("successes", Constants.SUCCESSES, OptionMapping::getAsInt);
        int target = event.getOption("target", Constants.TARGET_NUMBER, OptionMapping::getAsInt);
        String label = event.getOption("label", Constants.DEFAULT_LABEL, OptionMapping::getAsString);
        boolean privateRoll = event.getOption("private",Constants.PRIVATE_ROLL, OptionMapping::getAsBoolean);
        DamageRoll roll = new DamageRoll(amount, successes, target, label, privateRoll);
        String results = roll.getStringResult();
        event.reply(results).setEphemeral(privateRoll).queue();

    }

}
