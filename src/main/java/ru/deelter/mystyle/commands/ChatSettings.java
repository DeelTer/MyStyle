package ru.deelter.mystyle.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import ru.deelter.mystyle.Config;
import ru.deelter.mystyle.player.APlayer;
import ru.deelter.mystyle.utils.Other;

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
					if (args[1] == null) {
						sendHelpMessage((Player) sender);
						return true;
					}

					boolean setVisible = args[1].equalsIgnoreCase("false");
					sender.sendMessage(Other.color(setVisible ? "&8[&e#&8]&f Вы выключили глобальный чат" : "&8[&a#&8] &fВы включили глобальный чат"));
					aplayer.setMute(setVisible);
					return true;
				}
				
				/* Mute notifications of join and quit */
				else if (args[0].equalsIgnoreCase("notify")) {
					if (args[1] == null) {
						sendHelpMessage((Player) sender);
						return true;
					}

					boolean setVisible = args[1].equalsIgnoreCase("false");
					sender.sendMessage(Other.color(setVisible ? "&8[&e#&8] &fВы включили оповещения о входе/выходе игроков" : "&8[&c#&8] &fВы выключили оповещения о входе/выходе игроков"));
					aplayer.setNotify(setVisible);
					return true;
				}

				/* Set your own prefixes */
				else if (args[0].equalsIgnoreCase("prefix")) {
					if (args.length < 2) {
						sendHelpMessage((Player) sender);
						return true;
					}

					boolean isGlobal = !args[1].equalsIgnoreCase("local");
					String oldPrefix = isGlobal ? aplayer.getGlobalPrefix() : aplayer.getLocalPrefix();
					String newPrefix = isGlobal ? Config.GLOBAL_PREFIX : Config.LOCAL_PREFIX;

					if (args.length > 2) {
						newPrefix = args[2] + " ";
					}

					if(isGlobal) aplayer.setGlobalPrefix(newPrefix);
					else aplayer.setLocalPrefix(newPrefix);

					sender.sendMessage(Other.color("Вы поменяли префикс " + (isGlobal ? "глобального" : "локального") + " чата с \"" + oldPrefix + "&f\" на \"" + newPrefix + "&f\""));
					return true;
				}

				/* Set your own style */
				else if (args[0].equalsIgnoreCase("style")) {

					String style = aplayer.getStyle();
					String newStyle = Config.STYLE;
					if (args.length > 1) {

						newStyle = String.join(" ", args).substring(args[0].length() + 1);
						if (newStyle.length() > 50) {
							sender.sendMessage(Other.color("&8[&c#&8] &fСтиль чата не должен быть таким длинным!"));
							return true;
						}
					}

					sender.sendMessage(Other.color("&8[&6#&8] &fВы поменяли стиль чата с `" + style + "`&f на " + newStyle));
					aplayer.setStyle(newStyle);
					return true;
				}
			}
			
			sendHelpMessage((Player) sender);
		}

		return true;
	}

	private void sendHelpMessage(Player player) {
		/* Show player information */
		TextComponent component = new TextComponent(Other.color("&8[&6#&8] &fУведомления о входе/выходе игрока: "));
		TextComponent enableNotify = new TextComponent(Other.color("[&aВключить&f]"));
		TextComponent disableNotify = new TextComponent(Other.color("&f [&cВыключить&f]"));

		TextComponent component2 = new TextComponent(Other.color("\n&8[&6#&8] &fВидимость глобального чата: "));
		TextComponent enableGlobal = new TextComponent(Other.color("[&aВключить&f]"));
		TextComponent disableGlobal = new TextComponent(Other.color("&f [&cВыключить&f]"));

		TextComponent component3 = new TextComponent(Other.color("\n\n&8[&6#&8] &fИзменение префикса: "));
		TextComponent globalPrefix = new TextComponent(Other.color("[&6Глобальный&f]"));
		TextComponent localPrefix = new TextComponent(Other.color("&f [&6Локальный&f]"));

		TextComponent component4 = new TextComponent(Other.color("\n&8[&6#&8] &fИзменение стиля чата: "));
		TextComponent style = new TextComponent(Other.color("[&6Изменить&f]"));

		/* WORK */
		enableNotify.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(Other.color("Кликните, чтобы&a включить &f\nуведомления о входе/выходе"))));
		enableNotify.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/mystyle notify true"));
		component.addExtra(enableNotify);

		disableNotify.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(Other.color("Кликните, чтобы&c выключить &f\nуведомления о входе/выходе"))));
		disableNotify.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/mystyle notify false"));
		enableNotify.addExtra(disableNotify);

		enableGlobal.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(Other.color("Кликните, чтобы&a включить &f\nвидимость глобального чата"))));
		enableGlobal.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/mystyle mute true"));
		component2.addExtra(enableGlobal);

		disableGlobal.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(Other.color("Кликните, чтобы&c выключить &f\nвидимость глобального чата"))));
		disableGlobal.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/mystyle mute false"));
		component2.addExtra(disableGlobal);
		component.addExtra(component2);

		globalPrefix.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(Other.color("Кликните, чтобы показать команду &f\nдля изменения&6 глобального&f префикса"))));
		globalPrefix.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/mystyle prefix global " + Config.GLOBAL_PREFIX));
		component3.addExtra(globalPrefix);

		localPrefix.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(Other.color("Кликните, чтобы показать команду &f\nдля изменения&a локального&f префикса"))));
		localPrefix.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/mystyle prefix local " + Config.LOCAL_PREFIX));
		component3.addExtra(localPrefix);
		component2.addExtra(component3);

		style.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(Other.color("Кликните, чтобы показать команду &f\nдля изменения&6 стиля&f чата\n&f\n&7%%PLAYER% - вместо неё будет ник\n%MESSAGE% - вместо неё будет сообщение"))));
		style.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/mystyle style " + Config.STYLE));
		component4.addExtra(style);
		component3.addExtra(component4);

		player.sendMessage(component);
	}
}
