package ru.deelter.mystyle.utils;

import org.bukkit.Bukkit;
import ru.deelter.mystyle.Config;
import ru.deelter.mystyle.MyStyle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LoggerManager {

    /* Just log */
    public static void logFile(String category, String name, List<String> info) {
        if (!Config.FILE_LOGS)
            return;

        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        File folder = new File(MyStyle.getInstance().getDataFolder() + File.separator + category);
        if (!folder.exists())
            folder.mkdir();

        File dateFolder = new File(folder + "/" + date);
        if (!dateFolder.exists())
            dateFolder.mkdir();

        File file = new File(dateFolder, name + ".txt");
        int i = 1;
        while (file.exists()) {
            file = new File(dateFolder, name + i++ + ".txt");
        }

        try {
            if(!file.exists())
                file.createNewFile();

            FileWriter writer = new FileWriter(file.getPath(), true);
            BufferedWriter bufferWriter = new BufferedWriter(writer);
            for (String s : info) {
                bufferWriter.write(s);
            }
            bufferWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logFile(String name, List<String> info) {
        logFile("logs", name, info);
    }

    public static void log(String s, boolean color) {
        if (Config.CONSOLE_LOGS) {
            String format = color ? Other.color(s) : s;
            Bukkit.getLogger().info(format);
        }
    }

    public static void log(String s) {
        log(s, true);
    }
}
