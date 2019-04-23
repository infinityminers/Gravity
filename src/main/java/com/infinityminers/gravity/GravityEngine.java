package com.infinityminers.gravity;

import javafx.util.Pair;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

public class GravityEngine implements Runnable
{
	private Gravity plugin;

	private HashMap<UUID, Vector> velocities;
	private HashMap<UUID, Boolean> onGround;

	public GravityEngine(Gravity plugin)
	{
		this.plugin = plugin;
		velocities = new HashMap<>();
		onGround = new HashMap<>();
	}

	@Override
	public void run()
	{
		/* Check if plugin is applying gravity changes */
		if (plugin.doGravity)
		{
			for (Pair<World, Double> p : plugin.affectedWorlds)
			{
				World w = p.getKey();
				double gravity = p.getValue();
				Iterator<Entity> entIter = w.getEntities().iterator();

				while (entIter.hasNext())
				{
					Entity e = entIter.next();
					Vector newV = e.getVelocity().clone();
					UUID uuid = e.getUniqueId();

					if (velocities.containsKey(uuid) && onGround.containsKey(uuid) && !e.isOnGround() && !e
							.isInsideVehicle() && !((e instanceof Player) && ((Player) e).isFlying()))
					{
						Vector oldV = velocities.get(uuid);
						Vector pos;
						if (!onGround.get(uuid))
						{
							pos = oldV.clone();
							pos.subtract(newV);
							double dy = pos.getY();

							if (dy > 0.0D && (newV.getY() < -0.01D || newV.getY() > 0.01D))
							{
								newV.setY(oldV.getY() - dy * gravity);
								boolean newXChanged = newV.getX() < -0.001D || newV.getX() > 0.001D;
								boolean oldXChanged = oldV.getX() < -0.001D || oldV.getX() > 0.001D;
								if (newXChanged && oldXChanged)
								{
									newV.setX(oldV.getX());
								}

								boolean newZChanged = newV.getZ() < -0.001D || newV.getZ() > 0.001D;
								boolean oldZChanged = oldV.getZ() < -0.001D || oldV.getZ() > 0.001D;
								if (newZChanged && oldZChanged)
								{
									newV.setZ(oldV.getZ());
								}

								e.setVelocity(newV.clone());
							}
						}

						e.setVelocity(newV.clone());
					}

					this.velocities.put(uuid, newV.clone());
					this.onGround.put(uuid, e.isOnGround());
				}
			}
		}
	}
}