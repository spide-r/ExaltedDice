package me.spider.commands.shortcuts;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;

public class DamageBox extends SlashCommand {
    public DamageBox(){
        this.name = "takedamage";
        this.help = "Takes damage";
        this.children = new SlashCommand[]{new TakeDamage("bashing"), new TakeDamage("aggravated"), new TakeDamage("lethal")};

    }
    @Override
    protected void execute(SlashCommandEvent event) {
        event.reply("Whoops!").queue();
    }
}
