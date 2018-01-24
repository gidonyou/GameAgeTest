package com.gmail.gidonyouyt.gameAge;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;

import com.gmail.gidonyouyt.gameAge.core.GameStatus;
import com.gmail.gidonyouyt.gameAge.core.SendMessage;

public class GameBorder {
	private static int autoTimes = 0;
	private static double newSize = 0;
	private static boolean isDefaultB = false;
	private static boolean forceDamage = false;

	private static World world = GameSettings.world;
	private static WorldBorder wb = world.getWorldBorder();

	public static void setborder() {
		setborder((int) GameSettings.MAP_LENGTH.value());
	}

	public static void setborder(int size) {
		wb.setCenter(GameSettings.centerLoc);
		wb.setSize(size);
		wb.setWarningDistance(2);
		wb.setWarningTime(0);
		wb.setDamageBuffer(0);
		Location l = wb.getCenter();
		SendMessage.broadcastMessage(ChatColor.DARK_RED + "[경고] " + ChatColor.RED + "경기 구역이 제한되었습니다.");
		SendMessage.broadcastMessage(ChatColor.DARK_RED + "[경고] " + ChatColor.RED
				+ "구역 중심: (%x, %y)".replace("%x", String.valueOf(l.getX())).replace("%y", String.valueOf(l.getZ())));
		SendMessage.broadcastMessage(ChatColor.DARK_RED + "[경고] " + ChatColor.RED + "구역 크기: " + wb.getSize());

		GameBorder.newSize = size;
		isDefaultB = true;
	}

	public static void setBorder(double size, double minute, Location center) {
		// minute = 0;
		if (!isDefaultB) {
			SendMessage.sendMessageOP("기본 베리어를 설정하는중 초기 설정이 안되있어서 실패.");
			return;
		}
		if (center == null)
			center = GameSettings.centerLoc;

		if (wb.getSize() <= GameSettings.FORCE_DAMAGE_DISTANCE.value()) {
			forceDamage();
			return;
		}

		int time = (int) Math.floor(minute * 60);
		wb.setCenter(center);
		wb.setSize(size, time);
		Location l = wb.getCenter();
		SendMessage.broadcastMessage(ChatColor.DARK_RED + "[경고] " + ChatColor.RED
				+ "%t간 경기 구역이 제한되었습니다.".replace("%t", ChatColor.GOLD + String.valueOf(minute) + "분" + ChatColor.RED));
		SendMessage.broadcastMessage(ChatColor.DARK_RED + "[경고] " + ChatColor.RED
				+ "구역 중심: (%x, %y)".replace("%x", String.valueOf(l.getX())).replace("%y", String.valueOf(l.getZ())));
		SendMessage
				.broadcastMessage(
						ChatColor.DARK_RED
								+ "[경고] %o  -->  %n"
										.replace("%o",
												ChatColor.RED + String.valueOf(Math.floor(wb.getSize()))
														+ ChatColor.RESET)
										.replace("%n", ChatColor.GREEN + String.valueOf(size)));

		GameBorder.newSize = size;
	}

	public static void clearBorder() {
		wb.reset();
		SendMessage.broadcastMessage(ChatColor.DARK_RED + "[경고] " + ChatColor.GREEN + "경기 구역이 초기화 되었습니다.");
		autoTimes = 0;
		GameBorder.newSize = wb.getSize();
		isDefaultB = false;
		forceDamage = false;
	}

	public static void autoLimit() {
		if (!isDefaultB) {
			SendMessage.sendMessageOP("오토베리어를 설정하는중 기본 베리어가 없어서 실패하였습니다.");
			return;
		}

		double[] factors = { 0.85, 0.6, 0.4 };
		double[] durs = { 2, 1, 0.5 };
		double[] damage = { 0.1, 0.2, 0.4 };

		if (autoTimes >= durs.length - 1)
			autoTimes = durs.length - 1;

		double newSize = wb.getSize() * factors[autoTimes];
		setBorder(newSize, durs[autoTimes], null);
		wb.setDamageAmount(damage[autoTimes]);
		autoTimes++;
	}

	public static boolean isInside(Player player) {
		Location centerLoc = GameSettings.centerLoc.clone();
		centerLoc.setY(player.getLocation().getY());
		if (centerLoc.distance(player.getLocation()) > newSize / 2)
			return false;
		return true;
	}

	@SuppressWarnings("unused")
	private static int getRandomValue(int min, int max) {
		return (int) Math.floor(Math.random() * (max - min + 1)) + min;
	}

	private static void forceDamage() {
		double limit = GameSettings.FORCE_DAMAGE_DISTANCE.value();
		SendMessage.broadcastMessage(ChatColor.DARK_RED + "[경고] " + ChatColor.RED + "경기 구역이 %s 블럭 아래 이므로 모든 플레이어에게 데미지."
				.replace("%s", ChatColor.GOLD + String.valueOf(limit) + ChatColor.RED));
		SendMessage
				.broadcastMessage(ChatColor.DARK_RED + "[경고] " + ChatColor.RED + "%s 초 후에 모든 플레이어 에게 데미지".replace("%s",
						ChatColor.GOLD + String.valueOf(GameSettings.FORCE_DAMAGE_TIME.value()) + ChatColor.RED));
		forceDamage = true;
	}

	private static int forceCoolTime = 0;

	public static void forceBarUpdate() {
		if (!forceDamage)
			return;

		if (GameStatus.getStatus() != GameStatus.RUNNING)
			return;

		newSize = 0;

		if (forceCoolTime == 1) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (!Sequence.getPlayerPlaying().contains(p))
					continue;
				p.damage(1);
				p.getWorld().strikeLightningEffect(p.getLocation());
			}
		} else if (forceCoolTime <= 0) {
			forceCoolTime = (int) GameSettings.FORCE_DAMAGE_TIME.value();
		}else {
			forceCoolTime--;
			if (forceCoolTime <= 10) {
				SendMessage.broadcastMessage(ChatColor.DARK_RED + "[경고] " + ChatColor.RED + "%s 초 이후에 모든 플레이어에게 데미지"
						.replace("%s", ChatColor.GOLD + String.valueOf(forceCoolTime) + ChatColor.RED));
				for (Player p : Bukkit.getOnlinePlayers())
					p.playSound(p.getLocation(), Sound.ITEM_FIRECHARGE_USE, 10, 1);
			}
		}

	}

}
