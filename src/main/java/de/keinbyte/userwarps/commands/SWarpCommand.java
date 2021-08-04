package de.keinbyte.userwarps.commands;

import de.keinbyte.userwarps.UserWarps;
import de.keinbyte.userwarps.manager.sql.MessageSql;
import de.keinbyte.userwarps.manager.sql.WarpSql;
import de.keinbyte.userwarps.utils.WarpUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Objects;

/**
 * @author KeinByte
 * @since 14.07.2021
 */

public class SWarpCommand implements CommandExecutor, Listener {

    private static String nameOfWarps = null;
    private static FileConfiguration config = UserWarps.getInstance().getConfig();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        Player player = (Player) commandSender;

        if (command.getName().equalsIgnoreCase("swarp")){

            if (!player.hasPermission("swarp.use")){
                player.sendMessage(config.getString("messages.swarp.hasNoRights"));
                return true;
            }

            if (strings.length == 0){
                WarpUtil.createWarpInventory(player);
                return true;
            }

            if (strings.length == 2){
                String warpName = strings[0];
                if (strings[1].equalsIgnoreCase("delete")){
                    if (WarpSql.existsWarp(warpName)){
                        WarpSql.deleteLocation(WarpSql.getWarpId(warpName), warpName);
                        player.sendMessage(Objects.requireNonNull(config.getString("messages.swarp.deleted").replace("{0}", warpName)));
                    }else{
                        player.sendMessage(Objects.requireNonNull(config.getString("messages.swarp.noExists")));
                    }
                }else if (strings[1].equalsIgnoreCase("create")){
                    Location location = player.getLocation();
                    if (warpName.length() <= 10){
                        if (!WarpSql.existsWarp(warpName)){
                            WarpSql.createWarp(player.getUniqueId(), warpName, Objects.requireNonNull(location.getWorld()).getName(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
                            player.sendMessage("Warp mit dem Namen " + warpName + " erstellt!");
                        }else{
                            player.sendMessage(Objects.requireNonNull(config.getString("messages.swarp.isExists")));
                        }
                    }else{
                        player.sendMessage("Der Warpname darf nicht länger als 10 Zeichen sein!");
                    }

                }else if (strings[1].equalsIgnoreCase("list")){
                    player.sendMessage("list" + warpName);
                }else if (strings[1].equalsIgnoreCase("tp")){
                    if (WarpSql.existsWarp(warpName)){
                        player.teleport(Objects.requireNonNull(WarpSql.getLocation(warpName)));
                        player.sendMessage("Du hast dich zu dem Warp " + warpName + " teleportiert.");
                    }else{
                        player.sendMessage("Warp ist nicht vorhanden.");
                    }
                }else{
                    player.sendMessage("§e/swarp §8<§eName§8> §8<§ecreate§8|§eremove§8|§elist§8|§etp§8>");
                }

            }

        }

        return false;
    }

    @EventHandler
    public void handleInventoryClick(InventoryClickEvent inventoryClickEvent){
        Player player = (Player) inventoryClickEvent.getWhoClicked();
        String warpName = inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().substring(6);

        if (inventoryClickEvent.getView().getTitle().equalsIgnoreCase("TITLE1") && inventoryClickEvent.getCurrentItem() != null && inventoryClickEvent.getCurrentItem().getItemMeta() != null){
            inventoryClickEvent.setCancelled(true);
            if (inventoryClickEvent.getClick().isLeftClick() || inventoryClickEvent.getClick().isRightClick()){
                player.closeInventory();
                Bukkit.dispatchCommand(player, "swarp " + warpName + " tp");
            }
        }
        if (inventoryClickEvent.getView().getTitle().equalsIgnoreCase(UserWarps.getInstance().getConfig().getString("inventory.administration.title")) && inventoryClickEvent.getCurrentItem() != null && inventoryClickEvent.getCurrentItem().getItemMeta() != null){
            inventoryClickEvent.setCancelled(true);
            if (inventoryClickEvent.getClick().isLeftClick() || inventoryClickEvent.getClick().isRightClick()){
                WarpUtil.createConfirmationInventory(player);
                setNameOfWarps(warpName);
            }
        }

        if (inventoryClickEvent.getView().getTitle().equalsIgnoreCase(UserWarps.getInstance().getConfig().getString("inventory.administration.delete.title")) && inventoryClickEvent.getCurrentItem() != null && inventoryClickEvent.getCurrentItem().getItemMeta() != null){
            inventoryClickEvent.setCancelled(true);
            if (inventoryClickEvent.getClick().isLeftClick() || inventoryClickEvent.getClick().isRightClick()){
                if (inventoryClickEvent.getCurrentItem().getType() == Material.GREEN_DYE){
                    player.closeInventory();
                    Bukkit.getServer().dispatchCommand(player, "swarp " + getNameOfWarps() + " delete");
                    player.sendMessage("Der Warp wurde gelöscht.");
                }else if (inventoryClickEvent.getCurrentItem().getType() == Material.RED_DYE){
                    player.closeInventory();
                    player.sendMessage("Der Warp wurde nicht gelöscht.");
                }
            }
        }else{
            try {
                player.closeInventory();
            }catch (NullPointerException exception){
                exception.printStackTrace();
            }
        }

    }

    public static String getNameOfWarps(){
        return nameOfWarps;
    }

    public static void setNameOfWarps(String nameOfWarps) {
        SWarpCommand.nameOfWarps = nameOfWarps;
    }
}
