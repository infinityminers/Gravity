package com.jstnf.gravity;

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
		if (plugin.running)
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
								boolean newxchanged = newV.getX() < -0.001D || newV.getX() > 0.001D;
								boolean oldxchanged = oldV.getX() < -0.001D || oldV.getX() > 0.001D;
								if (newxchanged && oldxchanged)
								{
									newV.setX(oldV.getX());
								}

								boolean newzchanged = newV.getZ() < -0.001D || newV.getZ() > 0.001D;
								boolean oldzchanged = oldV.getZ() < -0.001D || oldV.getZ() > 0.001D;
								if (newzchanged && oldzchanged)
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