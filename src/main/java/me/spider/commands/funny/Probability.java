package me.spider.commands.funny;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import me.spider.Constants;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class Probability extends SlashCommand {
    public Probability(){
        this.name = "probability";
        this.help = "Checks your luck against the mean.";
        this.options.add(new OptionData(OptionType.INTEGER, "successes", "How many successes did you hit?", true));
        this.options.add(new OptionData(OptionType.INTEGER, "dice", "How many dice did you throw?", true));
        this.options.add(new OptionData(OptionType.INTEGER, "threshold", "What is your success threshold?"));
        this.options.add(new OptionData(OptionType.BOOLEAN, "doublehit", "Do 10's count as a double hit?"));
    }
    @Override
    protected void execute(SlashCommandEvent event) {
        int dice = event.getOption("dice", Constants.DICE_AMOUNT, OptionMapping::getAsInt);
        if(dice < 1){
            event.reply("You must provide more than one die!").queue();
            return;
        }
        int successes = event.getOption("successes", Constants.SUCCESSES, OptionMapping::getAsInt);
        int threshold = event.getOption("threshold", Constants.SUCCESS_THRESHOLD, OptionMapping::getAsInt);
        if(threshold > 9 || threshold < 1 ){
            event.reply("The success threshold must be between 1 and 9").queue();
            return;
        }
        boolean doubleHit = event.getOption("doublehit", true, OptionMapping::getAsBoolean);
        double percent = getPercentSuccess(threshold, doubleHit);
        int average = getAverageHits(percent, dice);
        String curve = (average == successes) ? "on top" : (average > successes) ? "below" : "above";
        event.reply("You hit " + successes + "/" + dice + ". You are " + curve + " the curve. On average, you would hit " + average + " successes.\n-# Tens are " + ((doubleHit) ? "two hits." : "one hit.") + "\n-# The success threshold is " + threshold + ".").queue();
    }

    public int getAverageHits(double percentSuccess, int dice){
        return (int) Math.floor(percentSuccess * dice);
    }

    public double getPercentSuccess(int threshold, boolean tensAreDouble){
        double totalHits = 0;
        for (int i = 1; i <= 10; i++) {
            if(i >= threshold){
                totalHits++;
            }
            if(tensAreDouble && i == 10){
                totalHits++;
            }
        }
        System.out.println(totalHits);
        return totalHits / 10;
    }
}
