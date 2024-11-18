package me.spider.commands;

import me.spider.Constants;
import me.spider.dice.DamageRoll;
import me.spider.dice.Roll;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

import java.util.HashMap;
import java.util.Objects;

public class DiceRoll extends Command{
    HashMap<String, Integer> modifiedEssence = new HashMap<>();

    @Override
    public void OnCommand(SlashCommandInteractionEvent event) {
        int amount = event.getOption("amount", Constants.DICE_AMOUNT, OptionMapping::getAsInt);
        int successes = event.getOption("successes", Constants.SUCCESSES, OptionMapping::getAsInt);
        int threshold = event.getOption("threshold", Constants.SUCCESS_THRESHOLD, OptionMapping::getAsInt);
        String label = event.getOption("label", Constants.DEFAULT_LABEL, OptionMapping::getAsString);
        int essenceAmount = event.getOption("essencemodification", Constants.ESSENCE, OptionMapping::getAsInt);
        boolean privateRoll = event.getOption("private",Constants.PRIVATE_ROLL, OptionMapping::getAsBoolean);
        boolean modifiesEssence = essenceAmount != 0;
        if(Objects.equals(event.getSubcommandName(), "damage")){
            DamageRoll roll = new DamageRoll(amount, successes, threshold, label, privateRoll);
            String results = roll.getStringResult();
            sendDiceInformation(event, modifiesEssence, essenceAmount, results, privateRoll);
        } else {
            Roll roll = new Roll(amount, successes, threshold, label, privateRoll);
            String results = roll.getStringResult();
            sendDiceInformation(event, modifiesEssence, essenceAmount, results, privateRoll);
        }
    }

    private void sendDiceInformation(SlashCommandInteractionEvent event, boolean modifiesEssence, int essenceAmount, String results, boolean privateRoll) {
        if(modifiesEssence){
            modifiedEssence.put(event.getChannelId() + event.getUser().getId(), essenceAmount);
            event.reply(results).setEphemeral(privateRoll).addActionRow(StringSelectMenu.create("choose-essence")
                    .addOptions(SelectOption.of("Personal Motes", "personalMotes"),
                            SelectOption.of("Personal Max", "personalMax"),
                            SelectOption.of("Peripheral Motes", "peripheralMotes"),
                            SelectOption.of("Peripheral Max", "peripheralMax"),
                            SelectOption.of("Other Motes", "otherMotes"),
                            SelectOption.of("Other Max", "otherMax"))
                    .setPlaceholder("Select an essence Type").build()
            ).queue();
        } else {
            event.reply(results).setEphemeral(privateRoll).queue();
        }
    }
}
