package ru.deelter.mychat.player;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import ru.deelter.mychat.Config;
import ru.deelter.mychat.Main;

public class APlayer {

	private UUID uuid;

	private String style = Config.STYLE;

	private String globalPrefix = Config.GLOBAL_PREFIX;
	private String localPrefix = Config.LOCAL_PREFIX;

	private boolean mute = Config.MUTE;
	private boolean notify = Config.NOTIFY;

	private static Map<UUID, APlayer> players = new HashMap<>();

	public APlayer(UUID uuid) {
		this.uuid = uuid;
		load();
	}

	public static APlayer getPlayer(Player player) {
		if (!players.containsKey(player.getUniqueId())) {

			APlayer aplayer = new APlayer(player.getUniqueId());
			aplayer.register();

			return aplayer;
		}

		return players.get(player.getUniqueId());
	}

	public UUID getUUID() {
		return uuid;
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

	public void register() {
		players.putIfAbsent(uuid, this);
	}

	public void unregister() {
		players.remove(uuid);
	}
	
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

				StringBuilder text = new StringBuilder();
				text.append("style: '" + style + "'\n");
				text.append("globalPrefix: '" + globalPrefix + "'\n");
				text.append("localPrefix: '" + localPrefix + "'\n");
				text.append("mute: '" + mute + "'\n");
				text.append("notify: '" + notify + "'\n");

				FileWriter writer = new FileWriter(file.getPath(), true);
				BufferedWriter bufferWriter = new BufferedWriter(writer);
				bufferWriter.write(text.toString());
				bufferWriter.close();
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}

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
			System.out.println(e);
		}
	}
}
