package me.cubecrafter.migrator.utility;

import com.cryptomorin.xseries.SkullUtils;
import com.cryptomorin.xseries.XMaterial;
import me.cubecrafter.migrator.HypixelMigrator;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class ItemBuilder {

    private ItemStack item;

    public ItemBuilder(String material) {
        item = XMaterial.matchXMaterial(material).get().parseItem();
    }

    public ItemBuilder setDisplayName(String name) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatUtils.colorTranslate(name));
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        ItemMeta meta = item.getItemMeta();
        meta.setLore(ChatUtils.colorTranslate(lore));
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setTexture(String identifier) {
        ItemMeta meta = item.getItemMeta();
        if (!(meta instanceof SkullMeta)) return this;
        item.setItemMeta(SkullUtils.applySkin(meta, identifier));
        return this;
    }

    public ItemBuilder setTag(String key, String value) {
        item = HypixelMigrator.getInstance().getBedWars().getVersionSupport().setTag(item, key, value);
        return this;
    }

    public ItemStack build() {
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemBuilder fromConfig(ConfigurationSection section) {
        ItemBuilder builder = new ItemBuilder(section.getString("material"));
        if (section.contains("displayname")) builder.setDisplayName(section.getString("displayname"));
        if (section.contains("lore")) builder.setLore(section.getStringList("lore"));
        if (section.contains("texture")) builder.setTexture(section.getString("texture"));
        return builder;
    }

    public static String getTag(ItemStack item, String key) {
        String tag = HypixelMigrator.getInstance().getBedWars().getVersionSupport().getTag(item, key);
        return tag == null ? "" : tag;
    }

}
