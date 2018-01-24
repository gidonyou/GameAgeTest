package com.gmail.gidonyouyt.gameAge.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;

import com.gmail.gidonyouyt.gameAge.core.GameStatus;

public class PlayerPreLogin implements Listener {
	
	@EventHandler
	public void onPlayerPreLogin (AsyncPlayerPreLoginEvent event) {
		if (GameStatus.getStatus() != GameStatus.RUNNING)
			return;
		event.disallow(Result.KICK_OTHER, "게임이 진행중입니다.");		
	}

}
