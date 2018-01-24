package com.gmail.gidonyouyt.gameAge;

import java.util.ArrayList;
import java.util.HashSet;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.ShulkerBox;
import org.bukkit.inventory.Inventory;

import com.gmail.gidonyouyt.gameAge.core.SendMessage;

import net.md_5.bungee.api.ChatColor;

public class ItemDistribute {

	// private static int minx = -54, miny = 83, minz = -25, maxx = 15, maxy = 100,
	// maxz = 178;
	private static World world = GameSettings.world;

	private static HashSet<Location> allChests = new HashSet<Location>();

	private static int failedAttempts = 0;

	public static boolean DistributeItems() {

		int x, y, z;

		// Test Clear
		clearAllBlock();

		if (!(allChests.isEmpty())) {
			SendMessage.sendMessageOP(ChatColor.RED + "배분애러: 상자 db 가 비어있지 않습니다. 에러 - 아이템 배분 실패");
			for (Location l : allChests)
				SendMessage.sendMessageOP(l.toString());
			return false;
		}

		for (int i = 0; i < GameSettings.NUM_CHEST.value();) {

			x = getRandomValue(GameSettings.getRange()[0].getBlockX(), GameSettings.getRange()[1].getBlockX() - 1);
			y = getRandomValue(GameSettings.getRange()[0].getBlockY(), GameSettings.getRange()[1].getBlockY() - 1);
			z = getRandomValue(GameSettings.getRange()[0].getBlockZ(), GameSettings.getRange()[1].getBlockZ() - 1);

			Location location = new Location(world, x, y, z);

			ArrayList<Material> forbidden = new ArrayList<Material>();
			forbidden.add(Material.AIR);
			forbidden.add(Material.GLOWSTONE);
			forbidden.add(Material.STONE_SLAB2);
			// forbidden.add(Material.DOUBLE_STONE_SLAB2);
			forbidden.add(Material.TORCH);
			forbidden.add(Material.REDSTONE_BLOCK);
			forbidden.add(Material.STONE_PLATE);
			forbidden.add(Material.COMMAND);
			forbidden.add(Material.COMMAND_CHAIN);
			forbidden.add(Material.COMMAND_REPEATING);
			for (Material m : allShulkerBox())
				forbidden.add(m);
			
			boolean try1 = false;
			if (location.getBlock().getType() != Material.AIR
					|| forbidden.contains(location.clone().subtract(0, 1, 0).getBlock().getType())
					|| location.clone().add(0, 1, 0).getBlock().getType() != Material.AIR)
				try1 = true;

			boolean try2 = false;
			for (int ii = 1; ii < 7; ii++) {
				Material mat = location.clone().add(0, ii, 0).getBlock().getType();
				if (mat != Material.AIR) {
					if (mat == Material.STONE || mat == Material.STONE_SLAB2)
						break;
					try2 = true;
					break;
				}
			}

			if (try1 || !try2) {
				// Bukkit.broadcastMessage("FAILED");
				failedAttempts++;
				if (failedAttempts == GameSettings.MAX_CHEST_FAIL.value()) {
					SendMessage.sendMessageOP(ChatColor.RED + "배분애러: 설정에 표기된 시도만큼 했지만 실패했습니다.");
					return false;
				}
				continue;
			}

			Block block = location.getBlock();
			block.setType(getRandomShulkerBox());

			// Put Random Item
			Inventory inv = ((ShulkerBox) block.getState()).getInventory();
			inv.setItem(13, SpecialItems.getRandomItem());

			allChests.add(location);
			i++;
		}
//		SendMessage.broadcastMessage(failedAttempts + "실패 끝에 성공");
		failedAttempts = 0;
		return true;
	}

	private static int getRandomValue(int min, int max) {
		return (int) Math.floor(Math.random() * (max - min + 1)) + min;
	}

	private static ArrayList<Material> allShulkerBox() {
		ArrayList<Material> boxes = new ArrayList<Material>();
		boxes.add(Material.BLACK_SHULKER_BOX);
		boxes.add(Material.BLUE_SHULKER_BOX);
		boxes.add(Material.BROWN_SHULKER_BOX);
		boxes.add(Material.CYAN_SHULKER_BOX);
		boxes.add(Material.GRAY_SHULKER_BOX);
		boxes.add(Material.GREEN_SHULKER_BOX);
		boxes.add(Material.LIGHT_BLUE_SHULKER_BOX);
		boxes.add(Material.LIME_SHULKER_BOX);
		boxes.add(Material.MAGENTA_SHULKER_BOX);
		boxes.add(Material.ORANGE_SHULKER_BOX);
		boxes.add(Material.PINK_SHULKER_BOX);
		boxes.add(Material.PURPLE_SHULKER_BOX);
		boxes.add(Material.RED_SHULKER_BOX);
		boxes.add(Material.SILVER_SHULKER_BOX);
		boxes.add(Material.WHITE_SHULKER_BOX);
		boxes.add(Material.YELLOW_SHULKER_BOX);
		return boxes;

	}

	private static Material getRandomShulkerBox() {
		ArrayList<Material> boxes = allShulkerBox();
		return boxes.get(getRandomValue(0, boxes.size() - 1));
	}

	public static void clearAllBlock() {
		for (double x = GameSettings.getRange()[0].getBlockX(); x < GameSettings.getRange()[1].getBlockX(); x++) {
			for (double y = GameSettings.getRange()[0].getBlockY(); y < GameSettings.getRange()[1].getBlockY(); y++) {
				for (double z = GameSettings.getRange()[0].getBlockZ(); z < GameSettings.getRange()[1]
						.getBlockZ(); z++) {
					Location loc = new Location(world, x, y, z);
					if (allShulkerBox().contains(loc.getBlock().getType())) {
						loc.getBlock().setType(Material.AIR);
						allChests.remove(loc);
					}
				}
			}
		}
	}

	public static void beaconChests() {
		if (allChests.isEmpty()) {
			return;
		}

		// for (Location loc : allChests) {
		// World world = loc.getWorld();

		// world.spawnParticle(Particle.FIREWORKS_SPARK, loc, 5);

		// Firework fw = (Firework) world.spawn(loc.clone().add(0.5, 1,
		// 0.5), Firework.class);
		// FireworkMeta fm = fw.getFireworkMeta();
		// fm.setPower(1);
		// fm.addEffect(FireworkEffect.builder().flicker(false).trail(true).with(Type.BALL).withColor(Color.RED)
		// .withFade(Color.ORANGE).build());
		// fw.setFireworkMeta(fm);
		// fw.detonate();

		// world.spawnParticle(Particle.SMOKE_LARGE, loc, 1);
		// }

		// PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles();
	}

	@SuppressWarnings("unchecked")
	public static HashSet<Location> getAllLocation() {
		return (HashSet<Location>) allChests.clone();
	}
}
