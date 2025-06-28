package me.spider.commands.sheets;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import me.spider.commands.shortcuts.Refresh;

public class Sheet extends SlashCommand {

    public Sheet(){
        this.name = "sheet";
        this.help = "Commands relating to your character sheet.";
        this.children = new SlashCommand[]{new GetAttribute(), new SheetHelp(), new ModifyAttribute(), new SetAttribute()};
    }
    @Override
    protected void execute(SlashCommandEvent event) {

    }
}
