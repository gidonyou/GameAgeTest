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
		event.setQuitMessage(event.getPlayer().getName() + "Ã«â€¹ËœÃ¬ÂÂ´ Ã«Ââ€žÃ¬Â£Â¼Ã­â€¢ËœÃ¬â€¦Â¨Ã¬Å ÂµÃ«â€¹Ë†Ã«â€¹Â¤.");
		
		Sequence.out(event.getPlayer());
		
	}

}
