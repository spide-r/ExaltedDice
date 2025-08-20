package me.spider.commands.search;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import me.spider.commands.combat.*;

public class Search extends SlashCommand {
    public Search(){
        this.name = "search";
        this.help = "Search Commands.";
        this.children = new SlashCommand[]{new GenericSearch("anima"),
                new GenericSearch("astrology"),
                new GenericSearch("charms"),
                new GenericSearch("equipment"),
                new GenericSearch("hearthstones"),
                new GenericSearch("martialarts"),
                new GenericSearch("sorcery"),
                new GenericSearch("excellencies"),
                new GenericSearch("knacks"),
                new GenericSearch("submodules")
        };
    }
    @Override
    protected void execute(SlashCommandEvent event) {
        event.reply("oops!").queue();
    }
}
