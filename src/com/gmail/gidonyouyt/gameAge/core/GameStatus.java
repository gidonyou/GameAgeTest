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
		SendMessage.sendMessageOP(ChatColor.GRAY + "게임상태가 변경되었습니다. %old% --> %new%"
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
		return ChatColor.GOLD + "게임상태 : " + currentStatus.color + currentStatus.toString();
	}

}
