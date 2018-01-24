package com.gmail.gidonyouyt.gameAge;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.gidonyouyt.gameAge.core.SendMessage;

import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagInt;
import net.minecraft.server.v1_12_R1.NBTTagList;
import net.minecraft.server.v1_12_R1.NBTTagString;

public enum SpecialItems {
	LOCATION_FINDER(5),
	PLAYER_TIME_ABOVE(8),
	PLAYER_TIME_BELOW(8),
	PLAYER_TIME_LIST(3),
	DIAMOND_SWORD(3),
	IRON_SWORD(8),
	GOLD_SWORD(4),
	STONE_SWORD(15),
	WOOD_SWORD(0),
	FIRST_AID(7),
	BANDAGE(15),
	STEAL_TARGET(3),
	EARN_SECONDS(6),
	
	INVISIBILITY_WATCH(2),
	ENDER_PEARL(2);

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
		meta.setDisplayName(ChatColor.DARK_RED + "ê¸°ëˆìœ ì˜ ë²„ê·¸ ê¸°ë³¸ ì•„ì´í…œ");
		meta.setLore(Arrays.asList(ChatColor.RED + "ì•„ë¬´ëŸ° ì˜ë¯¸ ì—†ëŠ” ì•„ì´í…œ", ChatColor.GRAY + "ê¸°ëˆìœ ì˜ ë²„ê·¸í…œ?"));
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
			meta.setDisplayName(ChatColor.RESET + "ê°€ë¦¬í‚¤ëŠ” ë‚˜ì¹¨íŒ  " + ChatColor.GRAY + "(1íšŒìš©)");
			lore.add(ChatColor.GREEN + "ìžì‹ ë³´ë‹¤ ë§Žì€ ì‹œê°„ì„ ê°€ì§€ê³  ìžˆëŠ” ì‚¬ëžŒì˜ ìœ„ì¹˜ë¥¼ ì•Œë ¤ì¤ë‹ˆë‹¤.");
			lore.add(ChatColor.LIGHT_PURPLE
					+ "%s ì´ˆ ì´ìš© ê°€ëŠ¥".replace("%s", String.valueOf(GameSettings.COMPASS_DURABILITY.value())));
			lore.add(ChatColor.LIGHT_PURPLE + "ìš°í´ë¦­ìœ¼ë¡œ ì´ìš© ê°€ëŠ¥.");
			lore.add(ChatColor.DARK_PURPLE + "ê³ ìœ ID: " + Math.floor(Math.random() * 100000));
			break;

		case PLAYER_TIME_ABOVE:
			is = new ItemStack(Material.INK_SACK, 1, (short) 1);
			meta = is.getItemMeta();
			meta.setDisplayName(ChatColor.RESET + "ë§Žì€ ì‹œê°„ì˜ ìœ ì €  " + ChatColor.GRAY + "(1íšŒìš©)");
			lore.add(ChatColor.GREEN + "ìžì‹ ë³´ë‹¤ ë§Žì€ ì‹œê°„ì„ ê°€ì§€ê³  ìžˆëŠ” ì‚¬ëžŒì˜ ì´ë¦„ì„ ì•Œë ¤ì¤ë‹ˆë‹¤.");
			lore.add(ChatColor.LIGHT_PURPLE + "ìš°í´ë¦­ìœ¼ë¡œ ì´ìš© ê°€ëŠ¥.");
			break;

		case PLAYER_TIME_BELOW:
			is = new ItemStack(Material.INK_SACK, 1, (short) 4);
			meta = is.getItemMeta();
			meta.setDisplayName(ChatColor.RESET + "ì ì€ ì‹œê°„ì˜ ìœ ì €  " + ChatColor.GRAY + "(1íšŒìš©)");
			lore.add(ChatColor.GREEN + "ìžì‹ ë³´ë‹¤ ì ì€ ì‹œê°„ì„ ê°€ì§€ê³  ìžˆëŠ” ì‚¬ëžŒì˜ ì´ë¦„ì„ ì•Œë ¤ì¤ë‹ˆë‹¤.");
			lore.add(ChatColor.LIGHT_PURPLE + "ìš°í´ë¦­ìœ¼ë¡œ ì´ìš© ê°€ëŠ¥.");
			break;

		case PLAYER_TIME_LIST:
			is = new ItemStack(Material.BOOK);
			meta = is.getItemMeta();
			meta.setDisplayName(ChatColor.RESET + "ì£½ìŒì˜ ì±…  " + ChatColor.GRAY + "(1íšŒìš©)");
			lore.add(ChatColor.GREEN + "ëª¨ë“ ì‚¬ëžŒì˜ ì£½ëŠ” ìˆœì„œë¥¼ ì•Œë ¤ì¤ë‹ˆë‹¤.");
			lore.add(ChatColor.LIGHT_PURPLE + "ìš°í´ë¦­ìœ¼ë¡œ ì´ìš© ê°€ëŠ¥.");
			break;

		case DIAMOND_SWORD:
			is = new ItemStack(Material.DIAMOND_SWORD);
			meta = is.getItemMeta();
			meta.setDisplayName(ChatColor.GOLD + "ë‹¤ì´ì•„ ì¹¼");
			lore.add(ChatColor.GREEN + "ë‹¹ì‹ ì€ í–‰ìš´ì˜ ë‹¤ì´ì•„ ì¹¼ì„ ë°›ìœ¼ì…¨ìŠµë‹ˆë‹¤.");
			lore.add(ChatColor.LIGHT_PURPLE + "ì¹¼ì²˜ëŸ¼ ì´ìš© ê°€ëŠ¥");
			meta.setUnbreakable(true);
			break;
			
		case GOLD_SWORD:
			is = new ItemStack(Material.GOLD_SWORD);
			meta = is.getItemMeta();
			meta.setDisplayName(ChatColor.GOLD + "ê¸ˆ ì¹¼  " + ChatColor.GRAY + "(1íšŒìš©)");
			lore.add(ChatColor.GREEN + "ì´ ì¹¼ì€ ì–´ë§ˆì–´ë§ˆí•œ ë°ë¯¸ì§€ë¥¼");
			lore.add(ChatColor.GREEN + "ê°€ì§€ê³  ìžˆì§€ë§Œ 1ë²ˆë§Œ ì´ìš© ê°€ëŠ¥í•©ë‹ˆë‹¤.");
			lore.add(ChatColor.BLUE + "ë°ë¯¸ì§€: " + ChatColor.YELLOW + "í•˜íŠ¸ 5ì¹¸");
			lore.add(ChatColor.LIGHT_PURPLE + "ì¹¼ì²˜ëŸ¼ ì´ìš© ê°€ëŠ¥");
			meta.setUnbreakable(true);
			is.setItemMeta(meta);
			
			net.minecraft.server.v1_12_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(is);
			NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
            NBTTagList modifiers = new NBTTagList();
            NBTTagCompound damage = new NBTTagCompound();
            damage.set("AttributeName", new NBTTagString("generic.attackDamage"));
            damage.set("Name", new NBTTagString("generic.attackDamage"));
            damage.set("Amount", new NBTTagInt(10));
            damage.set("Operation", new NBTTagInt(0));
            damage.set("UUIDLeast", new NBTTagInt(894654));
            damage.set("UUIDMost", new NBTTagInt(2872));
            damage.set("Slot", new NBTTagString("mainhand"));
            
            modifiers.add(damage);
            compound.set("AttributeModifiers", modifiers);
            nmsStack.setTag(compound);
            is = CraftItemStack.asBukkitCopy(nmsStack);
			break;

		case IRON_SWORD:
			is = new ItemStack(Material.IRON_SWORD);
			meta = is.getItemMeta();
			meta.setDisplayName(ChatColor.GOLD + "ì²  ì¹¼");
			lore.add(ChatColor.GREEN + "ë‹¹ì‹ ì€ í–‰ìš´ì˜ ì² ì² ì²  ì¹¼ì„ ë°›ìœ¼ì…¨ìŠµë‹ˆë‹¤.");
			lore.add(ChatColor.LIGHT_PURPLE + "ì¹¼ì²˜ëŸ¼ ì´ìš© ê°€ëŠ¥");
			meta.setUnbreakable(true);
			break;

		case STONE_SWORD:
			is = new ItemStack(Material.STONE_SWORD);
			meta = is.getItemMeta();
			meta.setDisplayName(ChatColor.GOLD + "ëŒ ì¹¼");
			lore.add(ChatColor.GREEN + "ë‹¹ì‹ ì€ í–‰ìš´ì˜ ëŒ ì¹¼ì„ ë°›ìœ¼ì…¨ìŠµë‹ˆë‹¤.");
			lore.add(ChatColor.LIGHT_PURPLE + "ì¹¼ì²˜ëŸ¼ ì´ìš© ê°€ëŠ¥");
			meta.setUnbreakable(true);
			break;

		case WOOD_SWORD:
			is = new ItemStack(Material.WOOD_SWORD);
			meta = is.getItemMeta();
			meta.setDisplayName(ChatColor.GOLD + "ê¸°ë³¸ ì¹¼");
			lore.add(ChatColor.GREEN + "ëª¨ë‘ì—ê²Œ ì§€ê¸‰ë˜ëŠ” ë‚˜ë¬´ì¹¼");
			lore.add(ChatColor.LIGHT_PURPLE + "ì¹¼ì²˜ëŸ¼ ì´ìš© ê°€ëŠ¥");
			meta.setUnbreakable(true);
			break;

		case FIRST_AID:
			is = new ItemStack(Material.CLAY_BRICK);
			meta = is.getItemMeta();
			meta.setDisplayName(ChatColor.RESET + "êµ¬ê¸‰ìƒìž  " + ChatColor.GRAY + "(1íšŒìš©)");
			lore.add(ChatColor.GREEN + "ìžì‹ ì˜ ì²´ë ¥ì„ ìµœëŒ€ 4ì¹¸ê¹Œì§€ ì¹˜ìœ í•©ë‹ˆë‹¤.");
			lore.add(ChatColor.LIGHT_PURPLE + "ìš°í´ë¦­ìœ¼ë¡œ ì´ìš© ê°€ëŠ¥.");
			lore.add(ChatColor.LIGHT_PURPLE + "");
			lore.add(ChatColor.YELLOW + "ì¿¨íƒ€ìž„: 6ì´ˆ");
			break;

		case BANDAGE:
			is = new ItemStack(Material.PAPER);
			meta = is.getItemMeta();
			meta.setDisplayName(ChatColor.RESET + "ë¶•ëŒ€  " + ChatColor.GRAY + "(1íšŒìš©)");
			lore.add(ChatColor.GREEN + "ìžì‹ ì˜ ì²´ë ¥ì„ ìµœëŒ€ 1.5ì¹¸ê¹Œì§€ ì¹˜ìœ í•©ë‹ˆë‹¤.");
			lore.add(ChatColor.LIGHT_PURPLE + "ìš°í´ë¦­ìœ¼ë¡œ ì´ìš© ê°€ëŠ¥.");
			lore.add(ChatColor.LIGHT_PURPLE + "");
			lore.add(ChatColor.YELLOW + "ì¿¨íƒ€ìž„: 3ì´ˆ");
			break;

		case STEAL_TARGET:
			is = new ItemStack(Material.BOOK_AND_QUILL);
			meta = is.getItemMeta();
			meta.setDisplayName(ChatColor.RESET + "ìƒëŒ€ë°© ìˆ˜ëª…ëºê¸°  " + ChatColor.GRAY + "(1íšŒìš©)");
			lore.add(ChatColor.GREEN + "ì§€ëª© ìƒëŒ€ì—ì„œ ì‹œê°„ì„ ëºìŠµë‹ˆë‹¤.");
			lore.add("%i ì´ˆ ì´ìƒ ë‚¨ì€ ìƒëŒ€ì—ê²Œ %s ì´ˆ ê°•íƒˆ"
					.replace("%i",
							ChatColor.YELLOW + String.valueOf(GameSettings.STEAL_IMMUNE_TIME_SEC.value())
									+ ChatColor.BLUE)
					.replace("%s",
							ChatColor.RED + String.valueOf(GameSettings.STEAL_TIME_SEC.value()) + ChatColor.BLUE));
			lore.add("");
			lore.add(ChatColor.DARK_RED + "ì£¼ì˜: 1íŽ˜ì´ì§€ì— ");
			lore.add(ChatColor.DARK_RED + "ì§€ëª©ì¸ ì´ë¦„ì„ ì •í™•ížˆ ìž…ë ¥í•˜ì„¸ìš” ");
			lore.add("");
			lore.add(ChatColor.LIGHT_PURPLE + "ì±…ì— ì“´í›„ ì„œëª…í›„");
			lore.add(ChatColor.LIGHT_PURPLE + "ìš°í´ë¦­ í•˜ë©´ ì´ìš© ê°€ëŠ¥");
			break;

		case EARN_SECONDS:
			is = new ItemStack(Material.REDSTONE);
			meta = is.getItemMeta();
			meta.setDisplayName(ChatColor.RESET + "ì‹œê°„ì¶”ê°€  " + ChatColor.GRAY + "(1íšŒìš©)");
			lore.add(ChatColor.GREEN + "ìžì‹ ì—ê²Œ %s ì´ˆë¥¼ ì¶”ê°€í•©ë‹ˆë‹¤.".replace("%s",
					ChatColor.YELLOW + String.valueOf(GameSettings.EARN_SECOUNDS_TIME.value()) + ChatColor.GREEN));
			lore.add(ChatColor.LIGHT_PURPLE + "ìš°í´ë¦­ìœ¼ë¡œ ì´ìš© ê°€ëŠ¥.");
			break;
			
		case INVISIBILITY_WATCH:
			is = new ItemStack(Material.GOLD_RECORD);
			meta = is.getItemMeta();
			meta.setDisplayName(ChatColor.RESET + "íˆ¬ëª…í™” ì‹œê³„  " + ChatColor.GRAY + "(1íšŒìš©)");
			lore.add(ChatColor.GREEN + "%s ì´ˆë™ì•ˆ íˆ¬ëª…í•´ì§‘ë‹ˆë‹¤.".replace("%s",
					ChatColor.YELLOW + String.valueOf(GameSettings.IS_INVISIBILITY.value()) + ChatColor.GREEN));
			lore.add(ChatColor.LIGHT_PURPLE + "ìš°í´ë¦­ìœ¼ë¡œ ì´ìš© ê°€ëŠ¥.");
			break;
			
		case ENDER_PEARL:
			is = new ItemStack(Material.ENDER_PEARL);
			meta = is.getItemMeta();
			meta.setDisplayName(ChatColor.RESET + "ì—”ë” ì§„ì£¼  " + ChatColor.GRAY + "(1íšŒìš©)");
			lore.add(ChatColor.GREEN + "í…”ë ˆí¬íŠ¸ë¥¼ ê°€ëŠ¥í•˜ê²Œ í•´ì£¼ëŠ” ì•„ì´í…œ.".replace("%s",
					ChatColor.YELLOW + String.valueOf(GameSettings.EARN_SECOUNDS_TIME.value()) + ChatColor.GREEN));
			lore.add(ChatColor.LIGHT_PURPLE + "ì—”ë” ì§„ì£¼ì²˜ëŸ¼ ì´ìš© ê°€ëŠ¥");
			break;
			
			
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
