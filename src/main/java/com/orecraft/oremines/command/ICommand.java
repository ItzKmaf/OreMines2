package com.orecraft.oremines.command;

import org.bukkit.command.CommandSender;

public interface ICommand {

    String getName();

    String getDescription();

    String getUsage();

    String getPermission();

    boolean executableByConsole();

    void execute(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args);

    //TODO
    //  Add a tab complete thing.
}