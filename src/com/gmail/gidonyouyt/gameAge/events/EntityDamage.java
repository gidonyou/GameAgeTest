package com.gmail.gidonyouyt.gameAge.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.gmail.gidonyouyt.gameAge.GameSettings;
import com.gmail.gidonyouyt.gameAge.Sequence;
import com.gmail.gidonyouyt.gameAge.core.GameStatus;
import com.gmail.gidonyouyt.gameAge.core.SendMessage;

public class EntityDamage implements Listener {

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		if (GameStatus.getStatus() != GameStatus.RUNNING)
			return;
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();

			if (event.getCause() == DamageCause.ENTITY_ATTACK)
				return;

			if (event.getDamage() >= player.getHealth()) {
				event.setDamage(0);
				Sequence.out(player);

				for (Player p : Bukkit.getOnlinePlayers()) {
					if (!Sequence.getPlayerPlaying().contains(p))
						SendMessage.sendMessagePlayer(player,
								player.getName() + "님의 죽은이유 -- " + event.getCause().name());
				}
			}
		}
	}

	@EventHandler
	public void onEntityDamageEntity(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof Player && event.getEntity() instanceof Player))
			return;

		if (GameStatus.getStatus() != GameStatus.RUNNING)
			return;

		Player target = (Player) event.getEntity();
		Player damager = (Player) event.getDamager();

		if (!(Sequence.getPlayerPlaying().contains(target) && Sequence.getPlayerPlaying().contains(damager)))
			return;

		// Check Rank
		if (Sequence.getRank(damager) == 1 && Sequence.getRank(target) == Sequence.getPlayerPlaying().size()) {
			if (GameSettings.FIRST_LAST_DAMAGE_MULT.value() < 0)
				return;
			double newDamage = event.getDamage() * GameSettings.FIRST_LAST_DAMAGE_MULT.value();
			if (newDamage >= target.getHealth()) {
				pvpTime(event, target, damager);
			} else {
				event.setDamage(newDamage);
			}
			return;
		}
		if (Sequence.getRank(damager) <= Sequence.getRank(target)) {
			// Inverse Attack
			event.setCancelled(true);
			double newDamage = event.getDamage() * 2;
			if (newDamage >= damager.getHealth()) {
				pvpTime(event, damager, target);
			} else {
				damager.damage(newDamage, target);
			}
			return;
		}

		// Normal Attack
		if (event.getDamage() >= target.getHealth())
			pvpTime(event, target, damager);

	}

	public void pvpTime(EntityDamageByEntityEvent event, Player killed, Player killer) {
		event.setCancelled(true);

		Sequence.out(killed);

		SendMessage.sendMessagePlayer(killer, killed.getName() + " 님을 죽이셨습니다.");
		int leftOver = Sequence.getPlayerTime().get(killed) / 2;
		int newTime = Sequence.getPlayerTime().get(killer) + leftOver;
		Sequence.updateTime(killer, newTime);
		SendMessage.sendMessagePlayer(killer,
				"당신은 %s 초를 받으셨습니다.".replace("%s", ChatColor.GREEN + String.valueOf(leftOver) + ChatColor.RESET));

		for (Player p : Bukkit.getOnlinePlayers()) {
			if (!Sequence.getPlayerPlaying().contains(p))
				SendMessage.sendMessagePlayer(p,
						"%s 님이 %t 을 아웃시켰습니다.".replace("%s", killer.getName()).replace("%t", killed.getName()));
		}
	}

}
