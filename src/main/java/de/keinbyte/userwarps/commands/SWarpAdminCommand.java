package de.keinbyte.userwarps.commands;

import de.keinbyte.userwarps.manager.sql.MessageSql;
import de.keinbyte.userwarps.manager.sql.WarpSql;
import de.keinbyte.userwarps.utils.WarpUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @author KeinByte
 * @since 25.07.2021
 */

public class SWarpAdminCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        Player player = (Player) commandSender;
        OfflinePlayer offlinePlayer = null;

        if (command.getName().equalsIgnoreCase("swarpadmin")){
            if (player.hasPermission("userwarps.admin")){

                if (strings.length == 0){
                   // WarpUtil.createAdminWarpManagement(player);
                }

                if (strings.length == 1){

                    if (strings[0].length() == 36){

                        if (WarpSql.existsUserWarp(offlinePlayer)){
                            try {
                                UUID uuid = UUID.fromString(strings[0]);
                                offlinePlayer = Bukkit.getOfflinePlayer(uuid);
                            }catch (NumberFormatException exception){
                                player.sendMessage("Keine gültige UUID!");
                            }



                        }else{
                            String targetName = strings[0];
                            offlinePlayer = Bukkit.getOfflinePlayer(targetName);
                        }
                    }


                }

            }
        }

        return false;
    }
}
