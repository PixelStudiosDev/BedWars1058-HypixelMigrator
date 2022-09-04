package me.cubecrafter.migrator.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

@UtilityClass
public class TextUtil {

    public String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public List<String> color(List<String> list) {
        list.replaceAll(TextUtil::color);
        return list;
    }

    public void sendMessage(Player player, String message) {
        player.sendMessage(color(message));
    }

}
