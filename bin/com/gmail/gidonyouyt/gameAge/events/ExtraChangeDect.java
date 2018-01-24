package com.gmail.gidonyouyt.gameAge.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class ExtraChangeDect implements Listener {

	@EventHandler
	public void onHungerDeplete(FoodLevelChangeEvent event) {
		if(!(event.getEntity() instanceof Player))
			return;
		event.setCancelled(true);
		event.setFoodLevel(20);
	}
	
	@EventHandler
	public void onWeatherChangeEvent (WeatherChangeEvent event) {
		event.setCancelled(true);
//		event.getWorld().setStorm(false);
	}
	
	@EventHandler
	public void onCreatureSpawn (CreatureSpawnEvent event) {
		if(event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM)
			event.setCancelled(true);
	}
	
}
