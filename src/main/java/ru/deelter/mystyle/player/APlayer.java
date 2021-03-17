package ru.deelter.mystyle.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.deelter.mystyle.Config;
import ru.deelter.mystyle.MyStyle;
import ru.deelter.mystyle.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class APlayer {

	private final UUID uuid;

	private String style = Config.STYLE;
	private String globalPrefix = Config.GLOBAL_PREFIX;
	private String localPrefix = Config.LOCAL_PREFIX;

	private boolean mute = Config.MUTE;
	private boolean notify = Config.NOTIFY;

	private static final Map<UUID, APlayer> players = new HashMap<>();

	public APlayer(UUID uuid) {
		this.uuid = uuid;
		load();
	}

	public static APlayer getPlayer(Player player) {
		return getPlayer(player.getUniqueId());
	}

	public static APlayer getPlayer(UUID uuid) {
		if (!players.containsKey(uuid)) {
			APlayer aplayer = new APlayer(uuid);
			aplayer.register();

			return aplayer;
		}
		return players.get(uuid);
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getGlobalPrefix() {
		return globalPrefix;
	}

	public void setGlobalPrefix(String globalPrefix) {
		this.globalPrefix = globalPrefix;
	}

	public String getLocalPrefix() {
		return localPrefix;
	}

	public void setLocalPrefix(String localPrefix) {
		this.localPrefix = localPrefix;
	}

	public boolean isMute() {
		return mute;
	}

	public void setMute(boolean mute) {
		this.mute = mute;
	}

	public void setNotify(boolean notify) {
		this.notify = notify;
	}

	public boolean isNotifyVisible() {
		return notify;
	}

	public static Map<UUID, APlayer> getPlayers() {
		return players;
	}

	public void register() {
		players.putIfAbsent(uuid, this);
	}

	public void unregister() {
		players.remove(uuid);
	}

	/* Load player */
	public void load() {

		/* Database connect */
		try (Connection con = Database.openConnection()) {
			String sql = "SELECT * FROM Players WHERE UUID = '" + uuid + "';";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				style = rs.getString("STYLE");
				globalPrefix = rs.getString("GLOBAL_PREFIX");
				localPrefix = rs.getString("LOCAL_PREFIX");
				mute = rs.getBoolean("MUTE");
				notify = rs.getBoolean("NOTIFY");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/* Save player */
	public void save() {
		try (Connection con = Database.openConnection()) {
			String sql = "REPLACE INTO Players (UUID,STYLE,GLOBAL_PREFIX,LOCAL_PREFIX,MUTE,NOTIFY) VALUES(?,?,?,?,?,?);";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, uuid.toString());
			ps.setString(2, style);
			ps.setString(3, globalPrefix);
			ps.setString(4, localPrefix);
			ps.setBoolean(5, mute);
			ps.setBoolean(6, notify);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void runSaveTimer() {
		Bukkit.getScheduler().runTaskTimerAsynchronously(MyStyle.getInstance(), () -> new ArrayList<>(players.values()).forEach(APlayer::save), 0L, 600 * 20L);
	}
}
