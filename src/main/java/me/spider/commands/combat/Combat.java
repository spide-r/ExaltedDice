package me.spider.commands.combat;

import me.spider.Main;
import me.spider.commands.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.Objects;

public class Combat extends Command {
    AddToTick addToTick = new AddToTick();
    AdvanceCombat advanceCombat = new AdvanceCombat();
    CurrentTick currentTick = new CurrentTick();
    Delay delay = new Delay();
    JoinCombat joinCombat = new JoinCombat();
    StartCombat startCombat = new StartCombat();
    RemoveActor removeActor = new RemoveActor();
    CheckTick checkTick = new CheckTick();
    Preview preview = new Preview();
    CombatHelp help = new CombatHelp();
    @Override
    public void OnCommand(SlashCommandInteractionEvent event) {
        try{
            switch (Objects.requireNonNull(event.getSubcommandName())){
                case "start":
                    startCombat.OnCommand(event);
                    break;
                case "join":
                    joinCombat.OnCommand(event);
                    break;
                case "advance":
                    advanceCombat.OnCommand(event);
                    break;
                case "delay":
                    delay.OnCommand(event);
                    break;
                case "add":
                    addToTick.OnCommand(event);
                    break;
                case "remove":
                    removeActor.OnCommand(event);
                    break;
                case "check":
                    checkTick.OnCommand(event);
                    break;
                case "preview":
                    preview.OnCommand(event);
                    break;
                case "tick":
                    currentTick.OnCommand(event);
                    break;
                case "help":
                    help.OnCommand(event);
                    break;
            }
        } catch (NullPointerException e){
            event.reply("Error! Has Combat started?").queue();
        }
    }
}
