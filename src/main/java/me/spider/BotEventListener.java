package me.spider;

import me.spider.commands.combat.*;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static me.spider.Main.cc;

public class BotEventListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
         if(!event.isFromGuild()){
             if(event.getAuthor().getId().equals("102845358677176320")){
                 if (event.getMessage().getContentRaw().equals("exalted!update")) {
                     cc.upsertInteractions(event.getJDA());
                     event.getChannel().sendMessage("Updating Commands!").queue();
                 } else if (event.getMessage().getContentRaw().equals("exalted!shutdown")) {
                     event.getChannel().sendMessage("Shutting down!").queue();
                     event.getJDA().shutdown();
                 } else {
                     event.getChannel().sendMessage("Hello! Not a command!").queue();
                 }
             }
         }
    }

}
