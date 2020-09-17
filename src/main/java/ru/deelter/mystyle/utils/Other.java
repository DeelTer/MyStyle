package ru.deelter.mystyle.utils;

import org.bukkit.ChatColor;

public class Other {

	public static String color(String string) {
		return string == null ? "" : ChatColor.translateAlternateColorCodes('&', string);
	}

	public static String strip(String string) {
		return string == null ? "" : ChatColor.stripColor(string);
	}
}
