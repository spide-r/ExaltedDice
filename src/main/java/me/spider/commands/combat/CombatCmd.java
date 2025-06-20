package me.spider.commands.combat;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;

public class CombatCmd extends SlashCommand {

    public CombatCmd(){
        this.name = "combat";
        this.help = "Combat Commands.";
        this.children = new SlashCommand[]{new AddToTick(), new AdvanceCombat(), new CheatSheet(), new CheckTick(),
                new CombatHelp(), new CurrentTick(), new Delay(), new JoinCombat(), new Ready(), new RemoveActor(), new StartCombat(),
                new NextActions(), new NextTicks()};
    }
    @Override
    protected void execute(SlashCommandEvent event) {
        event.reply("Oops!").queue();
    }
}
