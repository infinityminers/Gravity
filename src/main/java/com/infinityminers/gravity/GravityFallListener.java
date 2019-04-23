package com.infinityminers.gravity;

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
		/* Check damage cause */
		if (e.getCause() == EntityDamageEvent.DamageCause.FALL)
		{
			/* Check if entity is in an affected world */
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