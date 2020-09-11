package ru.deelter.mystyle.player;

import org.bukkit.entity.Player;
import ru.deelter.mystyle.Config;
import ru.deelter.mystyle.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class APlayer {

	private final UUID uuid;

	private String style = Config.STYLE;
	private String globalPrefix = Config.GLOBAL_PREFIX;
	private String localPrefix = Config.LOCAL_PREFIX;

	private boolean mute = Config.MUTE;
	private boolean notify = Config.NOTIFY;

	private static List<String> ignore = new ArrayList<>();
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

	public void setIgnore(UUID uuid, boolean add) {
		if (add) ignore.add(uuid.toString());
		else ignore.remove(uuid.toString());
	}

	public List<String> getIgnoreList() {
		return ignore;
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
		/* Default values */
		style = Config.STYLE;
		globalPrefix = Config.GLOBAL_PREFIX;
		localPrefix = Config.LOCAL_PREFIX;
		mute = Config.MUTE;
		notify = Config.NOTIFY;

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

				String[] ignoredPlayers = rs.getString("IGNORE").split(",");
				for (String ignoredPlayer : ignoredPlayers) {
					ignore.add(ignoredPlayer);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/*
	public void load() {
		File folder = new File(Main.getInstance().getDataFolder() + File.separator + "players");
		if (!folder.exists())
			folder.mkdir();

		File file = new File(folder, uuid + ".yml");
		if (file.exists()) {

			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			style = config.getString("style");
			globalPrefix = config.getString("globalPrefix");
			localPrefix = config.getString("localPrefix");
			mute = config.getBoolean("mute");
			notify = config.getBoolean("notify");

		} else {
			try {
				file.createNewFile();

				FileWriter writer = new FileWriter(file.getPath(), true);
				BufferedWriter bufferWriter = new BufferedWriter(writer);
				String text = "style: '" + style + "'\n" +
						"globalPrefix: '" + globalPrefix + "'\n" +
						"localPrefix: '" + localPrefix + "'\n" +
						"mute: " + mute + "\n" +
						"notify: " + notify;
				bufferWriter.write(text);
				bufferWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	*/

	/* Save player */
	public void save() {
		try (Connection con = Database.openConnection()) {
			String sql = "REPLACE INTO Players (UUID,IGNORE,STYLE,GLOBAL_PREFIX,LOCAL_PREFIX,MUTE,NOTIFY) VALUES(?,?,?,?,?,?,?);";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, uuid.toString());
			ps.setString(3, style);
			ps.setString(4, globalPrefix);
			ps.setString(5, localPrefix);
			ps.setBoolean(6, mute);
			ps.setBoolean(7, notify);

			StringBuilder ignoredPlayers = new StringBuilder();
			ignore.forEach(uuid -> ignoredPlayers.append(uuid).append(","));
			ps.setString(2, ignoredPlayers.substring(ignoredPlayers.length() - 1));

			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/*
	public void save() {
		File folder = new File(Main.getInstance().getDataFolder() + File.separator + "players");
		File file = new File(folder, uuid + ".yml");
		
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.set("style", style);
		config.set("globalPrefix", globalPrefix);
		config.set("localPrefix", localPrefix);
		config.set("mute", mute);
		config.set("notify", notify);
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	 */
}
