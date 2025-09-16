package me.spider.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class ServerConfiguration {
    private final String serverID;
    Dao<Character, String> characterDao;
    Dao<Combat, String> combatDao;
    Logger LOG = LoggerFactory.getLogger(ServerConfiguration.class);

    //todo split this class into one that just does the guild stuff and one that does the db stuff - dao's can be static
    public ServerConfiguration(String serverID, ConnectionSource connection){
        this.serverID = serverID;
        try {
            characterDao = DaoManager.createDao(connection, Character.class);
            combatDao = DaoManager.createDao(connection, Combat.class);
        } catch (SQLException e) {
            LOG.error("Issue Creating DAO", e);
            throw new RuntimeException(e);
        }
    }

    public Character getCharacter(String userID) {
        try{
            Character c = characterDao.queryForId(serverID + userID);
            if( c == null){
                LOG.error("Character is null.");
                return new Character(serverID, userID);
            }
            return c;
        } catch (IndexOutOfBoundsException | SQLException e){
            LOG.error("Issue Trying to pull a character", e);
            return new Character(serverID, userID);
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
            LOG.error("Issue Trying to pull combat", e);
            return new Combat(channelID);
        }
    }

    public boolean isCombatActive(String channelID) {
        try {
            return combatDao.queryForId(channelID) != null;
        } catch (SQLException e) {
            LOG.error("Cannot determine combat status.", e);
            return false;
        }
    }

    public void saveCharacter(Character character) throws SQLException {
        characterDao.createOrUpdate(character);
    }

    public void saveCombat(Combat combat) throws SQLException {
        combatDao.createOrUpdate(combat);
    }

    //pulls individual user configs regarding essence, wp, limit
    //maybe also pulls the combat manager?
}
