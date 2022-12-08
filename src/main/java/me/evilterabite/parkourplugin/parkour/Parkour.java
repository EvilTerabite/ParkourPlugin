package me.evilterabite.parkourplugin.parkour;

import com.sk89q.worldguard.protection.flags.UUIDFlag;
import me.evilterabite.parkourplugin.ParkourPlugin;
import me.evilterabite.parkourplugin.mysql.SQLGetter;
import me.evilterabite.parkourplugin.util.TimeUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Parkour {

    public HashMap<UUID, Long> timedPlayers = new HashMap<>();
    public HashMap<UUID, Integer> playerCheckpointMap = new HashMap<>();
    private ArrayList<Location> points;
    private Location startPoint;
    private Location endPoint;
    private ArrayList<Location> checkPoints;

    public Parkour(ArrayList<Location> points) {
        this.points = points;
        this.startPoint = points.get(0);
        this.endPoint = points.get(points.size() - 1);
        this.checkPoints = new ArrayList<>(points.subList(1,points.size() - 1));
    }

    public ArrayList<Location> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<Location> points) {
        this.points = points;
    }

    public void startTimer(Player player) {
        timedPlayers.put(player.getUniqueId(), System.currentTimeMillis());
    }

    public void endTimer(Player player) {
        SQLGetter data = ParkourPlugin.getInstance().data;
        long bestTime = data.getTime(player);
        if(isTimed(player)) {
            long endTime = System.currentTimeMillis() - timedPlayers.get(player.getUniqueId());
            if(bestTime <= endTime) {
                player.sendMessage(ChatColor.GREEN + "You finished the parkour in " + TimeUtil.timeIntoHHMMSS(endTime));
                player.sendMessage(ChatColor.YELLOW + "You did not beat your best time of " + TimeUtil.timeIntoHHMMSS(bestTime));
            } else {
                player.sendMessage(ChatColor.GREEN + "You finished the parkour in " + TimeUtil.timeIntoHHMMSS(endTime));
                player.sendMessage(ChatColor.YELLOW + "NEW RECORD! You beat your best time of: " + TimeUtil.timeIntoHHMMSS(bestTime));
                data.logTime(player, endTime);
            }
            timedPlayers.remove(player.getUniqueId());
        }

    }

    public void cancelTimer(Player player) {
        timedPlayers.remove(player.getUniqueId());
    }

    public void hitCheckpoint(Player player, Location location) {
        int checkpointInt = 0;
        int currentCheckpoint = playerCheckpointMap.get(player.getUniqueId());
        UUID uuid = player.getUniqueId();
        for(Location checkpoint : checkPoints) {
            if(checkpoint.equals(startPoint)) {
                cancelTimer(player);
                startTimer(player);
                player.sendMessage(ChatColor.YELLOW + "Timer set to 00:00:00!");
            }
            if(currentCheckpoint >= checkpointInt) {
                player.sendMessage(ChatColor.RED + "You already hit that checkpoint!");
            }
            if(location.equals(checkPoints.get(checkpointInt))) {
                break;
            }
            checkpointInt++;
        }
        if(playerCheckpointMap.containsKey(uuid)) {
            playerCheckpointMap.replace(uuid, checkpointInt);
        } else {
            playerCheckpointMap.put(player.getUniqueId(), checkpointInt);
        }
    }

    public boolean isTimed(Player player) {
        return timedPlayers.containsKey(player.getUniqueId());
    }

    public Location getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Location startPoint) {
        this.startPoint = startPoint;
    }

    public Location getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Location endPoint) {
        this.endPoint = endPoint;
    }

    public ArrayList<Location> getCheckPoints() {
        return checkPoints;
    }

    public void setCheckPoints(ArrayList<Location> checkPoints) {
        this.checkPoints = checkPoints;
    }
}
