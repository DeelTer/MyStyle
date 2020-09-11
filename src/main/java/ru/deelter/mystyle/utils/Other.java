package ru.deelter.mystyle.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import ru.deelter.mystyle.Config;

public class Other {

	public static String color(String string) {
		return string == null ? "" : ChatColor.translateAlternateColorCodes('&', string);
	}

    public static void log(String s) {
		log(s, true);
    }

	public static void log(String s, boolean color) {
		if (!Config.DISABLE_LOGS) {
			String format = color ? color(s) : s;
			Bukkit.getLogger().info(format);
		}
	}
}
