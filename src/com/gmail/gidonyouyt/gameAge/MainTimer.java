package com.gmail.gidonyouyt.gameAge;

import com.gmail.gidonyouyt.gameAge.core.SponseredMsgs;
import com.gmail.gidonyouyt.gameAge.events.BlockBreak;

public class MainTimer implements Runnable {

	@Override
	public void run() {
		Sequence.update();
		GameBorder.forceBarUpdate();
		BlockBreak.autoRecover();
		SponseredMsgs.run();
	}

}
