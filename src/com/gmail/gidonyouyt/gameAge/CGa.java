package com.gmail.gidonyouyt.gameAge;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.gidonyouyt.gameAge.core.SendMessage;
import com.gmail.gidonyouyt.gameAge.events.PlayerInteract;

public class CGa implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if(arg3[0].equalsIgnoreCase("steal")) {
			if(!(arg0 instanceof Player))
				return true;
			Player player = (Player) arg0;
			if(arg3.length != 2)
				return false;
			Player target = Bukkit.getPlayer(arg3[1]);
			if(target == null)
				return false;
			
			// Check item inventory
			if (!player.getInventory().contains(SpecialItems.EARN_SECONDS.get())) {
				SendMessage.sendMessagePlayer(player, ChatColor.DARK_RED + "지정된 아이템이 없습니다.");
				return false;
			}
			
			HashMap<Player, Integer> pTimes = Sequence.getPlayerTime();
			int targetTime = pTimes.get(target);
			double immuneTime = GameSettings.STEAL_IMMUNE_TIME_SEC.value();
			if (targetTime <= immuneTime) {
				SendMessage.sendMessagePlayer(player,
						ChatColor.DARK_RED + "그대의 지목은 " + immuneTime + " 미만이기에 실패.");
			} else {
				pTimes.put(player, (int) (pTimes.get(player) + GameSettings.STEAL_TIME_SEC.value()));
				pTimes.put(target, (int) (pTimes.get(target) - GameSettings.STEAL_TIME_SEC.value()));
				SendMessage.sendMessagePlayer(player, ChatColor.BLUE + "그대의 지목에서 %t 초를 뺏었다"
						.replace("%t", String.valueOf(GameSettings.STEAL_TIME_SEC.value())));
				SendMessage.sendMessagePlayer(target,
						ChatColor.RED + "그대는 %s 한테  %t 초를 뺏겼다".replace("%s", player.getName())
								.replace("%t", String.valueOf(GameSettings.STEAL_TIME_SEC.value())));
				target.playSound(target.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 1, 1);
				PlayerInteract.removeItem(player);
			}
			
			return true;
		}
		return false;
	}

}
