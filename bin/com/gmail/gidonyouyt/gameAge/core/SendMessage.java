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
		Bukkit.broadcastMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "  ====  플러그인 정보  ====  ");
		Bukkit.broadcastMessage("게임 이름: " + name);
		Bukkit.broadcastMessage("게임 버전: " + version);
		Bukkit.broadcastMessage("개발자: gidonyou");
		Bukkit.broadcastMessage("블로그: https://blog.naver.com/gidonyou");
		Bukkit.broadcastMessage("유튜브: https://www.youtube.com/gidonyou");
		Bukkit.broadcastMessage("");
	}

	public static void specialThanks() {
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "[  도움을 주신분  ]");
		TitleManager.sendClickableText(ChatColor.GOLD + "[v1.1.0 실험쥐 협찬] " + ChatColor.RESET + "디스코드 조합대 (클릭)",
				"https://discord.gg/mpNKx4Q", "none");
		Bukkit.broadcastMessage(
				ChatColor.YELLOW + "NF3charcoaL --> " + ChatColor.RESET + "각종 문법 오류와 컨텐츠 밸런스 수정을 도와주셨습니다.");
		Bukkit.broadcastMessage(
				ChatColor.YELLOW + "[v1.1.0 테스터] " + ChatColor.RESET + "(초대) NF3charcoaL, J_arvi_S, 퓨리, 심심하면 밥묵자");
		Bukkit.broadcastMessage(ChatColor.YELLOW + "[v1.1.0 테스터] " + ChatColor.GRAY + "(참여) ");
		Bukkit.broadcastMessage(
				ChatColor.YELLOW + "[Beta3 테스터] " + ChatColor.RESET + "NF3charcoaL, J_arvi_S, TF2_ValS ");
		Bukkit.broadcastMessage("");
	}
}
