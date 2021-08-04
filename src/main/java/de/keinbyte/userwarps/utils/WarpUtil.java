package de.keinbyte.userwarps.utils;

import com.google.common.collect.Lists;
import de.keinbyte.userwarps.UserWarps;
import de.keinbyte.userwarps.manager.builder.ItemBuilder;
import de.keinbyte.userwarps.manager.sql.WarpSql;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.*;

/**
 * @author KeinByte
 * @since 19.07.2021
 */

public class WarpUtil {

    private static FileConfiguration config = UserWarps.getInstance().getConfig();
    private static boolean disabledWarpCommand = false;
    private static List<String> deniedWarps = Lists.newArrayList();
    @Getter
    public static List<String> deleteWarps = Lists.newArrayList();

    public static void createWarpInventory(Player player){

        Inventory  inventory = Bukkit.createInventory(null, 9, Objects.requireNonNull(config.getString("inventory.main_inventory.title")));
        inventory.setItem(config.getInt("inventory.main_inventory.sort.alphabetical.slot"), new ItemBuilder(Material.getMaterial(Objects.requireNonNull(config.getString("inventory.main_inventory.sort.alphabetical.material")))).setDisplayName(config.getString("inventory.main_inventory.sort.alphabetical.name")).build());
        inventory.setItem(config.getInt("inventory.main_inventory.sort.last.slot"), new ItemBuilder(Material.getMaterial(Objects.requireNonNull(config.getString("inventory.main_inventory.sort.last.material")))).setDisplayName(config.getString("inventory.main_inventory.sort.last.name")).build());
        inventory.setItem(config.getInt("inventory.main_inventory.sort.uses.slot"), new ItemBuilder(Material.getMaterial(Objects.requireNonNull(config.getString("inventory.main_inventory.sort.uses.material")))).setDisplayName(config.getString("inventory.main_inventory.sort.uses.name")).build());
        inventory.setItem(config.getInt("inventory.main_inventory.administration.slot"), new ItemBuilder(Material.getMaterial(Objects.requireNonNull(config.getString("inventory.main_inventory.administration.material")))).setDisplayName(config.getString("inventory.main_inventory.administration.name")).build());

        player.openInventory(inventory);

    }

    public static void createAlphabeticSortInventory(Player player){

        Inventory  inventory = Bukkit.createInventory(null, 9*4, "TITLE1");
        for (int i = inventory.getSize()-9; i < inventory.getSize(); i++) {
            inventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§4").build());
        }
        List<String> warpNames = Lists.newArrayList(WarpSql.getWarpNames());
        Collections.sort(warpNames);
        for(String warpName : warpNames) {
            inventory.addItem(new ItemBuilder(Material.BONE_MEAL).setDisplayName("§7» §6" + warpName).build());
        }

        player.openInventory(inventory);

    }

    public static void createLastSortInventory(Player player){

        Inventory  inventory = Bukkit.createInventory(null, 9*4, "TITLE");
        for (int i = inventory.getSize()-9; i < inventory.getSize(); i++) {
            inventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§4").build());
        }
        List<String> warpNames = Lists.newArrayList(WarpSql.getWarpNames());
        Collections.sort(warpNames);
        for(String warpName : warpNames) {
            inventory.addItem(new ItemBuilder(Material.BONE_MEAL).setDisplayName("§7» §6" + warpName).build());
        }

        player.openInventory(inventory);

    }

    public static void createLastUseInventory(Player player){

        Inventory  inventory = Bukkit.createInventory(null, 9*4, "TITLE");
        for (int i = inventory.getSize()-9; i < inventory.getSize(); i++) {
            inventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§4").build());
        }
        List<String> warpNames = Lists.newArrayList(WarpSql.getWarpNames());
        Collections.sort(warpNames);
        for(String warpName : warpNames) {
            inventory.addItem(new ItemBuilder(Material.BONE_MEAL).setDisplayName("§7» §6" + warpName).build());
        }

        player.openInventory(inventory);

    }
    public static void createAdministrationInventory(Player player){

        Inventory  inventory = Bukkit.createInventory(null, 9*4, Objects.requireNonNull(UserWarps.getInstance().getConfig().getString("inventory.administration.title")));
        for (int i = inventory.getSize()-9; i < inventory.getSize(); i++) {
            inventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§4").build());
        }
        List<String> warpNames = Lists.newArrayList(WarpSql.getWarpByNameForOwner(String.valueOf(player.getUniqueId())));
        for(String warpName : warpNames) {
            inventory.addItem(new ItemBuilder(Material.BONE_MEAL).setDisplayName("§7» §6" + warpName).build());
        }

        player.openInventory(inventory);

    }

    public static void setDisabledWarpCommand(boolean status) {
        disabledWarpCommand = status;
    }

    public static List<String> getNotAllowedMaps(){
        return deniedWarps;
    }

    public static void createConfirmationInventory(Player player){

        Inventory  inventory = Bukkit.createInventory(null, 9*4, Objects.requireNonNull(UserWarps.getInstance().getConfig().getString("inventory.administration.delete.title")));
        for (int i = inventory.getSize()-9; i < inventory.getSize(); i++) {
            inventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§4").build());
        }

        inventory.setItem(11, new ItemBuilder(Material.GREEN_DYE).setDisplayName("§aLösche den Warp").build());
        inventory.setItem(15, new ItemBuilder(Material.RED_DYE).setDisplayName("§cAbbrechen").build());

        player.openInventory(inventory);

    }

    public static void createAdminWarpList(Player player){

        Inventory  inventory = Bukkit.createInventory(null, 9*4, "TITLE1");
        for (int i = inventory.getSize()-9; i < inventory.getSize(); i++) {
            inventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§4").build());
        }
        List<String> warpNames = Lists.newArrayList(WarpSql.getWarpNames());
        Collections.sort(warpNames);
        for(String warpName : warpNames) {
            inventory.addItem(new ItemBuilder(Material.BONE_MEAL).setDisplayName("§7» §6" + warpName).build());
        }

        player.openInventory(inventory);

    }

    public static void createAdminWarpManagement(Player player){

        Inventory  inventory = Bukkit.createInventory(null, 9*4, "TITLE1");
        for (int i = inventory.getSize()-9; i < inventory.getSize(); i++) {
            inventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§4").build());
        }

        inventory.setItem(12, new ItemBuilder(Material.PAPER).setDisplayName("§aAlle Warps anzeigen").build());
        inventory.setItem(14, new ItemBuilder(Material.COMMAND_BLOCK).setDisplayName("§cAlle Warps administrieren").build());

        player.openInventory(inventory);

    }

    public static void createAdminPlayerWarpManagement(Player player){

        Inventory  inventory = Bukkit.createInventory(null, 9*4, "TITLE1");
        for (int i = inventory.getSize()-9; i < inventory.getSize(); i++) {
            inventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§4").build());
        }
        List<String> warpNames = Lists.newArrayList(WarpSql.getWarpByNameForOwner(String.valueOf(player.getUniqueId())));
        Collections.sort(warpNames);
        for(String warpName : warpNames) {
            inventory.addItem(new ItemBuilder(Material.BONE_MEAL).setDisplayName("§7» §6" + warpName).build());
        }

        player.openInventory(inventory);

    }

}
