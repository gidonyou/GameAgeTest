package com.gmail.gidonyouyt.gameAge;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.gmail.gidonyouyt.gameAge.core.CGame;
import com.gmail.gidonyouyt.gameAge.events.BlockBreak;
import com.gmail.gidonyouyt.gameAge.events.BlockPlace;
import com.gmail.gidonyouyt.gameAge.events.EntityDamage;
import com.gmail.gidonyouyt.gameAge.events.EntityRegainHealth;
import com.gmail.gidonyouyt.gameAge.events.ExtraChangeDect;
import com.gmail.gidonyouyt.gameAge.events.PlayerInteract;
import com.gmail.gidonyouyt.gameAge.events.PlayerLeave;
import com.gmail.gidonyouyt.gameAge.events.PlayerPreLogin;

public class GameAge extends JavaPlugin {

	public static String pluginName = "버그덩어리";
	public static String pluginVersion = "버그버전";
	public ConfigYml yml = new ConfigYml(this);

	public GameAge() {

	}

	public void onEnable() {
		pluginName = getDescription().getName();
		pluginVersion = getDescription().getVersion();

		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = Bukkit.getServer().getLogger();
		logger.info("GID'S MINI CONTENT!!");
		logger.info("Plugin Name: " + pdfFile.getName());
		logger.info("Plugin Version: " + pdfFile.getVersion());
		
		// Load Config
		yml.loadConfiguration();

		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.scheduleSyncRepeatingTask(this, new MainTimer(), 0L, 20L);

		// Commands
		getCommand("game").setExecutor(new CGame());
		getCommand("play").setExecutor(new CPlay(this));
		getCommand("play").setTabCompleter(new CPlayCompleter());

		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerLeave(), this);
		pm.registerEvents(new PlayerPreLogin(), this);
		pm.registerEvents(new BlockPlace(), this);
		pm.registerEvents(new BlockBreak(), this);
		pm.registerEvents(new EntityDamage(), this);
		pm.registerEvents(new PlayerInteract(this), this);
		pm.registerEvents(new EntityRegainHealth(), this);
		pm.registerEvents(new ExtraChangeDect(), this);

		// Test Web UI
		try {
			if (GameSettings.WEBPAGE_ON.value() == 1)
				WebManager.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void onDisable() {
		Bukkit.getServer().getScheduler().cancelAllTasks();
		Sequence.stop();

	}

}
