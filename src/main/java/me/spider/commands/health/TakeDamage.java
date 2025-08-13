package me.spider.commands.health;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;

public class TakeDamage extends SlashCommand {
    public TakeDamage(){
        this.name = "takedamage";
        this.help = "Takes damage";
        this.children = new SlashCommand[]{new TakeDamageBox("bashing"), new TakeDamageBox("aggravated"), new TakeDamageBox("lethal")};

    }
    @Override
    protected void execute(SlashCommandEvent event) {
        event.reply("Whoops!").queue();
    }
}
