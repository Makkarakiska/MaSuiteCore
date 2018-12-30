package fi.matiaspaavilainen.masuitecore.core.database;

import java.sql.SQLException;

public class ConnectionManager {

    public static Database db = new Database();

    /**
     * An empty constructor for ConnectionManager
     */
    public ConnectionManager() {}

    /**
     * Get database
     * @return {@link Database}
     */
    public Database getDatabase() {
        return db;
    }

    /**
     * Connect to {@link Database}
     */
    public void connect() {
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
