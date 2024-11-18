package me.spider.commands.combat;

import me.spider.Main;
import me.spider.commands.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

public class StartCombat extends Command {
    @Override
    public void OnCommand(SlashCommandInteractionEvent event) {
        boolean sure = event.getOption("areyousure", false, OptionMapping::getAsBoolean);
        if(sure){
            Main.combatManager.newScene(event.getChannelId());
            event.reply(":crossed_swords: New Combat Scene! Participants please `/combat join` ").queue();
        } else {
            event.reply("Scene not started. You weren't sure enough!").queue();
        }

    }
}
