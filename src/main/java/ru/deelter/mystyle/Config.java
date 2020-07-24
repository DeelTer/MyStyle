package ru.deelter.mystyle;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {
	
	public static List<String> HELP_PAGE;

	/* Settings */
	public static String STYLE;
	public static String GLOBAL_PREFIX;
	public static String LOCAL_PREFIX;
	
	public static int RADIUS;
	
	public static boolean MUTE;
	public static boolean NOTIFY;

	/* Messages */
	public static String MSG_NO_PERM;
	public static String MSG_RELOAD;

	/* Permissions */
	public static String MUTE_PERM;
	public static String STYLE_PERM;
	public static String NOTIFY_PERM;
	public static String PREFIX_PERM;
	
	public static void reloadConfig() {
		Main.getInstance().reloadConfig();
		
		HELP_PAGE = config().getStringList("help");

		STYLE = config().getString("default-values.style");
		GLOBAL_PREFIX = config().getString("default-values.global-prefix");
		LOCAL_PREFIX = config().getString("default-values.local-prefix");

		RADIUS = config().getInt("default-values.radius");
		MUTE = config().getBoolean("default-values.mute");
		NOTIFY = config().getBoolean("default-values.notify");

		MSG_NO_PERM = config().getString("messages.no-perm");
		MSG_RELOAD = config().getString("messages.reload");

		/* Permissions */
		MUTE_PERM = config().getString("permissions.mute");
		STYLE_PERM = config().getString("permissions.style");
		NOTIFY_PERM = config().getString("permissions.notify");
		PREFIX_PERM = config().getString("permissions.prefix");
	}

	private static FileConfiguration config() {
		return Main.getInstance().getConfig();
	}
}
