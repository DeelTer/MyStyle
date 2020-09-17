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

public class Try implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Доступно только игроку");
            return true;
        }

        Player player = (Player) sender;
        String message = Other.strip(String.join(" ", args);

        String result = Math.random() < 0.5D ? "&aУспешно" : "&cНеудачно";
        TextComponent component = new TextComponent(Other.color("&6* &f" + player.getName() + "" + message + " [" + result + "&f]&6 *"));

        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(Other.color("&6Кликните&7, чтобы выполнить\nкоманду /try")));
        component.setHoverEvent(hoverEvent);

        ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/try ");
        component.setClickEvent(clickEvent);

        player.getLocation().getNearbyPlayers(100).forEach(target -> target.sendMessage(component));
        return true;
    }
}
