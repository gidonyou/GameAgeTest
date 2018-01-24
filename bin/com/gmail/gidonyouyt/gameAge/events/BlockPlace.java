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
			SendMessage.sendMessagePlayer(player, ChatColor.DARK_RED + "크리에이티브가 아니면 베드락을 설치하실수 없습니다.");
		}

		if (!Sequence.getPlayerPlaying().contains(player))
			return;
		if ((GameStatus.getStatus() == GameStatus.RUNNING || GameStatus.getStatus() == GameStatus.COUNT_DOWN)
				&& event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
			event.setCancelled(true);
			SendMessage.sendMessagePlayer(player, ChatColor.DARK_RED + "게임 중에는 블럭을 설치하실수 없습니다.");

			// // Advanced Gameplay
			// SendMessage.sendMessagePlayer(player, ChatColor.DARK_RED + "게임중에는 블럭을 부술때마다
			// 반피씩 깎입니다.");
			// blockDB.add(event.getBlock());
		}
	}

}
