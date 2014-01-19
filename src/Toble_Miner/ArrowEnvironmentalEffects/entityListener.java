package Toble_Miner.ArrowEnvironmentalEffects;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.Vector;

public class entityListener implements Listener
{
	@EventHandler
	void onProjectileHit(ProjectileHitEvent e)
	{
		Projectile p = e.getEntity();
		if(p instanceof Arrow)
		{
			Location loc = p.getLocation();
			Vector vel = p.getVelocity();
			Double speed = vel.length();
			World world = p.getWorld();
			int cycles = (int)Math.ceil(speed*2d);
			boolean blocked = false; //Is set to true when a solid block blocks the arrows path. Prevents the arrow from breaking glass after hitting a solid block.
			for(int i=0;i<=cycles;i++) //One more cycle than value of cycles so I can also scan the arrows current position
			{
				for(int j=0;j<100;j++)
				{
					Location currentLoc = loc.clone().add(vel.toLocation(world).clone().multiply(1d/speed).multiply(i+(((double)j))/100d));
					Block b = world.getBlockAt(currentLoc);
					if((b != null))
					{
						if((b.getType().equals(Material.GLASS) || b.getType().equals(Material.THIN_GLASS)) && !blocked) //I think usually glass isn't too flammable :D
						{
							if(b.getType().equals(Material.THIN_GLASS))
							{
								b.setType(Material.AIR);
							}
							else
							{
								b.setType(Material.THIN_GLASS);
								cycles--;
								break;
							}
							world.playEffect(currentLoc,Effect.POTION_BREAK,0);
						}
						else if(b.getType().isFlammable() && (p.getFireTicks() > 0))
						{
							world.playEffect(currentLoc,Effect.MOBSPAWNER_FLAMES,0);
							world.playEffect(currentLoc,Effect.SMOKE,i);
							world.playEffect(currentLoc,Effect.BLAZE_SHOOT,i);
							Block bl = world.getBlockAt(b.getLocation().clone().add(new Location(world,0,1,0)));
							bl.setType(Material.FIRE);
							cycles -= 2;
						}
						if(b.getType() != Material.THIN_GLASS && b.getType() != Material.AIR)
						{
							blocked = true;
						}
					}
				}
			}
			if(p.getFireTicks() > 0)
			{
				p.remove();
			}
		}
	}
}
