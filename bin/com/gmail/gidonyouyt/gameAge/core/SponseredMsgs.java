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
			msgPool.add("이 컨텐츠는 기돈유 YT에서 만들어 진것입니다.");
			msgPool.add("개발자: 기돈유 (유튜브에서 기돈유 검색)");
			break;
		case (2):
			msgPool.add("이 컨텐츠는 기돈유 YT에서 만들어 진것입니다.");
			msgPool.add("개발자: 기돈유 (유튜브에서 기돈유 검색)");

			msgPool.add("본 서버는 기돈유 YT 에서 호스팅 중입니다.");
			msgPool.add("기돈유 YT에서 재미있는 컨텐츠 시청하시려면 유튜브에서 '기돈유' 검색해보세요");
			msgPool.add("꿀잼컨텐츠의 시작은 기돈유 TV 에서");
			msgPool.add("이런 재미있는 플러그인을 보시려면 유튜브에서 '기돈유 검색'");
			msgPool.add("유튜브에서 '기돈유'를 검색하시면 재미있는 컨텐츠를 시청하실수 있습니다.");
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

		Bukkit.broadcastMessage(ChatColor.YELLOW + "[개발진] " + ChatColor.RESET
				+ msg.replace("기돈유", ChatColor.LIGHT_PURPLE + "기돈유" + ChatColor.RESET));

	}

	private static int getRandomValue(int min, int max) {
		return (int) Math.floor(Math.random() * (max - min + 1)) + min;
	}

}
