package me.evilterabite.parkourplugin.mysql;

import me.evilterabite.parkourplugin.ParkourPlugin;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SQLGetter {
    private final ParkourPlugin plugin;

    public SQLGetter(ParkourPlugin plugin) {
        this.plugin = plugin;
    }

    public void createTable() {
        try {
            PreparedStatement ps = plugin.sql.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS parkour "
                 + "(UUID VARCHAR(100), TIMES BIGINT(20), PRIMARY KEY (UUID))");

            ps.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public void logTime(Player player, long millis) {
        UUID uuid = player.getUniqueId();
        try {
            String sql;
            if(!exists(uuid)) {
                sql = "UPDATE `parkour` SET `UUID`=" + uuid + ",`TIMES`=" + millis;
            }
            else {
                sql = "INSERT INTO `parkour`(`UUID`, `TIMES`) VALUES ("
                        + uuid + ","
                        + millis + ")";
            }
            PreparedStatement psTwo = plugin.sql.getConnection().prepareStatement(sql);
            psTwo.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public boolean exists(UUID uuid) {
        try {
            PreparedStatement ps = plugin.sql.getConnection().prepareStatement("SELECT * FROM parkour WHERE UUID=?");
            ps.setString(1, uuid.toString());

            ResultSet results = ps.executeQuery();
            return results.next();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public long getTime(Player player) {
        String sql = "SELECT * FROM parkour WHERE UUID=?";
        long time = 0;
        try {
            PreparedStatement ps = plugin.sql.getConnection().prepareStatement(sql);
            ps.setString(1, player.getUniqueId().toString());
            ResultSet set = ps.executeQuery();
            if(set.next()) {
                time = set.getLong(2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return time;
    }

    public HashMap<Long, UUID> getAllTimes() {
        String sql = "SELECT * FROM parkour";
        HashMap<Long, UUID> allTimes = new HashMap<>();
        try {
            PreparedStatement ps = plugin.sql.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                allTimes.put(rs.getLong(2), UUID.fromString(rs.getString(1)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allTimes;
    }
}
