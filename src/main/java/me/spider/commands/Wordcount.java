package me.spider.commands;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;

import java.util.ArrayList;
import java.util.List;

public class Wordcount extends SlashCommand {
    public Wordcount(){
        this.name = "wordcount";
        this.help = "Counts words in a thread/channel. Will not pull past 500 messages.";
    }
    @Override
    protected void execute(SlashCommandEvent event) {
        MessageHistory history = event.getChannel().getHistory();
        ArrayList<Message> messages = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            List<Message> mh = history.retrievePast(100).complete();
            if(mh.isEmpty()){
                i = 5;
            } else {
                messages.addAll(mh);
            }

        }
        int count = 0;
        for (Message message : messages) {
            String content = message.getContentRaw();
            String replaced = content.replaceAll("> .*", "");
            int words = replaced.trim().split("\\s+").length;
            count += words;
        }
        event.reply(count + " words in " + messages.size() + " messages.").setEphemeral(true).queue();
    }
}
