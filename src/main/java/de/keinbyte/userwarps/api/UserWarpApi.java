package de.keinbyte.userwarps.api;

import com.google.common.collect.Lists;
import de.keinbyte.userwarps.manager.sql.WarpSql;
import de.keinbyte.userwarps.utils.WarpUtil;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author KeinByte
 * @since 28.07.2021
 */

public class UserWarpApi {

    public static void createWarp(UUID creatorUuid, String warpName, String world, double cord_x, double cord_y, double cord_z, float yaw, float pitch){
        WarpSql.createWarp(creatorUuid, warpName, world, cord_x, cord_y, cord_z, yaw,pitch);
    }

    public static void deleteWarp(int warpId, String warpName){
        WarpSql.deleteLocation(warpId, warpName);
    }

    public static void getWarpByWarpName(String warpName){
        WarpSql.getLocation(warpName);
    }

    public static void setCommandStatus(boolean commandStatus){
        WarpUtil.setDisabledWarpCommand(commandStatus);
    }

    public static void addNotAllowedMap(String world){
        WarpUtil.getNotAllowedMaps().add(world);
    }

    public static void removeNotAllowedMap(String world){
        WarpUtil.getNotAllowedMaps().remove(world);
    }

    public static void getWarpByWorld(String warpName){
        WarpSql.getLocation(warpName);
    }

    public static int getTotalWarps() {
        return WarpSql.getWarpNames().size();
    }

    public static int getTotalWarpsFromUser(String playerUuid) {
        return WarpSql.getWarpsByPlayer(playerUuid).size();
    }

    public static double getAveragePerPlayer() {
        List<Integer> totalWarps = Lists.newArrayList();
        for (Iterator<String> interator = Objects.requireNonNull(WarpSql.getPlayers()).iterator(); interator.hasNext(); ) {
            String player_uuid = ((String)interator.next());
            totalWarps.add(getTotalWarpsFromUser(player_uuid));
        }
        double totalAverage = 0.0D;
        for (Iterator<Integer> iterator2 = totalWarps.iterator(); iterator2.hasNext(); ) {
            double totalWarp = iterator2.next();
            totalAverage += totalWarp;
        }
        return totalAverage / totalWarps.size();
    }

    public static void updateWarp(UUID creatorUuid, String warpName, String world, double cord_x, double cord_y, double cord_z, float yaw, float pitch, int uses, long last_use){
        //WarpSql.updateWarpLocation(creatorUuid, warpName, world, cord_x, cord_y, cord_z, yaw, pitch, uses, last_use);
    }

}
