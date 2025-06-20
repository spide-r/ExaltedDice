package me.spider.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

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
            Character c = cDao.queryForId(guildID + userID);
            if( c == null){
                return new Character(guildID, userID);
            }
            return c;
        } catch (IndexOutOfBoundsException | SQLException e){
            return new Character(guildID, userID);
        }
    }

    public Combat getCombat(String channelID) {
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


    public boolean isCombatInactive(String channelID) {
        try {
            return combatDao.queryForId(channelID) != null;
        } catch (SQLException e) {
            return false;
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
