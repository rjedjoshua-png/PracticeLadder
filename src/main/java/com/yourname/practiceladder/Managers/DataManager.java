package com.yourname.practiceladder.Managers;

import com.yourname.practiceladder.Model.PlayerStats;
import com.yourname.practiceladder.PracticeLadder;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class DataManager {

    private final PracticeLadder plugin;
    private final File dataFolder;

    public DataManager(PracticeLadder plugin) {
        this.plugin = plugin;
        this.dataFolder = new File(plugin.getDataFolder(), "data");
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
    }

    public PlayerStats getPlayerStats(UUID uuid) {
        File file = new File(dataFolder, uuid + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        if (!file.exists()) {
            config.set("wins", 0);
            config.set("losses", 0);
            config.set("rank.vanilla.rank", "LT");
            config.set("rank.vanilla.level", 1);
            config.set("rank.vanilla.progression", 0);
            config.set("rank.sumo.rank", "LT");
            config.set("rank.sumo.level", 1);
            config.set("rank.sumo.progression", 0);
            config.set("rank.archer.rank", "LT");
            config.set("rank.archer.level", 1);
            config.set("rank.archer.progression", 0);
            config.set("rank.no_debuff.rank", "LT");
            config.set("rank.no_debuff.level", 1);
            config.set("rank.no_debuff.progression", 0);
            saveConfig(config, file);
        }

        return new PlayerStats(config);
    }

    public void savePlayerStats(UUID uuid, PlayerStats stats) {
        File file = new File(dataFolder, uuid + ".yml");
        saveConfig(stats.getConfig(), file);
    }

    private void saveConfig(FileConfiguration config, File file) {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}