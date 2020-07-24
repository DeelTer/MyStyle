package ru.deelter.mychat.chat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import ru.deelter.mychat.player.APlayer;
import ru.deelter.mychat.utils.Utils;

public class JoinAndQuit implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		e.setJoinMessage(null);
		sendNotify(e.getPlayer(), true);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		e.setQuitMessage(null);
		sendNotify(e.getPlayer(), false);
	}

	private void sendNotify(Player leaver, boolean isJoin) {

		String message = Utils.colorize("&e" + leaver.getName() + (isJoin ? " присоединился к игре" : " вышел из игры"));
		for (Player online : Bukkit.getOnlinePlayers()) {

			APlayer ap = APlayer.getPlayer(online);
			if (!ap.isNotifyVisible())
				continue;

			online.sendMessage(message);
		}
	}
}
