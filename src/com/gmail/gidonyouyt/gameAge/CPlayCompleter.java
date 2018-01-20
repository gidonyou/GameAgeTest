package com.gmail.gidonyouyt.gameAge;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class CPlayCompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		if (arg3.length == 2) {
			if (arg3[0].equalsIgnoreCase("u")) {
				ArrayList<String> list = new ArrayList<String>();
				if (!arg3[1].equals("")) {
					for (GameSettings settings : GameSettings.values()) {
						if (settings.name().toLowerCase().startsWith(arg3[1].toLowerCase()))
							list.add(settings.name());
					}
				} else {
					for (GameSettings settings : GameSettings.values()) {
						list.add(settings.name());
					}
				}
//				Collections.sort(list);
				return list;
			}
		}
		return null;
	}

}
