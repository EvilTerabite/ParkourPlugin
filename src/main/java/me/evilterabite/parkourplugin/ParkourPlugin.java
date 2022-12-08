package me.evilterabite.parkourplugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.evilterabite.parkourplugin.commands.DebugCommand;
import me.evilterabite.parkourplugin.commands.ParkourCommand;
import me.evilterabite.parkourplugin.mysql.MySQL;
import me.evilterabite.parkourplugin.mysql.SQLGetter;
import me.evilterabite.parkourplugin.parkour.Parkour;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class ParkourPlugin extends JavaPlugin implements Listener {

    public HashMap<Long, UUID> userTimes;

    public MySQL sql;
    public SQLGetter data;
    public PluginManager pluginManager;
    public final Gson gson = new GsonBuilder().setPrettyPrinting().create();;
    public final Map<String, Object> checkpointMap = new HashMap<>();
    private File checkpointsFile;
    public Parkour parkour;
    @Override
    public void onEnable() {
        this.userTimes = new HashMap<>();
        this.pluginManager = getServer().getPluginManager();

        //Generate config files
        this.checkpointsFile = new File(getDataFolder(), "checkpoints.json");
        if(!checkpointsFile.exists()) {
            saveResource(checkpointsFile.getName(), false);
        }

        saveDefaultConfig();

    //Connect to MySQL database
        this.sql = new MySQL();
        this.data = new SQLGetter(this);

        try {
            sql.connect();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Database not connected.");
        }

        if(sql.isConnected()) {
            System.out.println("database connected!");
            data.createTable();
            pluginManager.registerEvents(this, this);
        }

        //Update top 5 times
        getServer().getScheduler().runTaskTimer(this, () -> {
            if(sql.isConnected()) {
                userTimes = data.getAllTimes();
            }
        },0, 20L * 5);

        //Register Commands
        getCommand("parkour").setExecutor(new ParkourCommand());
        getCommand("debug").setExecutor(new DebugCommand());
    }

    @Override
    public void onDisable() {
        final String json = gson.toJson(checkpointMap);
        checkpointsFile.delete();
        try {
            Files.write(checkpointsFile.toPath(), json.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sql.disconnect();
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
