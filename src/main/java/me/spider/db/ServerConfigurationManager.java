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
    private ConnectionSource connection;

    @Override
    public ServerConfiguration getSettings(Guild guild) {
        return new ServerConfiguration(guild.getId(), connection);
    }

    @Override
    public void init() {
        try {
            this.connection = new JdbcConnectionSource("jdbc:sqlite:exalted.db");
            TableUtils.createTableIfNotExists(connection, Character.class);
            TableUtils.createTableIfNotExists(connection, Combat.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void shutdown() {
        connection.closeQuietly();
        //sqlite is cool
    }
}
