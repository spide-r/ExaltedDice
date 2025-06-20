package me.spider;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

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
                 } else if (event.getMessage().getContentRaw().equals("exalted!clearOldCommands")) {
                     for (Guild guild : event.getJDA().getGuilds()) {
                         guild.updateCommands().queue();
                     }
                     event.getChannel().sendMessage("Cleared").queue();
             } else if (event.getMessage().getContentRaw().equals("exalted!clear")) {
                 event.getChannel().sendMessage("Clearing & Upserting an empty list").queue();
                 cc.getSlashCommands().clear();
                 cc.upsertInteractions(event.getJDA());
             } else {
                     event.getChannel().sendMessage("Hello! Not a command!").queue();
                 }
             }
         }
    }

}
