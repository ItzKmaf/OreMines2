package com.orecraft.oremines;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

@UtilityClass
public class Util {

    public void logToConsole(ConsoleCommandSender console, String message) {
        message = message.replace("{plugin-name}", "&8[&6Ore-Mines&8]&7");
        console.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public void sendMessage(CommandSender sender, String message) {
        message = message.replace("{plugin-name}", "&8[&6Ore-Mines&8]&7");
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
}
