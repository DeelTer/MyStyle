package ru.deelter.mystyle.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.deelter.mystyle.Config;
import ru.deelter.mystyle.player.APlayer;
import ru.deelter.mystyle.utils.LoggerManager;
import ru.deelter.mystyle.utils.Other;

public class Tell implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length <= 1) {
            sender.sendMessage(Other.color("&8[&6#&8] &fИспользование: /" + label + " <ник> <сообщение>"));
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage("Доступно только в игре");
            return true;
        }

        Player player = (Player) sender, target = Bukkit.getPlayer(args[0]);
        String msg = String.join(" ", args).substring(args[0].length() + 1);

        if (target == null || target == player) {
            player.sendActionBar(Other.color(target != player ? "&cИгрок &6" + args[0] + "&c не в сети" : "&cВы не можете отправить сообщение себе"));
            return true;
        }

        APlayer aTarget = APlayer.getPlayer(target);
        if (aTarget.getIgnoreList().contains(player.getUniqueId().toString())) {
            player.sendMessage(Config.MSG_PLAYER_IGNORE_YOU);
            return true;
        }

            TextComponent chat = new TextComponent(Other.color("&8[&7" + player.getName() + "&8]&f ") + msg);
            chat.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/" + label + " " + player.getName() + " "));
            target.sendMessage(chat);

            String message = Other.color("&8[&7" + player.getName() + " &6->&7 " + args[0] + "&8]&f ") + msg;
            player.sendMessage(message);

            /* Sound */
            target.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);

            /* Log */
            LoggerManager.log(message);
            return true;
    }
}
