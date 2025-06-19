package me.spider.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class ServerConfiguration {
    private final String guildID;
    Dao<Character, String> dao;

    public ServerConfiguration(String guildID, ConnectionSource connection){
        this.guildID = guildID;
        try {
            dao = DaoManager.createDao(connection, Character.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Character getCharacter(String userID) {
        try{
            return dao.queryForFieldValues(Map.of("serverID", guildID, "userID", userID)).get(0);
        } catch (IndexOutOfBoundsException | SQLException e){
            return new Character();
        }
    }

    public void saveCharacter(Character character) throws SQLException {
        dao.createOrUpdate(character);
    }
    
    //pulls individual user configs regarding essence, wp, limit
    //maybe also pulls the combat manager?
}
