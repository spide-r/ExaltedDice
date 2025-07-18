package me.spider.commands.combat;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;

public class CombatHelp extends SlashCommand {

    public CombatHelp(){
        this.name = "help";
        this.help = "Shows valid combat commands.";
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        event.reply("""
                __Tutorial__:
                **/combat start True**
                    (everyone rolls their join battle commands)
                **/combat join N** (replace N with your successes, if you're the GM or dont want to be pinged when its your turn to go up, then use the `name` field to refer to yourself or any NPC's\s
                **/combat advance** - Starts/Advances the combat to the next tick w/ actors - anyone can use this
                **/combat delay N** - delays your next tick action by the specified amount.
                
                __Other Commands__:
                **/combat check N** - checks an arbitrary tick
                **/combat tick** - Shows Current tick
                **/combat preview actions** - shows all combat actors and their next action
                **/combat preview ticks** - shows the next 6 ticks and all actors
                **/combat ready** - Shows who is ready for combat.
                
                **/combat add** - Adds Person to tick (NOT THE SAME AS /combat join !!!! )
                **/combat remove** - Removes person from combat (RIP)
                """).queue();
    }
}
