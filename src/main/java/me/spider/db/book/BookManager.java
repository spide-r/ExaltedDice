package me.spider.db.book;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashSet;

public class BookManager {
    private final ConnectionSource connection;

    Dao<Anima, String> animaDao;
    Dao<AstrologyCollege, String> astrologyDao;
    Dao<Charm, String> charmDao;
    Dao<Equipment, String> equipmentDao;
    Dao<Hearthstone, String> hearthstoneDao;
    Dao<MartialArts, String> martialArtsDao;
    Dao<Sorcery, String> sorceryDao;

    private final HashSet<String> animaKeys = new HashSet<>();
    private final HashSet<String> astrologyKeys = new HashSet<>();
    private final HashSet<String> charmKeys = new HashSet<>();
    private final HashSet<String> equipmentKeys = new HashSet<>();
    private final HashSet<String> hearthstoneKeys = new HashSet<>();
    private final HashSet<String> martsKeys = new HashSet<>();
    private final HashSet<String> sorceryKeys = new HashSet<>();

    public BookManager(){
        try {
            this.connection = new JdbcConnectionSource("jdbc:sqlite:book.db");
            TableUtils.createTableIfNotExists(connection, Anima.class);
            TableUtils.createTableIfNotExists(connection, AstrologyCollege.class);
            TableUtils.createTableIfNotExists(connection, Charm.class);
            TableUtils.createTableIfNotExists(connection, Equipment.class);
            TableUtils.createTableIfNotExists(connection, Hearthstone.class);
            TableUtils.createTableIfNotExists(connection, MartialArts.class);
            TableUtils.createTableIfNotExists(connection, Sorcery.class);

            animaDao = DaoManager.createDao(connection, Anima.class);
            astrologyDao = DaoManager.createDao(connection, AstrologyCollege.class);
            charmDao = DaoManager.createDao(connection, Charm.class);
            equipmentDao = DaoManager.createDao(connection, Equipment.class);
            hearthstoneDao = DaoManager.createDao(connection, Hearthstone.class);
            martialArtsDao = DaoManager.createDao(connection, MartialArts.class);
            sorceryDao = DaoManager.createDao(connection, Sorcery.class);

            loadKeys();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public BookPage getPage(String name, String toSearch) throws SQLException {
        return switch (name.toLowerCase()) {
            case "anima" -> animaDao.queryForId(toSearch);
            case "astrology" -> astrologyDao.queryForId(toSearch);
            case "charms", "charm" -> charmDao.queryForId(toSearch);
            case "equipment" -> equipmentDao.queryForId(toSearch);
            case "hearthstones", "hearthstone" -> hearthstoneDao.queryForId(toSearch);
            case "martialarts", "marts", "mart", "martialart" -> martialArtsDao.queryForId(toSearch);
            case "sorcery" -> sorceryDao.queryForId(toSearch);
            default -> null;
        };
    }

    public HashSet<String> getKeys(String name) throws SQLException {
        return switch (name.toLowerCase()) {
            case "anima" -> animaKeys;
            case "astrology" -> astrologyKeys;
            case "charms", "charm" -> charmKeys;
            case "equipment" -> equipmentKeys;
            case "hearthstones", "hearthstone" -> hearthstoneKeys;
            case "martialarts", "marts", "mart", "martialart" -> martsKeys;
            case "sorcery" -> sorceryKeys;
            default -> null;
        };
    }

    public void loadKeys() throws SQLException {
        animaDao.queryForAll().forEach( a -> animaKeys.add(a.getCaste()));
        astrologyDao.queryForAll().forEach( a -> astrologyKeys.add(a.getName()));
        charmDao.queryForAll().forEach( c -> charmKeys.add(c.getName()));
        equipmentDao.queryForAll().forEach( e -> equipmentKeys.add(e.getName()));
        hearthstoneDao.queryForAll().forEach( h -> hearthstoneKeys.add(h.getName()));
        martialArtsDao.queryForAll().forEach( m -> martsKeys.add(m.getName()));
        sorceryDao.queryForAll().forEach( s -> sorceryKeys.add(s.getName()));
    }

    public Dao<Anima, String> getAnimaDao() {
        return animaDao;
    }

    public Dao<AstrologyCollege, String> getAstrologyDao() {
        return astrologyDao;
    }

    public Dao<Charm, String> getCharmDao() {
        return charmDao;
    }

    public Dao<Equipment, String> getEquipmentDao() {
        return equipmentDao;
    }

    public Dao<Hearthstone, String> getHearthstoneDao() {
        return hearthstoneDao;
    }

    public Dao<MartialArts, String> getMartialArtsDao() {
        return martialArtsDao;
    }

    public Dao<Sorcery, String> getSorceryDao() {
        return sorceryDao;
    }

    public HashSet<String> getAnimaKeys() {
        return animaKeys;
    }

    public HashSet<String> getAstrologyKeys() {
        return astrologyKeys;
    }

    public HashSet<String> getCharmKeys() {
        return charmKeys;
    }

    public HashSet<String> getEquipmentKeys() {
        return equipmentKeys;
    }

    public HashSet<String> getHearthstoneKeys() {
        return hearthstoneKeys;
    }

    public HashSet<String> getMartsKeys() {
        return martsKeys;
    }

    public HashSet<String> getSorceryKeys() {
        return sorceryKeys;
    }

    public void shutdown() {
        connection.closeQuietly();
    }
}
