package ru.deelter.mystyle.player;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import ru.deelter.mystyle.Main;

public class PlayerIdentification implements Listener {

	@EventHandler
	public void onJoin(AsyncPlayerPreLoginEvent e) {

		UUID uuid = e.getUniqueId();
		APlayer aplayer = new APlayer(uuid);
		aplayer.load();

		aplayer.register();
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
			APlayer aplayer = APlayer.getPlayer(e.getPlayer());
			aplayer.save();

			aplayer.unregister();
		});
	}
}
