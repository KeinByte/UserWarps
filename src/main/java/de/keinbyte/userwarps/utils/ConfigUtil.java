package de.keinbyte.userwarps.utils;

import de.keinbyte.userwarps.UserWarps;
import org.bukkit.configuration.Configuration;

/**
 * @author KeinByte
 * @since 31.07.2021
 */

public class ConfigUtil {

    public static void setConfig(){

        Configuration configuration = UserWarps.getInstance().getConfig();

        //mysql
        configuration.addDefault("mysql.database", "UserWarps");
        configuration.addDefault("mysql.username", "root");
        configuration.addDefault("mysql.host", "127.0.0.1");
        configuration.addDefault("mysql.port", 3306);
        configuration.addDefault("mysql.password", "minesucht123");

        //main-inventory
        configuration.addDefault("inventory.main.title", "§8» §6Warp-Menü");

        configuration.addDefault("inventory.main.sort.alphabetical.name", "§8» §aAlphabetisch sortieren");
        configuration.addDefault("inventory.main.sort.alphabetical.material", "BOOK");
        configuration.addDefault("inventory.main.sort.alphabetical.slot", 1);

        configuration.addDefault("inventory.main.sort.last.name", "§8» §e27 letzte Benutzungen");
        configuration.addDefault("inventory.main.sort.last.material", "HOPPER");
        configuration.addDefault("inventory.main.sort.last.slot", 3);

        configuration.addDefault("inventory.main.sort.uses.name", "§8» §6Meister nutzen §7-> §eWenigsten nutzen");
        configuration.addDefault("inventory.main.sort.uses.material", "GOLD_INGOT");
        configuration.addDefault("inventory.main.sort.uses.slot", 5);

        configuration.addDefault("inventory.main.sort.administration.name", "§8» §4Einstellungen");
        configuration.addDefault("inventory.main.sort.administration.material", "NAME_TAG");
        configuration.addDefault("inventory.main.sort.administration.slot", 7);

        //administration-inventory
        configuration.addDefault("inventory.main.administration.title", "§8» §cAdministrations-Menü");
        configuration.addDefault("inventory.main.sort.administration.slot", 7);

        //messages
        configuration.addDefault("prefix", "§9§lMineSucht §8» §r");

    }

}
