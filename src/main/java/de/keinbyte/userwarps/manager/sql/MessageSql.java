package de.keinbyte.userwarps.manager.sql;

import de.keinbyte.userwarps.manager.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author KeinByte
 * @since 31.07.2021
 */

public class MessageSql {

    public static String getMessage(String messageKey) {
        try {
            PreparedStatement preparedStatement = DatabaseManager.getConnection().prepareStatement("SELECT message FROM warp_messages");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                messageKey = resultSet.getString("message");
            }
            return messageKey;
        } catch (SQLException exception) {
            return null;
        }
    }

}
