package me.spider.commands;

import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;

public abstract class GenericCommand {
    public abstract void OnCommand(SlashCommandInteractionEvent event);

    public void OnStringSelectInteraction(StringSelectInteractionEvent event) {

    }

    public void OnCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event){

    }
}
