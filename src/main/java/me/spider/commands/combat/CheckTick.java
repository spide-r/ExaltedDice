package me.spider.commands.combat;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import me.spider.Constants;
import me.spider.Main;
import me.spider.db.Combat;
import me.spider.db.ServerConfiguration;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class CheckTick extends SlashCommand {
    public CheckTick(){
        this.name = "check";
        this.help = "Check's actors at a given tick.";
        this.options.add(new OptionData(OptionType.INTEGER, "tick", "Which tick should be examined?", true));
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        ServerConfiguration sc = Main.cc.getSettingsFor(event.getGuild());
        Combat combat = sc.getCombat(event.getChannelId());
        int tick = event.getOption("tick", Constants.DEFAULT_TICK, OptionMapping::getAsInt);
        String tickStatus = combat.getActorsAt(tick);
        event.reply("Tick " + tick + ":\n" + tickStatus).setEphemeral(true).queue();
    }
}
