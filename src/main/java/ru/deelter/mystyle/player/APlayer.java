package ru.deelter.mystyle.player;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import ru.deelter.mystyle.Config;
import ru.deelter.mystyle.Main;

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

	/* Save player */
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
}
