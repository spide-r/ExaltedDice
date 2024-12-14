package me.spider;

import me.spider.combat.CombatManager;
import me.spider.db.JDBCManager;
import me.spider.dice.Roller;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

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
            String TOKEN = new BufferedReader(new FileReader(".TOKEN")).readLine().trim();
            JDABuilder.createDefault(TOKEN, GatewayIntent.GUILD_WEBHOOKS, GatewayIntent.DIRECT_MESSAGES)
                    .addEventListeners(new BotEventListener())
                    .build();

            ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
            service.schedule(() -> {
                combatManager.saveCombat();
            }, 5, TimeUnit.MINUTES);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}