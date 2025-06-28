package me.spider;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import me.spider.commands.Damage;
import me.spider.commands.DiceRoll;
import me.spider.commands.combat.CombatCmd;
import me.spider.commands.funny.BlowOnDice;
import me.spider.commands.sheets.Sheet;
import me.spider.commands.shortcuts.*;
import me.spider.db.ServerConfigurationManager;
import me.spider.dice.Roller;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class Main {
    public static Roller roller;
    public static CommandClient cc;
    public static void main(String[] args) {
        try {
            roller = new Roller();

            CommandClientBuilder commandClientBuilder = new CommandClientBuilder();
            commandClientBuilder.setOwnerId(102845358677176320L);
            commandClientBuilder.setActivity(Activity.listening("Autochthon"));
            commandClientBuilder.addSlashCommands(new DiceRoll(), new Damage(), new BlowOnDice(), new CombatCmd(), new Sheet(),
            new Recover(), new Spend(), new Stunt(), new Refresh(), new Essence());
            commandClientBuilder.setGuildSettingsManager(new ServerConfigurationManager());
            cc = commandClientBuilder.build();

            String TOKEN = new BufferedReader(new FileReader(".TOKEN")).readLine().trim();
            JDABuilder.createDefault(TOKEN, GatewayIntent.GUILD_WEBHOOKS, GatewayIntent.DIRECT_MESSAGES)
                    .disableCache(CacheFlag.ACTIVITY, CacheFlag.VOICE_STATE, CacheFlag.EMOJI, CacheFlag.STICKER, CacheFlag.CLIENT_STATUS, CacheFlag.ONLINE_STATUS)
                    .addEventListeners(new BotEventListener(), cc)
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}