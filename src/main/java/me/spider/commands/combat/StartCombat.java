package me.spider.commands.combat;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import me.spider.Main;
import me.spider.db.Combat;
import me.spider.db.ServerConfiguration;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.sql.SQLException;

public class StartCombat extends SlashCommand {
    public StartCombat(){
        this.name = "start";
        this.help = "Resets Current Combat and Creates a new Scene. Be very sure about this!";
        this.options.add(new OptionData(OptionType.BOOLEAN, "areyousure", "Are you sure you wish to start a new scene?", true));
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        boolean sure = event.getOption("areyousure", false, OptionMapping::getAsBoolean);
        if(sure){
            ServerConfiguration sc = Main.cc.getSettingsFor(event.getGuild());
            Combat combat = sc.getCombat(event.getChannelId());
            combat.newScene();
            try {
                sc.saveCombat(combat);
                event.reply(":crossed_swords: New Combat Scene! Participants please `/combat join` ").queue();
            } catch (SQLException e) {
                e.printStackTrace();
                event.reply("Issue starting combat!").queue();
            }
        } else {
            event.reply("Scene not started. You weren't sure enough!").queue();
        }
    }
}
