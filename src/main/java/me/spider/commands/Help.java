package me.spider.commands;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;

public class Help extends SlashCommand {
    public Help(){
        this.name = "help";
        this.help = "Learn about the bot!";
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        event.reply("""
                Thank you for inviting! You can do the following with this bot:
                
                - Roll d10's with **/roll**. Use **/damage** to make 10's count as 1 hit
                - Run and manage combat! (Type (**/combat help**)
                - Manage a bit of your character's attributes. (Type **/sheet help**)
                - Manage your health levels! (Use **/sheet health, /sethealthlevels, /takedamage, /heal**)
                - Search 2.5 charms, sorcery, astrology, anima effects, hearthstones, martial arts, and equipment! **/search**
                - Some Shortcuts:
                    - /essence - Shows your essence.
                    - /limit - Manage your limit
                    - /recover - Recover motes.
                    - /refresh - Raise attributes back to their starting values.
                    - /spend - Spend motes.
                    - /stunt - Refresh Motes after you stunt
                    - /willpower - Manage Willpower.
                    - /sheet get - Shows all of your attributes
                """).setEphemeral(true).queue();

    }
}
