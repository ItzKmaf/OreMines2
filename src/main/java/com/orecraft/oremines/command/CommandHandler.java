package com.orecraft.oremines.command;

import com.orecraft.oremines.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;


public class CommandHandler implements CommandExecutor {
    private final ArrayList<ICommand> commands = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        System.out.println("Hello!");
        // The user has not entered an argument.
        if (args.length == 0) {
            showOptions(commandSender);
            return false;
        }
        for (ICommand commandElement: commands) {
            if (commandElement.getName().equalsIgnoreCase(args[0])) {
                // If the sender is a player, check if they have permission. If the sender is console check that the
                // command can be executed by console
                if ((commandSender instanceof Player && commandSender.hasPermission(commandElement.getPermission())) || commandElement.executableByConsole()) {
                    commandElement.execute(commandSender, command, label, Arrays.copyOfRange(args, 1, args.length));
                } else {
                    Util.sendMessage(commandSender, "{plugin-name}&7: &cError, You are not allowed to run that command..." +
                            "\n&7Try one of these below!");
                    showOptions(commandSender);
                }
            }
        }
        return false;
    }

    private void showOptions(CommandSender commandSender) {
        ArrayList<ICommand> availableCommands = new ArrayList<>(commands);
        availableCommands.removeIf(x -> commandSender.hasPermission(x.getPermission()));
        if (!(commandSender instanceof Player)) {
            availableCommands.removeIf(ICommand::executableByConsole);
        }
        Util.sendMessage(commandSender, "&7------&7====={plugin-name}&7=====&7------");
        Util.sendMessage(commandSender, "    &7Command &f: &aDescription");
        for (ICommand command: availableCommands) {
            Util.sendMessage(commandSender, "    &7" + command.getName() + " &f: &a" + command.getDescription());
        }
        Util.sendMessage(commandSender, "&7------&7====={plugin-name}&7=====&7------");
    }

    public void registerCommand(ICommand command) {
        commands.add(command);
    }
    public void deregisterCommand(ICommand command) {
        commands.remove(command);
    }
}