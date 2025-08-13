package me.spider.commands.sheets;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;

public class Sheet extends SlashCommand {

    public Sheet(){
        this.name = "sheet";
        this.help = "Commands relating to your character sheet.";
        this.children = new SlashCommand[]{new GetAttributes(), new SheetHelp(), new SetAttributes(), new Health(),
                new ModifyAttributes()};
    }
    @Override
    protected void execute(SlashCommandEvent event) {

    }
}
