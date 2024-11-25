package me.spider;

import me.spider.commands.DiceRoll;
import me.spider.dice.DamageRoll;
import me.spider.dice.Roll;
import me.spider.commands.combat.*;
import me.spider.commands.sheets.*;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BotEventListener extends ListenerAdapter {

    Sheet sheet = new Sheet();
    DiceRoll diceRoll = new DiceRoll();
    Combat combat = new Combat();


    HashMap<String, Integer> modifiedEssence = new HashMap<>();
    //todo register commands on guild join
    @Override
    public void onGenericEvent(@NotNull GenericEvent event) {
        if (event instanceof ReadyEvent){
            //todo no need to create commands every event
            /*
            /roll standard|damage|help
            /sheet get|set|modify|reset essence/limit/willpower
            /combat start|join|advance|delay|add|remove|check|next|help
             */
            event.getJDA().getGuilds().forEach( guild -> guild.updateCommands().addCommands(
                    Commands.slash("roll", "Rolls The Dice - 10's count as 2 successes")
                            .addOption(OptionType.INTEGER, "amount", "Amount Of Dice", true)
                            .addOption(OptionType.STRING, "label", "Describe what this dice roll is for", false, false)
                            .addOption(OptionType.INTEGER, "successes", "Amount of Automatic Successes", false, false)
                            .addOption(OptionType.INTEGER, "threshold", "If the success threshold needs to be changed, change it here.", false, false)
                            .addOption(OptionType.INTEGER, "essencemodification", "If the dice roll will modify your essence.", false, false)
                            .addOption(OptionType.BOOLEAN, "private", "If the dice roll should be shown only to you.", false, false),
                    Commands.slash("damage", "Rolls The Dice - 10's count as 1 success")
                            .addOption(OptionType.INTEGER, "amount", "Amount Of Dice", true)
                            .addOption(OptionType.STRING, "label", "Describe what this dice roll is for", false, false)
                            .addOption(OptionType.INTEGER, "successes", "Amount of Automatic Successes", false, false)
                            .addOption(OptionType.INTEGER, "threshold", "If the success threshold needs to be changed, change it here.", false, false)
                            .addOption(OptionType.INTEGER, "essencemodification", "If the dice roll will modify your essence, change it here.", false, false)
                            .addOption(OptionType.BOOLEAN, "private", "If the dice roll should be shown only to you.", false, false),
                    Commands.slash("sheet", "Character Sheet Management").addSubcommands(
                            new SubcommandData("get", "Gets Attribute")
                                    .addOption(OptionType.STRING, "attribute", "Which Attribute?", true, true),
                            new SubcommandData("set", "Sets Attribute")
                                    .addOption(OptionType.STRING, "attribute", "Which Attribute?", true, true)
                                    .addOption(OptionType.INTEGER, "value", "The number to set the attribute at.", true),
                            new SubcommandData("modify", "Modifies Attribute")
                                    .addOption(OptionType.STRING, "attribute", "Which Attribute?", true, true)
                                    .addOption(OptionType.INTEGER, "value", "The number to set the attribute at.", true),
                            new SubcommandData("help", "Is this thing on?"),
                            new SubcommandData("refresh", "Resets all your essences back to their max value.")
                    ),


                    //combat start|join|advance|delay|add|remove|check|preview|help

                    Commands.slash("combat", "Combat Commands").addSubcommands(
                            new SubcommandData("start", "Resets Current Combat and Creates a new Scene. Be very sure about this!")
                                    .addOption(OptionType.BOOLEAN, "areyousure", "Are you sure you wish to start a new scene?", true),
                            new SubcommandData("join", "Joins the current combat")
                                    .addOption(OptionType.INTEGER, "successes", "How Many successes did you get on your Join Combat Roll?", true)
                                    .addOption(OptionType.STRING, "name", "The name of the actor (if it isn't yourself)."),
                            new SubcommandData("advance", "Advances Combat to the next tick where a combatant moves."),
                            new SubcommandData("delay", "Delays your next action by a specified value")
                                    .addOption(OptionType.INTEGER, "delay", "How Many ticks should the next action be delayed by?", true)
                                    .addOption(OptionType.STRING, "name", "The name of the actor (if it isn't yourself)."),
                            new SubcommandData("add", "Adds a specific actor to participate at a certain tick. !!! DIFFERENT FROM DELAYING !!!")
                                    .addOption(OptionType.INTEGER, "tick", "The tick the actor should be added to.", true)
                                    .addOption(OptionType.STRING, "name", "The name of the actor (if it isn't yourself)."),
                            new SubcommandData("remove", "Removes an actor from future combat ticks. (RIP)")
                                    .addOption(OptionType.STRING, "name", "The name of the actor (if it isn't yourself)."),
                            new SubcommandData("check", "Checks Actions at a given tick")
                                    .addOption(OptionType.INTEGER, "tick", "Which tick should be examined?", true),
                            new SubcommandData("preview", "Shows a preview of whats to come in combat.")
                                    .addOption(OptionType.STRING, "data", "What Combat Data would you like to see? Next Actions or Next 6 Ticks?", true, true),
                            new SubcommandData("tick", "Shows the current Tick Data"),
                            new SubcommandData("help", "I don't know how to computer???")


                    )

                   /* Commands.slash("addtotick", "Adds a specific actor to participate at a certain tick. !!! DIFFERENT FROM DELAYING !!!")
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
                    Commands.slash("nextactions", "Shows a list each actor and the next tick they act on.")*/




                    ).queue());
        }
    }

    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {
        if(event.getFocusedOption().getName().equals("attribute")){
            List<Command.Choice> options = Stream.of(Constants.ATTRIBUTE_LIST).filter(w -> w.startsWith(event.getFocusedOption().getValue()))
                    .map(w -> new Command.Choice(w, w)).collect(Collectors.toList());
            event.replyChoices(options).queue();
        } else if (event.getFocusedOption().getName().equals("data")){
            List<Command.Choice> options = Stream.of(Constants.COMBAT_DATA).filter(w -> w.startsWith(event.getFocusedOption().getValue()))
                    .map(w -> new Command.Choice(w, w)).collect(Collectors.toList());
            event.replyChoices(options).queue();
        }
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


        switch (event.getName().toLowerCase()){
            case "roll":
            case "damage":
                diceRoll.OnCommand(event);
                break;
            case "sheet":
                sheet.OnCommand(event);
                break;
            case "combat":
                combat.OnCommand(event);
                break;
        }
    }

}
