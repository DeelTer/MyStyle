package ru.deelter.mystyle.commands.roleplay;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.deelter.mystyle.utils.Other;

import java.util.Random;

public class Roll implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Доступно только игроку");
            return true;
        }

        Player player = (Player) sender;
        int result = new Random().nextInt(100);

        TextComponent component = new TextComponent(Other.color("&6* &f" + player.getName() + " бросает кости: " + result + "&6 *"));

        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(Other.color("&fПри выполнении команды показывает\nслучайное число от &e0&f до &e100" + "\n&f\n&7Кликните, чтобы выполнить\nкоманду /roll")));
        component.setHoverEvent(hoverEvent);

        ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/roll ");
        component.setClickEvent(clickEvent);

        player.getLocation().getNearbyPlayers(100).forEach(target -> target.sendMessage(component));
        return true;
    }
}
