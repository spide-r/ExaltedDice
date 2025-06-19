package me.spider.commands.combat;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;

public class Preview extends SlashCommand {
    //parent of nextactions and nextticks

    public Preview(){
        this.name = "preview";
        this.help = "Previews combat.";
        this.children = new SlashCommand[]{new NextActions(), new NextTicks()};
    }

    @Override
    protected void execute(SlashCommandEvent event) {

    }
}
