package dev.masa.masuitecore.core.services;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import lombok.Getter;

import java.sql.SQLException;

public class DatabaseService {

    @Getter
    private JdbcPooledConnectionSource connection;

    public DatabaseService(String address, int port, String name, String username, String password) {
        try {
            connection = new JdbcPooledConnectionSource("jdbc:mysql://" + address + ":" + port + "/" + name + "?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=UTF-8", username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
