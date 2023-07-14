package me.cubecrafter.migrator;

import com.andrei1058.bedwars.api.BedWars;
import lombok.Getter;
import me.cubecrafter.migrator.config.FileManager;
import me.cubecrafter.migrator.config.Messages;
import me.cubecrafter.migrator.core.LayoutMigrator;
import me.cubecrafter.migrator.listeners.InventoryListener;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class HypixelMigrator extends JavaPlugin {

    @Getter
    private static HypixelMigrator instance;
    private FileManager fileManager;
    private LayoutMigrator migrator;
    private BedWars bedWars;

    @Override
    public void onEnable() {
        instance = this;
        fileManager = new FileManager(this);
        Messages.setupMessages();
        if (fileManager.getConfig().getString("hypixel-api-key").isEmpty()) {
            getLogger().severe("The Hypixel API key is not set! Please set it in config.yml!");
            getLogger().severe("To get your API key, join hypixel.net and use the command /api new. Disabling...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        bedWars = getServer().getServicesManager().getRegistration(BedWars.class).getProvider();
        migrator = new LayoutMigrator(this);
        getServer().getPluginManager().registerEvents(new InventoryListener(this), this);
        new Metrics(this, 16340);
    }

}
