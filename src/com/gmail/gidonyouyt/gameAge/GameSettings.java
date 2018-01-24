package com.gmail.gidonyouyt.gameAge;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import com.gmail.gidonyouyt.gameAge.core.SendMessage;

import net.md_5.bungee.api.ChatColor;

public enum GameSettings {

	GAME_TIME("ê²Œìž„ ì§„í–‰ ì‹œê°„ (ë¶„)", 0),// Minutes
	COUNTDOWN_TIME("ê²Œìž„ ì¹´ìš´íŠ¸ ë‹¤ìš´ ì‹œê°„ (ì´ˆ)", 5),
	MERCY_TIME("ì´ˆê¸° ë²„í”„ ì‹œê°„ (ì´ˆ)", 15), // Seconds
	MIN_LIFE("ìµœì†Œ ìˆ˜ëª… ì‹œê°„ (ë¶„)", 6), // Minute
	MAX_LIFE("ìµœê³  ìˆ˜ëª… ì‹œê°„ (ë¶„)", 12), // Minute
	STEAL_TIME_SEC("ëºì„ìˆ˜ ìžˆëŠ” ì‹œê°„ (ì´ˆ)", 30), // Seconds
	STEAL_IMMUNE_TIME_SEC("ë»¿ëŠ”ê²ƒ ë³´í˜¸ë°›ëŠ” ì‹œê°„ (ì´ˆ)", 45), // Seconds
	EARN_SECOUNDS_TIME("ì‹œê°„ ì¶”ê°€ ì•„ì´í…œ ì‹œê°„ (ì´ˆ)", 20), // Seconds
	AUTO_BORDER("ìžë™ ê²Œìž„êµ¬ì—­ ì œí•œ (ë¶„) [0 - disable]", 3), // Time in Minute -- 0 none;
	COMPASS_DURABILITY("ë‚˜ì¹¨íŒ ìž‘ë™ì‹œê°„ (ì´ˆ)", 30),
	FORCE_DAMAGE_DISTANCE("ê°•ì œ ë°ë¯¸ì§€ ê±°ë¦¬", 60),
	FORCE_DAMAGE_TIME("ê°•ì œ ë°ë¯¸ì§€ ê²½ê³  ì‹œê°„ (ì´ˆ)", 20),
	NUM_CHEST("ì•„ì´í…œ ìƒìž ê°¯ìˆ˜", 70), // Number
	MAX_CHEST_FAIL("ìƒìž ì„¤ì¹˜ì‹œ ìµœëŒ€ ì‹¤íŒ¨ ê°¯ìˆ˜", 1000000),
	AUTO_REGEN("ê²Œìž„ì¤‘ ìžë™ íšŒë³µ (B)", 0), // Boolean 0/1
	SMART_DISTRIBUTE("í™•ë¥ ì§€ì • ë°°ë¶„ (B)", 1), // 0/1
	MAP_LENGTH("ë§µ í¬ê¸°", 240), // Blocks
	MINY("ìµœì†Œ Y", 4), // Blocks
	MAXY("ìµœëŒ€ Y", 64), // Blocks
	ALLOW_BREAK_SPLATE("ëŒ ì••ë ¥íŒì„ ì œê±°í• ìˆ˜ ìžˆê²Œ í•´ì¤ë‹ˆë‹¤. (B)", 1),
	TIME_RECOVER_SPLATE("ëŒ ì••ë ¥íŒ ë³µêµ¬ì‹œê°„ (ì´ˆ) [-1 : disable]", 15),
	ADD_NULL_PLAYER("ì™¸í†¨ì´ ê¸°ëˆìœ ë¥¼ ìœ„í•œ NULL í”Œë ˆì´ì–´ (B)", 0),
	FIRST_LAST_DAMAGE_MULT("1ë“±ì´ ê¼´ë“±ì„ ê³µê²©í• ì‹œ ë°ë¯¸ì§€ ê³±í•˜ê¸° [-1 : disable]", 1.5),
	WEBPAGE_ON("ì›¹íŽ˜ì´ì§€ UI í™œì„±í™” (B) [ì‹¤í–‰ì‹œ ì£¼ì˜: ë°°íƒ€ì´ë¯€ë¡œ ì• ëŸ¬ ê°€ëŠ¥]", 0),
	WEBPAGE_PORT("ì›¹íŽ˜ì´ì§€ UI í¬íŠ¸ë²ˆí˜¸", 8000),
	WEBPAGE_BLANK("ì›¹íŽ˜ì´ì§€ ì´ˆë°˜ ê°ì¶¤ ì‹œê°„ (ì´ˆ) [ì•„ì§ ë¯¸ì ìš©]", 0),
	WEBPAGE_DEBUG("ì›¹íŽ˜ì´ì§€ ê°œë°œìš© í…ŒìŠ¤íŠ¸/ë””ë²„ê·¸ (B)", 0),
	DEV_CREDIT("ê°œë°œì§„ëŒ€í•´ ê´‘ê³ í•´ì¤ë‹ˆë‹¤ [0/1/2] - ë¯¸ìž‘ë™", 0),
	BROADCAST_CONFIG("ê²Œìž„ ì‹œìž‘ì‹œ ì„¤ì •ì„ OP ì—ê²Œ ì•Œë ¤ì¤ë‹ˆë‹¤ (B)", 0),

	IS_INVISIBILITY("ì•„ì´í…œ íˆ¬ëª…í™” ì‹œê³„ ì§€ì†ì‹œê°„ (ì´ˆ)", 10);

	public static final World world = Bukkit.getWorld("World");
	public static final Location centerLoc = world.getSpawnLocation();
	// new Location(world, -2254, 63, -2084);

	private String desc;
	public double value;

	GameSettings(String desc, double value) {
		this.value = value;
		this.desc = desc;
	}

	public String desc() {
		return desc;
	}

	public double value() {
		return value;
	}

	public int seconds() {
		return (int) Math.floor(value * 60);
	}

	public static Location[] getRange() {
		double distance = GameSettings.MAP_LENGTH.value() / 2;
		Location minLoc = new Location(world, centerLoc.getX() - distance, GameSettings.MINY.value(),
				centerLoc.getZ() - distance);
		Location maxLoc = new Location(world, centerLoc.getX() + distance, GameSettings.MAXY.value(),
				centerLoc.getZ() + distance);
		Location[] result = { minLoc, maxLoc };
		// Bukkit.broadcastMessage(minLoc.toString() + " " + maxLoc.toString());
		return result;
	}

	public static void broadcastSettingToOp() {
		SendMessage.sendMessageOP("");
		SendMessage.sendMessageOP("===== ê²Œìž„ ì„¤ì • =====");
		for (GameSettings settings : GameSettings.values()) {
			SendMessage.sendMessageOP(settings.desc + " - " + settings.value());
		}
	}

	public static void gameInfo() {
		// ê²Œìž„ ì„¤ëª…
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage("");
		SendMessage.sendCreditInfo();
		SendMessage.broadcastMessage(ChatColor.RED + "ì£¼ì˜: ë³¸ í”ŒëŸ¬ê·¸ì¸ì€ ì € í˜¼ìž ì“°ë ¤ê³  ë§Œë“  ê²ƒìž…ë‹ˆë‹¤.");
		SendMessage.broadcastMessage(ChatColor.RED + "ì£¼ì˜: ì—¬ë ¤ë¶„ì´ ì§„í–‰í• ë•Œ ë¬¸ì œê°€ ë°œìƒí• ìˆ˜ ìžˆìŠµë‹ˆë‹¤.");
		SendMessage.broadcastMessage(ChatColor.RED + "ì£¼ì˜: ì¦‰ì‹œ ê°œë°œìžì—ê²Œ ì•Œë ¤ì£¼ì„¸ìš”.");
		SendMessage.broadcastMessage(ChatColor.RED + "gidonyou@naver.com  ë˜ëŠ”  gidonyouyt@gmail.com");
		SendMessage.broadcastMessage("");
		SendMessage.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + " == ê²Œìž„ ì„¤ëª… == ");
		SendMessage.broadcastMessage("ì´ ê²Œìž„ì€ ì œí•œì‹œê°„ì´ ìžˆëŠ” ê¼¬ë¦¬ ìž¡ê¸°ìž…ë‹ˆë‹¤.");
		SendMessage.broadcastMessage("ìžì‹ ì—ê²Œ ì§€ì •ëœ ì œí•œì‹œê°„ì´ ëë‚˜ë©´ ì•„ì›ƒë˜ëŠ”ë°ìš”");
		SendMessage.broadcastMessage("ì•„ì´í…œì„ íšë“í•´ì„œ ì‹œê°„ì„ ëŠ˜ë¦¬ë˜ì§€");
		SendMessage.broadcastMessage("ìžì‹ ë³´ë‹¤ ì‹œê°„ì´ ë§Žì´ ë‚¨ì€ ì‚¬ëžŒì„ ì°¾ì•„ì„œ ì•„ì›ƒì‹œí‚¤ë©´");
		SendMessage.broadcastMessage("ì•„ì›ƒëœ í”Œë ˆì´ì–´ì˜ ë‚¨ì€ ì‹œê°„ ë°˜ì ˆì„ ì–»ê²Œ ë©ë‹ˆë‹¤.");
		SendMessage.broadcastMessage("í•˜ì§€ë§Œ ìžì‹ ë³´ë‹¤ ì‹œê°„ì´ ì ê²Œ ë‚¨ì€ ì‚¬ëžŒì„ ê³µê²©í•œë‹¤ë©´");
		SendMessage.broadcastMessage("ê±°ê¸°ì— 2ë°°ì— í•´ë‹¹ë˜ëŠ” ë°ë¯¸ì§€ë¥¼ ìž…ìœ¼ë‹ˆ ì¡°ì‹¬í•˜ì„¸ìš”");
		SendMessage.broadcastMessage("ë˜í•œ, 1ë“±ì€ ê¼´ë“±ë§Œ ê³µê²©ì´ ê°€ëŠ¥í•©ë‹ˆë‹¤.");
		SendMessage.broadcastMessage("(ì„¤ì •ì— ë”°ë¼ ê¼´ë“±ì„ ê³µê²©í• ë•Œ ë°ë¯¸ì§€ê°€ ë” ë“¤ì–´ê°ˆìˆ˜ ìžˆìŠµë‹ˆë‹¤.");
		Bukkit.broadcastMessage("");

	}

}
