package me.spider.db.book;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

public class BookManager {
    private final ConnectionSource connection;

    Dao<Anima, String> animaDao;
    Dao<AstrologyCollege, String> astrologyDao;
    Dao<Charm, String> charmDao;
    Dao<Equipment, String> equipmentDao;
    Dao<Hearthstone, String> hearthstoneDao;
    Dao<MartialArts, String> martialArtsDao;
    Dao<Sorcery, String> sorceryDao;
    Dao<Knack, String> knackDao;
    Dao<Submodule, String> submoduleDao;
    Dao<Excellency, String> excellencyDao;

    private final HashSet<String> animaKeys = new HashSet<>();
    private final HashSet<String> astrologyKeys = new HashSet<>();
    private final HashSet<String> charmKeys = new HashSet<>();
    private final HashSet<String> equipmentKeys = new HashSet<>();
    private final HashSet<String> hearthstoneKeys = new HashSet<>();
    private final HashSet<String> martsKeys = new HashSet<>();
    private final HashSet<String> sorceryKeys = new HashSet<>();
    private final HashSet<String> knackKeys = new HashSet<>();
    private final HashSet<String> submoduleKeys = new HashSet<>();
    private final HashSet<String> excellencyKeys = new HashSet<>();
    private final HashSet<String> charmSubsections = new HashSet<>();
    private final HashSet<String> charmCategories = new HashSet<>();
    private final HashSet<String> styles = new HashSet<>();

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
            TableUtils.createTableIfNotExists(connection, Sorcery.class);
            TableUtils.createTableIfNotExists(connection, Knack.class);
            TableUtils.createTableIfNotExists(connection, Submodule.class);
            TableUtils.createTableIfNotExists(connection, Excellency.class);

            animaDao = DaoManager.createDao(connection, Anima.class);
            astrologyDao = DaoManager.createDao(connection, AstrologyCollege.class);
            charmDao = DaoManager.createDao(connection, Charm.class);
            equipmentDao = DaoManager.createDao(connection, Equipment.class);
            hearthstoneDao = DaoManager.createDao(connection, Hearthstone.class);
            martialArtsDao = DaoManager.createDao(connection, MartialArts.class);
            sorceryDao = DaoManager.createDao(connection, Sorcery.class);
            knackDao = DaoManager.createDao(connection, Knack.class);
            submoduleDao = DaoManager.createDao(connection, Submodule.class);
            excellencyDao = DaoManager.createDao(connection, Excellency.class);

            loadKeys();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<String> getMarts(String style) throws SQLException {
        style = "%" + style + "%";
        return martialArtsDao.queryBuilder().where().raw("LOWER(style) LIKE ?", new SelectArg(SqlType.STRING, style.toLowerCase())).query().stream().map(MartialArts::getName).toList();

    }

    public List<String> getCharms(String category, String subsection) throws SQLException {
        category = "%" + category + "%";
        subsection = "%" + subsection + "%";
        return charmDao.queryBuilder().where().raw("LOWER(category) LIKE ? AND LOWER(subsection) LIKE ?",
                new SelectArg(SqlType.STRING, category.toLowerCase()),new SelectArg(SqlType.STRING, subsection.toLowerCase())).query().stream().map(Charm::getName).toList();

    }
    public BookPage getPage(String name, String toSearch) throws SQLException {
        toSearch = "%" + toSearch + "%";
        return switch (name.toLowerCase()) {
            case "anima" -> animaDao.queryBuilder().where().raw("LOWER(caste) LIKE ?", new SelectArg(SqlType.STRING, toSearch.toLowerCase())).queryForFirst();
            case "astrology" -> astrologyDao.queryBuilder().where().raw("LOWER(name) LIKE ?", new SelectArg(SqlType.STRING, toSearch.toLowerCase())).queryForFirst();
            case "charms", "charm" -> charmDao.queryBuilder().where().raw("LOWER(name) LIKE ?", new SelectArg(SqlType.STRING, toSearch.toLowerCase())).queryForFirst();
            case "equipment" -> equipmentDao.queryBuilder().where().raw("LOWER(name) LIKE ?", new SelectArg(SqlType.STRING, toSearch.toLowerCase())).queryForFirst();
            case "hearthstones", "hearthstone" -> hearthstoneDao.queryBuilder().where().raw("LOWER(name) LIKE ?", new SelectArg(SqlType.STRING, toSearch.toLowerCase())).queryForFirst();
            case "martialarts", "marts", "mart", "martialart" -> martialArtsDao.queryBuilder().where().raw("LOWER(name) LIKE ?", new SelectArg(SqlType.STRING, toSearch.toLowerCase())).queryForFirst();
            case "sorcery" -> sorceryDao.queryBuilder().where().raw("LOWER(name) LIKE ?", new SelectArg(SqlType.STRING, toSearch.toLowerCase())).queryForFirst();
            case "knack", "knacks" -> knackDao.queryBuilder().where().raw("LOWER(name) LIKE ?", new SelectArg(SqlType.STRING, toSearch.toLowerCase())).queryForFirst();
            case "submodule", "submodules" -> submoduleDao.queryBuilder().where().raw("LOWER(name) LIKE ?", new SelectArg(SqlType.STRING, toSearch.toLowerCase())).queryForFirst();
            case "excellencies", "excellency" -> excellencyDao.queryBuilder().where().raw("LOWER(name) LIKE ?", new SelectArg(SqlType.STRING, toSearch.toLowerCase())).queryForFirst();
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
            case "knack", "knacks" -> knackKeys;
            case "submodule", "submodules" -> submoduleKeys;
            case "excellencies", "excellency" -> excellencyKeys;
            case "styles" -> styles;
            case "charmsubsections"->charmSubsections;
            case "charmcategories"->charmCategories;
            default -> null;
        };
    }

    public void loadKeys() throws SQLException {
        animaDao.queryForAll().forEach( a -> animaKeys.add(a.getCaste()));
        astrologyDao.queryForAll().forEach( a -> astrologyKeys.add(a.getName()));
        charmDao.queryForAll().forEach( c ->
        {
            charmKeys.add(c.getName());
            charmSubsections.add(c.getSubsection());
            charmCategories.add(c.getCategory());
        });
        equipmentDao.queryForAll().forEach( e -> equipmentKeys.add(e.getName()));
        hearthstoneDao.queryForAll().forEach( h -> hearthstoneKeys.add(h.getName()));
        martialArtsDao.queryForAll().forEach( m -> {
            martsKeys.add(m.getName());
            styles.add(m.getStyle());
        });
        sorceryDao.queryForAll().forEach( s -> sorceryKeys.add(s.getName()));
        knackDao.queryForAll().forEach( s -> knackKeys.add(s.getName()));
        excellencyDao.queryForAll().forEach( s -> excellencyKeys.add(s.getName()));
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
