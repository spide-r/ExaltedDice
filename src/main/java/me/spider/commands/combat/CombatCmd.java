package me.spider.commands.combat;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;

public class CombatCmd extends SlashCommand {

    public CombatCmd(){
        this.name = "combat";
        this.help = "Combat Commands.";
        this.children = new SlashCommand[]{new AddToTick(), new AdvanceCombat(), new CheatSheet(), new CheckTick(), new CombatHelp(), new CurrentTick(), new Delay(), new JoinCombat(), new Preview(), new Ready(), new RemoveActor(), new StartCombat()}; //todo
    }
    @Override
    protected void execute(SlashCommandEvent event) {
        //todo check all combat commands when combat hasnt started
    }
}
