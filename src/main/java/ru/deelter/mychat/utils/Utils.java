package ru.deelter.mychat.utils;

import org.bukkit.ChatColor;

public class Utils {

	public static String colorize(String string) {
		return string == null ? "" : ChatColor.translateAlternateColorCodes('&', string);
	}
}
