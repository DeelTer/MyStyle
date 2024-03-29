package ru.deelter.mystyle.chat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import ru.deelter.mystyle.Config;
import ru.deelter.mystyle.MyStyle;
import ru.deelter.mystyle.player.APlayer;
import ru.deelter.mystyle.utils.LoggerManager;
import ru.deelter.mystyle.utils.Other;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Chat implements Listener {

	private final List<UUID> cooldown = new ArrayList<>();

	@EventHandler(priority = EventPriority.HIGH)
	public void onChat(AsyncPlayerChatEvent e) {
		e.setCancelled(true);

		Player player = e.getPlayer();
		String message = e.getMessage();

		boolean isGlobal = false;
		if (!message.startsWith("!")) {

			e.getRecipients().removeIf(other -> isFar(player, other));
			if (!Config.MSG_NO_PLAYERS.equalsIgnoreCase("none") && e.getRecipients().size() == 1) {
				player.sendMessage(Config.MSG_NO_PLAYERS);
				return;
			}
		} else {
			/* If global chat is off */
			if (!Config.ENABLE_GLOBAL) {
				player.sendMessage(Config.MSG_GLOBAL_DISABLE_ALL);
				return;
			}

			/* if player disable global chat */
			if (APlayer.getPlayer(player).isMute()) {
				player.sendMessage(Config.MSG_GLOBAL_DISABLE);
				return;
			}

			isGlobal = true;
			message = message.replaceFirst("!", "");
			e.getRecipients().removeIf(other -> APlayer.getPlayer(other).isMute());
		}

		/* Chat cooldown */
		if (Config.ENABLE_CHAT_COOLDOWN) {
			if (cooldown.contains(player.getUniqueId())) {
				player.sendMessage(Config.MSG_COOLDOWN);
				return;
			}
			startCooldown(player.getUniqueId(), Config.CHAT_COOLDOWN);
		}

		/* Sending message */
		for (Player recipient : e.getRecipients()) {

			APlayer aRecipient = APlayer.getPlayer(recipient);
			String style = Other.color(aRecipient.getStyle());
			style = style.replace("%PLAYER%", player.getName());
			style = style.replace("%MESSAGE%", message);

			String prefix = isGlobal ? Other.color(aRecipient.getGlobalPrefix()) : Other.color(aRecipient.getLocalPrefix());
			recipient.sendMessage(prefix + style);
		}

		/* Logger */
		LoggerManager.log((isGlobal ? "[G] " : "[L] ") + player.getName() + ": " + message, false);
	}

	private boolean isFar(Player player1, Player player2) {
		if (player1.getWorld() != player2.getWorld())
			return true;

		double distance = player1.getLocation().distance(player2.getLocation());
		return distance > Config.RADIUS;
	}

	private void startCooldown(UUID uuid, int seconds) {
		cooldown.add(uuid);
		Bukkit.getScheduler().runTaskLaterAsynchronously(MyStyle.getInstance(), () -> cooldown.remove(uuid), seconds * 20L);
	}
}