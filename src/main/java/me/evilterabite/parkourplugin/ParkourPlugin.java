package me.evilterabite.parkourplugin;

import com.sk89q.worldguard.WorldGuard;
import fr.mrmicky.fastboard.FastBoard;
import me.evilterabite.parkourplugin.mysql.MySQL;
import me.evilterabite.parkourplugin.mysql.SQLGetter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public final class ParkourPlugin extends JavaPlugin implements Listener {

    public HashMap<Long, UUID> userTimes;

    public MySQL sql;
    public SQLGetter data;
    public PluginManager pluginManager;
    @Override
    public void onEnable() {
        this.userTimes = new HashMap<>();
        this.pluginManager = getServer().getPluginManager();

        //Connect to MySQL database
        this.sql = new MySQL();
        this.data = new SQLGetter(this);

        try {
            sql.connect();
        } catch (ClassNotFoundException | SQLException e) {
            Bukkit.getLogger().info("Database not connected.");
        }

        if(sql.isConnected()) {
            Bukkit.getLogger().info("Database connected successfully!");
            data.createTable();
            pluginManager.registerEvents(this, this);
        }

        //Update top 5 times
        getServer().getScheduler().runTaskTimer(this, () -> {
            userTimes = data.getAllTimes();
        },0, 20L * 5);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    //Join Listener
    @EventHandler
    void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(player.isOp()) {
            if(!sql.isConnected()) {
                player.sendMessage(ChatColor.RED + "Hey! The parkour plugin database is NOT connected! Times will not be saved.");
            }
        }
    }
    public static ParkourPlugin getInstance() {
        return ParkourPlugin.getPlugin(ParkourPlugin.class);
    }

}
