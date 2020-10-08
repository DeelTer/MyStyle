package ru.deelter.mystyle.database;

import java.sql.Connection;
import java.sql.SQLException;

import org.bukkit.plugin.Plugin;

public class Database {

	private static Database database;
	
	public static void setupDatabase(Plugin plugin) {
		database = new SQLite(plugin.getDataFolder());
		database.setupTables();
	}

	public void setupTables() {
		Connection con = Database.openConnection();
		try {
			con.prepareStatement("CREATE TABLE IF NOT EXISTS `Players`("
					+ "`UUID` varchar(64) PRIMARY KEY,"
					+ "`STYLE` varchar(64) NOT NULL,"
					+ "`GLOBAL_PREFIX` varchar(64) NOT NULL,"
					+ "`LOCAL_PREFIX` varchar(64) NOT NULL,"
					+ "`MUTE` TINYINT(1) NOT NULL,"
					+ "`NOTIFY` TINYINT(1) NOT NULL" + ");")
					.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Connection openConnection() {
		return SQLite.getConnection();
	}
}
