package com.gmail.gidonyouyt.gameAge;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import com.gmail.gidonyouyt.gameAge.core.SendMessage;

import net.md_5.bungee.api.ChatColor;

public enum GameSettings {

	GAME_TIME("게임 진행 시간 (분)", 0),// Minutes
	COUNTDOWN_TIME("게임 카운트 다운 시간 (초)", 5),
	MERCY_TIME("초기 버프 시간 (초)", 15), // Seconds
	MIN_LIFE("최소 수명 시간 (분)", 6), // Minute
	MAX_LIFE("최고 수명 시간 (분)", 12), // Minute
	STEAL_TIME_SEC("뺏을수 있는 시간 (초)", 30), // Seconds
	STEAL_IMMUNE_TIME_SEC("뻿는것 보호받는 시간 (초)", 45), // Seconds
	EARN_SECOUNDS_TIME("시간 추가 아이템 시간 (초)", 20), // Seconds
	AUTO_BORDER("자동 게임구역 제한 (분) [0 - disable]", 3), // Time in Minute -- 0 none;
	COMPASS_DURABILITY("나침판 작동시간 (초)", 30),
	FORCE_DAMAGE_DISTANCE("강제 데미지 거리", 60),
	FORCE_DAMAGE_TIME("강제 데미지 경고 시간 (초)", 20),
	NUM_CHEST("아이템 상자 갯수", 70), // Number
	MAX_CHEST_FAIL("상자 설치시 최대 실패 갯수", 1000000),
	AUTO_REGEN("게임중 자동 회복 (B)", 0), // Boolean 0/1
	SMART_DISTRIBUTE("확률지정 배분 (B)", 1), // 0/1
	MAP_LENGTH("맵 크기", 240), // Blocks
	MINY("최소 Y", 20), // Blocks
	MAXY("최대 Y", 180), // Blocks
	ALLOW_BREAK_SPLATE("돌 압력판을 제거할수 있게 해줍니다. (B)", 0),
	TIME_RECOVER_SPLATE("돌 압력판 복구시간 (초) [-1 : disable]", 15),
	ADD_NULL_PLAYER("외톨이 기돈유를 위한 NULL 플레이어 (B)", 0),
	FIRST_LAST_DAMAGE_MULT("1등이 꼴등을 공격할시 데미지 곱하기 [-1 : disable]", 1.5),
	WEBPAGE_ON("웹페이지 UI 활성화 (B) [실행시 주의: 배타이므로 애러 가능]", 0),
	WEBPAGE_PORT("웹페이지 UI 포트번호", 8000),
	WEBPAGE_BLANK("웹페이지 초반 감춤 시간 (초) [아직 미적용]", 0),
	WEBPAGE_DEBUG("웹페이지 개발용 테스트/디버그 (B)", 0),
	DEV_CREDIT("개발진대해 광고해줍니다 [0/1/2] - 미작동", 0),
	BROADCAST_CONFIG("게임 시작시 설정을 OP 에게 알려줍니다 (B)", 0),

	IS_INVISIBILITY("아이템 투명화 시계 지속시간 (초)", 10),
	LEGACY("꼬리잡기 룰처럼 진행합니다. (B)", 0),
	EASTEREGG("베타테스터를 위한 작은 선물 (특별 리소스팩 필요)", 0),
	ADD_ENCHANTMENT("아이템에 인첸트를 추가합니다 (B) ", 0);

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
		SendMessage.sendMessageOP("===== 게임 설정 =====");
		for (GameSettings settings : GameSettings.values()) {
			SendMessage.sendMessageOP(settings.desc + " - " + settings.value());
		}
	}

	public static void gameInfo() {
		// 게임 설명
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage("");
		SendMessage.sendCreditInfo();
		SendMessage.broadcastMessage(ChatColor.RED + "주의: 본 플러그인은 저 혼자 쓰려고 만든 것입니다.");
		SendMessage.broadcastMessage(ChatColor.RED + "주의: 여려분이 진행할때 문제가 발생할수 있습니다.");
		SendMessage.broadcastMessage(ChatColor.RED + "주의: 즉시 개발자에게 알려주세요.");
		SendMessage.broadcastMessage(ChatColor.RED + "gidonyou@naver.com  또는  gidonyouyt@gmail.com");
		SendMessage.broadcastMessage("");
		SendMessage.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + " == 게임 설명 == ");
		SendMessage.broadcastMessage("이 게임은 제한시간이 있는 꼬리 잡기입니다.");
		SendMessage.broadcastMessage("자신에게 지정된 제한시간이 끝나면 아웃되는데요");
		SendMessage.broadcastMessage("아이템을 획득해서 시간을 늘리던지");
		SendMessage.broadcastMessage("자신보다 시간이 많이 남은 사람을 찾아서 아웃시키면");
		SendMessage.broadcastMessage("아웃된 플레이어의 남은 시간 반절을 얻게 됩니다.");
		SendMessage.broadcastMessage("하지만 자신보다 시간이 적게 남은 사람을 공격한다면");
		SendMessage.broadcastMessage("거기에 2배에 해당되는 데미지를 입으니 조심하세요");
		SendMessage.broadcastMessage("또한, 1등은 꼴등만 공격이 가능합니다.");
		SendMessage.broadcastMessage("(설정에 따라 꼴등을 공격할때 데미지가 더 들어갈수 있습니다.");
		Bukkit.broadcastMessage("");

	}

}
