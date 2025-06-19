package me.spider.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.jagrosh.jdautilities.command.GuildSettingsManager;
import net.dv8tion.jda.api.entities.Guild;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ServerConfigurationManager implements GuildSettingsManager<ServerConfiguration> {
    private Statement statement;
    private ConnectionSource connection;

    @Override
    public ServerConfiguration getSettings(Guild guild) {
        return new ServerConfiguration(guild.getId(), connection);
    }

    @Override
    public void init() {
        try {
            this.connection = new JdbcConnectionSource("jdbc:sqlite:exalted.db");
            Dao<Character, String> d = DaoManager.createDao(connection, Character.class);
            TableUtils.createTableIfNotExists(connection, Character.class);
            //todo create combat table if not exists
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
/*        try
        {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:exalted.db");
            Statement statement = connection.createStatement();
            this.connection = connection;
            this.statement = statement;
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS characters" +
                    " (serverID string, userID string, personalMotes integer default 0, personalMax integer default 0, " +
                    "peripheralMotes integer default 0, peripheralMax integer default 0, otherMotes integer default 0, otherMax integer default 0, limitbreak integer default 0, willpower integer default 5, PRIMARY KEY (serverID, userID))"
            );

            connection.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS combat" +
                    " (channelID string, combatString string, PRIMARY KEY (channelID))");
        } catch (SQLException e){
            e.printStackTrace();
        }*/
    }

    @Override
    public void shutdown() {
        connection.closeQuietly();
        //sqlite is cool
    }
}
