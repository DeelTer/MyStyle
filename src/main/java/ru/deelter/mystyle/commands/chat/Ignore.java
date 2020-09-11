package ru.deelter.mystyle.commands.chat;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.deelter.mystyle.player.APlayer;
import ru.deelter.mystyle.utils.Other;

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

        APlayer aPlayer = APlayer.getPlayer(player);
        if (aPlayer.getIgnoreList().contains(target.getUniqueId().toString())) {
            player.sendMessage(Other.color("&8[&c#&8] &fВы уже игнорируете этого игрока"));
            return true;
        }

        aPlayer.addIgnore(target.getUniqueId());
        player.sendMessage(Other.color("&8[&6#&8] &fТеперь вы игнорируете " + args[0]));
        return true;
    }
}
