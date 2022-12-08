package me.evilterabite.parkourplugin.listeners;

import fr.mrmicky.fastboard.FastBoard;
import me.evilterabite.parkourplugin.ParkourPlugin;
import me.evilterabite.parkourplugin.parkour.Parkour;
import me.evilterabite.parkourplugin.util.ParkourDataUtil;
import me.evilterabite.parkourplugin.util.TimeUtil;
import me.evilterabite.parkourplugin.util.WGUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.List;

public class PlayerMoveListener implements Listener {

    @EventHandler
    void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        ParkourPlugin pl = ParkourPlugin.getInstance();
        Parkour parkour = pl.parkour;
        if (WGUtil.isPlayerInParkourRegion(player)) {
            FastBoard board = new FastBoard(player);
            List<Long> bestTimes = ParkourDataUtil.getTopFiveTimes();
            board.updateTitle(ChatColor.YELLOW + "-=Parkour=-");
            board.updateLines(
                    "",
                    "Best Attempt: " + TimeUtil.timeIntoHHMMSS(ParkourDataUtil.getPlayerBestTime(player)),
                    "",
                    "Leaderboard",
                    "   #1 - " + pl.userTimes.get(bestTimes.get(0)) + " - " + bestTimes.get(0),
                    "   #2 - " + pl.userTimes.get(bestTimes.get(1)) + " - " + bestTimes.get(1),
                    "   #3 - " + pl.userTimes.get(bestTimes.get(2)) + " - " + bestTimes.get(2),
                    "   #4 - " + pl.userTimes.get(bestTimes.get(3)) + " - " + bestTimes.get(3),
                    "   #5 - " + pl.userTimes.get(bestTimes.get(4)) + " - " + bestTimes.get(4)
            );
        }

        //Start timer
        if(!parkour.isTimed(player)) {
            if(player.getLocation().equals(parkour.getStartPoint())) {
                parkour.startTimer(player);
                parkour.hitCheckpoint(player, player.getLocation());
            }
            if(parkour.getCheckPoints().contains(player.getLocation())) {
                parkour.hitCheckpoint(player, player.getLocation());
            }
        }

    }
}
