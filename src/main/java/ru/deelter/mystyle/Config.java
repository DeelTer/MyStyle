package ru.deelter.mystyle;

import org.bukkit.configuration.file.FileConfiguration;
import ru.deelter.mystyle.utils.Other;

public class Config {

	/* Settings */
	public static String STYLE;
	public static String GLOBAL_PREFIX;
	public static String LOCAL_PREFIX;
	
	public static int RADIUS;
	
	public static boolean MUTE;
	public static boolean NOTIFY;

	/* Settings */
	public static boolean ENABLE_PRIVATE;
	public static boolean ENABLE_CHAT_COOLDOWN;
	public static int CHAT_COOLDOWN;

	public static boolean DISABLE_LOGS;

	/* Messages */
	public static String MSG_NO_PERM;
	public static String MSG_RELOAD;

	public static String MSG_NO_PLAYERS;
	public static String MSG_GLOBAL_DISABLE;
	public static String MSG_COOLDOWN;

	public static String MSG_PLAYER_IGNORE_YOU;
	public static String MSG_IGNORE_ADD;
	public static String MSG_IGNORE_REMOVE;

	/* Permissions */
	public static String MUTE_PERM;
	public static String STYLE_PERM;
	public static String NOTIFY_PERM;
	public static String PREFIX_PERM;
	
	public static void reloadConfig() {
		Main.getInstance().reloadConfig();
		FileConfiguration config = Main.getInstance().getConfig();

		STYLE = config.getString("default-values.style");
		GLOBAL_PREFIX = config.getString("default-values.global-prefix");
		LOCAL_PREFIX = config.getString("default-values.local-prefix");

		RADIUS = config.getInt("default-values.radius");
		MUTE = config.getBoolean("default-values.mute");
		NOTIFY = config.getBoolean("default-values.notify");

		/* Settings */
		ENABLE_PRIVATE = config.getBoolean("settings.enable-private");
		ENABLE_CHAT_COOLDOWN = config.getBoolean("settings.chat-cooldown.enable");
		CHAT_COOLDOWN = config.getInt("settings.chat-cooldown.seconds");

		DISABLE_LOGS = config.getBoolean("disable-logs");

		/* Other */
		MSG_NO_PERM = config.getString("messages.no-perm");
		MSG_RELOAD = config.getString("messages.reload");

		MSG_NO_PLAYERS = Other.color(config.getString("messages.chat.no-players"));
		MSG_GLOBAL_DISABLE = Other.color(config.getString("messages.chat.global-disable"));
		MSG_COOLDOWN = Other.color(config.getString("messages.chat.cooldown"));

		MSG_PLAYER_IGNORE_YOU = Other.color(config.getString("messages.chat.ignore-you"));
		MSG_IGNORE_ADD = Other.color(config.getString("messages.chat.player-ignore-add"));
		MSG_IGNORE_REMOVE = Other.color(config.getString("messages.chat.player-ignore-remove"));

		/* Permissions */
		MUTE_PERM = config.getString("permissions.mute");
		STYLE_PERM = config.getString("permissions.style");
		NOTIFY_PERM = config.getString("permissions.notify");
		PREFIX_PERM = config.getString("permissions.prefix");
	}
}
