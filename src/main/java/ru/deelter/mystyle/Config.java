package ru.deelter.mystyle;

import org.bukkit.configuration.ConfigurationSection;
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
	public static boolean ENABLE_GLOBAL;

	public static boolean ENABLE_PRIVATE;
	public static boolean ENABLE_ROLEPLAY;

	public static boolean ENABLE_CHAT_COOLDOWN;
	public static int CHAT_COOLDOWN;

	public static boolean CONSOLE_LOGS;
	public static boolean FILE_LOGS;

	/* Messages */
	public static String MSG_NO_PERM;
	public static String MSG_RELOAD;

	public static String MSG_NO_PLAYERS;
	public static String MSG_GLOBAL_DISABLE;
	public static String MSG_GLOBAL_DISABLE_ALL;
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

		/* Default values */
		ConfigurationSection def = config.getConfigurationSection("default-values");
		STYLE = def.getString("style");
		GLOBAL_PREFIX = def.getString("global-prefix");
		LOCAL_PREFIX = def.getString("local-prefix");

		RADIUS = def.getInt("radius");
		MUTE = def.getBoolean("mute");
		NOTIFY = def.getBoolean("notify");

		/* Settings */
		ConfigurationSection settings = config.getConfigurationSection("settings");
		ConfigurationSection chat = settings.getConfigurationSection("chat");

		ENABLE_GLOBAL = chat.getBoolean("global");
		ENABLE_PRIVATE = chat.getBoolean("commands.private");
		ENABLE_ROLEPLAY = chat.getBoolean("commands.roleplay");

		ENABLE_CHAT_COOLDOWN = chat.getBoolean("cooldown.enable");
		CHAT_COOLDOWN = chat.getInt("cooldown.seconds");

		CONSOLE_LOGS = settings.getBoolean("logs.console");
		FILE_LOGS = settings.getBoolean("logs.file");

		/* Messages */
		ConfigurationSection messages = config.getConfigurationSection("messages");
		MSG_NO_PERM = messages.getString("no-perm");
		MSG_RELOAD = messages.getString("reload");

		MSG_NO_PLAYERS = Other.color(messages.getString("chat.no-players"));
		MSG_GLOBAL_DISABLE = Other.color(messages.getString("chat.global-disable"));
		MSG_GLOBAL_DISABLE_ALL = Other.color(messages.getString("chat.global-disable-all"));
		MSG_COOLDOWN = Other.color(messages.getString("chat.cooldown"));

		MSG_PLAYER_IGNORE_YOU = Other.color(messages.getString("chat.ignore-you"));
		MSG_IGNORE_ADD = Other.color(messages.getString("chat.player-ignore-add"));
		MSG_IGNORE_REMOVE = Other.color(messages.getString("chat.player-ignore-remove"));

		/* Permissions */
		ConfigurationSection permissions = config.getConfigurationSection("permissions");
		MUTE_PERM = permissions.getString("mute");
		STYLE_PERM = permissions.getString("style");
		NOTIFY_PERM = permissions.getString("notify");
		PREFIX_PERM = permissions.getString("prefix");
	}
}
