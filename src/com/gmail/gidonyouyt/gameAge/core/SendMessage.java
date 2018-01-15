package com.gmail.gidonyouyt.gameAge.core;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.gidonyouyt.gameAge.GameAge;

public class SendMessage extends JavaPlugin {
	private static final String name = GameAge.pluginName;

	public static void logConsole(String msg) {
		Logger logger = Bukkit.getServer().getLogger();
		logger.info("[%plugin%] ".replace("%plugin%", name) + msg);
	}

	public static void broadcastMessage(String msg) {
		Bukkit.broadcastMessage(ChatColor.GOLD + "[%plugin%] ".replace("%plugin%", name) + ChatColor.RESET + msg);
	}

	public static void sendMessagePlayer(Player player, String msg) {
		player.sendMessage(
				ChatColor.GOLD + "[%plugin% > %name%] ".replace("%plugin%", name).replace("%name%", player.getName())
						+ ChatColor.RESET + msg);

		logConsole(player.getName() + "  -  " + msg);
	}

	public static void sendMessageOP(String msg) {
		for (Player player : Bukkit.getOnlinePlayers())
			if (player.isOp())
				sendMessagePlayer(player, msg);
	}
}
