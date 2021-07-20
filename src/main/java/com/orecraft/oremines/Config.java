package com.orecraft.oremines;

import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Config {
    private final File configFile;
    @Getter private final FileConfiguration confFile;
    private final String filename;

    public Config(JavaPlugin plugin, String filename) throws InvalidConfigurationException, IOException{
        this.filename = filename;
        this.configFile = new File(plugin.getDataFolder(), filename);
        this.confFile = new YamlConfiguration();

        // If the config files parent directory didn't exist,
        if (configFile.getParentFile().mkdirs() || !configFile.exists()) {
            plugin.saveResource(filename, false);
        }
        this.confFile.load(this.configFile);
    }

    public void saveConfig() {
        try {
            this.confFile.save(this.configFile);
        } catch (Exception var2) {
            var2.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return filename;
    }
}