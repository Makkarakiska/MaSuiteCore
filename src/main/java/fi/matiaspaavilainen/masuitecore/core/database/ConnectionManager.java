package fi.matiaspaavilainen.masuitecore.core.database;

import java.sql.SQLException;

public class ConnectionManager {

    private String tablePrefix;
    private String address;
    private int port;
    private String database;
    private String username;
    private String password;

    public static Database db = null;

    /**
     * An empty constructor for ConnectionManager
     *
     * @param tablePrefix prefix of all MaSuite tables
     * @param address     address of the MySQL server
     * @param port        port of the MySQL server
     * @param database    database of the MySQL server
     * @param username    username of the MySQl server
     * @param password    password of the MySQL server
     */
    public ConnectionManager(String tablePrefix, String address, int port, String database, String username, String password) {
        this.tablePrefix = tablePrefix;
        this.address = address;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    /**
     * Get database
     *
     * @return {@link Database}
     */
    public Database getDatabase() {
        return db;
    }

    /**
     * Connect to {@link Database}
     */
    public void connect() {
        db = new Database(this.tablePrefix, this.address, this.port, this.database, this.username, this.password);
        db.connect();
    }

    /**
     * Close connection
     */
    public void close() {
        try {
            if (db.hikari.getConnection() != null) {
                db.hikari.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
