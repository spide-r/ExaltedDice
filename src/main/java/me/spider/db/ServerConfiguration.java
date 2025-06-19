package me.spider.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.Map;

public class ServerConfiguration {
    private final String guildID;
    Dao<Character, String> cDao;
    Dao<Combat, String> combatDao;

    public ServerConfiguration(String guildID, ConnectionSource connection){
        this.guildID = guildID;
        try {
            cDao = DaoManager.createDao(connection, Character.class);
            combatDao = DaoManager.createDao(connection, Combat.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Character getCharacter(String userID) {
        try{
            return cDao.queryForFieldValues(Map.of("serverID", guildID, "userID", userID)).get(0);
        } catch (IndexOutOfBoundsException | SQLException e){
            return new Character(guildID, userID);
        }
    }

    public Combat getCombat(String channelID){
        try{
            Combat c = combatDao.queryForId(channelID);
            if(c == null){
                return new Combat(channelID);
            }
            return c;
        } catch (IndexOutOfBoundsException | SQLException e){
            return new Combat(channelID);
        }
    }

    public void saveCharacter(Character character) throws SQLException {
        cDao.createOrUpdate(character);
    }

    public void saveCombat(Combat combat) throws SQLException {
        combatDao.createOrUpdate(combat);
    }

    //pulls individual user configs regarding essence, wp, limit
    //maybe also pulls the combat manager?
}
