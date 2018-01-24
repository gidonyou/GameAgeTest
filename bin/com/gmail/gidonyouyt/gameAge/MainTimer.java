package com.gmail.gidonyouyt.gameAge;

import com.gmail.gidonyouyt.gameAge.events.BlockBreak;
import com.gmail.gidonyouyt.gameAge.events.PlayerInteract;

public class MainTimer implements Runnable {

	@Override
	public void run() {
		Sequence.update();
		GameBorder.forceBarUpdate();
		BlockBreak.autoRecover();
		PlayerInteract.update();
//		SponseredMsgs.run();
	}

}
