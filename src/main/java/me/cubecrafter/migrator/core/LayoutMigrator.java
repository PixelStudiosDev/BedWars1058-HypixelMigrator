package me.cubecrafter.migrator.core;

import com.andrei1058.bedwars.shop.quickbuy.PlayerQuickBuyCache;
import com.andrei1058.bedwars.shop.quickbuy.QuickBuyElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.cubecrafter.migrator.HypixelMigrator;
import me.cubecrafter.migrator.utils.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;
import java.util.UUID;
import java.util.function.Consumer;

public class LayoutMigrator {

    private final HypixelMigrator plugin;
    private final YamlConfiguration config;

    public LayoutMigrator(HypixelMigrator plugin) {
        this.plugin = plugin;
        config = plugin.getFileManager().getConfig();
    }

    public void migrate(Player player) {
        if (config.getBoolean("online-mode")) {
            applyLayout(player, player.getUniqueId());
        } else {
            fetchOnlineUUID(player, uuid -> {
                if (uuid == null) {
                    TextUtil.sendMessage(player, config.getString("messages.not-premium-player"));
                } else {
                    applyLayout(player, uuid);
                }
            });
        }
    }

    private void applyLayout(Player player, UUID uuid) {
        TextUtil.sendMessage(player, config.getString("messages.migration-started"));
        fetchProfile(uuid, response -> {
            if (response == null) {
                TextUtil.sendMessage(player, config.getString("messages.not-premium-player"));
                return;
            }
            if (response.get("player").isJsonNull()) {
                TextUtil.sendMessage(player, config.getString("messages.migration-failed"));
                return;
            }
            String layout = response.getAsJsonObject("player").getAsJsonObject("stats").getAsJsonObject("Bedwars").get("favourites_2").getAsString();
            String[] items = layout.split(",");
            PlayerQuickBuyCache cache = PlayerQuickBuyCache.getQuickBuyCache(player.getUniqueId());
            Iterator<String> it = Arrays.stream(items).iterator();
            for (int i = 19; i < 44; i++) {
                if (i == 26 || i == 27 || i == 35 || i == 36) continue;
                String item = it.next();
                if (item.equals("null")) {
                    cache.setElement(i, null);
                } else {
                    String category = LayoutItem.matchItem(item).getCategory();
                    cache.setElement(i, new QuickBuyElement(category, i).getCategoryContent());
                }
            }
            cache.pushChangesToDB();
            TextUtil.sendMessage(player, config.getString("messages.migration-success"));
        });
    }

    private void fetchProfile(UUID uuid, Consumer<JsonObject> callback) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                URL url = new URL(String.format("https://api.hypixel.net/player?key=%s&uuid=%s", config.getString("hypixel-api-key"), uuid.toString()));
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json");
                if (connection.getResponseCode() == 200) {
                    InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                    JsonObject json = new JsonParser().parse(reader).getAsJsonObject();
                    callback.accept(json);
                } else {
                    callback.accept(null);
                }
                connection.disconnect();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void fetchOnlineUUID(Player player, Consumer<UUID> callback) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + player.getName());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json");
                if (connection.getResponseCode() == 200) {
                    InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                    JsonObject json = new JsonParser().parse(reader).getAsJsonObject();
                    String uuid = json.get("id").getAsString().replaceAll("(.{8})(.{4})(.{4})(.{4})(.+)", "$1-$2-$3-$4-$5");
                    callback.accept(UUID.fromString(uuid));
                } else {
                    callback.accept(null);
                }
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
