package me.evilterabite.parkourplugin.files;

import me.evilterabite.parkourplugin.ParkourPlugin;
import me.evilterabite.parkourplugin.parkour.Parkour;
import org.bukkit.Location;

import java.util.ArrayList;

public class CheckpointData {

    public static void createParkour(ArrayList<Location> locations) {
        ParkourPlugin pl = ParkourPlugin.getInstance();
        pl.checkpointMap.put("checkpointsData", locations);
        pl.parkour = new Parkour(locations);
    }


}
