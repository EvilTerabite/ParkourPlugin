package me.evilterabite.parkourplugin.commands;

import me.evilterabite.parkourplugin.files.CheckpointData;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ParkourCommand implements CommandExecutor {
    ArrayList<Location> locations = new ArrayList<>();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length >= 1) {
                String cmd = args[0];
                if(cmd.equals("create")) {
                    if(!locations.isEmpty() && locations.size() >= 2) {
                        CheckpointData.createParkour(locations);
                        player.sendMessage(ChatColor.GREEN + "Parkour created successfully!");
                    }
                }
                if(cmd.equals("set")) {
                    locations.add(player.getLocation());
                    player.sendMessage(ChatColor.GREEN + "Saved location! Do /parkour create to finish!");
                }

            }
        }
        return true;
    }
}
