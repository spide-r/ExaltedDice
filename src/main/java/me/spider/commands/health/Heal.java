package me.spider.commands.health;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;

public class Heal extends SlashCommand {
    public Heal(){
        this.name = "heal";
        this.help = "Heals Damage.";
        this.children = new SlashCommand[]{new HealDamageBox("bashing"), new HealDamageBox("aggravated"), new HealDamageBox("lethal")};

    }
    @Override
    protected void execute(SlashCommandEvent event) {
        event.reply("Whoops!").queue();
    }
}
