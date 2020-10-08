package ru.deelter.mystyle.chat.managers;

import org.bukkit.configuration.file.YamlConfiguration;
import ru.deelter.mystyle.Main;
import ru.deelter.mystyle.utils.LoggerManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class IgnoreManager {

    private File folder, file;
    private YamlConfiguration config;

    private final Map<String, String> values = new HashMap<>();

    public IgnoreManager(String uuid) {
        this.folder = new File(Main.getInstance().getDataFolder() + File.separator + "ignores");
        this.file = new File(folder, uuid + ".yml");
    }

    /* Does this player exist in the folder */
    public boolean hasPlayer() {
        return file.exists();
    }

    /* Get file as configuration */
    public YamlConfiguration getConfiguration() {
        config = YamlConfiguration.loadConfiguration(file);
        return config;
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

        config.set("ignore", list);
        saveConfiguration();
    }

    public List<UUID> getIgnoreList() {
        List<String> players = new ArrayList<>(config.getStringList("ignore"));
        List<UUID> list = new ArrayList<>();
        players.forEach(p -> list.add(UUID.fromString(p)));

        return list;
    }
}
