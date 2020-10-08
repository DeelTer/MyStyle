package ru.deelter.mystyle.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.deelter.mystyle.Config;
import ru.deelter.mystyle.chat.managers.IgnoreManager;
import ru.deelter.mystyle.player.APlayer;
import ru.deelter.mystyle.utils.Other;

import java.util.List;
import java.util.UUID;

public class Ignore implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(Other.color("&8[&6#&8]&f Использование: /" + label + " игрок"));
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage("Доступно только в игре");
            return true;
        }

        Player player = (Player) sender, target = Bukkit.getPlayer(args[0]);
        if (target == null || target == player) {
            player.sendActionBar(Other.color(target != player ? "&c" + args[0] + " не в сети" : "&cВы не можете игнорировать себя"));
            return true;
        }

        /*
        IgnoreManager im = new IgnoreManager(player.getUniqueId().toString());
        List<UUID> ignore = im.getIgnoreList();

        if (ignore.contains(target.getUniqueId())) {
            player.sendMessage(Config.MSG_IGNORE_REMOVE.replace("%PLAYER%", args[0]));
            im.setIgnore(target.getUniqueId(), false);
            return true;
        }

        im.setIgnore(target.getUniqueId(), true);
        player.sendMessage(Config.MSG_IGNORE_ADD.replace("%PLAYER%", args[0]));
        */
        return true;
    }
}
