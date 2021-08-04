package de.keinbyte.userwarps;

import de.keinbyte.userwarps.commands.SWarpAdminCommand;
import de.keinbyte.userwarps.commands.SWarpCommand;
import de.keinbyte.userwarps.listener.InventoryClickListener;
import de.keinbyte.userwarps.manager.DatabaseManager;
import de.keinbyte.userwarps.manager.builder.ItemBuilder;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author KeinByte
 * @since 13.07.2021
 */

public class UserWarps extends JavaPlugin {

    @Getter
    private static UserWarps instance;
    @Getter
    private static ItemBuilder itemBuilder;
    @Getter
    private static DatabaseManager databaseManager;

    @Override
    public void onEnable() {

        instance = this;

        getConfig().options().copyDefaults(true);
        saveConfig();
        reloadConfig();

        setupDatabase();

        getCommand("swarp").setExecutor(new SWarpCommand());
        getCommand("swarpadmin").setExecutor(new SWarpAdminCommand());
        getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
        getServer().getPluginManager().registerEvents(new SWarpCommand(), this);

    }

    @Override
    public void onDisable() {
        DatabaseManager.disconnectDatabase();
    }

    private void setupDatabase(){

        DatabaseManager.connectDatabase();
        if (DatabaseManager.isConnected()){
            DatabaseManager.updateQuery("CREATE TABLE IF NOT EXISTS `warp_location` (`player_uuid` varchar(36) NOT NULL, `location_name` varchar(10) NOT NULL, `location_world` varchar(100) NOT NULL, `location_cord_x` double(50, 14) NOT NULL, `location_cord_y` double(25, 14) NOT NULL, `location_cord_z` double(50, 14) NOT NULL, `location_yaw` float(50) NOT NULL, `location_pitch` float(50) NOT NULL, `location_uses` int(100) NOT NULL, `location_last_use` bigint(50) NOT NULL, `warp_id` int(20) NOT NULL AUTO_INCREMENT PRIMARY KEY)");
            DatabaseManager.updateQuery("CREATE TABLE IF NOT EXISTS `warp_messages` (`key` varchar(100) NOT NULL, `message` varchar(1000) NOT NULL)");
        }
    }

}
