package me.spider;

import me.spider.commands.DiceRoll;
import me.spider.commands.combat.*;
import me.spider.commands.sheets.*;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.events.session.ShutdownEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BotEventListener extends ListenerAdapter {

    Combat combat = new Combat();


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
                 } else if (event.getMessage().getContentRaw().equals("exalted!save")) {
                 event.getChannel().sendMessage("Saving combat!").queue();
                     try {
                         HashMap<String, String> combat = Main.combatManager.serialize();
                         Main.jdbcManager.setAllCombat(combat);
                     } catch (SQLException | IOException ex) {
                         throw new RuntimeException(ex);
                     }
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
    public void onShutdown(ShutdownEvent event) {
        Main.combatManager.saveCombat();
    }

    @Override
    public void onReady(ReadyEvent event) {
        try {
            Main.jdbcManager.init();
            HashMap<String, String> combat = Main.jdbcManager.getAllCombat();
            Main.combatManager.startupTask(combat);
        } catch (SQLException | IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
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
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){


        if (event.getName().equalsIgnoreCase("combat")) {
            combat.OnCommand(event);
        }
    }

}
