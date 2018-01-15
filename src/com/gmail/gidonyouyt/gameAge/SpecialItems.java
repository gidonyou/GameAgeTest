package com.gmail.gidonyouyt.gameAge;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.gidonyouyt.gameAge.core.SendMessage;

public enum SpecialItems {
	LOCATION_FINDER(25),
	PLAYER_TIME_ABOVE(15),
	PLAYER_TIME_BELOW(15),
	PLAYER_TIME_LIST(10),
	DIAMOND_SWORD(5),
	IRON_SWORD(10),
	STONE_SWORD(20),
	WOOD_SWORD(0),
	SHIELD(18),
	FIRST_AID(20),
	BANDAGE(35),
	STEAL_TARGET(15),
	EARN_SECONDS(40);

	private static ArrayList<SpecialItems> itemPool;

	private int chance;

	SpecialItems(int chance) {
		this.chance = chance;
	}

	public ItemStack get() {
		return getItemMateral(this);
	}

	public int chance() {
		return chance;
	}

	public static ItemStack getItemMateral() {

		ItemStack is = new ItemStack(Material.BEDROCK);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(ChatColor.DARK_RED + "기돈유의 버그 기본 아이템");
		meta.setLore(Arrays.asList(ChatColor.RED + "아무런 의미 없는 아이템", ChatColor.GRAY + "기돈유의 버그템?"));
		is.setItemMeta(meta);
		is.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		is.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		return is;
	}

	public static ItemStack getItemMateral(SpecialItems spit) {

		ItemStack is = getItemMateral();
		ItemMeta meta = is.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();

		switch (spit) {
		case LOCATION_FINDER:
			is = new ItemStack(Material.COMPASS);
			meta = is.getItemMeta();
			meta.setDisplayName(ChatColor.RESET + "가리키는 나침판  " + ChatColor.GRAY + "(1회용)");
			lore.add(ChatColor.GREEN + "자신보다 시간이 많은 플레이어 위치와 거리를 알려줍니다.");
			lore.add(ChatColor.LIGHT_PURPLE
					+ "%s 초 이용 가능".replace("%s", String.valueOf(GameSettings.COMPASS_DURBILITY.value())));
			lore.add(ChatColor.LIGHT_PURPLE + "우클릭으로 이용 가능.");
			lore.add(ChatColor.DARK_PURPLE + "고유ID: " + Math.floor(Math.random() * 100000));
			break;

		case PLAYER_TIME_ABOVE:
			is = new ItemStack(Material.INK_SACK, 1, (short) 1);
			meta = is.getItemMeta();
			meta.setDisplayName(ChatColor.RESET + "많은 시간의 유저  " + ChatColor.GRAY + "(1회용)");
			lore.add(ChatColor.GREEN + "자신보다 시간이 많은 남은 플레이어 이름을 알려줍니다.");
			lore.add(ChatColor.LIGHT_PURPLE + "우클릭으로 이용 가능.");
			break;

		case PLAYER_TIME_BELOW:
			is = new ItemStack(Material.INK_SACK, 1, (short) 4);
			meta = is.getItemMeta();
			meta.setDisplayName(ChatColor.RESET + "적은 시간의 유저  " + ChatColor.GRAY + "(1회용)");
			lore.add(ChatColor.GREEN + "자신보다 시간이 적게 남은 플레이어 이름을 알려줍니다.");
			lore.add(ChatColor.LIGHT_PURPLE + "우클릭으로 이용 가능.");
			break;

		case PLAYER_TIME_LIST:
			is = new ItemStack(Material.BOOK);
			meta = is.getItemMeta();
			meta.setDisplayName(ChatColor.RESET + "죽음의 책  " + ChatColor.GRAY + "(1회용)");
			lore.add(ChatColor.GREEN + "모든사람의 죽는 순서를 알려줍니다.");
			lore.add(ChatColor.LIGHT_PURPLE + "우클릭으로 이용 가능.");
			break;

		case DIAMOND_SWORD:
			is = new ItemStack(Material.DIAMOND_SWORD);
			meta = is.getItemMeta();
			meta.setDisplayName(ChatColor.GOLD + "다이아 칼");
			lore.add(ChatColor.GREEN + "당신은 행운의 다이아 칼을 받으셨습니다.");
			lore.add(ChatColor.LIGHT_PURPLE + "칼처럼 이용 가능");
			meta.setUnbreakable(true);
			break;

		case IRON_SWORD:
			is = new ItemStack(Material.IRON_SWORD);
			meta = is.getItemMeta();
			meta.setDisplayName(ChatColor.GOLD + "철 칼");
			lore.add(ChatColor.GREEN + "당신은 행운의 철철철 칼을 받으셨습니다.");
			lore.add(ChatColor.LIGHT_PURPLE + "칼처럼 이용 가능");
			meta.setUnbreakable(true);
			break;

		case STONE_SWORD:
			is = new ItemStack(Material.STONE_SWORD);
			meta = is.getItemMeta();
			meta.setDisplayName(ChatColor.GOLD + "돌 칼");
			lore.add(ChatColor.GREEN + "당신은 행운의 돌 칼을 받으셨습니다.");
			lore.add(ChatColor.LIGHT_PURPLE + "칼처럼 이용 가능");
			meta.setUnbreakable(true);
			break;

		case WOOD_SWORD:
			is = new ItemStack(Material.WOOD_SWORD);
			meta = is.getItemMeta();
			meta.setDisplayName(ChatColor.GOLD + "기본 칼");
			lore.add(ChatColor.GREEN + "모두에게 지급되는 나무칼");
			lore.add(ChatColor.LIGHT_PURPLE + "칼처럼 이용 가능");
			meta.setUnbreakable(true);
			break;
			
		case SHIELD:
			is = new ItemStack(Material.SHIELD);
			meta = is.getItemMeta();
			meta.setDisplayName(ChatColor.GOLD + "방패");
			lore.add(ChatColor.GREEN + "당신의 데미지를 먹어줄 방패");
			lore.add(ChatColor.LIGHT_PURPLE + "방패처럼 이용 가능");
			meta.setUnbreakable(true);
			break;

		case FIRST_AID:
			is = new ItemStack(Material.CLAY_BRICK);
			meta = is.getItemMeta();
			meta.setDisplayName(ChatColor.RESET + "구급상자  " + ChatColor.GRAY + "(1회용)");
			lore.add(ChatColor.GREEN + "이용 즉시 피 5칸을 채웁니다.");
			lore.add(ChatColor.LIGHT_PURPLE + "우클릭으로 이용 가능.");
			break;

		case BANDAGE:
			is = new ItemStack(Material.PAPER);
			meta = is.getItemMeta();
			meta.setDisplayName(ChatColor.RESET + "붕대  " + ChatColor.GRAY + "(1회용)");
			lore.add(ChatColor.GREEN + "이용 즉시 피 2칸을 채워줍니다.");
			lore.add(ChatColor.LIGHT_PURPLE + "우클릭으로 이용 가능.");
			break;

		case STEAL_TARGET:
			is = new ItemStack(Material.BOOK_AND_QUILL);
			meta = is.getItemMeta();
			meta.setDisplayName(ChatColor.RESET + "상대방 수명뺏기  " + ChatColor.GRAY + "(1회용)");
			lore.add(ChatColor.GREEN + "지목 상대에 시간을 뺏습니다.");
			lore.add("%i 초 이상 남은 상대에게 %s 초 강탈"
					.replace("%i",
							ChatColor.YELLOW + String.valueOf(GameSettings.STEAL_IMMUNE_TIME_SEC.value())
									+ ChatColor.BLUE)
					.replace("%s",
							ChatColor.RED + String.valueOf(GameSettings.STEAL_TIME_SEC.value()) + ChatColor.BLUE));
			lore.add("");
			lore.add(ChatColor.DARK_RED + "주의: 1페이지에 ");
			lore.add(ChatColor.DARK_RED + "지목인 이름을 정확히 입력하세요 ");
			lore.add("");
			lore.add(ChatColor.LIGHT_PURPLE + "책에 쓴후 서명후");
			lore.add(ChatColor.LIGHT_PURPLE + "우클릭 하면 이용 가능");
			break;

		case EARN_SECONDS:
			is = new ItemStack(Material.REDSTONE);
			meta = is.getItemMeta();
			meta.setDisplayName(ChatColor.RESET + "시간추가  " + ChatColor.GRAY + "(1회용)");
			lore.add(ChatColor.GREEN + "자신에게 %s 초를 추가합니다.".replace("%s",
					ChatColor.YELLOW + String.valueOf(GameSettings.EARN_SECOUNDS_TIME.value()) + ChatColor.GREEN));
			lore.add(ChatColor.LIGHT_PURPLE + "우클릭으로 이용 가능.");
		default:
			break;
		}

		if (!(lore.isEmpty()))
			meta.setLore(lore);
		is.setItemMeta(meta);
		is.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		return is;
	}

	public static ArrayList<ItemStack> getAllItemsList() {
		ArrayList<ItemStack> resultList = new ArrayList<ItemStack>();
		for (SpecialItems items : SpecialItems.values()) {
			resultList.add(getItemMateral(items));
		}
		return resultList;
	}

	public static ItemStack[] getAllItems() {
		ArrayList<ItemStack> resultList = getAllItemsList();
		ItemStack[] result = new ItemStack[resultList.size()];
		for (int i = 0; i < resultList.size(); i++)
			result[i] = resultList.get(i);

		return result;
	}

	public static ItemStack getRandomItem() {
		Boolean smart = false;
		if (GameSettings.SMART_DISTRIBUTE.value() == 1)
			smart = true;

		ItemStack is = getItemMateral();

		if (smart) {
			if (itemPool == null)
				setPool();

			int random = (int) Math.floor(Math.random() * (itemPool.size() - 1 - 0 + 1)) + 0;
			is = getItemMateral(itemPool.get(random));
		} else {
			int random = (int) Math.floor(Math.random() * (SpecialItems.values().length - 1 - 0 + 1)) + 0;
			is = getItemMateral(SpecialItems.values()[random]);
		}
		return is;
	}

	public static void setPool() {
		SendMessage.sendMessageOP("Asked to Create Random Item Pool");
		ArrayList<SpecialItems> pool = new ArrayList<SpecialItems>();
		for (SpecialItems items : SpecialItems.values())
			for (int i = 0; i < items.chance(); i++)
				pool.add(items);

		itemPool = pool;
		SendMessage.sendMessageOP("Setup Random Pool - Entry: " + itemPool.size());
	}

}
