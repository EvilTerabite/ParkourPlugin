package me.evilterabite.parkourplugin.commands;

import me.evilterabite.parkourplugin.ParkourPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DebugCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length >= 1) {
            String cmd = args[0];
            if(cmd.equalsIgnoreCase("mysqlconnected")) {
                sender.sendMessage("MySQL: " + ParkourPlugin.getInstance().sql.isConnected());
            }
        }
        return true;
    }
}
