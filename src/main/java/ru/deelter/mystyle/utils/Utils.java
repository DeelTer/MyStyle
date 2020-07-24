package ru.deelter.mystyle.utils;

import org.bukkit.ChatColor;

public class Utils {

	public static String colorize(String string) {
		return string == null ? "" : ChatColor.translateAlternateColorCodes('&', string);
	}
}
