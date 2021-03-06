

import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
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

public class Sequence {

	private static int gameTime = 0;

	// Scoreboard Data for Players
	private static HashMap<Player, Objective> playerObjective = new HashMap<Player, Objective>();
	private static HashMap<Player, Integer> playerRemaingTime = new HashMap<Player, Integer>();
	private static HashSet<Player> playerPlaying = new HashSet<Player>();

	public static void start() {
		cleanup();
		setup();

	}

	public static void stop() {
		GameStatus.setStatus(GameStatus.STOPPED);

	}

	public static void setup() {
		GameStatus.setStatus(GameStatus.ENGINE_SETUP);

		ItemDistribute.DistributeItems();
		gameTime = 0;

		// Remove Dropped Item
		for (Entity current : GameSettings.world.getEntities()) {
			if (current instanceof Item) {
				current.remove();
			}
		}

		ScoreboardManager manager = Bukkit.getScoreboardManager();

		for (Player player : Bukkit.getOnlinePlayers()) {
			if(player.getGameMode() != GameMode.SURVIVAL)
				continue;

			// Give Regen
			player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 3));

			// Distribute Item
			player.getInventory().clear();
			player.getInventory().addItem(SpecialItems.WOOD_SWORD.get());
			
			// Add to Playing List
			playerPlaying.add(player);			

			// SETUP REMAING TIME
			int lifeTime = (int) Math
					.floor(Math.random() * (GameSettings.MAX_LIFE.seconds() - GameSettings.MIN_LIFE.seconds() + 1))
					+ GameSettings.MIN_LIFE.seconds();
			playerRemaingTime.put(player, lifeTime);

			// SETUP SCOREBOARD
			Logger logger = Bukkit.getServer().getLogger();
			logger.info(">> Setting up user : " + player.getName());

			Scoreboard board = manager.getNewScoreboard();
			Objective obj = board.registerNewObjective(player.getName(), "dummy");
			obj.setDisplaySlot(DisplaySlot.SIDEBAR);
			obj.setDisplayName(player.getName());

			obj.getScore("").setScore(9);
			obj.getScore(ChatColor.GREEN + ChatColor.BOLD.toString() + "랭크").setScore(8);
			obj.getScore("1/" + playerPlaying.size());
			obj.getScore(" ").setScore(6);
			obj.getScore(ChatColor.RED + ChatColor.BOLD.toString() + "남은시간").setScore(5);
			obj.getScore(toMinute(lifeTime) + " ").setScore(4);
			obj.getScore("  ").setScore(3);
			obj.getScore(ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + "게임시간").setScore(2);
			obj.getScore(toMinute(gameTime)).setScore(1);

			player.setScoreboard(board);

			playerObjective.put(player, obj);

		}
		GameStatus.setStatus(GameStatus.RUNNING);

	}

	public static void update() {
		if (GameStatus.getStatus() != GameStatus.RUNNING)
			return;

		int oldGameTime = gameTime;
		gameTime++;

		for (Player player : Bukkit.getOnlinePlayers()) {

			// Global Scoreboard
			Objective obj = playerObjective.get(player);
			if (obj == null)
				return;
			Scoreboard board = obj.getScoreboard();
			board.resetScores(toMinute(oldGameTime));
			obj.getScore(toMinute(gameTime)).setScore(1);

			// Personal Scoreboard

			int lifeTime = playerRemaingTime.get(player);

			if (lifeTime > 0) {
				board.resetScores(toMinute(lifeTime) + " ");
				obj.getScore(toMinute(--lifeTime) + " ").setScore(4);

				if (lifeTime < 60) {
					player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BELL, 0.7f, 0.2f);
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

	}

	public static void out(Player player) {
		if (GameStatus.getStatus() != GameStatus.RUNNING)
			return;
		// playerObjective.remove(player);

		player.getWorld().createExplosion(player.getLocation(), 0);

		for (ItemStack is : player.getInventory().getContents()) {
			if (is != null)
				player.getWorld().dropItem(player.getLocation().add(0, 0.5, 0), is);
		}
		player.getInventory().clear();

		Bukkit.broadcastMessage(player.getName() + "님 아웃");
		player.setGameMode(GameMode.SPECTATOR);

	}

	private static String toMinute(int second) {
		int remainder = second % 3600;
		int minutes = remainder / 60;
		int seconds = remainder % 60;
		String mins = (minutes < 10 ? "0" : "") + minutes;
		String secs = (seconds < 10 ? "0" : "") + seconds;
		String formattedTime = mins + "분 " + secs + "초";
		return formattedTime;
	}

}
