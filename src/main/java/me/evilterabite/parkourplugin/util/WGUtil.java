package me.evilterabite.parkourplugin.util;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class WGUtil {
    public static boolean isPlayerInParkourRegion(Player player) {
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        Location wgLocation = BukkitAdapter.adapt(player.getLocation());
        ApplicableRegionSet regionSet = container.createQuery().getApplicableRegions(wgLocation);
        for(ProtectedRegion pRegion : regionSet) {
            return pRegion.getId().equals("parkour");
        }
        return false;
    }
}
