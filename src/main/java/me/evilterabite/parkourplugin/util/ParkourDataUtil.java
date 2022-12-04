package me.evilterabite.parkourplugin.util;

import me.evilterabite.parkourplugin.ParkourPlugin;
import org.bukkit.entity.Player;

import java.util.*;

public class ParkourDataUtil {
    public static long getPlayerBestTime(Player player) {
        ParkourPlugin pl = ParkourPlugin.getInstance();
        return pl.data.getTime(player);
    }

    public static List<Long> getTopFiveTimes() {
        ParkourPlugin pl = ParkourPlugin.getInstance();
        HashMap<Long, UUID> allTimes = pl.data.getAllTimes();
        List<Long> longList = new ArrayList<>(Arrays.asList(allTimes.keySet().toArray(new Long[0])));
        Collections.sort(longList);

        return longList.subList(0,4);
    }
}
