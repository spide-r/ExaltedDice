package me.spider;

import me.spider.commands.DamageRoll;
import me.spider.commands.Roll;
import me.spider.commands.combat.*;
import me.spider.commands.sheets.*;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.HashMap;

public class BotEventListener extends ListenerAdapter {
    GetEssences getEssences = new GetEssences();
    GetLimit getLimit = new GetLimit();
    GetWillpower getWillpower = new GetWillpower();
    ModifyEssence modifyEssence = new ModifyEssence();
    SetEssence setEssence = new SetEssence();
    SetLimit setLimit = new SetLimit();
    SetWillpower setWillpower = new SetWillpower();
    ResetEssences resetEssences = new ResetEssences();
    AddToTick addToTick = new AddToTick();
    AdvanceCombat advanceCombat = new AdvanceCombat();
    CurrentTick currentTick = new CurrentTick();
    Delay delay = new Delay();
    JoinCombat joinCombat = new JoinCombat();
    StartCombat startCombat = new StartCombat();
    RemoveActor removeActor = new RemoveActor();
    TickZero tickZero = new TickZero();
    CheckTick checkTick = new CheckTick();
    NextTicks nextTicks = new NextTicks();
    NextActions nextActions = new NextActions();

    HashMap<String, Integer> modifiedEssence = new HashMap<>();
    @Override
    public void onGenericEvent(@NotNull GenericEvent event) {
        if (event instanceof ReadyEvent){
            //todo no need to create commands every event
            event.getJDA().getGuilds().forEach( guild -> guild.updateCommands().addCommands(
                    Commands.slash("roll", "Rolls The Dice - 10's count as 2 successes")
                            .addOption(OptionType.INTEGER, "amount", "Amount Of Dice", true)
                            .addOption(OptionType.STRING, "label", "Describe what this dice roll is for", false, false)
                            .addOption(OptionType.INTEGER, "successes", "Amount of Automatic Successes", false, false)
                            .addOption(OptionType.INTEGER, "threshold", "If the success threshold needs to be changed, change it here.", false, false)
                            .addOption(OptionType.INTEGER, "essencemodification", "If the dice roll will modify your essence.", false, false)
                            .addOption(OptionType.BOOLEAN, "private", "If the dice roll should be shown only to you.", false, false),

                    Commands.slash("damageroll", "Rolls The Dice - 10's count as 1 success")
                            .addOption(OptionType.INTEGER, "amount", "Amount Of Dice", true)
                            .addOption(OptionType.STRING, "label", "Describe what this dice roll is for", false, false)
                            .addOption(OptionType.INTEGER, "successes", "Amount of Automatic Successes", false, false)
                            .addOption(OptionType.INTEGER, "threshold", "If the success threshold needs to be changed, change it here.", false, false)
                            .addOption(OptionType.INTEGER, "essencemodification", "If the dice roll will modify your essence, change it here.", false, false)
                            .addOption(OptionType.BOOLEAN, "private", "If the dice roll should be shown only to you.", false, false),

                    Commands.slash("getessences", "Gets a list of all essence motes attached to you."),
                    Commands.slash("getlimit", "Shows your limit break."),
                    Commands.slash("getwillpower", "Shows your willpower"),
                    Commands.slash("modifyessence", "Modifies an essence by a set amount").addOption(OptionType.STRING, "essence", "Essence Type", true, true)
                            .addOption(OptionType.INTEGER, "value", "The amount to modify your essence (can be a negative number)", true),
                    Commands.slash("setessence", "Sets your essence to the specified amount - this overwrites what you currently have set.")
                            .addOption(OptionType.STRING, "essence", "Essence Type", true, true)
                            .addOption(OptionType.INTEGER, "value", "The new amount", true),
                    Commands.slash("setlimit", "Sets your limit break to the specified amount")
                            .addOption(OptionType.INTEGER, "value", "The New Value", true),
                    Commands.slash("setwillpower", "Sets your willpower to the specified amount")
                            .addOption(OptionType.INTEGER, "value", "The New Value", true),
                    Commands.slash("resetessences", "Resets all your essences back to their max value."),


                    Commands.slash("addtotick", "Adds a specific actor to participate at a certain tick. !!! DIFFERENT FROM DELAYING !!!")
                            .addOption(OptionType.INTEGER, "tick", "The tick the actor should be added to.", true)
                            .addOption(OptionType.STRING, "name", "The name of the actor (if it isn't yourself)."),
                    Commands.slash("advancecombat", "Advances Combat to the next tick where a combatant moves."),
                    Commands.slash("currenttick", "Shows the current tick and all participants."),
                    Commands.slash("delay", "Delays your next action by a specified value")
                            .addOption(OptionType.INTEGER, "delay", "How Many ticks should the next action be delayed by?", true)
                            .addOption(OptionType.STRING, "name", "The name of the actor (if it isn't yourself)."),
                    Commands.slash("joincombat", "Joins the current combat")
                            .addOption(OptionType.INTEGER, "successes", "How Many successes did you get on your Join Combat Roll?", true)
                            .addOption(OptionType.STRING, "name", "The name of the actor (if it isn't yourself)."),
                    Commands.slash("startcombat", "Resets Current Combat and Creates a new Scene. Be very sure about this!")
                            .addOption(OptionType.BOOLEAN, "areyousure", "Are you sure you wish to start a new scene?", true),
                    Commands.slash("removeactor", "Removes an actor from future combat ticks. (RIP)")
                            .addOption(OptionType.STRING, "name", "The name of the actor (if it isn't yourself)." ),
                    Commands.slash("tickzero", "Finalizes all combat and officially starts the scene at tick zero."),
                    Commands.slash("checktick", "Checks Actions at a given tick")
                            .addOption(OptionType.INTEGER, "tick", "Which tick should be examined?", true),
                    Commands.slash("nextticks", "Shows a preview of the next 6 ticks."),
                    Commands.slash("nextactions", "Shows a list each actor and the next tick they act on.")




                    ).queue());
        }
    }

    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {
         modifyEssence.OnCommandAutoCompleteInteraction(event);
         setEssence.OnCommandAutoCompleteInteraction(event);
    }

    @Override
    public void onStringSelectInteraction(StringSelectInteractionEvent event) {
        if(event.getComponentId().equals("choose-essence")){
            String essence = event.getValues().get(0);
            String serverID = event.getGuild().getId();
            String userID = event.getUser().getId();
            boolean privateRoll = event.getMessage().getContentRaw().startsWith(":ghost:");
            int essenceChange = modifiedEssence.get(event.getChannelId() + userID);
            int oldNumber;
            try {
                oldNumber = Main.jdbcManager.getEssenceValue(serverID,userID,essence);
                int newEssence = oldNumber + essenceChange;
                Main.jdbcManager.setEssence(serverID, userID, essence, newEssence);

                event.reply("Your " + essence + " is now " + newEssence).setEphemeral(privateRoll).queue();
                event.editSelectMenu(event.getSelectMenu().createCopy().setPlaceholder("You have selected: " + essence).build().asDisabled()).queue();

            } catch (SQLException e) {
                event.reply("SQL exception!").queue();
            }

        }
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){

        int amount = event.getOption("amount", Constants.DICE_AMOUNT, OptionMapping::getAsInt);
        int successes = event.getOption("successes", Constants.SUCCESSES, OptionMapping::getAsInt);
        int threshold = event.getOption("threshold", Constants.SUCCESS_THRESHOLD, OptionMapping::getAsInt);
        String label = event.getOption("label", Constants.DEFAULT_LABEL, OptionMapping::getAsString);
        int essenceAmount = event.getOption("essencemodification", Constants.ESSENCE, OptionMapping::getAsInt);
        boolean privateRoll = event.getOption("private",Constants.PRIVATE_ROLL, OptionMapping::getAsBoolean);

        boolean modifiesEssence = essenceAmount != 0;
        if(event.getName().equals("roll")){
            Roll roll = new Roll(amount, successes, threshold, label, privateRoll);
            String results = roll.getStringResult();
            sendDiceInformation(event, modifiesEssence, essenceAmount, results, privateRoll);
        } else if(event.getName().equals("damageroll")){
            DamageRoll roll = new DamageRoll(amount, successes, threshold, label, privateRoll);
            String results = roll.getStringResult();
            sendDiceInformation(event, modifiesEssence, essenceAmount, results, privateRoll);
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
            case "resetessences":
                resetEssences.OnCommand(event);
                break;

            case "addtotick":
                addToTick.OnCommand(event);
                break;
            case "advancecombat":
                advanceCombat.OnCommand(event);
                break;
            case "currenttick":
                currentTick.OnCommand(event);
                break;
            case "delay":
                delay.OnCommand(event);
                break;
            case "joincombat":
                joinCombat.OnCommand(event);
                break;
            case "startcombat":
                startCombat.OnCommand(event);
                break;
            case "removeactor":
                removeActor.OnCommand(event);
                break;
            case "tickzero":
                tickZero.OnCommand(event);
                break;
            case "checktick":
                checkTick.OnCommand(event);
                break;
            case "nextticks":
                nextTicks.OnCommand(event);
                break;
            case "nextactions":
                nextActions.OnCommand(event);
                break;
        }
        //todo if it modifies essence, prompt the user to remove essence
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
