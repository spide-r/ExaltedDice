package me.spider;

import me.spider.commands.DamageRoll;
import me.spider.commands.Roll;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;

public class BotEventListener extends ListenerAdapter {
    @Override
    public void onGenericEvent(@NotNull GenericEvent event) {
        if (event instanceof ReadyEvent){
            System.out.println("BOT is ready!!!!");
            event.getJDA().getGuilds().forEach( guild -> guild.updateCommands().addCommands(
                    Commands.slash("roll", "Rolls The Dice - 10's count as 2 successes")
                            .addOption(OptionType.INTEGER, "amount", "Amount Of Dice", true)
                            .addOption(OptionType.STRING, "label", "Describe what this dice roll is for", false, false)
                            .addOption(OptionType.INTEGER, "successes", "Amount of Automatic Successes", false, false)
                            .addOption(OptionType.INTEGER, "threshold", "If the success threshold needs to be changed, change it here.", false, false),

                    Commands.slash("damageroll", "Rolls The Dice - 10's count as 1 success")
                            .addOption(OptionType.INTEGER, "amount", "Amount Of Dice", true)
                            .addOption(OptionType.STRING, "label", "Describe what this dice roll is for", false, false)
                            .addOption(OptionType.INTEGER, "successes", "Amount of Automatic Successes", false, false)
                            .addOption(OptionType.INTEGER, "threshold", "If the success threshold needs to be changed, change it here.", false, false)

            ).queue());
        }
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){

        event.deferReply().queue();

        int amount = event.getOption("amount", Constants.DICE_AMOUNT, OptionMapping::getAsInt);
        int successes = event.getOption("successes", Constants.SUCCESSES, OptionMapping::getAsInt);
        int threshold = event.getOption("threshold", Constants.SUCCESS_THRESHOLD, OptionMapping::getAsInt);
        String label = event.getOption("label", Constants.DEFAULT_LABEL, OptionMapping::getAsString);

        if(event.getName().equals("roll")){
            Roll roll = new Roll(amount, successes, threshold, label);
            String results = roll.getStringResult();
            event.getHook().sendMessage(results).queue();
        } else if(event.getName().equals("damageroll")){
            DamageRoll roll = new DamageRoll(amount, successes, threshold, label);
            String results = roll.getStringResult();
            event.getHook().sendMessage(results).queue();
        }
    }


}
