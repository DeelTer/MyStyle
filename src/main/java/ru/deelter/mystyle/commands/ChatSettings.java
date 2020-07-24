package ru.deelter.mystyle.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import ru.deelter.mystyle.Config;
import ru.deelter.mystyle.player.APlayer;

public class ChatSettings implements CommandExecutor, Listener {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) { // /CS ARGS
		if (sender instanceof Player) {
			if (args.length > 0) {
				APlayer aplayer = APlayer.getPlayer((Player) sender);
				if (args[0].equalsIgnoreCase("reload")) { // reload command
					if (!sender.isOp()) {
						sender.sendMessage(Config.MSG_NO_PERM);
						return true;
					}

					Config.reloadConfig();
					return true;
				}

				/* Mute your global chat */
				else if (args[0].equalsIgnoreCase("mute")) {
					if (!sender.hasPermission(Config.MUTE_PERM)) {
						sender.sendMessage(Config.MSG_NO_PERM);
						return true;
					}

					sender.sendMessage(aplayer.isMute() ? "Вы включили глобальный чат" : "Вы выключили глобальный чат");
					aplayer.setMute(!aplayer.isMute());
					return true;
				}
				
				/* Mute notifications of join and quit */
				else if (args[0].equalsIgnoreCase("notify")) {
					if (!sender.hasPermission(Config.NOTIFY_PERM)) {
						sender.sendMessage(Config.MSG_NO_PERM);
						return true;
					}

					sender.sendMessage(aplayer.isNotifyVisible() ? "Вы выключили оповещения о входе/выходе игроков" : "Вы включили оповещения о входе/выходе игроков");
					aplayer.setNotify(!aplayer.isNotifyVisible());
					return true;
				}

				/* Set your own prefixes */
				else if (args[0].equalsIgnoreCase("prefix")) {
					if (!sender.hasPermission(Config.PREFIX_PERM)) {
						sender.sendMessage(Config.MSG_NO_PERM);
						return true;
					}

					if (args.length < 2) {
						sender.sendMessage(getHelpPage());
						return true;
					}

					boolean isGlobal = !args[1].equalsIgnoreCase("local");

					String oldPrefix = isGlobal ? aplayer.getGlobalPrefix() : aplayer.getLocalPrefix();
					String newPrefix = isGlobal ? Config.GLOBAL_PREFIX : Config.LOCAL_PREFIX;

					if (args.length > 2) {
						newPrefix = args[2];
					}

					if(isGlobal) aplayer.setGlobalPrefix(newPrefix + " ");
					else aplayer.setLocalPrefix(newPrefix + " ");

					sender.sendMessage("Вы поменяли префикс " + (isGlobal ? "глобального" : "локального") + " чата с \"" + oldPrefix + "\" на \"" + newPrefix + "\"");
					return true;
				}

				/* Set your own style */
				else if (args[0].equalsIgnoreCase("style")) {
					if (!sender.hasPermission(Config.STYLE_PERM)) {
						sender.sendMessage(Config.MSG_NO_PERM);
						return true;
					}
					
					String style = aplayer.getStyle();
					String newStyle = Config.STYLE;
					if (args.length > 1) {
						newStyle = String.join(" ", args).substring(args[0].length() + 1);

						if (newStyle.length() > 50) {
							sender.sendMessage("Стиль чата не должен быть таким длинным!");
							return true;
						}
					}

					sender.sendMessage("Вы поменяли стиль чата с `" + style + "` на " + newStyle);
					aplayer.setStyle(newStyle);
					return true;
				}
			}
			
			sender.sendMessage(getHelpPage());
		}

		return true;
	}

	private String getHelpPage() {
		StringBuilder help = new StringBuilder();
		for (String s : Config.HELP_PAGE)
			help.append(ChatColor.translateAlternateColorCodes('&', s)).append("\n");

		return help.toString();
	}

//	private boolean isInteger(String s) {
//		try {
//			Integer.parseInt(s);
//		} catch (NumberFormatException e) {
//			return false;
//		} catch (NullPointerException e) {
//			return false;
//		}
//
//		return true;
//	}
}
