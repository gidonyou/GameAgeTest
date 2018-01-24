package com.gmail.gidonyouyt.gameAge.events;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.bukkit.inventory.meta.BookMeta;
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
			SendMessage.sendMessagePlayer(player, "ê²Œìž„ ì‹œìž‘ì „ì—ëŠ” ì‚¬ìš©í•˜ì‹¤ ìˆ˜ ì—†ìŠµë‹ˆë‹¤.");
			return;
		}

		ItemStack item = new ItemStack(event.getItem());
		item.setAmount(1);

		// Check Action
		if (!(event.getAction() == Action.RIGHT_CLICK_AIR)) // || event.getAction() == Action.RIGHT_CLICK_BLOCK
			return;

		// Check List && Written Book Trigger
		if (!SpecialItems.getAllItemsList().contains(item)) {
			if (item.getType().equals(Material.WRITTEN_BOOK)) {
				BookMeta bm = (BookMeta) item.getItemMeta();
				String author = bm.getAuthor();
				Player playerAuthor = Bukkit.getPlayer(author);
				if (author == null || playerAuthor == null)
					return;
				if (GameStatus.getStatus() != GameStatus.RUNNING) {
					SendMessage.sendMessagePlayer(player, "ë³¸ ì•„ì´í…œì€ ê²Œìž„ì¤‘ì—ë§Œ ì´ìš©í•˜ì‹¤ìˆ˜ ìžˆìŠµë‹ˆë‹¤.");
					return;
				}
				if (!Sequence.getPlayerPlaying().contains(player)) {
					SendMessage.sendMessagePlayer(player, "ë³¸ ì•„ì´í…œì€ ê²Œìž„ì°¸ê°€ìžë§Œ ì´ìš©í•˜ì‹¤ìˆ˜ ìžˆìŠµë‹ˆë‹¤.");
					return;
				}
				if (player == playerAuthor) {
					Player target = Bukkit.getPlayer(bm.getPage(1));
					if (target == null) {
						SendMessage.sendMessagePlayer(playerAuthor, ChatColor.DARK_RED + "ê·¸ëŒ€ì˜ ì§€ëª©ì„ ì°¾ì„ìˆ˜ ì—†ë‹¤.");
					} else {
						if (!(Sequence.getPlayerPlaying().contains(target)
								|| Sequence.getPlayerTime().containsKey(target))) {
							SendMessage.sendMessagePlayer(playerAuthor, ChatColor.DARK_RED + "ê·¸ëŒ€ì˜ ì§€ëª©ì€ ê²Œìž„ì¤‘ì´ ì•„ë‹ˆë‹¤.");
						} else {
							HashMap<Player, Integer> pTimes = Sequence.getPlayerTime();
							int targetTime = pTimes.get(target);
							double immuneTime = GameSettings.STEAL_IMMUNE_TIME_SEC.value();
							if (targetTime <= immuneTime) {
								SendMessage.sendMessagePlayer(playerAuthor,
										ChatColor.DARK_RED + "ê·¸ëŒ€ì˜ ì§€ëª©ì€ " + immuneTime + " ë¯¸ë§Œì´ê¸°ì— ì‹¤íŒ¨.");
							} else {
								pTimes.put(player, (int) (pTimes.get(player) + GameSettings.STEAL_TIME_SEC.value()));
								pTimes.put(target, (int) (pTimes.get(target) - GameSettings.STEAL_TIME_SEC.value()));
								SendMessage.sendMessagePlayer(player, ChatColor.BLUE + "ê·¸ëŒ€ì˜ ì§€ëª©ì—ì„œ %t ì´ˆë¥¼ ëºì—ˆë‹¤"
										.replace("%t", String.valueOf(GameSettings.STEAL_TIME_SEC.value())));
								SendMessage.sendMessagePlayer(target,
										ChatColor.RED + "ê·¸ëŒ€ëŠ” %s í•œí…Œ  %t ì´ˆë¥¼ ëºê²¼ë‹¤".replace("%s", player.getName())
												.replace("%t", String.valueOf(GameSettings.STEAL_TIME_SEC.value())));
								target.playSound(target.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 1, 1);
							}
						}
					}
				} else {
					SendMessage.sendMessagePlayer(player, ChatColor.DARK_RED + "ê·¸ëŒ€ëŠ” ì´ì±…ì˜ ì£¼ì¸ì´ ì•„ë‹™ë‹ˆë‹¤.");
				}

				removeItem(event);
			} else if (item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName()
					.equals(SpecialItems.LOCATION_FINDER.get().getItemMeta().getDisplayName())) {
				double left0 = GameSettings.COMPASS_DURABILITY.value();
				if (GameStatus.getStatus() != GameStatus.RUNNING) {
					SendMessage.sendMessagePlayer(player, "ë³¸ ì•„ì´í…œì€ ê²Œìž„ì¤‘ì—ë§Œ ì´ìš©í•˜ì‹¤ìˆ˜ ìžˆìŠµë‹ˆë‹¤.");
					return;
				}
				if (left0 <= 0) {
					SendMessage.sendMessagePlayer(player, ChatColor.RED + "ë³¸ ë‚˜ì¹¨íŒì€ ìˆ˜ëª…ì´ ë‹¤í–ˆìŠµë‹ˆë‹¤.");
				} else {

					int playerRank = Sequence.getRank(player);
					if (playerRank <= 1) {
						SendMessage.sendMessagePlayer(player, ChatColor.RED + "ë‹¹ì‹ ë³´ë‹¤ ì‹œê°„ì´ ë§Žì´ ë‚¨ì€ì‚¬ëžŒì´ ì—†ìŠµë‹ˆë‹¤.");
					} else {
						Set<Player> NextPlayers = Sequence.getKeysByValue(Sequence.getRankList()[playerRank - 2]);
						if (NextPlayers.isEmpty()) {
							SendMessage.sendMessagePlayer(player, ChatColor.DARK_RED + "ì• ëŸ¬: ë‹¤ìŒí”Œë ˆì´ì–´ ì°¾ì„ìˆ˜ ì—†ìŒ");
						} else {
							Player np = (Player) NextPlayers.toArray()[0];
							if (np == null) {
								SendMessage.sendMessagePlayer(player, ChatColor.DARK_RED + "ì• ëŸ¬: NULL í”Œë ˆì´ì–´ ì°¾ì„ìˆ˜ ì—†ìŒ");
							} else {
								SendMessage.sendMessagePlayer(np, ChatColor.GOLD + "ë‹¹ì‹ ì€ ì¶”ì ë‹¹í•˜ê³  ìžˆìŠµë‹ˆë‹¤.");
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
											im.setDisplayName(ChatColor.RED + "ë‹¤ì“´ ë‚˜ì¹¨íŒ");
											cancel();
										} else if (!Sequence.getPlayerPlaying().contains(np)) {
											im.setDisplayName(ChatColor.RED + "ì§€ëª©ëœ í”Œë ˆì´ì–´ ì°¾ì„ìˆ˜ ì—†ìŒ");
											cancel();
										} else {
											player.setCompassTarget(np.getLocation());
											Location npLoc = np.getLocation().clone();
											npLoc.setY(player.getLocation().getY());
											im.setDisplayName(String.valueOf(
													Math.round(player.getLocation().distance(npLoc) * 10) / 10.0));
											ArrayList<String> lores = new ArrayList<String>();
											lores.add(ChatColor.GREEN + "ë‚¨ì€ì‹œê°„ %s ì´ˆ".replace("%s",
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
			SendMessage.sendMessagePlayer(player, "ë³¸ ì•„ì´í…œì€ ê²Œìž„ì¤‘ì—ë§Œ ì´ìš©í•˜ì‹¤ìˆ˜ ìžˆìŠµë‹ˆë‹¤.");
			return;
		}

		if (!Sequence.getPlayerPlaying().contains(player)) {
			SendMessage.sendMessagePlayer(player, "ë³¸ ì•„ì´í…œì€ ê²Œìž„ì°¸ê°€ìžë§Œ ì´ìš©í•˜ì‹¤ìˆ˜ ìžˆìŠµë‹ˆë‹¤.");
			return;
		}

		// Item Effect
		if (item.equals(SpecialItems.PLAYER_TIME_ABOVE.get())) {
			ArrayList<String> entry = new ArrayList<String>();
			int playerRank = Sequence.getRank(player);

			entry.add("ë‹¹ì‹ ì˜ ì´ë¦„ì€: " + player.getName());
			entry.add("ë‹¹ì‹ ì˜ ëž­í¬ëŠ”: " + playerRank);
			entry.add("");
			entry.add("ë‹¤ìŒìœ¼ë¡œ ëž­í¬ ë†’ì€ì‚¬ëžŒ(ë“¤)");

			if (playerRank <= 1) {
				entry.add(ChatColor.GOLD + "ë‹¹ì‹ ì´ ë„˜ë²„ 1");
				entry.add(ChatColor.DARK_RED + "ì´í…œ ë‚ ë¦°ê²¨");
			} else {
				Set<Player> NextPlayers = Sequence.getKeysByValue(Sequence.getRankList()[playerRank - 2]);
				if (NextPlayers.isEmpty()) {
					entry.add(ChatColor.RED + "ì—†ìŒ");
				} else {
					for (Player p : NextPlayers) {
						if (p == null) {
							entry.add(ChatColor.RED + "ì• ëŸ¬");
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

			entry.add("ë‹¹ì‹ ì˜ ì´ë¦„ì€: " + player.getName());
			entry.add("ë‹¹ì‹ ì˜ ëž­í¬ëŠ”: " + playerRank);
			entry.add("");
			entry.add("ë‹¤ìŒìœ¼ë¡œ ëž­í¬ ë‚®ì€ì‚¬ëžŒ(ë“¤)");

			if (playerRank >= Sequence.getRankList().length) {
				entry.add(ChatColor.GOLD + "ë‹¹ì‹ ì´ ê¼´ë“±");
				entry.add(ChatColor.DARK_RED + "ì´í…œ ë‚ ë¦°ê²¨");
			} else {
				Set<Player> NextPlayers = Sequence.getKeysByValue(Sequence.getRankList()[playerRank]);
				if (NextPlayers.isEmpty()) {
					entry.add(ChatColor.RED + "ì—†ìŒ");
				} else {
					for (Player p : NextPlayers) {
						if (p == null) {
							entry.add(ChatColor.RED + "ì• ëŸ¬");
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

			entry.add("ë‹¹ì‹ ì˜ ì´ë¦„ì€: " + player.getName());
			entry.add("ë‹¹ì‹ ì˜ ëž­í¬ëŠ”: " + playerRank);
			entry.add("");
			entry.add("ëª¨ë“  ì‚¬ëžŒë“¤ì˜ ëž­í¬");

			for (int i = 0; i < Sequence.getRankList().length; i++) {
				Set<Player> NextPlayers = Sequence.getKeysByValue(Sequence.getRankList()[i]);

				if (NextPlayers.isEmpty()) {
					entry.add(ChatColor.RESET + String.valueOf(i) + ". " + ChatColor.RED + "ì—†ìŒ");
				} else {
					for (Player p : NextPlayers) {
						if (p == null) {
							entry.add(ChatColor.RESET + String.valueOf(i) + ". " + ChatColor.RED + "ì• ëŸ¬");
							continue;
						}
						entry.add(ChatColor.RESET + String.valueOf(i) + ". " + p.getName());
					}
				}
			}

			openBook(player, entry);
			removeItem(event);

		} else if (item.equals(SpecialItems.FIRST_AID.get())) {
			if (healCool.containsKey(player)) {
				SendMessage.sendMessagePlayer(player,
						ChatColor.YELLOW + String.valueOf(healCool.get(player)) + ChatColor.GOLD + "ì´ˆ ë’¤ì— ì´ìš© ê°€ëŠ¥.");
				return;
			}
			healCool.put(player, 6);
			if (player.getHealth() > 10)
				player.setHealth(20);
			else
				player.setHealth(player.getHealth() + 8);
			SendMessage.sendMessagePlayer(player, "ë‹¹ì‹ ì€ 4 í•˜íŠ¸ ë§Œí¼ ì¹˜ë£Œë°›ìœ¼ì…¨ìŠµë‹ˆë‹¤.");
			removeItem(event);

		} else if (item.equals(SpecialItems.BANDAGE.get())) {
			if (healCool.containsKey(player)) {
				SendMessage.sendMessagePlayer(player,
						ChatColor.YELLOW + String.valueOf(healCool.get(player)) + ChatColor.GOLD + "ì´ˆ ë’¤ì— ì´ìš© ê°€ëŠ¥.");
				return;
			}
			healCool.put(player, 3);
			if (player.getHealth() > 16)
				player.setHealth(20);
			else
				player.setHealth(player.getHealth() + 3);
			SendMessage.sendMessagePlayer(player, "ë‹¹ì‹ ì€ 1.5 í•˜íŠ¸ ë§Œí¼ ì¹˜ë£Œë°›ìœ¼ì…¨ìŠµë‹ˆë‹¤.");
			removeItem(event);

		} else if (item.equals(SpecialItems.EARN_SECONDS.get())) {
			HashMap<Player, Integer> pTimes = Sequence.getPlayerTime();
			pTimes.put(player, (int) (pTimes.get(player) + GameSettings.EARN_SECOUNDS_TIME.value()));
			SendMessage.sendMessagePlayer(player,
					String.valueOf(GameSettings.EARN_SECOUNDS_TIME.value()) + "ì´ˆë¥¼ ë°›ìœ¼ì…¨ìŠµë‹ˆë‹¤.");
			player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
			removeItem(event);

		} else if (item.equals(SpecialItems.INVISIBILITY_WATCH.get())) {
			player.addPotionEffect(
					new PotionEffect(PotionEffectType.INVISIBILITY, 20 * (int) GameSettings.IS_INVISIBILITY.value(), 0),
					false);

			SendMessage.sendMessagePlayer(player,
					String.valueOf(GameSettings.IS_INVISIBILITY.value()) + "ì´ˆë™ì•ˆ íˆ¬ëª…í•´ì§‘ë‹ˆë‹¤.");
			removeItem(event);
		}
	}

	private void openBook(Player player, ArrayList<String> entry) {
		BookManager.openBook(BookManager.book("", "", String.join("\n", entry)), player);
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
