package ru.deelter.mystyle.chat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import ru.deelter.mystyle.Config;
import ru.deelter.mystyle.player.APlayer;
import ru.deelter.mystyle.utils.Other;

public class Chat implements Listener {

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		e.setCancelled(true);

		Player player = e.getPlayer();
		String message = e.getMessage();
		boolean isGlobal = false;

		if (!message.startsWith("!")) { /* if local chat */

			e.getRecipients().removeIf(other -> isFar(player, other));
			if (e.getRecipients().size() == 1) {
				player.sendMessage(Other.color("&cВ вашем радиусе нет игроков"));
				return;
			}
		} else { /* if global chat */
			/* if player disable global chat */
			if (APlayer.getPlayer(player).isMute()) {
				player.sendMessage(Other.color("&cУ вас отключен глобальный чат, используйте &6/cs mute"));
				return;
			}

			isGlobal = true;
			message = message.replaceFirst("!", "");
			e.getRecipients().removeIf(other -> APlayer.getPlayer(other).isMute());
		}

		/* Sending message */
		for (Player recipient : e.getRecipients()) {

			APlayer arecipient = APlayer.getPlayer(recipient);
			String style = Other.color(arecipient.getStyle());
			style = style.replace("%PLAYER%", player.getName());
			style = style.replace("%MESSAGE%", message);

			String prefix = isGlobal ? Other.color(arecipient.getGlobalPrefix()) : Other.color(arecipient.getLocalPrefix());
			recipient.sendMessage(prefix + style);
		}
		Bukkit.getConsoleSender().sendMessage((isGlobal ? "[G] " : "[L] ") + player.getName() + ": " + message);
	}

	private boolean isFar(Player player1, Player player2) {
		if (player1.getWorld() != player2.getWorld())
			return true;

		double distance = player1.getLocation().distance(player2.getLocation());
		return distance > Config.RADIUS;
	}
}