package de.keinbyte.userwarps.manager.sql;

import com.google.common.collect.Lists;
import de.keinbyte.userwarps.manager.DatabaseManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author KeinByte
 * @since 17.07.2021
 */

public class WarpSql {

    private static int location_uses;
    private static String warpName;
    private static int warpId;

    public static boolean existsWarp(String warpName){
        try {
            PreparedStatement preparedStatement = DatabaseManager.getConnection().prepareStatement("SELECT * FROM warp_location WHERE location_name=?");
            preparedStatement.setString(1, warpName);

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        }catch (SQLException exception){
            return false;
        }
    }

    public static boolean existsUserWarp(OfflinePlayer player){
        try {
            PreparedStatement preparedStatement = DatabaseManager.getConnection().prepareStatement("SELECT * FROM warp_location WHERE player_uuid=?");
            preparedStatement.setString(1, String.valueOf(player));

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        }catch (SQLException exception){
            return false;
        }
    }

    public static void createWarp(UUID creatorUuid, String warpName, String world, double cordX, double cordY, double cordZ, float yaw, float pitch){
        if (!existsWarp(warpName)){
            try {
                PreparedStatement preparedStatement = DatabaseManager.getConnection().prepareStatement("INSERT INTO warp_location (player_uuid, location_name, location_world, location_cord_x, location_cord_y, location_cord_z, location_yaw, location_pitch, location_uses, location_last_use) VALUES (?,?,?,?,?,?,?,?,?,?)");
                preparedStatement.setString(1, String.valueOf(creatorUuid));
                preparedStatement.setString(2, warpName);
                preparedStatement.setString(3, world);
                preparedStatement.setDouble(4, cordX);
                preparedStatement.setDouble(5, cordY);
                preparedStatement.setDouble(6, cordZ);
                preparedStatement.setFloat(7, yaw);
                preparedStatement.setFloat(8, pitch);
                preparedStatement.setInt(9, 0);
                preparedStatement.setLong(10, 0L);
                preparedStatement.execute();
                Bukkit.getLogger().info("The UserWarp " + warpName + " was created by UUID " + creatorUuid + ".");
            }catch (SQLException exception){
                exception.printStackTrace();
            }
        }else{
            Bukkit.getConsoleSender().sendMessage("BEREITS VORHANDEN");
            return;
        }
    }

    public static Location getLocation(String warpName){
        Location location = null;

        try {

            PreparedStatement preparedStatement = DatabaseManager.getConnection().prepareStatement("SELECT * FROM warp_location WHERE location_name=?");
            preparedStatement.setString(1, warpName);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                location = new Location(Bukkit.getWorld(resultSet.getString("location_world")), resultSet.getDouble("location_cord_x"), resultSet.getDouble("location_cord_y"), resultSet.getDouble("location_cord_z"), resultSet.getFloat("location_yaw"), resultSet.getFloat("location_pitch"));

            }
            return location;
        }catch (SQLException exception){
            exception.printStackTrace();
            return null;
        }

    }

    public static int getWarpId(String warpName){
        int iD = 0;
        try {
            PreparedStatement preparedStatement = DatabaseManager.getConnection().prepareStatement("SELECT * FROM warp_location WHERE location_name=?");
            preparedStatement.setString(1, warpName);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                iD = resultSet.getInt("warp_id");
            }

        }catch (SQLException exception){
            exception.printStackTrace();
        }
        return iD;
    }
    public static void deleteLocation(int warpId, String warpName) {

        if (existsWarp(warpName)){
            try {
                PreparedStatement preparedStatement = DatabaseManager.getConnection().prepareStatement("DELETE FROM warp_location WHERE warp_id=? AND location_name=?");
                preparedStatement.setInt(1, warpId);
                preparedStatement.setString(2, warpName);
                preparedStatement.executeUpdate();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    public static List<String> getWarpNames() {
        List<String> warpNames = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = DatabaseManager.getConnection().prepareStatement("SELECT location_name FROM warp_location");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                warpNames.add(resultSet.getString("location_name"));
            }
            return warpNames;
        } catch (SQLException exception) {
            return null;
        }
    }

    public static List<String> getWarpByNameForOwner(String warpName){
        List<String> warpsByUuid = Lists.newArrayList();
        try {
            PreparedStatement preparedStatement = DatabaseManager.getConnection().prepareStatement("SELECT * FROM warp_location WHERE player_uuid=?");
            preparedStatement.setString(1, warpName);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                warpName = resultSet.getString("location_name");
                warpsByUuid.add(warpName);
            }

        }catch (SQLException exception){
            exception.printStackTrace();
        }
        return warpsByUuid;
    }

    public static List<String> getWarpByWorld(String world){
        List<String> warpsByWorld = Lists.newArrayList();
        try {
            PreparedStatement preparedStatement = DatabaseManager.getConnection().prepareStatement("SELECT * FROM warp_location WHERE location_world=?");
            preparedStatement.setString(1, world);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                world = resultSet.getString("location_world");
                warpsByWorld.add(world);
            }

        }catch (SQLException exception){
            exception.printStackTrace();
        }
        return warpsByWorld;
    }

    public static List<String> getWarpsByPlayer(String playerUuid){
        List<String> playerWarps = Lists.newArrayList();
        try {
            PreparedStatement preparedStatement = DatabaseManager.getConnection().prepareStatement("SELECT * FROM warp_location WHERE player_uuid=?");
            preparedStatement.setString(1, String.valueOf(playerUuid));

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                playerUuid = resultSet.getString("player_uuid");
                playerWarps.add(playerUuid);
            }

        }catch (SQLException exception){
            exception.printStackTrace();
        }
        return playerWarps;
    }

    public static List<String> getPlayers() {
        List<String> playerUuids = Lists.newArrayList();
        try {
            PreparedStatement preparedStatement = DatabaseManager.getConnection().prepareStatement("SELECT player_uuid FROM warp_location");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String playerUuid = resultSet.getString("player_uuid");
                playerUuids.add(playerUuid);
            }
            return playerUuids;
        } catch (SQLException exception) {
            return null;
        }
    }
//    public static void updateWarpLocation(UUID creatorUuid, String warpName, String world, double locationX, double locationY, double locationZ, float yaw, float pitch, int uses, long lastUse) {
    //      if (!existsWarp(warpName)) {
//      try {
    //              PreparedStatement preparedStatement = DatabaseManager.getConnection().prepareStatement("UPDATE warp_location SET location_name=?, location_world=?, location_cord_x=?, location_cord_y=?, location_cord_z=?, location_yaw=?, location_pitch=?, location_uses=?, location_last_use=?) WHERE player_uuid=?");
    //          preparedStatement.setString(1, warpName);
    //          preparedStatement.setString(2, world);
    //          preparedStatement.setDouble(3, locationX);
    //          preparedStatement.setDouble(4, locationY);
    //          preparedStatement.setDouble(5, locationZ);
    //          preparedStatement.setFloat(6, yaw);
    //          preparedStatement.setFloat(7, pitch);
    //          preparedStatement.setInt(8, uses);
    //          preparedStatement.setLong(9, lastUse);
    //          preparedStatement.setString(10, String.valueOf(creatorUuid));
    //          preparedStatement.execute();
    //      } catch (SQLException exception) {
    //          exception.printStackTrace();
    //      }
    //  }
    //}

    public static int getLocationUses(String warpName) {
        int uses = 0;
        try {
            PreparedStatement preparedStatement = DatabaseManager.getConnection().prepareStatement("SELECT location_uses FROM warp_location=?");
            preparedStatement.setString(1, WarpSql.warpName);
            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                uses = uses + resultSet.getInt("location_uses");
            }
            return uses;
        } catch (SQLException e) {
            return 0;
        }
    }

}
