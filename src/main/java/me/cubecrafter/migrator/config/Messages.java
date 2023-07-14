package me.cubecrafter.migrator.config;

import com.andrei1058.bedwars.api.language.Language;

public class Messages {

    public static void setupMessages(){
        Language.saveIfNotExists("hypixel-migrator.not-premium-player", "&cYou are not a premium player!");
        Language.saveIfNotExists("hypixel-migrator.migration-started", "&aMigrating your Hypixel Quick Buy layout...");
        Language.saveIfNotExists("hypixel-migrator.migration-failed", "&cSeems like you don't have an Hypixel Profile!");
        Language.saveIfNotExists("hypixel-migrator.migration-success", "&aYour Hypixel Quick Buy layout has been migrated!");
    }
}
