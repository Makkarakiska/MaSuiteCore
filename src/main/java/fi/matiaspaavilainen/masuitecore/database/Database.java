package fi.matiaspaavilainen.masuitecore.database;

import com.zaxxer.hikari.HikariDataSource;
import fi.matiaspaavilainen.masuitecore.config.Loader;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {

    private static HikariDataSource hikari;

    public static void connectToDatabase() {
        try {
            Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File("plugins/MaSuite", "config.yml"));
            String address = config.getString("database.address");
            String port = config.getString("database.port");
            String name = config.getString("database.database");
            String username = config.getString("database.username");
            String password = config.getString("database.password");

            hikari = new HikariDataSource();
            hikari.setMaximumPoolSize(10);
            hikari.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
            hikari.addDataSourceProperty("serverName", address);
            hikari.addDataSourceProperty("port", port);
            hikari.addDataSourceProperty("databaseName", name);
            hikari.addDataSourceProperty("user", username);
            hikari.addDataSourceProperty("password", password);
        } catch (IOException e) {
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
