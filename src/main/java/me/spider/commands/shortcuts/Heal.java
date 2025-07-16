package me.spider.commands.shortcuts;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;

public class Heal extends SlashCommand {
    public Heal(){
        this.name = "heal";
        this.help = "Heals Damage.";
        this.children = new SlashCommand[]{new HealDamage("bashing"), new HealDamage("aggravated"), new HealDamage("lethal")};

    }
    @Override
    protected void execute(SlashCommandEvent event) {
        event.reply("Whoops!").queue();
    }
}
