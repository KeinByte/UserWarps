package de.keinbyte.userwarps.manager;

import de.keinbyte.userwarps.UserWarps;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author KeinByte
 * @since 14.07.2021
 */

@Getter
public class DatabaseManager {

    @Getter
    private static Connection connection;
    private static String host = UserWarps.getInstance().getConfig().getString("mysql.connection.host");
    private static String database = UserWarps.getInstance().getConfig().getString("mysql.connection.database");;
    private static String username = UserWarps.getInstance().getConfig().getString("mysql.connection.username");;
    private static String password = UserWarps.getInstance().getConfig().getString("mysql.connection.password");;
    private static String port = UserWarps.getInstance().getConfig().getString("mysql.connection.port");;

    public static void connectDatabase() {
        if (!isConnected()) {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true", username, password);
                Bukkit.getConsoleSender().sendMessage("§9MineSucht §8• §7Die Verbindung wurde §aerfolgreich hergestellt§7!");
            } catch (SQLException exception) {
                Bukkit.getConsoleSender().sendMessage("§9MineSucht §8• §7Die Verbindung konnte §cnicht hergestellt §7werden!");
            }
        }
    }

    public static void disconnectDatabase() {
        if (isConnected()) {
            try {
                connection.close();
                Bukkit.getConsoleSender().sendMessage("§9MineSucht §8• §7Die Verbindung wurde §aerfolgreich §cgeschlossen§7!");
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    public static boolean isConnected() {
        return (connection != null);
    }

    public static void updateQuery(String query) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException exception) {
            connectDatabase();
            System.err.println(exception);
        }
    }

}
