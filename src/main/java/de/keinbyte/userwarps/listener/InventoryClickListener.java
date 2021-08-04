package de.keinbyte.userwarps.listener;

import de.keinbyte.userwarps.UserWarps;
import de.keinbyte.userwarps.manager.sql.WarpSql;
import de.keinbyte.userwarps.utils.WarpUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Objects;

/**
 * @author KeinByte
 * @since 25.07.2021
 */

public class InventoryClickListener implements Listener {

    private static String alphabeticDisplay = UserWarps.getInstance().getConfig().getString("inventory.main_inventory.sort.alphabetical.name");
    private static String lastDisplay = UserWarps.getInstance().getConfig().getString("inventory.main_inventory.sort.last.name");
    private static String lastUsesDisplay = UserWarps.getInstance().getConfig().getString("inventory.main_inventory.sort.uses.name");
    private static String administrationDisplay = UserWarps.getInstance().getConfig().getString("inventory.main_inventory.administration.name");

    private static String deleteDisplay = UserWarps.getInstance().getConfig().getString("inventory.administration.delete.name");

    @EventHandler
    public void handleInventoryClick(InventoryClickEvent inventoryClickEvent){

        Player player = (Player) inventoryClickEvent.getWhoClicked();
        String warpName = inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().substring(6);

        if (inventoryClickEvent.isRightClick() || inventoryClickEvent.isLeftClick() && inventoryClickEvent.getCurrentItem() != null && inventoryClickEvent.getCurrentItem().getItemMeta() != null){
            if (inventoryClickEvent.getView().getTitle().equalsIgnoreCase(UserWarps.getInstance().getConfig().getString("inventory.main_inventory.title"))){
                inventoryClickEvent.setCancelled(true);

                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(alphabeticDisplay)){
                    WarpUtil.createAlphabeticSortInventory(player);
                }else if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(lastDisplay)){
                    WarpUtil.createLastSortInventory(player);
                }else if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(lastUsesDisplay)){
                    WarpUtil.createLastUseInventory(player);
                }else if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(administrationDisplay)){
                    WarpUtil.createAdministrationInventory(player);
                }

            }
        }else{
            try {
                player.closeInventory();
            }catch (NullPointerException exception){
                exception.printStackTrace();
                System.out.println(exception);
            }
        }

        if (inventoryClickEvent.getView().getTitle().equalsIgnoreCase("TITLE1") && inventoryClickEvent.getCurrentItem() != null && inventoryClickEvent.getCurrentItem().getItemMeta() != null){
            inventoryClickEvent.setCancelled(true);
            if (inventoryClickEvent.getClick().isLeftClick() || inventoryClickEvent.getClick().isRightClick()){
                if (inventoryClickEvent.getCurrentItem().getType() == Material.PAPER){
                    WarpUtil.createAdminWarpList(player);
                }else if (inventoryClickEvent.getCurrentItem().getType() == Material.COMMAND_BLOCK){
                    WarpUtil.createAdminPlayerWarpManagement(player);
                }
            }
        }

    }

}
