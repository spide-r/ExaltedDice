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

public class BotEventListener extends ListenerAdapter {

    CombatCmd combat = new CombatCmd();


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
         if(!event.isFromGuild()){
             if(event.getAuthor().getId().equals("102845358677176320")){
                 if (event.getMessage().getContentRaw().equals("exalted!update")) {
                     updateCommands(event.getJDA().getGuilds());
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

    private void updateCommands(List<Guild> guilds){
        guilds.forEach(g -> g.updateCommands().addCommands(Constants.commandData).queue());
    }



    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        event.getGuild().updateCommands().addCommands(Constants.commandData).queue();
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

}
