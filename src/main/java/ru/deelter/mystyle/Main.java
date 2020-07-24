package ru.deelter.mystyle;

import java.io.File;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import ru.deelter.mystyle.chat.Chat;
import ru.deelter.mystyle.chat.JoinAndQuit;
import ru.deelter.mystyle.commands.ChatSettings;
import ru.deelter.mystyle.player.PlayerIdentification;

public class Main extends JavaPlugin implements Listener {
	
	public static JavaPlugin instance;

	public void onEnable() {
		instance = this;
		if (!(new File(instance.getDataFolder().getPath() + "/config.yml").exists())) {
			getLogger().info("Конфига не существует, загружаем новый..");
			saveDefaultConfig();
		}

		Config.reloadConfig();

		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerIdentification(), this);
		pm.registerEvents(new ChatSettings(), this);
		pm.registerEvents(new JoinAndQuit(), this);
		pm.registerEvents(new Chat(), this);
		
		getCommand("chatsettings").setExecutor(new ChatSettings());
	}

	public void onDisable() {
		// Chat.saveStyles();
	}
	
	public static JavaPlugin getInstance() {
		return instance;
	}
}