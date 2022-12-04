package me.evilterabite.parkourplugin.mysql;

import me.evilterabite.parkourplugin.ParkourPlugin;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

    private final FileConfiguration config = ParkourPlugin.getInstance().getConfig();
    private String host = config.getString("mysql.host");
    private String port = config.getString("mysql.port");;
    private String database = config.getString("mysql.database");;
    private String username = config.getString("mysql.username");;
    private String password = config.getString("mysql.password");;

    private Connection connection;

    public boolean isConnected() {
        return (connection == null ? false : true);
    }

    public void connect() throws ClassNotFoundException, SQLException {
        if(isConnected()) {
            connection = DriverManager.getConnection("jdbc:mysql://" +
                            host + ":" + port + "/" + database + "?useSSL=false",
                    username, password);
        }
    }

    public void disconnect() {
        if(isConnected()) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }
}