package me.spider.commands;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class CheckPerms extends SlashCommand {
    public CheckPerms(){
        this.name = "checkperms";
        this.help = "Checks server permissions";
    }
    @Override
    protected void execute(SlashCommandEvent event) {
        boolean canSendInChannel = event.getChannel().canTalk();
        boolean canGetUsers;
        boolean cacheNotEmpty;
        if(event.getGuild() != null && event.getMember() != null){
            canGetUsers = event.getGuild().getMemberById(event.getMember().getId()) != null;
            cacheNotEmpty = !event.getGuild().getMemberCache().isEmpty();
        } else {
            canGetUsers = false;
            cacheNotEmpty = false;
        }

        event.reply(String.format("""
                Can Send: %s
                Can Get Users: %s
                Cache Full: %s
                """, canSendInChannel, canGetUsers, cacheNotEmpty)).setEphemeral(true).queue();
    }
}
