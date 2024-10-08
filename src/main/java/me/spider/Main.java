package me.spider;

import me.spider.dice.Roller;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static Roller roller;

    public static void main(String[] args) {
        System.out.println("Hello world!");
        try {
            String TOKEN = new BufferedReader(new FileReader(".TOKEN")).readLine().trim();
            JDABuilder.createDefault(TOKEN, GatewayIntent.GUILD_WEBHOOKS)
                    .addEventListeners(new BotEventListener())
                    .build();
            roller = new Roller();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}