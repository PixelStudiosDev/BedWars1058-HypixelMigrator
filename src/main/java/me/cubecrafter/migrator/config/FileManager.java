package me.cubecrafter.migrator.config;

import lombok.Getter;
import me.cubecrafter.migrator.HypixelMigrator;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class FileManager {

    @Getter
    private final YamlConfiguration config;

    public FileManager(HypixelMigrator plugin) {
        File file = new File("plugins/BedWars1058/Addons/HypixelMigrator/config.yml");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            saveResource(plugin.getResource("config.yml"), file);
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    private void saveResource(InputStream in, File destination) {
        try {
            Files.copy(in, destination.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
