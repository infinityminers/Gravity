package com.jstnf.gravity;

import javafx.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.List;

public class Gravity extends JavaPlugin
{
	private GravityEngine gravityTask;
	private GravitySettings settings;

	public boolean doGravity;
	public ArrayList<Pair<World, Double>> affectedWorlds;

	public Gravity()
	{
		gravityTask = new GravityEngine(this);
		affectedWorlds = new ArrayList<>();
		doGravity = true;
	}

	@Override
	public void onEnable()
	{
		settings = new GravitySettings(this);
		if (!settings.generateConfig())
		{
			return;
		}

		doGravity = settings.config.getBoolean(GravitySettings.Defaults.ENABLED_ON_START.getPath(),
				(boolean) GravitySettings.Defaults.ENABLED_ON_START.getDefaultValue());

		validateWorlds();

		GravityCommand gravityCommand = new GravityCommand(this);
		this.getCommand("gravity").setExecutor(gravityCommand);

		BukkitScheduler scheduler = getServer().getScheduler();
		scheduler.scheduleSyncRepeatingTask(this, gravityTask, 0L, 1L);

		getServer().getPluginManager().registerEvents(new GravityFallListener(this), this);
	}

	@Override
	public void onDisable()
	{
		getLogger().info("Gravity has been disabled.");
	}

	/**
	 * Get the entries from config and assign the Worlds and gravity values to be used in the GravityEngine.
	 */
	private void validateWorlds()
	{
		List<String> entries = settings.config.getStringList(GravitySettings.Defaults.WORLDS.getPath());
		if (entries == null)
		{
			entries = new ArrayList<String>();
		}

		for (String s : entries)
		{
			int colonIndex = s.indexOf(":");
			if (colonIndex != -1 && s.length() >= 3)
			{
				String worldName = s.substring(0, colonIndex);
				String g = s.substring(colonIndex + 1);
				if (Bukkit.getWorld(worldName) != null && isDouble(g))
				{
					World world = Bukkit.getWorld(worldName);
					double gravity = Double.parseDouble(g);
					Pair<World, Double> pair = new Pair(world, gravity);
					affectedWorlds.add(pair);
				}
			}
		}
	}

	/**
	 * Returns whether or not the String s is a double.
	 * @param s - A string.
	 * @return - If s is a double, return true, false otherwise.
	 */
	private boolean isDouble(String s)
	{
		try
		{
			Double.parseDouble(s);
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}
}