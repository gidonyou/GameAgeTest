package com.gmail.gidonyouyt.gameAge.core;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.gidonyouyt.gameAge.GameAge;

public class SendMessage extends JavaPlugin {
	private static final String name = GameAge.pluginName;
	private static final String version = GameAge.pluginVersion;

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

	public static void sendCreditInfo() {
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "  ====  í”ŒëŸ¬ê·¸ì¸ ì •ë³´  ====  ");
		Bukkit.broadcastMessage("ê²Œìž„ ì´ë¦„: " + name);
		Bukkit.broadcastMessage("ê²Œìž„ ë²„ì „: " + version);
		Bukkit.broadcastMessage("ê°œë°œìž: gidonyou");
		Bukkit.broadcastMessage("ë¸”ë¡œê·¸: https://blog.naver.com/gidonyou");
		Bukkit.broadcastMessage("ìœ íŠœë¸Œ: https://www.youtube.com/gidonyou");
		Bukkit.broadcastMessage("");
	}

	public static void specialThanks() {
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "[  ë„ì›€ì„ ì£¼ì‹ ë¶„  ]");
		TitleManager.sendClickableText(ChatColor.GOLD + "[v1.1.0 ì‹¤í—˜ì¥ í˜‘ì°¬] " + ChatColor.RESET + "ë””ìŠ¤ì½”ë“œ ì¡°í•©ëŒ€ (í´ë¦­)",
				"https://discord.gg/mpNKx4Q", "none");
		Bukkit.broadcastMessage(
				ChatColor.YELLOW + "NF3charcoaL --> " + ChatColor.RESET + "ê°ì¢… ë¬¸ë²• ì˜¤ë¥˜ì™€ ì»¨í…ì¸  ë°¸ëŸ°ìŠ¤ ìˆ˜ì •ì„ ë„ì™€ì£¼ì…¨ìŠµë‹ˆë‹¤.");
		Bukkit.broadcastMessage(
				ChatColor.YELLOW + "[v1.1.0 í…ŒìŠ¤í„°] " + ChatColor.RESET + "(ì´ˆëŒ€) NF3charcoaL, J_arvi_S, í“¨ë¦¬, ì‹¬ì‹¬í•˜ë©´ ë°¥ë¬µìž");
		Bukkit.broadcastMessage(ChatColor.YELLOW + "[v1.1.0 í…ŒìŠ¤í„°] " + ChatColor.GRAY + "(ì°¸ì—¬) ");
		Bukkit.broadcastMessage(
				ChatColor.YELLOW + "[Beta3 í…ŒìŠ¤í„°] " + ChatColor.RESET + "NF3charcoaL, J_arvi_S, TF2_ValS ");
		Bukkit.broadcastMessage("");
	}
}
