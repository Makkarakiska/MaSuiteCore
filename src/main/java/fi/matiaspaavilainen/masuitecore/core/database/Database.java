package fi.matiaspaavilainen.masuitecore.core.database;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {

    public HikariDataSource hikari;

    private String tablePrefix;
    private String address;
    private int port;
    private String database;

    private String username;
    private String password;

    /**
     * Constructor for Database
     *
     * @param tablePrefix prefix of all MaSuite tables
     * @param address     address of the MySQL server
     * @param port        port of the MySQL server
     * @param database    database of the MySQL server
     * @param username    username of the MySQl server
     * @param password    password of the MySQL server
     */
    public Database(String tablePrefix, String address, int port, String database, String username, String password) {
        this.tablePrefix = tablePrefix;
        this.address = address;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    /**
     * Create connection by using credentials from config.yml
     */
    public void connect() {
        try {
            hikari = new HikariDataSource();
            hikari.setMaximumPoolSize(10);
            hikari.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
            hikari.addDataSourceProperty("serverName", this.address);
            hikari.addDataSourceProperty("port", this.port);
            hikari.addDataSourceProperty("databaseName", this.database);
            hikari.addDataSourceProperty("user", this.username);
            hikari.addDataSourceProperty("password", this.password);
        } catch (Exception e) {
            System.out.println("Ooops! Something went wrong. Check your MaSuite database settings!");
        }

    }


    /**
     * Create table with fields
     *
     * @param name table name
     * @param SQL  SQL code to execute
     */
    public void createTable(String name, String SQL) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = hikari.getConnection();
            statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + this.tablePrefix + name + " " + SQL);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param tablePrefix table prefix of MaSuite plugins
     */
    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    /**
     * @return table prefix of MaSuite plugins
     */
    public String getTablePrefix() {
        return tablePrefix;
    }

    /**
     * @return address of the MySQL server
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address address of the MySQL server
     */
    public void setAddress(String address) {
        this.address = address;
    }


    /**
     * @return port of the MySQL server
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port port of the MySQL server
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * @return name of the MySQL server
     */
    public String getDatabase() {
        return database;
    }

    /**
     * @param database database of the MySQL server
     */
    public void setDatabase(String database) {
        this.database = database;
    }

    /**
     * @return username of the MySQL server
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username username of the MySQL server
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return password of the MySQL server
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password password of the MySQL server
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
