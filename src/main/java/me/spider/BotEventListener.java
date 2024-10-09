package me.spider;

import me.spider.commands.DamageRoll;
import me.spider.commands.Roll;
import me.spider.commands.sheets.*;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;

public class BotEventListener extends ListenerAdapter {
    GetEssences getEssences = new GetEssences();
    GetLimit getLimit = new GetLimit();
    GetWillpower getWillpower = new GetWillpower();
    ModifyEssence modifyEssence = new ModifyEssence();
    SetEssence setEssence = new SetEssence();
    SetLimit setLimit = new SetLimit();
    SetWillpower setWillpower = new SetWillpower();

    @Override
    public void onGenericEvent(@NotNull GenericEvent event) {
        if (event instanceof ReadyEvent){
            System.out.println("BOT is ready!!!!");
            event.getJDA().getGuilds().forEach( guild -> guild.updateCommands().addCommands(
                    Commands.slash("roll", "Rolls The Dice - 10's count as 2 successes")
                            .addOption(OptionType.INTEGER, "amount", "Amount Of Dice", true)
                            .addOption(OptionType.STRING, "label", "Describe what this dice roll is for", false, false)
                            .addOption(OptionType.INTEGER, "successes", "Amount of Automatic Successes", false, false)
                            .addOption(OptionType.INTEGER, "threshold", "If the success threshold needs to be changed, change it here.", false, false)
                            .addOption(OptionType.BOOLEAN, "modifyessence", "If the dice roll will modify your essence (Does Nothing ATM)", false, false),

                    Commands.slash("damageroll", "Rolls The Dice - 10's count as 1 success")
                            .addOption(OptionType.INTEGER, "amount", "Amount Of Dice", true)
                            .addOption(OptionType.STRING, "label", "Describe what this dice roll is for", false, false)
                            .addOption(OptionType.INTEGER, "successes", "Amount of Automatic Successes", false, false)
                            .addOption(OptionType.INTEGER, "threshold", "If the success threshold needs to be changed, change it here.", false, false)
                            .addOption(OptionType.BOOLEAN, "modifyessence", "If the dice roll will modify your essence (Does Nothing ATM)", false, false),
                    Commands.slash("getessences", "Gets a list of all essence motes attached to you."),
                    Commands.slash("getlimit", "Shows your limit break."),
                    Commands.slash("getwillpower", "Shows your willpower"),
                    Commands.slash("modifyessence", "Modifies an essence by a set amount").addOption(OptionType.STRING, "essence", "Essence Type", true, true)
                            .addOption(OptionType.INTEGER, "value", "The amount to modify your essence (can be a negative number)", true),
                    Commands.slash("setessence", "Sets your essence to the specified amount - this overwrites what you currently have set.").addOption(OptionType.STRING, "essence", "Essence Type", true, true)
                            .addOption(OptionType.INTEGER, "value", "The new amount", true),
                    Commands.slash("setlimit", "Sets your limit break to the specified amount")
                            .addOption(OptionType.INTEGER, "value", "The New Value", true),
                    Commands.slash("setwillpower", "Sets your willpower to the specified amount")
                            .addOption(OptionType.INTEGER, "value", "The New Value", true)

                    ).queue());
        }
    }

    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {
         modifyEssence.OnCommandAutoCompleteInteraction(event);
         setEssence.OnCommandAutoCompleteInteraction(event);
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){


        int amount = event.getOption("amount", Constants.DICE_AMOUNT, OptionMapping::getAsInt);
        int successes = event.getOption("successes", Constants.SUCCESSES, OptionMapping::getAsInt);
        int threshold = event.getOption("threshold", Constants.SUCCESS_THRESHOLD, OptionMapping::getAsInt);
        String label = event.getOption("label", Constants.DEFAULT_LABEL, OptionMapping::getAsString);
        boolean modifiesEssence = event.getOption("modifyessence", Constants.ESSENCE_MODIFIED, OptionMapping::getAsBoolean);

        if(event.getName().equals("roll")){
            event.deferReply().queue();
            Roll roll = new Roll(amount, successes, threshold, label, modifiesEssence);
            String results = roll.getStringResult();
            event.getHook().sendMessage(results).queue();
        } else if(event.getName().equals("damageroll")){
            event.deferReply().queue();
            DamageRoll roll = new DamageRoll(amount, successes, threshold, label, modifiesEssence);
            String results = roll.getStringResult();
            event.getHook().sendMessage(results).queue();
        }

        switch (event.getName().toLowerCase()){
            case "getessences":
                getEssences.OnCommand(event);
                break;
            case "getlimit":
                getLimit.OnCommand(event);
                break;
            case "getwillpower":
                getWillpower.OnCommand(event);
                break;
            case "modifyessence":
                modifyEssence.OnCommand(event);
                break;
            case "setessence":
                setEssence.OnCommand(event);
                break;
            case "setlimit":
                setLimit.OnCommand(event);
                break;
            case "setwillpower":
                setWillpower.OnCommand(event);
                break;
        }
        //todo if it modifies essence, prompt the user to remove essence
    }


}
