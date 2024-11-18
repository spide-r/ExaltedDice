package me.spider.commands.combat;

import me.spider.Main;
import me.spider.commands.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

public class RemoveActor extends Command {
    @Override
    public void OnCommand(SlashCommandInteractionEvent event) {
        String name = event.getOption("name", event.getUser().getId(), OptionMapping::getAsString);
        if(name.matches("\\d+")){
            event.reply("<@" + name + "> removed from combat." ).queue();

        } else {
            event.reply(name + " removed from combat.").queue();
        }
        Main.combatManager.removeFromCombat(event.getChannelId(), name);
    }
}
