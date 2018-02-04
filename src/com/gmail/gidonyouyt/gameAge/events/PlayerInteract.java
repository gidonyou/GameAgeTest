package com.gmail.gidonyouyt.gameAge.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.gmail.gidonyouyt.gameAge.GameAge;
import com.gmail.gidonyouyt.gameAge.GameSettings;
import com.gmail.gidonyouyt.gameAge.Sequence;
import com.gmail.gidonyouyt.gameAge.SpecialItems;
import com.gmail.gidonyouyt.gameAge.core.BookManager;
import com.gmail.gidonyouyt.gameAge.core.GameStatus;
import com.gmail.gidonyouyt.gameAge.core.SendMessage;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;

public class PlayerInteract implements Listener {
	private GameAge plugin;

	private static HashMap<Player, Integer> healCool = new HashMap<Player, Integer>();

	public PlayerInteract(GameAge plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.getItem() == null)
			return;

		if (GameStatus.getStatus() == GameStatus.COUNT_DOWN) {
			event.setCancelled(true);
			SendMessage.sendMessagePlayer(player, "게임 시작전에는 사용하실 수 없습니다.");
			return;
		}

		ItemStack item = new ItemStack(event.getItem());
		item.setAmount(1);

		// Check Action
		if (!(event.getAction() == Action.RIGHT_CLICK_AIR)) // || event.getAction() == Action.RIGHT_CLICK_BLOCK
			return;

		// Check List && Written Book Trigger
		if (!SpecialItems.getAllItemsList().contains(item)) {
			if (item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName()
					.equals(SpecialItems.LOCATION_FINDER.get().getItemMeta().getDisplayName())) {
				double left0 = GameSettings.COMPASS_DURABILITY.value();
				if (GameStatus.getStatus() != GameStatus.RUNNING) {
					SendMessage.sendMessagePlayer(player, "본 아이템은 게임중에만 이용하실수 있습니다.");
					return;
				}
				if (left0 <= 0) {
					SendMessage.sendMessagePlayer(player, ChatColor.RED + "본 나침판은 수명이 다했습니다.");
				} else {

					int playerRank = Sequence.getRank(player);
					Set<Player> NextPlayers;

					if (playerRank <= 1) {
						SendMessage.sendMessagePlayer(player, ChatColor.RED + "당신보다 시간이 많이 남은사람이 없습니다. 꼴등을 찾습니다.");
						NextPlayers = Sequence
								.getKeysByValue(Sequence.getRankList()[Sequence.getPlayerPlaying().size() - 1]);
					} else {
						NextPlayers = Sequence.getKeysByValue(Sequence.getRankList()[playerRank - 2]);
					}
					if (NextPlayers.isEmpty()) {
						SendMessage.sendMessagePlayer(player, ChatColor.DARK_RED + "애러: 플레이어 찾을수 없음");
					} else {
						Player np = (Player) NextPlayers.toArray()[0];
						if (np == null) {
							SendMessage.sendMessagePlayer(player, ChatColor.DARK_RED + "애러: NULL 플레이어 찾을수 없음");
						} else {
							SendMessage.sendMessagePlayer(np, ChatColor.GOLD + "당신은 추적당하고 있습니다.");
							@SuppressWarnings("unused")
							BukkitTask runnable = new BukkitRunnable() {
								int tick = 0;
								double left = left0;
								ItemMeta im = item.getItemMeta();

								@Override
								public void run() {
									if (tick == 20) {
										tick = 0;
										left--;
									}
									tick++;
									if (left <= 0) {
										im.setDisplayName(ChatColor.RED + "다쓴 나침판");
										cancel();
									} else if (!Sequence.getPlayerPlaying().contains(np)) {
										im.setDisplayName(ChatColor.RED + "지목된 플레이어 찾을수 없음");
										cancel();
									} else {
										player.setCompassTarget(np.getLocation());
										Location npLoc = np.getLocation().clone();
										npLoc.setY(player.getLocation().getY());
										im.setDisplayName(String
												.valueOf(Math.round(player.getLocation().distance(npLoc) * 10) / 10.0));
										ArrayList<String> lores = new ArrayList<String>();
										lores.add(ChatColor.GREEN + "남은시간 %s 초".replace("%s",
												ChatColor.BLUE + String.valueOf(left) + ChatColor.GREEN));
										im.setLore(lores);
										if (tick == 0)
											np.playSound(np.getLocation(), Sound.BLOCK_DISPENSER_FAIL, 10, 1);
									}
									event.getItem().setItemMeta(im);
								}
							}.runTaskTimer(plugin, 1L, 1L);
						}
					}

				}
			}
			return;
		}

		// Check Weapon
		if (item.getType() == Material.WOOD_SWORD || item.getType() == Material.STONE_SWORD
				|| item.getType() == Material.IRON_SWORD || item.getType() == Material.DIAMOND_SWORD
				|| item.getType() == Material.BOOK_AND_QUILL || item.getType() == Material.ENDER_PEARL)
			return;

		event.setCancelled(true);

		if (GameStatus.getStatus() != GameStatus.RUNNING) {
			SendMessage.sendMessagePlayer(player, "본 아이템은 게임중에만 이용하실수 있습니다.");
			return;
		}

		if (!Sequence.getPlayerPlaying().contains(player)) {
			SendMessage.sendMessagePlayer(player, "본 아이템은 게임참가자만 이용하실수 있습니다.");
			return;
		}

		// Item Effect
		if (item.equals(SpecialItems.PLAYER_TIME_ABOVE.get())) {
			ArrayList<String> entry = new ArrayList<String>();
			int playerRank = Sequence.getRank(player);

			entry.add("당신의 이름은: " + player.getName());
			entry.add("당신의 랭크는: " + playerRank);
			entry.add("");
			entry.add("다음으로 랭크 높은사람(들)");

			if (playerRank <= 1) {
				Set<Player> NextPlayers = Sequence
						.getKeysByValue(Sequence.getRankList()[Sequence.getPlayerPlaying().size() - 1]);
				if (NextPlayers.isEmpty()) {
					entry.add(ChatColor.RED + "없음");
				} else {
					for (Player p : NextPlayers) {
						if (p == null) {
							entry.add(ChatColor.RED + "애러");
							continue;
						}
						entry.add(p.getName() + " (꼴등)");
					}
				}
			} else {
				Set<Player> NextPlayers = Sequence.getKeysByValue(Sequence.getRankList()[playerRank - 2]);
				if (NextPlayers.isEmpty()) {
					entry.add(ChatColor.RED + "없음");
				} else {
					for (Player p : NextPlayers) {
						if (p == null) {
							entry.add(ChatColor.RED + "애러");
							continue;
						}
						entry.add(p.getName());
					}
				}
			}
			openBook(player, entry);
			removeItem(event);

		} else if (item.equals(SpecialItems.PLAYER_TIME_BELOW.get())) {
			ArrayList<String> entry = new ArrayList<String>();
			int playerRank = Sequence.getRank(player);

			entry.add("당신의 이름은: " + player.getName());
			entry.add("당신의 랭크는: " + playerRank);
			entry.add("");
			entry.add("다음으로 랭크 낮은사람(들)");

			if (playerRank >= Sequence.getRankList().length) {
				Set<Player> NextPlayers = Sequence.getKeysByValue(Sequence.getRankList()[0]);
				if (NextPlayers.isEmpty()) {
					entry.add(ChatColor.RED + "없음");
				} else {
					for (Player p : NextPlayers) {
						if (p == null) {
							entry.add(ChatColor.RED + "애러");
							continue;
						}
						entry.add(p.getName() + " (1등)");
					}
				}
			} else {
				Set<Player> NextPlayers = Sequence.getKeysByValue(Sequence.getRankList()[playerRank]);
				if (NextPlayers.isEmpty()) {
					entry.add(ChatColor.RED + "없음");
				} else {
					for (Player p : NextPlayers) {
						if (p == null) {
							entry.add(ChatColor.RED + "애러");
							continue;
						}
						entry.add(p.getName());
					}
				}
			}
			openBook(player, entry);
			removeItem(event);
		} else if (item.equals(SpecialItems.PLAYER_TIME_LIST.get())) {
			ArrayList<String> entry = new ArrayList<String>();
			int playerRank = Sequence.getRank(player);

			entry.add("당신의 이름은: " + player.getName());
			entry.add("당신의 랭크는: " + playerRank);
			entry.add("");
			entry.add("모든 사람들의 랭크");

			for (int i = 0; i < Sequence.getRankList().length; i++) {
				Set<Player> NextPlayers = Sequence.getKeysByValue(Sequence.getRankList()[i]);

				if (NextPlayers.isEmpty()) {
					entry.add(ChatColor.RESET + String.valueOf(i + 1) + ". " + ChatColor.RED + "없음");
				} else {
					for (Player p : NextPlayers) {
						if (p == null) {
							entry.add(ChatColor.RESET + String.valueOf(i + 1) + ". " + ChatColor.RED + "애러");
							continue;
						}
						entry.add(ChatColor.RESET + String.valueOf(i + 1) + ". " + p.getName());
					}
				}
			}

			openBook(player, entry);
			removeItem(event);

		} else if (item.equals(SpecialItems.FIRST_AID.get())) {
			if (healCool.containsKey(player)) {
				SendMessage.sendMessagePlayer(player,
						ChatColor.YELLOW + String.valueOf(healCool.get(player)) + ChatColor.GOLD + "초 뒤에 이용 가능.");
				return;
			}
			healCool.put(player, 6);
			if (player.getHealth() > 10)
				player.setHealth(20);
			else
				player.setHealth(player.getHealth() + 8);
			SendMessage.sendMessagePlayer(player, "당신은 4 하트 만큼 치료받으셨습니다.");
			removeItem(event);

		} else if (item.equals(SpecialItems.BANDAGE.get())) {
			if (healCool.containsKey(player)) {
				SendMessage.sendMessagePlayer(player,
						ChatColor.YELLOW + String.valueOf(healCool.get(player)) + ChatColor.GOLD + "초 뒤에 이용 가능.");
				return;
			}
			healCool.put(player, 3);
			if (player.getHealth() > 16)
				player.setHealth(20);
			else
				player.setHealth(player.getHealth() + 3);
			SendMessage.sendMessagePlayer(player, "당신은 1.5 하트 만큼 치료받으셨습니다.");
			removeItem(event);

		} else if (item.equals(SpecialItems.EARN_SECONDS.get())) {
			HashMap<Player, Integer> pTimes = Sequence.getPlayerTime();
			pTimes.put(player, (int) (pTimes.get(player) + GameSettings.EARN_SECOUNDS_TIME.value()));
			SendMessage.sendMessagePlayer(player,
					String.valueOf(GameSettings.EARN_SECOUNDS_TIME.value()) + "초를 받으셨습니다.");
			player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
			removeItem(event);

		} else if (item.equals(SpecialItems.INVISIBILITY_WATCH.get())) {
			player.addPotionEffect(
					new PotionEffect(PotionEffectType.INVISIBILITY, 20 * (int) GameSettings.IS_INVISIBILITY.value(), 0),
					false);

			SendMessage.sendMessagePlayer(player, String.valueOf(GameSettings.IS_INVISIBILITY.value()) + "초동안 투명해집니다.");
			removeItem(event);
		} else if (item.equals(SpecialItems.SCOOP.get())) {
			int random = (int) Math.floor(Math.random() * Sequence.getPlayerPlaying().size());
			List<Player> list = new ArrayList<Player>(Sequence.getPlayerPlaying());
			Player luckyPlayer = list.get(random);
			SendMessage.broadcastMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "랜덤 1인 순위와 시간이 공개됩니다:");
			SendMessage
					.broadcastMessage(ChatColor.AQUA + luckyPlayer.getName() + ChatColor.GREEN
							+ "님의 랭크는 %l위, 남은 시간은 %t 입니다."
									.replace("%l",
											ChatColor.GOLD + String.valueOf(Sequence.getRank(luckyPlayer))
													+ ChatColor.GREEN)
									.replace("%t",
											ChatColor.GOLD
													+ Sequence.toMinute(Sequence.getPlayerTime().get(luckyPlayer))
													+ ChatColor.GREEN));
			for (Player pl : Bukkit.getOnlinePlayers())
				pl.playSound(pl.getLocation(), Sound.ENTITY_SHEEP_AMBIENT, 1, 1);
			removeItem(event);

		} else if (item.equals(SpecialItems.STEAL_TARGET.get())) {

			TextComponent finalText = new TextComponent("데스노트! \n\n");

			for (Player ep : Sequence.getPlayerPlaying()) {
				TextComponent text = new TextComponent("오류");
				if (ep == null) {
					text = new TextComponent("없음\n");
					text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
							new ComponentBuilder(ChatColor.RED + "없음" + "에서 시간을 뺏습니다.").create()));
				} else {
					text = new TextComponent(ep.getName() + "\n");
					text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/ga steal " + ep.getName()));
					text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
							new ComponentBuilder(ChatColor.YELLOW + ep.getName() + ChatColor.GOLD + "에서 시간을 뺏습니다. (좌클릭)")
									.create()));
				}
				finalText.addExtra(text);
			}
			IChatBaseComponent icb = ChatSerializer.a(ComponentSerializer.toString(finalText));

			ItemStack book = BookManager.book("Test", "Test", icb);
			BookManager.openBook(book, player);

		}
	}

	private void openBook(Player player, ArrayList<String> entry) {
		BookManager.openBook(BookManager.book("", "", String.join("\n", entry)), player);
	}
	
	public static void removeItem(Player player) {

			ItemStack item = player.getInventory().getItemInMainHand();
			if (item.getAmount() == 1)
				player.getInventory().setItemInMainHand(null);
			else if (item.getAmount() > 1) {
				item.setAmount(item.getAmount() - 1);
				player.getInventory().setItemInMainHand(item);
			}
		
	}

	private void removeItem(PlayerInteractEvent event) {
		Player player = event.getPlayer();

		if (event.getHand() == EquipmentSlot.HAND) {
			ItemStack item = player.getInventory().getItemInMainHand();
			if (item.getAmount() == 1)
				player.getInventory().setItemInMainHand(null);
			else if (item.getAmount() > 1) {
				item.setAmount(item.getAmount() - 1);
				player.getInventory().setItemInMainHand(item);
			}
		} else if (event.getHand() == EquipmentSlot.OFF_HAND) {
			ItemStack item = player.getInventory().getItemInOffHand();
			if (item.getAmount() == 1)
				player.getInventory().setItemInOffHand(null);
			else if (item.getAmount() > 1) {
				item.setAmount(item.getAmount() - 1);
				player.getInventory().setItemInOffHand(item);
			}
		}
	}

	public static void clear() {
		// TODO Auto-generated method stub
		healCool.clear();
	}

	public static void update() {
		// Update Cool

		for (Player p : healCool.keySet()) {
			healCool.put(p, healCool.get(p) - 1);

			if (healCool.get(p) <= 0)
				healCool.remove(p);
		}

	}

}
