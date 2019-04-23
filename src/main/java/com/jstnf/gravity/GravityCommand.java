package com.jstnf.gravity;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GravityCommand implements CommandExecutor
{
	private Gravity plugin;

	private final String header = ChatColor.WHITE + "InfinityMiners " + ChatColor.AQUA + ChatColor.BOLD + "Gravity";
	private final String version = ChatColor.WHITE + "v1.0.0 - jstnf / pokeball92870 - 2019";

	public GravityCommand(Gravity plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (args.length != 1)
		{
			sendDefaultMessage(sender);
			return true;
		}

		String sub = args[0];
		if (sub.equalsIgnoreCase("toggle"))
		{
			if (!sender.hasPermission("gravity.toggle"))
			{
				sendDefaultMessage(sender);
			}
			else
			{
				plugin.running = !plugin.running;
				if (plugin.running)
				{
					sender.sendMessage(
							"Gravity effects have now been " + ChatColor.GREEN + "ENABLED" + ChatColor.WHITE + ".");
				}
				else
				{
					sender.sendMessage(
							"Gravity effects have now been " + ChatColor.RED + "DISABLED" + ChatColor.WHITE + ".");
				}
			}
		}
		else
		{
			sendDefaultMessage(sender);
		}

		return true;
	}

	private void sendDefaultMessage(CommandSender sender)
	{
		sender.sendMessage("");
		sender.sendMessage(header);
		sender.sendMessage(version);
		sender.sendMessage("");
	}
}