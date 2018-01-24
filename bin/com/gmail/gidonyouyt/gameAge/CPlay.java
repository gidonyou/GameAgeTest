package com.gmail.gidonyouyt.gameAge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.ShulkerBox;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;

import com.gmail.gidonyouyt.gameAge.core.BookManager;
import com.gmail.gidonyouyt.gameAge.core.SendMessage;

public class CPlay implements CommandExecutor {

	GameAge plugin;

	public CPlay(GameAge plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		if (!(sender instanceof Player))
			return false;
		Player player = (Player) sender;

		if (!player.isOp() && player.getGameMode() == GameMode.SURVIVAL)
			return false;

		if (arg3.length == 0) {
			help(player);
			return true;

		} else {
			String arg = arg3[0];
			if (arg.equalsIgnoreCase("g")) {
				if (!player.isOp()) {
					player.sendMessage(ChatColor.DARK_RED + "OP 만 이용할 수 있는 명령어입니다.");
					return true;
				}
				PlayerInventory inv = player.getInventory();

				// inv.clear();
				// inv.addItem(SpecialItems.getItemMateral(SpecialItems.PLAYER_TIME_LIST));
				inv.addItem(SpecialItems.getAllItems());

				player.sendMessage("Given");
				return true;

			} else if (arg3[0].equalsIgnoreCase("r")) {
				if (!player.isOp()) {
					player.sendMessage(ChatColor.DARK_RED + "OP 만 이용할 수 있는 명령어입니다.");
					return true;
				}
				sender.sendMessage("상자 다시배분: " + ItemDistribute.DistributeItems());
				return true;
				// }else if (arg3[0].equalsIgnoreCase("d")){
				// ItemDistribute.clearAllBlock();
				// return true;

			} else if (arg.equalsIgnoreCase("i")) {
				if (!player.isOp()) {
					player.sendMessage(ChatColor.DARK_RED + "OP 만 이용할 수 있는 명령어입니다.");
					return true;
				}
				if (arg3.length != 2) {
					help(player);
					return true;

				}
				Player target = Bukkit.getPlayer(arg3[1]);
				if (target == null) {
					SendMessage.sendMessagePlayer(player, "플레이어 찾을수 없음");
					return true;

				}
				Inventory inv = target.getInventory();
				player.openInventory(inv);
				return true;

			} else if (arg3[0].equalsIgnoreCase("b")) {
				if (!player.isOp()) {
					player.sendMessage(ChatColor.DARK_RED + "OP 만 이용할 수 있는 명령어입니다.");
					return true;
				}
				BookManager.openBook(BookManager.book("", "", "테스트 책"), player);
				return true;
			} else if (arg3[0].equalsIgnoreCase("c")) {
				HashMap<String, Integer> result = new HashMap<String, Integer>();
				HashSet<Location> locations = ItemDistribute.getAllLocation();
				int total = 0;
				for (Location loc : locations) {
					Block block = loc.getBlock();
					if (block instanceof ShulkerBox) {
						result.put("애러발생", 33);
					}
					Inventory inv = ((ShulkerBox) block.getState()).getInventory();
					String name = "[없음]";
					if (inv.getItem(13) != null)
						name = inv.getItem(13).getItemMeta().getDisplayName();
					if (!result.containsKey(name))
						result.put(name, 1);
					else
						result.put(name, result.get(name) + 1);
					total++;
				}

				SendMessage.sendMessagePlayer(player, "");
				SendMessage.sendMessagePlayer(player, "");
				SendMessage.sendMessagePlayer(player, "Total Blocks: " + total);

				@SuppressWarnings("unchecked")
				Map<String, Integer> map = sortByValues(result);
				for (String s : map.keySet())
					SendMessage.sendMessagePlayer(player, s + ChatColor.WHITE + "  --  " + map.get(s));
				return true;
			} else if (arg3[0].equalsIgnoreCase("bl")) {
				SendMessage.sendMessagePlayer(player,
						"Min - %x, %y, %z".replace("%x", String.valueOf(GameSettings.getRange()[0].getBlockX()))
								.replace("%y", String.valueOf(GameSettings.getRange()[0].getBlockY()))
								.replace("%z", String.valueOf(GameSettings.getRange()[0].getBlockZ())));
				SendMessage.sendMessagePlayer(player,
						"Max - %x, %y, %z".replace("%x", String.valueOf(GameSettings.getRange()[1].getBlockX()))
								.replace("%y", String.valueOf(GameSettings.getRange()[1].getBlockY()))
								.replace("%z", String.valueOf(GameSettings.getRange()[1].getBlockZ())));
				return true;
			} else if (arg3[0].equalsIgnoreCase("f")) {
				if (!player.isOp()) {
					player.sendMessage(ChatColor.DARK_RED + "OP 만 이용할 수 있는 명령어입니다.");
					return true;
				}
				SendMessage.sendMessagePlayer(player, "-- Force Limit border --");
				GameBorder.autoLimit();
				return true;
			} else if (arg3[0].equalsIgnoreCase("l")) {
				for (int i = 0; i < Sequence.getRankList().length; i++) {
					Set<Player> NextPlayers = Sequence.getKeysByValue(Sequence.getRankList()[i]);

					if (NextPlayers.isEmpty()) {
						SendMessage.sendMessagePlayer(player,
								ChatColor.RESET + String.valueOf(i) + ". " + ChatColor.RED + "없음");
					} else {
						for (Player p : NextPlayers) {
							if (p == null) {
								SendMessage.sendMessagePlayer(player,
										ChatColor.RESET + String.valueOf(i) + ". " + ChatColor.RED + "애러");
								continue;
							}
							SendMessage.sendMessagePlayer(player,
									ChatColor.RESET + String.valueOf(i) + ". " + p.getName()
											+ " (%s)".replace("%s", String.valueOf(p.getHealth())) + "  "
											+ Sequence.toMinute(Sequence.getPlayerTime().get(p)));
						}
					}
				}
				return true;
			} else if (arg3[0].equalsIgnoreCase("s")) {
				SendMessage.sendMessagePlayer(player, "===== 게임 설정 =====");
				for (GameSettings settings : GameSettings.values())
					SendMessage.sendMessagePlayer(player, settings.desc() + " - " + settings.value());
				return true;

			} else if (arg3[0].equalsIgnoreCase("rl")) {
				if (!player.isOp()) {
					player.sendMessage(ChatColor.DARK_RED + "OP 만 이용할 수 있는 명령어입니다.");
					return true;
				}
				plugin.yml.loadConfiguration();
				player.sendMessage(ChatColor.GREEN + "설정 파일을 다시 불러왔습니다.");

				return true;

			} else if (arg3[0].equalsIgnoreCase("u")) {
				if (!player.isOp()) {
					player.sendMessage(ChatColor.DARK_RED + "OP 만 이용할 수 있는 명령어입니다.");
					return true;
				}
				if (arg3.length >= 2) {
					try {
						GameSettings setting = GameSettings.valueOf(arg3[1]);
						if (arg3.length == 2) {
							SendMessage.sendMessagePlayer(player, setting.name() + " : " + setting.value());
							return true;
						} else if (arg3.length == 3) {
							try {
								double d = Double.valueOf(arg3[2]);
								plugin.yml.updateSetting(setting, d);
								SendMessage.sendMessagePlayer(player, ChatColor.GREEN + "설정 업데이트 성공");
							} catch (NumberFormatException e) {
								SendMessage.sendMessagePlayer(player, ChatColor.DARK_RED + "잘못된 숫자 입력");
							}
						}

						return true;
					} catch (IllegalArgumentException e) {
						SendMessage.sendMessagePlayer(player, ChatColor.DARK_RED + "해당되는 설정 찾을수 없음");

					} catch (Exception e) {
						SendMessage.sendMessagePlayer(player, ChatColor.DARK_RED + "게임 설정 업데이트중 오류 발생");
						e.printStackTrace();
					}
				}
				ArrayList<String> list = new ArrayList<String>();
				for (GameSettings settings : GameSettings.values()) {
					list.add(settings.name());
				}
				SendMessage.sendMessagePlayer(player, "잘못된 이용방식: /play u [설정 이름] [숫자]");
				SendMessage.sendMessagePlayer(player, String.join(", ", list));
				return true;
			}
		}
		SendMessage.sendMessagePlayer(player, ChatColor.RED + "알수없는 커멘드");
		return false;
	}

	private void help(Player player) {
		// TODO Auto-generated method stub
		SendMessage.sendMessagePlayer(player, "가능한 커맨드 -- 개발 전용");
		SendMessage.sendMessagePlayer(player, "* - OP 전용");
		SendMessage.sendMessagePlayer(player, "");
		SendMessage.sendMessagePlayer(player, "g* - Get All Items");
		SendMessage.sendMessagePlayer(player, "r* - Re Dispute Items");
		SendMessage.sendMessagePlayer(player, "i {name}* - Open Player's Inventory");
		SendMessage.sendMessagePlayer(player, "b* - Open Beta Book");
		SendMessage.sendMessagePlayer(player, "c - CountChestLocation");
		SendMessage.sendMessagePlayer(player, "bl - boundry locations");
		SendMessage.sendMessagePlayer(player, "f* - force limit border");
		SendMessage.sendMessagePlayer(player, "l - get time and list");
		SendMessage.sendMessagePlayer(player, "s - View Settings");
		SendMessage.sendMessagePlayer(player, "rl* -Reload config.yml");
		SendMessage.sendMessagePlayer(player, "u* {Name} - Get Settings Value");
		SendMessage.sendMessagePlayer(player, "u* {Name} {Value} - Update Settings Value");
	}

	@SuppressWarnings("all")
	private static HashMap sortByValues(HashMap map) {
		List list = new LinkedList(map.entrySet());
		// Defined Custom Comparator here
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o2)).getValue()).compareTo(((Map.Entry) (o1)).getValue());
			}
		});

		// Here I am copying the sorted list in HashMap
		// using LinkedHashMap to preserve the insertion order
		HashMap sortedHashMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedHashMap.put(entry.getKey(), entry.getValue());
		}
		return sortedHashMap;
	}

}
