package fi.matiaspaavilainen.masuitecore.managers;

import fi.matiaspaavilainen.masuitecore.MaSuiteCore;
import fi.matiaspaavilainen.masuitecore.config.Configuration;
import fi.matiaspaavilainen.masuitecore.database.Database;
import fi.matiaspaavilainen.masuitecore.listeners.MaSuitePlayerGroup;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class MaSuitePlayer {

    Connection connection = null;
    PreparedStatement statement = null;
    Database db = MaSuiteCore.db;
    Configuration config = new Configuration();
    String tablePrefix = config.load(null, "config.yml").getString("database.table-prefix");
    private String username;
    private String nickname;
    private java.util.UUID UUID;
    private String ipAddress;
    private Long firstLogin;
    private Long lastLogin;
    private Location location;

    public MaSuitePlayer() {
    }

    public MaSuitePlayer(String username, String nickname, java.util.UUID UUID, String ipAddress, Long firstLogin, Long lastLogin) {
        this.username = username;
        this.nickname = nickname;
        this.UUID = UUID;
        this.ipAddress = ipAddress;
        this.firstLogin = firstLogin;
        this.lastLogin = lastLogin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public java.util.UUID getUUID() {
        return UUID;
    }

    public void setUUID(java.util.UUID UUID) {
        this.UUID = UUID;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Long getFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(Long firstLogin) {
        this.firstLogin = firstLogin;
    }

    public Long getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Long lastLogin) {
        this.lastLogin = lastLogin;
    }

    public void setLocation(Location location) { this.location = location; }

    public Location getLocation() { return this.location; }

    public synchronized void requestLocation() {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        ProxiedPlayer p = ProxyServer.getInstance().getPlayer(this.UUID);
        try {
            if (p == null) {
                return;
            }
            out.writeUTF("MaSuitePlayerLocation");
            out.writeUTF(String.valueOf(this.UUID));
            p.getServer().sendData("BungeeCord", b.toByteArray());

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public synchronized Group getGroup(){
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        ProxiedPlayer p = ProxyServer.getInstance().getPlayer(this.UUID);
        if (p == null) {
            return new Group();
        }
        // If Cache contains player
        if(MaSuitePlayerGroup.groups.asMap().containsKey(p.getUniqueId())){
            return MaSuitePlayerGroup.groups.getIfPresent(this.UUID);
        }
        try {
            out.writeUTF("MaSuitePlayerGroup");
            out.writeUTF(String.valueOf(this.UUID));
            p.getServer().sendData("BungeeCord", b.toByteArray());

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // Return group
        final Group[] group = {new Group()};
        ProxyServer.getInstance().getScheduler().schedule(new MaSuiteCore(), () -> group[0] = MaSuitePlayerGroup.groups.getIfPresent(this.UUID), 50, TimeUnit.MILLISECONDS);
        return group[0];
    }
    public synchronized Group getGroup(UUID uuid){
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        ProxiedPlayer p = ProxyServer.getInstance().getPlayer(uuid);
        if (p == null) {
            return new Group();
        }
        // If Cache contains player
        if(MaSuitePlayerGroup.groups.asMap().containsKey(p.getUniqueId())){
            return MaSuitePlayerGroup.groups.getIfPresent(uuid);
        }
        try {
            out.writeUTF("MaSuitePlayerGroup");
            out.writeUTF(String.valueOf(uuid));
            p.getServer().sendData("BungeeCord", b.toByteArray());

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // Return group
        final Group[] group = {new Group()};
        ProxyServer.getInstance().getScheduler().schedule(new MaSuiteCore(), () -> group[0] = MaSuitePlayerGroup.groups.getIfPresent(uuid), 50, TimeUnit.MILLISECONDS);
        return group[0];
    }
    public void insert() {
        String insert = "INSERT INTO " + tablePrefix + "players (username, nickname, uuid, ipAddress, firstLogin, lastLogin) VALUES (?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE username = ?, ipAddress = ?;";
        try {
            connection = db.hikari.getConnection();
            statement = connection.prepareStatement(insert);
            statement.setString(1, this.username);
            statement.setString(2, this.nickname);
            statement.setString(3, String.valueOf(this.UUID));
            statement.setString(4, this.ipAddress);
            statement.setLong(5, this.firstLogin);
            statement.setLong(6, this.lastLogin);
            statement.setString(7, this.username);
            statement.setString(8, this.ipAddress);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public MaSuitePlayer find(UUID uuid) {
        MaSuitePlayer msp = new MaSuitePlayer();
        ResultSet resultSet = null;

        try {
            connection = MaSuiteCore.db.hikari.getConnection();
            statement = connection.prepareStatement("SELECT * FROM " + tablePrefix + "players WHERE uuid = ?");
            statement.setString(1, String.valueOf(uuid));
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                msp.setUsername(resultSet.getString("username"));
                msp.setNickname(resultSet.getString("nickname"));
                msp.setUUID(java.util.UUID.fromString(resultSet.getString("uuid")));
                msp.setIpAddress(resultSet.getString("ipAddress"));
                msp.setFirstLogin(resultSet.getLong("firstLogin"));
                msp.setLastLogin(resultSet.getLong("lastLogin"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
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
        return msp;
    }

    public void update(MaSuitePlayer msp) {
        String update = "UPDATE " + tablePrefix + "players SET username = ?, nickname = ?, ipAddress = ?, lastLogin = ? WHERE uuid = ?";
        try {
            connection = db.hikari.getConnection();
            statement = connection.prepareStatement(update);
            statement.setString(1, msp.getUsername());
            statement.setString(2, msp.getNickname());
            statement.setString(3, msp.getIpAddress());
            statement.setLong(4, msp.getLastLogin());
            statement.setString(5, String.valueOf(msp.getUUID()));
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
}
