package com.gmail.gidonyouyt.gameAge.core;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.gmail.gidonyouyt.gameAge.Sequence;

public class CGame implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub

		if (arg3.length == 0)
			return false;
		
		if (!sender.isOp()) {
			sender.sendMessage(ChatColor.DARK_RED + "OP Ã«Â§Å’ Ã¬ÂÂ´Ã¬Å¡Â©Ã¬ÂÂ´ ÃªÂ°â‚¬Ã«Å Â¥Ã­â€¢Å“ Ã¬Â»Â¤Ã«Â©ËœÃ«â€œÅ“Ã¬Å¾â€¦Ã«â€¹Ë†Ã«â€¹Â¤.");
			return true;
		}

		String subCommand = arg3[0];

		if (subCommand.equalsIgnoreCase("Start")) {
			if (GameStatus.getStatus() == GameStatus.RUNNING) {
				sender.sendMessage(GameStatus.getMessage());
				return true;
			}
			SendMessage.broadcastMessage("ÃªÂ²Å’Ã¬Å¾â€žÃ¬ÂÂ´ " + ChatColor.GREEN + "Ã¬â€¹Å“Ã¬Å¾â€˜Ã«ÂÂ©Ã«â€¹Ë†Ã«â€¹Â¤.");

			// TODO test
			Sequence.start();

			return true;

		} else if (subCommand.equalsIgnoreCase("Stop")) {
			Sequence.stop();

			return true;

		} else if (subCommand.equalsIgnoreCase("Restart")) {
			Sequence.stop();
			Sequence.start();

			return true;

		} else if (subCommand.equalsIgnoreCase("status")) {
			if (sender instanceof ConsoleCommandSender) {
				SendMessage.logConsole(GameStatus.getMessage());
			}else if (sender instanceof Player) {
				SendMessage.sendMessagePlayer((Player)sender, GameStatus.getMessage());
			}else {
				sender.sendMessage(GameStatus.getMessage());
			}
			return true;
		}

		return false;
	}

}
