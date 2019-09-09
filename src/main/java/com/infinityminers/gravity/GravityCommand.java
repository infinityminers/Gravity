package com.infinityminers.gravity;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GravityCommand implements CommandExecutor
{
	private Gravity plugin;

	/* Messages for default /gravity message */
	private final String header = ChatColor.WHITE + "InfinityMiners " + ChatColor.AQUA + ChatColor.BOLD + "Gravity";
	private final String version = ChatColor.WHITE + "v1.0.2 - jstnf / pokeball92870 - 2019";

	public GravityCommand(Gravity plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		/* Check arguments */
		if (args.length == 1)
		{
			/* /gravity toggle */
			String sub = args[0];
			if (sub.equalsIgnoreCase("toggle"))
			{
				/* Check permissions */
				if (sender.hasPermission("gravity.toggle"))
				{
					plugin.doGravity = !plugin.doGravity;
					if (plugin.doGravity)
					{
						sender.sendMessage(
								"Gravity effects have now been " + ChatColor.GREEN + "ENABLED" + ChatColor.WHITE + ".");
					}
					else
					{
						sender.sendMessage(
								"Gravity effects have now been " + ChatColor.RED + "DISABLED" + ChatColor.WHITE + ".");
					}
					return true;
				}
			}
		}

		sendDefaultMessage(sender);
		return true;
	}

	/**
	 * Message displayed to sender if player enters incorrect command syntax or doesn't have permissions.
	 * @param sender - The command sender.
	 */
	private void sendDefaultMessage(CommandSender sender)
	{
		sender.sendMessage("");
		sender.sendMessage(header);
		sender.sendMessage(version);
		sender.sendMessage("");
	}
}
