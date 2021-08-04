package de.keinbyte.userwarps.manager.builder;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/**
 * @author KeinByte
 * @since 17.07.2021
 */

public class ItemBuilder {

    private ItemStack item;

    private ItemMeta meta;

    public ItemBuilder(Material material) {
        this.item = new ItemStack(material);
        this.meta = this.item.getItemMeta();
    }

    public ItemBuilder setDisplayName(String name) {
        this.meta.setDisplayName(name);
        return this;
    }

    public ItemBuilder setAmount(int Amount) {
        this.item.setAmount(Amount);
        return this;
    }

    public ItemBuilder setLore(String... Lore) {
        this.meta.setLore(Arrays.asList(Lore));
        return this;
    }

    public ItemStack build() {
        this.item.setItemMeta(this.meta);
        return this.item;
    }

}
