package me.spider;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import me.spider.commands.CheckPerms;
import me.spider.commands.Damage;
import me.spider.commands.DiceRoll;
import me.spider.commands.Help;
import me.spider.commands.combat.CombatCmd;
import me.spider.commands.funny.BlowOnDice;
import me.spider.commands.funny.Probability;
import me.spider.commands.health.Heal;
import me.spider.commands.health.SetHealthLevels;
import me.spider.commands.health.TakeDamage;
import me.spider.commands.search.Search;
import me.spider.commands.sheets.Sheet;
import me.spider.commands.shortcuts.*;
import me.spider.db.ServerConfigurationManager;
import me.spider.db.book.BookManager;
//import me.spider.db.book.Updater;
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
    public static BookManager bookManager;
    public static CommandClient cc;
    public static void main(String[] args) {
        try {
            //todo lunar charms dont have domino rules - maybe think of a toggle?

            roller = new Roller();
            bookManager = new BookManager();
            //Updater.update();

            CommandClientBuilder commandClientBuilder = new CommandClientBuilder();
            commandClientBuilder.setOwnerId(102845358677176320L);
            commandClientBuilder.setActivity(Activity.customStatus("Type /help!"));
            commandClientBuilder.addSlashCommands(new Help(), new DiceRoll(), new Damage(), new BlowOnDice(), new Probability(), new CombatCmd(), new Sheet(), new CheckPerms(),
            new Recover(), new Spend(), new Stunt(), new Refresh(), new Essence(), new SetHealthLevels(), new Heal(), new TakeDamage(), new Limit(), new Willpower(), new Search());
            commandClientBuilder.setGuildSettingsManager(new ServerConfigurationManager());
            cc = commandClientBuilder.build();

            String TOKEN = new BufferedReader(new FileReader(".TOKEN")).readLine().trim();
            JDABuilder.createDefault(TOKEN, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_MEMBERS)
                    .disableCache(CacheFlag.ACTIVITY, CacheFlag.VOICE_STATE, CacheFlag.EMOJI, CacheFlag.STICKER, CacheFlag.CLIENT_STATUS, CacheFlag.ONLINE_STATUS)
                    .addEventListeners(new BotEventListener(), cc)
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}