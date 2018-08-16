package fi.matiaspaavilainen.masuitecore.database;

import com.zaxxer.hikari.HikariDataSource;
import fi.matiaspaavilainen.masuitecore.config.Loader;
import net.md_5.bungee.config.Configuration;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {

    private static HikariDataSource hikari;

    public static void connect() {
        try {
            Configuration config = Loader.load("config.yml");
            hikari = new HikariDataSource();
            hikari.setMaximumPoolSize(10);
            hikari.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
            hikari.addDataSourceProperty("serverName", config.getString("database.address"));
            hikari.addDataSourceProperty("port", config.getString("database.port"));
            hikari.addDataSourceProperty("databaseName", config.getString("database.database"));
            hikari.addDataSourceProperty("user", config.getString("database.username"));
            hikari.addDataSourceProperty("password", config.getString("database.password"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void createTable(String name, String SQL) {
        Connection connection = null;
        PreparedStatement statement = null;
        String tablePrefix = Loader.load("config.yml").getString("database.table-prefix");
        try {
            connection = hikari.getConnection();
            statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + tablePrefix + name + " " + SQL);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
