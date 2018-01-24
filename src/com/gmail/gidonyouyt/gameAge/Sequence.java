package com.gmail.gidonyouyt.gameAge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.gmail.gidonyouyt.gameAge.core.GameStatus;
import com.gmail.gidonyouyt.gameAge.core.SendMessage;
import com.gmail.gidonyouyt.gameAge.core.TitleManager;
import com.gmail.gidonyouyt.gameAge.events.BlockBreak;
import com.gmail.gidonyouyt.gameAge.events.PlayerInteract;

public class Sequence {

	private static int gameTime = 0;
	private static int countDown = -1;

	// Scoreboard Data for Players
	private static HashMap<Player, Objective> playerObjective = new HashMap<Player, Objective>();
	private static HashMap<Player, Integer> playerRemaingTime = new HashMap<Player, Integer>();
	private static HashSet<Player> playerPlaying = new HashSet<Player>();

	public static void start() {
		SendMessage.broadcastMessage(ChatColor.BLUE + "ì‹œí€€ìŠ¤ - ê²Œìž„ì´ ì‹œìž‘ë©ë‹ˆë‹¤!");
		cleanup();
		setup();

	}

	public static void stop() {
		SendMessage.broadcastMessage(ChatColor.RED + "ì‹œí€€ìŠ¤ - ê²Œìž„ì´ ì¤‘ì§€ë©ë‹ˆë‹¤!");
		GameStatus.setStatus(GameStatus.STOPPED);
		cleanup();

	}

	public static void setup() {
		SendMessage.sendMessageOP(ChatColor.GRAY + "ì‹œí€€ìŠ¤ - ì…‹ì—… ì§„í–‰ì¤‘");
		GameStatus.setStatus(GameStatus.ENGINE_SETUP);

		GameBorder.setborder();

		if (!ItemDistribute.DistributeItems())
			return;
		gameTime = 0;

		// Broadcaset Setting
		if (GameSettings.BROADCAST_CONFIG.value == 1)
			GameSettings.broadcastSettingToOp();

		// Remove Dropped Item
		for (Entity current : GameSettings.world.getEntities()) {
			if (current instanceof Item) {
				current.remove();
			}
			if (current instanceof Creature) {
				current.remove();
			}
		}
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.getGameMode() != GameMode.SURVIVAL)
				continue;

			// Distribute Item
			player.getInventory().clear();
			player.getInventory().addItem(SpecialItems.WOOD_SWORD.get());

			// SETUP REMAING TIME
			Logger logger = Bukkit.getServer().getLogger();
			logger.info(">> Setting up user : " + player.getName());

			int lifeTime = (int) Math
					.floor(Math.random() * (GameSettings.MAX_LIFE.seconds() - GameSettings.MIN_LIFE.seconds() + 1))
					+ GameSettings.MIN_LIFE.seconds();
			// playerRemaingTime.put(player, lifeTime);
			updateTime(player, lifeTime);

			// FINAL STEP : Add to Playing List
			playerPlaying.add(player);
		}

		if (GameSettings.ADD_NULL_PLAYER.value() == 1) {
			playerRemaingTime.put(null, 60);
			playerPlaying.add(null);
		}

		SendMessage.broadcastMessage(ChatColor.GREEN + "ê²Œìž„ ì¤€ë¹„ ì™„ë£Œ -- ì‹œìž‘!");
		GameSettings.gameInfo();

		countDown = (int) GameSettings.COUNTDOWN_TIME.value();
		GameStatus.setStatus(GameStatus.COUNT_DOWN);
	}

	public static void update() {
		// Count Down
		if (GameStatus.getStatus().equals(GameStatus.COUNT_DOWN)) {
			if (countDown == 0)
				GameStatus.setStatus(GameStatus.RUNNING);
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (countDown == 0) {
					player.playSound(player.getLocation(), Sound.BLOCK_NOTE_XYLOPHONE, 1, 3);
					
					// Give Regen
					player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,
							(int) Math.floor(20 * GameSettings.MERCY_TIME.value()), 3));
					player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,
							(int) Math.floor(20 * GameSettings.MERCY_TIME.value()), 3));
				} else
					player.playSound(player.getLocation(), Sound.BLOCK_NOTE_XYLOPHONE, 1, 1);
				TitleManager.countDown(player, countDown);
			}
			countDown--;
		}

		// Main Timer
		if (GameStatus.getStatus() != GameStatus.RUNNING)
			return;

		if (gameTime == 5)
			SendMessage.sendCreditInfo();

		if (gameTime == 15)
			SendMessage.specialThanks();

		if (playerPlaying.size() <= 1) {
			if (playerPlaying.isEmpty()) {
				SendMessage.broadcastMessage(ChatColor.RED + "ëˆ„ê°€ ì´ê²¼ëŠ”ì§€ ì‹ë³„ ë¶ˆê°€ëŠ¥");
			} else {
				Player winner = (Player) playerPlaying.toArray()[0];
				if (winner == null) {
					SendMessage.broadcastMessage(ChatColor.RED + "ì•Œìˆ˜ì—†ëŠ” ì´ìš©ìž ìš°ìŠ¹");
					TitleManager.displayTitle("ì•Œìˆ˜ì—†ëŠ” ì´ìš©ìž ìš°ìŠ¹", "gold", "ì´ê±´ ì˜¤ë¥˜ì•¼", "red");
				} else {
					SendMessage
							.broadcastMessage(ChatColor.BLUE + winner.getName() + ChatColor.RESET + " ë‹˜ ìš°ìŠ¹!!! ì¶•í•˜í•©ë‹ˆë‹¤!");
					TitleManager.displayTitle(ChatColor.BLUE + winner.getName() + ChatColor.RESET + " ë‹˜ ìš°ìŠ¹", "gold",
							"ë‚¨ì€ì‹œê°„: " + toMinute(playerRemaingTime.get(winner)), "gray");
				}
			}
			GameStatus.setStatus(GameStatus.FINSHED);
			SendMessage.broadcastMessage(ChatColor.GREEN + "ê²Œìž„ ë");
			return;
		}

		gameTime++;

		// Auto Barrier
		if (GameSettings.AUTO_BORDER.seconds() != 0)
			if (gameTime % GameSettings.AUTO_BORDER.seconds() == 0)
				GameBorder.autoLimit();

		ScoreboardManager manager = Bukkit.getScoreboardManager();
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (!(playerRemaingTime.containsKey(player) || playerPlaying.contains(player)))
				continue;

			int lifeTime = playerRemaingTime.get(player);

			// SCOREBOARD
			Scoreboard board = manager.getNewScoreboard();
			Objective obj = board.registerNewObjective(player.getName(), "dummy");
			obj.setDisplaySlot(DisplaySlot.SIDEBAR);
			obj.setDisplayName(player.getName());

			obj.getScore("").setScore(9);
			obj.getScore(ChatColor.GREEN + ChatColor.BOLD.toString() + "ëž­í¬").setScore(8);
			obj.getScore(getRank(player) + "/" + playerPlaying.size()).setScore(7);
			obj.getScore(" ").setScore(6);
			obj.getScore(ChatColor.RED + ChatColor.BOLD.toString() + "ë‚¨ì€ì‹œê°„").setScore(5);
			obj.getScore(toMinute(lifeTime) + " ").setScore(4);
			obj.getScore("  ").setScore(3);
			obj.getScore(ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + "ê²Œìž„ì‹œê°„").setScore(2);
			obj.getScore(toMinute(gameTime)).setScore(1);
			obj.getScore("   ").setScore(0);
			if (GameBorder.isInside(player))
				obj.getScore(ChatColor.GREEN + "ê²½ê¸°êµ¬ì—­ âœ“").setScore(-1);
			else
				obj.getScore(ChatColor.RED + "ê²½ê¸°êµ¬ì—­ âœ˜").setScore(-1);

			player.setScoreboard(board);

			// Life Time Manager && Play Soound

			if (lifeTime > 0) {
				if (!playerPlaying.contains(player))
					continue;
				board.resetScores(toMinute(lifeTime) + " ");
				obj.getScore(toMinute(--lifeTime) + " ").setScore(4);

				if (lifeTime < 60) {
					player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BELL, 10, 0.2f);
				}
			}
			playerRemaingTime.put(player, lifeTime);

			if (lifeTime == 0) {
				if (player.getGameMode() == GameMode.SURVIVAL)
					out(player);
			}
		}

	}

	public static void cleanup() {
		ItemDistribute.clearAllBlock();

		for (Player player : Bukkit.getOnlinePlayers()) {
			player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		}

		playerObjective.clear();
		playerRemaingTime.clear();
		playerPlaying.clear();

		GameBorder.clearBorder();
		BlockBreak.recoverAll();
		PlayerInteract.clear();
		
		countDown = -1;
	}

	public static void out(Player player) {
		if (GameStatus.getStatus() != GameStatus.RUNNING || !playerPlaying.contains(player))
			return;
		// playerObjective.remove(player);

		player.getWorld().createExplosion(player.getLocation(), 0);

		for (ItemStack is : player.getInventory().getContents()) {
			if (is != null)
				player.getWorld().dropItem(player.getLocation().add(0, 0.5, 0), is);
		}
		player.getInventory().clear();

		TitleManager.displayTitle(null, null, player.getName() + "ë‹˜ ì•„ì›ƒ", "red");
		SendMessage.broadcastMessage(ChatColor.RED + player.getName() + ChatColor.RESET + "ë‹˜ ì•„ì›ƒ");
		playerPlaying.remove(player);
		player.setGameMode(GameMode.SPECTATOR);

	}

	@SuppressWarnings("unchecked")
	public static HashSet<Player> getPlayerPlaying() {
		return (HashSet<Player>) playerPlaying.clone();
	}

	public static HashMap<Player, Integer> getPlayerTime() {
		return (HashMap<Player, Integer>) playerRemaingTime;
	}

	public static void updateTime(Player player, int time) {
		SendMessage.logConsole("Player Time Update:  %s new time %t".replace("%s", player.getName()).replace("%t",
				String.valueOf(time)));
		playerRemaingTime.put(player, time);
	}

	public static String toMinute(int second) {
		int remainder = second % 3600;
		int minutes = remainder / 60;
		int seconds = remainder % 60;
		String mins = (minutes < 10 ? "0" : "") + minutes;
		String secs = (seconds < 10 ? "0" : "") + seconds;
		String formattedTime = mins + "ë¶„ " + secs + "ì´ˆ";
		return formattedTime;
	}

	public static int getRank(Player player) {

		Object[] ul = getRankList();

		int playerTime = playerRemaingTime.get(player);

		for (int i = 0; i < ul.length; i++) {
			// Bukkit.broadcastMessage(i + " " + String.valueOf(ul[i]));
			if (playerTime == (int) ul[i])
				return i + 1;
		}
		// SendMessage.sendMessagePlayer(player, Color.RED + "ë‹¹ì‹ ì˜ ëž­í¬ë¥¼ ì°¾ì„ìˆ˜ ì—†ìŠµë‹ˆë‹¤.");
		// SendMessage.sendMessageOP(player.getName() + "ë‹˜ì˜ ëž­í¬ë¥¼ ì°¾ì„ìˆ˜ ì—†ëŠ” ì• ëŸ¬ê°€ ë°œìƒí–ˆìŠµë‹ˆë‹¤.");
		return 0;

	}

	public static Integer[] getRankList() {
		ArrayList<Integer> al = new ArrayList<Integer>();
		for (Player p : playerRemaingTime.keySet())
			if (playerPlaying.contains(p))
				al.add(playerRemaingTime.get(p));
		Integer[] ul = al.toArray(new Integer[0]);
		Arrays.sort(ul, Collections.reverseOrder()); // , Collections.reverseOrder()
		// for (Object i : ul) {
		// Bukkit.broadcastMessage(i.toString());
		// }
		return ul;
	}

	public static Set<Player> getKeysByValue(Object value) {
		HashMap<Player, Integer> map = getPlayerTime();
		Set<Player> keys = new HashSet<Player>();
		for (Entry<Player, Integer> entry : map.entrySet()) {
			if (Objects.equals(value, entry.getValue())) {
				keys.add(entry.getKey());
			}
		}
		return keys;
	}

}
