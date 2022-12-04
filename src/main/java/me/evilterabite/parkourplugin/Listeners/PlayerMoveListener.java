package me.evilterabite.parkourplugin.Listeners;

import fr.mrmicky.fastboard.FastBoard;
import me.evilterabite.parkourplugin.util.ParkourDataUtil;
import me.evilterabite.parkourplugin.util.TimeUtil;
import me.evilterabite.parkourplugin.util.WGUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    @EventHandler
    void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (WGUtil.isPlayerInParkourRegion(player)) {
            FastBoard board = new FastBoard(player);
            board.updateTitle(ChatColor.YELLOW + "-=Parkour=-");
            board.updateLines(
                    "",
                    "Best Attempt: " + TimeUtil.timeIntoHHMMSS(ParkourDataUtil.getPlayerBestTime(player)),
                    "",
                    "Leaderboard",
                    "   #1 - $NAME - $TIME",
                    "   #2 - $NAME - $TIME",
                    "   #3 - $NAME - $TIME",
                    "   #4 - $NAME - $TIME",
                    "   #5 - $NAME - $TIME"
            );
        }
    }
}
