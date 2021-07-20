package com.orecraft.oremines.command.commands;

import com.orecraft.oremines.Util;
import com.orecraft.oremines.command.ICommand;
import com.orecraft.oremines.mines.Mine;
import com.orecraft.oremines.mines.MineManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ListMine_CMD implements ICommand {
    @Override
    public String getName() {
        return "ml";
    }

    @Override
    public String getDescription() {
        return "returns a list of loaded mines";
    }

    @Override
    public String getUsage() {
        return "&f/om lm &e{&7optional index&e} &7where optional index is an Integer";
    }

    @Override
    public String getPermission() {
        return "oremine.command.mine.list";
    }

    @Override
    public boolean executableByConsole() {
        return true;
    }

    @Override
    public void execute(CommandSender commandSender, Command command, String label, String[] args) {
        System.out.println("Hello2!");
        int index = 1;
        // Check if user has supplied args
        if (args.length > 0) {
            // Check if the arg they supplied is an Integer
            if (args[0].matches("-?(0|[1-9]\\d*)")) {
                index = Integer.parseInt(args[1]);
            } else {
                // if it wasn't send them the usage message.
                Util.sendMessage(commandSender, "{plugin-name}&f: &cError, Incorrect usage.");
                Util.sendMessage(commandSender, getUsage());
                return;
            }
        }
        List<Mine> mines = MineManager.getInstance().getMines();
        //TODO
        //  Make this pagenation a little cleaner - It looks ugly but i'm tired

        // How many pages are there?
        int pageCount = mines.size() % 6;
        System.out.println(pageCount);
        // Ensure the idiotic user hasn't entered a number greater than the number of pages
        if (index > pageCount) {
            index = pageCount;
        }
        // Ensure the idiotic user hasn't entered 0 or a negative number
        if (index < 1) {
            index = 1;
        }
        // Shift the index down by one
        index --;
        int count = 0;
        // Loop through the mines
        Util.sendMessage(commandSender, "&7<----====&8[&6Loaded mines&8]&7====---->");
        for (Mine mine: mines) {
            count ++;
            if (count % 6 == index) {
                Util.sendMessage(commandSender, "&f  - &6" + mine.getMineName());
            }
        }
        Util.sendMessage(commandSender, "&7<----====&8[&6{plugin-name}&8]&7====---->");
    }
}
