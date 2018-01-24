package com.gmail.gidonyouyt.gameAge.core;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public enum GameStatus {
	STANDBY(ChatColor.YELLOW),
	ENGINE_SETUP(ChatColor.GOLD),
	GAME_SETUP(ChatColor.GOLD),
	COUNT_DOWN(ChatColor.BLUE),
	RUNNING(ChatColor.GREEN),
	FINSHED(ChatColor.AQUA),
	STOPPED(ChatColor.RED),
	BREAK_PRD(ChatColor.LIGHT_PURPLE);

	// https://www.mkyong.com/java/java-enum-example/

	private ChatColor color;

	GameStatus(ChatColor color) {
		this.color = color;
	}

	public ChatColor color() {
		return color;
	}

	private static GameStatus currentStatus = GameStatus.STANDBY;

	public static void setStatus(GameStatus newStatus) {
		SendMessage.sendMessageOP(ChatColor.GRAY + "ÃªÂ²Å’Ã¬Å¾â€žÃ¬Æ’ÂÃ­Æ’Å“ÃªÂ°â‚¬ Ã«Â³â‚¬ÃªÂ²Â½Ã«ÂËœÃ¬â€”Ë†Ã¬Å ÂµÃ«â€¹Ë†Ã«â€¹Â¤. %old% --> %new%"
				.replace("%old%", currentStatus.toString()).replace("%new%", newStatus.toString()));
		currentStatus = newStatus;
	}

	public static GameStatus getStatus() {
		return currentStatus;
	}

	public static void broadcastStatus() {
		Bukkit.getServer().broadcastMessage(getMessage());
	}

	public static String getMessage() {
		return ChatColor.GOLD + "ÃªÂ²Å’Ã¬Å¾â€žÃ¬Æ’ÂÃ­Æ’Å“ : " + currentStatus.color + currentStatus.toString();
	}

}
