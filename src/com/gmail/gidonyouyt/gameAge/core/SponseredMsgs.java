package com.gmail.gidonyouyt.gameAge.core;

import java.util.ArrayList;

public class SponseredMsgs {
	
	private static ArrayList<String> msgPool = new ArrayList<>();
	private static int setupLv = 1;
//	0 - none;	1 - on;		2 - on/with server sponsored
	
	private static void setSponseredMessages() {
		switch (setupLv) {
		case(0):
			return;
		case(1):
			msgPool.add("");
		case(2):
			break;
		}
		
		
	}

}
