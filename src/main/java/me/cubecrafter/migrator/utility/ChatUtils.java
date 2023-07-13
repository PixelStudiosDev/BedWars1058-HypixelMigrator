package me.cubecrafter.migrator.utility;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class ChatUtils {

    public static final Pattern patternBrackets = Pattern.compile("\\{#[0-9a-fA-F]{6}\\}");
    public static final Pattern pattern = Pattern.compile("#[0-9a-fA-F]{6}");

    public String colorTranslate(String text) {
        if (text == null) {
            return null;
        }
        text = removeBrackets(text);
        Matcher match = pattern.matcher(text);
        while (match.find()) {
            String color = text.substring(match.start(), match.end());
            text = text.replace(color, net.md_5.bungee.api.ChatColor.of(color) + "");
            match = pattern.matcher(text);
        }
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    private static String removeBrackets(String text) {
        Matcher m = patternBrackets.matcher(text);
        String replaced = text;
        while (m.find()) {
            String hexcode = m.group();
            String fixed = hexcode.substring(2, 8);
            replaced = replaced.replace(hexcode, "#" + fixed);
        }
        return replaced;
    }

    public List<String> colorTranslate(List<String> list) {
        list.replaceAll(ChatUtils::colorTranslate);
        return list;
    }

    public void sendMessage(Player player, String message) {
        player.sendMessage(colorTranslate(message));
    }
}
