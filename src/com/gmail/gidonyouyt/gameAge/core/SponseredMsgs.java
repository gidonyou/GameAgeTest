package com.gmail.gidonyouyt.gameAge.core;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import com.gmail.gidonyouyt.gameAge.GameSettings;

public class SponseredMsgs {

	private static ArrayList<String> msgPool = new ArrayList<>();
	private static int setupLv = (int) GameSettings.DEV_CREDIT.value();
	private static int timeSec = 300;
	// 0 - none; 1 - on; 2 - on/with server sponsored

	private static void setSponseredMessages() {
		msgPool.clear();

		switch (setupLv) {
		case (0):
			return;
		case (1):
			msgPool.add("ì´ ì»¨í…ì¸ ëŠ” ê¸°ëˆìœ  YTì—ì„œ ë§Œë“¤ì–´ ì§„ê²ƒìž…ë‹ˆë‹¤.");
			msgPool.add("ê°œë°œìž: ê¸°ëˆìœ  (ìœ íŠœë¸Œì—ì„œ ê¸°ëˆìœ  ê²€ìƒ‰)");
			break;
		case (2):
			msgPool.add("ì´ ì»¨í…ì¸ ëŠ” ê¸°ëˆìœ  YTì—ì„œ ë§Œë“¤ì–´ ì§„ê²ƒìž…ë‹ˆë‹¤.");
			msgPool.add("ê°œë°œìž: ê¸°ëˆìœ  (ìœ íŠœë¸Œì—ì„œ ê¸°ëˆìœ  ê²€ìƒ‰)");

			msgPool.add("ë³¸ ì„œë²„ëŠ” ê¸°ëˆìœ  YT ì—ì„œ í˜¸ìŠ¤íŒ… ì¤‘ìž…ë‹ˆë‹¤.");
			msgPool.add("ê¸°ëˆìœ  YTì—ì„œ ìž¬ë¯¸ìžˆëŠ” ì»¨í…ì¸  ì‹œì²­í•˜ì‹œë ¤ë©´ ìœ íŠœë¸Œì—ì„œ 'ê¸°ëˆìœ ' ê²€ìƒ‰í•´ë³´ì„¸ìš”");
			msgPool.add("ê¿€ìž¼ì»¨í…ì¸ ì˜ ì‹œìž‘ì€ ê¸°ëˆìœ  TV ì—ì„œ");
			msgPool.add("ì´ëŸ° ìž¬ë¯¸ìžˆëŠ” í”ŒëŸ¬ê·¸ì¸ì„ ë³´ì‹œë ¤ë©´ ìœ íŠœë¸Œì—ì„œ 'ê¸°ëˆìœ  ê²€ìƒ‰'");
			msgPool.add("ìœ íŠœë¸Œì—ì„œ 'ê¸°ëˆìœ 'ë¥¼ ê²€ìƒ‰í•˜ì‹œë©´ ìž¬ë¯¸ìžˆëŠ” ì»¨í…ì¸ ë¥¼ ì‹œì²­í•˜ì‹¤ìˆ˜ ìžˆìŠµë‹ˆë‹¤.");
			break;
		}

	}

	public static void run() {
		if (setupLv == 0)
			return;

		if (msgPool.isEmpty())
			setSponseredMessages();

		if (timeSec > 0) {
			timeSec--;
			return;
		}

		timeSec = 300;
		int random = getRandomValue(0, msgPool.size() - 1);
		String msg = msgPool.get(random);

		Bukkit.broadcastMessage(ChatColor.YELLOW + "[ê°œë°œì§„] " + ChatColor.RESET
				+ msg.replace("ê¸°ëˆìœ ", ChatColor.LIGHT_PURPLE + "ê¸°ëˆìœ " + ChatColor.RESET));

	}

	private static int getRandomValue(int min, int max) {
		return (int) Math.floor(Math.random() * (max - min + 1)) + min;
	}

}
