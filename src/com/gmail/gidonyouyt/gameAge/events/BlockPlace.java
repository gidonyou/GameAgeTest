package com.gmail.gidonyouyt.gameAge.events;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.gmail.gidonyouyt.gameAge.Sequence;
import com.gmail.gidonyouyt.gameAge.core.GameStatus;
import com.gmail.gidonyouyt.gameAge.core.SendMessage;

public class BlockPlace implements Listener {

	// private static ArrayList<Block> blockDB = new ArrayList<Block>();

	@EventHandler
	public void blockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if (player.getGameMode() != GameMode.CREATIVE && event.getBlock().getType() == Material.BEDROCK) {
			event.setCancelled(true);
			SendMessage.sendMessagePlayer(player, ChatColor.DARK_RED + "í¬ë¦¬ì—ì´í‹°ë¸Œê°€ ì•„ë‹ˆë©´ ë² ë“œë½ì„ ì„¤ì¹˜í•˜ì‹¤ìˆ˜ ì—†ìŠµë‹ˆë‹¤.");
		}

		if (!Sequence.getPlayerPlaying().contains(player))
			return;
		if ((GameStatus.getStatus() == GameStatus.RUNNING || GameStatus.getStatus() == GameStatus.COUNT_DOWN)
				&& event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
			event.setCancelled(true);
			SendMessage.sendMessagePlayer(player, ChatColor.DARK_RED + "ê²Œìž„ ì¤‘ì—ëŠ” ë¸”ëŸ­ì„ ì„¤ì¹˜í•˜ì‹¤ìˆ˜ ì—†ìŠµë‹ˆë‹¤.");

			// // Advanced Gameplay
			// SendMessage.sendMessagePlayer(player, ChatColor.DARK_RED + "ê²Œìž„ì¤‘ì—ëŠ” ë¸”ëŸ­ì„ ë¶€ìˆ ë•Œë§ˆë‹¤
			// ë°˜í”¼ì”© ê¹Žìž…ë‹ˆë‹¤.");
			// blockDB.add(event.getBlock());
		}
	}

}
