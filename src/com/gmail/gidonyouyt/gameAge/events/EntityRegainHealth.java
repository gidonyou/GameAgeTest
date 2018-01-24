package com.gmail.gidonyouyt.gameAge.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;

import com.gmail.gidonyouyt.gameAge.GameSettings;
import com.gmail.gidonyouyt.gameAge.Sequence;
import com.gmail.gidonyouyt.gameAge.core.GameStatus;

public class EntityRegainHealth implements Listener {

	@EventHandler
	public void onEntityRegainHealth(EntityRegainHealthEvent event) {
		if (!(event.getEntity() instanceof Player))
			return;

		Player player = (Player) event.getEntity();
		// player.sendMessage(event.getRegainReason().name());

		if (GameStatus.getStatus() != GameStatus.RUNNING)
			return;

		// Setting Check
		if (GameSettings.AUTO_REGEN.value() != 0)
			return;

		if (!Sequence.getPlayerPlaying().contains(player))
			return;

		if (event.getRegainReason() == RegainReason.SATIATED)
			event.setCancelled(true);

	}

}
