package com.gmail.gidonyouyt.gameAge.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.gmail.gidonyouyt.gameAge.Sequence;
import com.gmail.gidonyouyt.gameAge.core.GameStatus;

public class PlayerLeave implements Listener {
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		if (GameStatus.getStatus() != GameStatus.RUNNING)
			return;
		event.setQuitMessage(event.getPlayer().getName() + "님이 도주하셨습니다.");
		
		Sequence.out(event.getPlayer());
		
	}

}
