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
			sender.sendMessage(ChatColor.DARK_RED + "OP 만 이용이 가능한 커멘드입니다.");
			return true;
		}

		String subCommand = arg3[0];

		if (subCommand.equalsIgnoreCase("Start")) {
			if (GameStatus.getStatus() == GameStatus.RUNNING) {
				sender.sendMessage(GameStatus.getMessage());
				return true;
			}
			SendMessage.broadcastMessage("게임이 " + ChatColor.GREEN + "시작됩니다.");

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
