package me.spider;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import me.spider.combat.CombatManager;
import me.spider.db.JDBCManager;
import me.spider.db.ServerConfigurationManager;
import me.spider.dice.Roller;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static Roller roller;
    public static CombatManager combatManager;
    public static JDBCManager jdbcManager;
    public static void main(String[] args) {
        try {
            jdbcManager = new JDBCManager();
            roller = new Roller();
            combatManager = new CombatManager();

            CommandClientBuilder commandClientBuilder = new CommandClientBuilder();
            commandClientBuilder.setOwnerId(102845358677176320L);
            commandClientBuilder.setActivity(Activity.listening("Autochthon"));
            //commandClientBuilder.addSlashCommand() todo
            commandClientBuilder.setGuildSettingsManager(new ServerConfigurationManager());
            CommandClient cc = commandClientBuilder.build();

            String TOKEN = new BufferedReader(new FileReader(".TOKEN")).readLine().trim();
            JDABuilder.createDefault(TOKEN, GatewayIntent.GUILD_WEBHOOKS, GatewayIntent.DIRECT_MESSAGES)
                    .disableCache(CacheFlag.ACTIVITY, CacheFlag.VOICE_STATE, CacheFlag.EMOJI, CacheFlag.STICKER, CacheFlag.CLIENT_STATUS, CacheFlag.ONLINE_STATUS)
                    .addEventListeners(new BotEventListener(), cc)
                    .build();


            ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
            service.schedule(() -> {
                combatManager.saveCombat();
            }, 5, TimeUnit.MINUTES); //todo move to its own class?

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}