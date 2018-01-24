package com.gmail.gidonyouyt.gameAge.core;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_12_R1.PlayerConnection;

// Tutorial from: https://www.spigotmc.org/threads/send-titles-to-players-using-spigot-1-8-1-11-2.48819/
public class TitleManager {

	public static void displayTitle(Player player, String title, String titleColor, String subtitle,
			String subtitleColor) {

		PacketPlayOutTitle titlePacket = null;
		if (title == null) {
			title = "";
			titleColor = "";
		}
		titlePacket = new PacketPlayOutTitle(EnumTitleAction.TITLE, ChatSerializer
				.a("{\"text\":\"%s\",\"color\":\"%c\",\"bold\":true}".replace("%s", title).replace("%c", titleColor)),
				10, 40, 30);

		PacketPlayOutTitle subTitlePacket = null;
		if (subtitle == null) {
			subtitle = "";
			subtitleColor = "";
		}
		subTitlePacket = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE,
				ChatSerializer.a("{\"text\":\"%s\",\"color\":\"%c\",\"bold\":true}".replace("%s", subtitle)
						.replace("%c", subtitleColor)),
				10, 40, 30);

		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;

		if (titlePacket != null)
			connection.sendPacket(titlePacket);
		if (subTitlePacket != null)
			connection.sendPacket(subTitlePacket);
	}

	public static void displayTitle(String title, String titleColor, String subtitle, String subtitleColor) {
		for (Player player : Bukkit.getOnlinePlayers())
			displayTitle(player, title, titleColor, subtitle, subtitleColor);
	}

	public static void sendClickableText(Player player, String json) {
		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
		PacketPlayOutChat packet = new PacketPlayOutChat(ChatSerializer.a(json));
		connection.sendPacket(packet);
	}

	public static void sendClickableText(Player player, String text, String link, String color) {

		String json = "{\"text\":\"" + text + "\",\"color\":\"" + color
				+ "\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"" + link
				+ "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"í´ë¦­í•˜ì—¬ ì—´ê¸°\",\"color\":\"gold\"}]}}}";

		sendClickableText(player, json);
	}

	public static void sendClickableText(String text, String link, String color) {

		String json = "{\"text\":\"" + text + "\",\"color\":\"" + color
				+ "\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"" + link
				+ "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"í´ë¦­í•˜ì—¬ ì—´ê¸°\",\"color\":\"gold\"}]}}}";
		for (Player player : Bukkit.getOnlinePlayers())
			sendClickableText(player, json);
	}

	public static void countDown(Player player, int i) {
		if (i > 0)
			displayTitle(player, i + " ì´ˆ ë’¤ì— ê²Œìž„ì´ ì‹œìž‘ë©ë‹ˆë‹¤", "green", "ì¤€ë¹„í•˜ì„¸ìš”!", "orange");
		else if (i == 0)
			displayTitle(player, "Game START!", "blue", null, null);

		else
			displayTitle(player, "ì˜¤ë¥˜ ë°œìƒ", "red", "ê°œë°œìž: ì´ê±´ ì˜ˆì¸¡í•˜ì§€ ëª»í–ˆì–´", "dark_red");

	}

}
