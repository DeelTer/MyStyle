package ru.deelter.mystyle;

import java.io.File;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import ru.deelter.mystyle.chat.Chat;
import ru.deelter.mystyle.chat.JoinAndQuit;
import ru.deelter.mystyle.commands.ChatSettings;
import ru.deelter.mystyle.commands.Ignore;
import ru.deelter.mystyle.commands.Tell;
import ru.deelter.mystyle.commands.roleplay.Roll;
import ru.deelter.mystyle.commands.roleplay.Try;
import ru.deelter.mystyle.database.Database;
import ru.deelter.mystyle.player.APlayer;
import ru.deelter.mystyle.player.PlayerIdentification;
import ru.deelter.mystyle.utils.LoggerManager;

public class Main extends JavaPlugin implements Listener {
	
	public static JavaPlugin instance;

	public static JavaPlugin getInstance() {
		return instance;
	}

	public void onEnable() {
		instance = this;

		File config = new File(instance.getDataFolder().getPath() + "/config.yml");
		if (!config.exists()) {
			LoggerManager.log("&cКонфиг не найден. Загружаем новый");
			saveDefaultConfig();
		}

		Config.reloadConfig();
		Database.setupDatabase(this);

		/* Listeners */
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerIdentification(), this);
		pm.registerEvents(new JoinAndQuit(), this);
		pm.registerEvents(new Chat(), this);

		/* Commands */
		getCommand("chatsettings").setExecutor(new ChatSettings());
		if (Config.ENABLE_PRIVATE) {
			getCommand("tell").setExecutor(new Tell());
			getCommand("ignore").setExecutor(new Ignore());
			LoggerManager.log("&aАктивируем классы для приватных сообщений");
		}

		if (Config.ENABLE_ROLEPLAY) {
			getCommand("try").setExecutor(new Try());
			getCommand("roll").setExecutor(new Roll());
			LoggerManager.log("&aАктивируем классы для приватных сообщений");
		}

		LoggerManager.log("&aПлагин успешно включен");
	}

	public void onDisable() {
		int count = APlayer.getPlayers().size();
		LoggerManager.log("&aСохранено " + count + " игроков");
	}
}