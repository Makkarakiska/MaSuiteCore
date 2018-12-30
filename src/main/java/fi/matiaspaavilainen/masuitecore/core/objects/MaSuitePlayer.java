package fi.matiaspaavilainen.masuitecore.core.objects;

import fi.matiaspaavilainen.masuitecore.core.database.ConnectionManager;
import fi.matiaspaavilainen.masuitecore.core.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class MaSuitePlayer {

    private Connection connection = null;
    private PreparedStatement statement = null;
    private Database db = ConnectionManager.db;
    private String tablePrefix = db.getTablePrefix();
    private String username;
    private String nickname;
    private java.util.UUID uniqueId;

    private Long firstLogin;
    private Long lastLogin;

    /**
     * An empty constructor for MaSuitePlayer
     */
    public MaSuitePlayer() {
    }

    /**
     * Constructor for MaSuitePlayer
     *
     * @param username   user's name
     * @param nickname   user's nickname
     * @param uniqueId   user's unique identifier
     * @param firstLogin user's first login
     * @param lastLogin  user's last login
     */
    public MaSuitePlayer(String username, String nickname, java.util.UUID uniqueId, Long firstLogin, Long lastLogin) {
        this.username = username;
        this.nickname = nickname;
        this.uniqueId = uniqueId;
        this.firstLogin = firstLogin;
        this.lastLogin = lastLogin;
    }

    /**
     * Saves user's data to database
     */
    public void create() {
        String sql = "INSERT INTO " + tablePrefix + "players (username, nickname, uuid, firstLogin, lastLogin) VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE username = ?;";
        try {
            connection = db.hikari.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, this.username);
            statement.setString(2, this.nickname);
            statement.setString(3, this.uniqueId.toString());
            statement.setLong(4, this.firstLogin);
            statement.setLong(5, this.lastLogin);
            statement.setString(6, this.username);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * Find user by unique identifier from database
     *
     * @param uuid user's unique identifier
     * @return matching MaSuitePlayer
     */
    public MaSuitePlayer find(UUID uuid) {
        if (uuid == null) {
            System.out.println("[MaSuite] [Core] There was an error while getting [MaSuitePlayer]");
            return null;
        }
        MaSuitePlayer msp = new MaSuitePlayer();
        ResultSet rs = null;
        try {
            connection = db.hikari.getConnection();
            statement = connection.prepareStatement("SELECT * FROM " + tablePrefix + "players WHERE uuid = ?;");
            statement.setString(1, uuid.toString());
            rs = statement.executeQuery();
            while (rs.next()) {
                setupMSP(rs, msp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs);
        }
        return msp;
    }

    /**
     * Find user by unique identifier from database
     *
     * @param name user's unique identifier
     * @return matching MaSuitePlayer
     */
    public MaSuitePlayer find(String name) {
        MaSuitePlayer msp = new MaSuitePlayer();
        ResultSet rs = null;
        try {
            connection = db.hikari.getConnection();
            statement = connection.prepareStatement("SELECT * FROM " + tablePrefix + "players WHERE username = ?;");
            statement.setString(1, name);
            rs = statement.executeQuery();
            while (rs.next()) {
                setupMSP(rs, msp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs);
        }
        return msp;
    }

    /**
     * Find all users from database
     *
     * @return Set of MaSuitePlayers
     */
    public Set<MaSuitePlayer> findAll() {
        Set<MaSuitePlayer> maSuitePlayers = new HashSet<>();
        ResultSet rs = null;
        try {
            connection = db.hikari.getConnection();
            statement = connection.prepareStatement("SELECT * FROM " + tablePrefix + "players;");
            rs = statement.executeQuery();
            while (rs.next()) {
                MaSuitePlayer msp = new MaSuitePlayer();
                setupMSP(rs, msp);
                maSuitePlayers.add(msp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs);
        }
        return maSuitePlayers;
    }

    /**
     * Updates user information
     */
    public void update() {
        String update = "UPDATE " + tablePrefix + "players SET username = ?, nickname = ?, lastLogin = ? WHERE uuid = ?";
        try {
            connection = db.hikari.getConnection();
            statement = connection.prepareStatement(update);
            statement.setString(1, this.username);
            statement.setString(2, this.nickname);
            statement.setLong(3, this.lastLogin);
            statement.setString(4, this.uniqueId.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Setups MSP from ResultSet
     *
     * @param rs  ResultSet to use
     * @param msp MaSuitePlayer
     * @throws SQLException If there are errors
     */
    private void setupMSP(ResultSet rs, MaSuitePlayer msp) throws SQLException {
        msp.setUsername(rs.getString("username"));
        msp.setNickname(rs.getString("nickname"));
        msp.setUniqueId(UUID.fromString(rs.getString("uuid")));
        msp.setFirstLogin(rs.getLong("firstLogin"));
        msp.setLastLogin(rs.getLong("lastLogin"));
    }

    /**
     * Closes connections
     *
     * @param rs ResultSet to close
     */
    private void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @return username of user
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username sets username of user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @param nickname sets nickname of user
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return nickname of user
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param uniqueId unique identifier of user
     */
    public void setUniqueId(UUID uniqueId) {
        this.uniqueId = uniqueId;
    }

    /**
     * @return unique identifier of user
     */
    public UUID getUniqueId() {
        return uniqueId;
    }

    /**
     * @return first login of user
     */
    public Long getFirstLogin() {
        return firstLogin;
    }

    /**
     * @param firstLogin first login of user
     */
    public void setFirstLogin(Long firstLogin) {
        this.firstLogin = firstLogin;
    }

    /**
     * @return last login of user
     */
    public Long getLastLogin() {
        return lastLogin;
    }

    /**
     * @param lastLogin last login of user
     */
    public void setLastLogin(Long lastLogin) {
        this.lastLogin = lastLogin;
    }
}
