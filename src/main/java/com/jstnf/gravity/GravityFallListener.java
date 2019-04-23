package com.jstnf.gravity;

import javafx.util.Pair;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class GravityFallListener implements Listener
{
	private Gravity plugin;

	public GravityFallListener(Gravity plugin)
	{
		this.plugin = plugin;
	}

	@EventHandler
	public void onFallDamage(EntityDamageEvent e)
	{
		if (e.getCause() == EntityDamageEvent.DamageCause.FALL)
		{
			String entityWorld = e.getEntity().getWorld().getName();
			for (Pair<World, Double> pair : plugin.affectedWorlds)
			{
				if (entityWorld.equalsIgnoreCase(pair.getKey().getName()))
				{
					e.setCancelled(true);
					return;
				}
			}
		}
	}
}