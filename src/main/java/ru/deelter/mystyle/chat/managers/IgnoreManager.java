package ru.deelter.mystyle.chat.managers;

import org.bukkit.configuration.file.YamlConfiguration;
import ru.deelter.mystyle.MyStyle;
import ru.deelter.mystyle.utils.LoggerManager;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class IgnoreManager {

    private File folder, file;
    private YamlConfiguration config;

    private final Map<String, String> values = new HashMap<>();

    public IgnoreManager(String uuid) {
        this.folder = new File(MyStyle.getInstance().getDataFolder() + File.separator + "ignores");
        this.file = new File(folder, uuid + ".yml");

        YamlConfiguration ign = YamlConfiguration.loadConfiguration(file);
        ign.set("ignore", new ArrayList<>());
        this.config = ign;
    }

    /* Does this player exist in the folder */
    public boolean hasPlayer() {
        return file.exists();
    }

    public void saveConfiguration() {
        if (config == null) {
            LoggerManager.log("&cКонфигурация игрока не найдена");
            return;
        }

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LoggerManager.log("Сохраняем файл " + file.toString());
    }

    public void setIgnore(UUID uuid, boolean b) {
        List<UUID> list = getIgnoreList();
        if (b) list.add(uuid);
        else list.remove(uuid);

        List<String> newList = new ArrayList<>();
        list.forEach(s -> newList.add(s.toString()));

        config.set("ignore", list);
        saveConfiguration();
    }

    public List<UUID> getIgnoreList() {
        List<String> players = config.getStringList("ignore");

        List<UUID> list = new ArrayList<>();
        players.forEach(p -> list.add(UUID.fromString(p)));

        return list;
    }
}
