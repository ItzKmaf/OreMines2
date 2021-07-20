package com.orecraft.oremines;

import com.orecraft.oremines.command.CommandHandler;
import com.orecraft.oremines.command.commands.ListMine_CMD;
import com.orecraft.oremines.mines.MineManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;


public class OreMines extends JavaPlugin {

    private static OreMines instance;
    @Getter private Config mineConfig;

    @Override
    public void onEnable() {
        long startTime = System.currentTimeMillis();
        Util.logToConsole(Bukkit.getConsoleSender(), "{plugin-name}&7: &aEnabling plugin...");
        if (instance != null && instance.isEnabled()) {
            Util.logToConsole(Bukkit.getConsoleSender(), "{plugin-name}&7: &cError: Plugin is already running!");
            Util.logToConsole(Bukkit.getConsoleSender(), "&cDisabling plugin...");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        instance = this;
        Util.logToConsole(Bukkit.getConsoleSender(), "{plugin-name}&7:&f Loading configs...");
        loadConfigs();
        Util.logToConsole(Bukkit.getConsoleSender(), "{plugin-name}&7:&f Loading Mines...");
        new MineManager();
        Util.logToConsole(Bukkit.getConsoleSender(), "{plugin-name}&7:&f Registering Commands...");
        registerCommands();
        long loadTime = System.currentTimeMillis() - startTime;
        long sec = loadTime / 1000L;
        long millis = loadTime % 1000L;
        Util.logToConsole(Bukkit.getConsoleSender(), "{plugin-name}&7: &aStartup completed in &f" + sec + "." + millis + "&a seconds.");
    }

    private void loadConfigs() {
        try {
            mineConfig = new Config(instance,"mines.yml");
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
            //TODO
            //  Handle error in a better way.
        } catch (IOException e) {
            e.printStackTrace();
            //TODO
            //  Handle error in a better way.
        }
    }

    private void registerCommands() {
        CommandHandler commandHandler = new CommandHandler();
        getCommand("om").setExecutor(commandHandler);
        commandHandler.registerCommand(new ListMine_CMD());
    }

    @Override
    public void onDisable() {

    }

    public static OreMines getInstance() {
        return instance;
    }
}
