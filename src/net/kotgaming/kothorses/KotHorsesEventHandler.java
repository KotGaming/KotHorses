package net.kotgaming.kothorses;

import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.entity.Zombie;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class KotHorsesEventHandler implements Listener
{
	@SuppressWarnings("unused")
	private KotHorses parent;

	public KotHorsesEventHandler(KotHorses kotHorses)
	{
		parent = kotHorses;
	}

	@EventHandler
	public void onSomethingLivingWasKilled(EntityDeathEvent e) throws InterruptedException
	{
		if (e.getEntity() instanceof Horse && ((Horse) e.getEntity()).getVariant() == Variant.HORSE) // ein
																										// Pferd
																										// starb
		{
			Horse h = (Horse) e.getEntity();
			
			Entity killer = ((EntityDamageByEntityEvent) e.getEntity().getLastDamageCause())
					.getDamager();

			switch (killer.getType())
			{
			case WITHER:
				// Wither
				Bukkit.getServer().broadcastMessage("Ein Pferd wurde von einem Wither gekillt.");
				spawnHorseWithChance(h.getLocation(), KotHorses.chanceSkeletonHorseWither, h,
						Variant.SKELETON_HORSE);
				break;

			case SKELETON:
				if (((Skeleton) killer).getSkeletonType() == SkeletonType.NORMAL)
				{
					// normales Skeleton
					Bukkit.getServer().broadcastMessage(
							"Ein Pferd wurde von einem Skeleton gekillt.");
					spawnHorseWithChance(h.getLocation(), KotHorses.chanceSkeletonHorseSkeleton, h,
							Variant.SKELETON_HORSE);
				}
				else if (((Skeleton) killer).getSkeletonType() == SkeletonType.WITHER)
				{
					// witherskeleton
					Bukkit.getServer().broadcastMessage(
							"Ein Pferd wurde von einem Witherskeleton gekillt.");
					spawnHorseWithChance(h.getLocation(),
							KotHorses.chanceSkeletonHorseWitherskeleton, h, Variant.SKELETON_HORSE);
				}
				break;
			case CREEPER:
				// Creeper
				Bukkit.getServer().broadcastMessage("Ein Pferd wurde von einem Creeper gekillt.");
				spawnHorseWithChance(h.getLocation(), KotHorses.chanceZombieHorseCreeper, h,
						Variant.UNDEAD_HORSE);
				break;
			case ZOMBIE:
				// Zombie
				Bukkit.getServer().broadcastMessage("Ein Pferd wurde von einem Zombie gekillt.");
				spawnHorseWithChance(h.getLocation(), KotHorses.chanceZombieHorseZombie, h,
						Variant.UNDEAD_HORSE);
				break;
			case PIG_ZOMBIE:
				// PigZombie
				Bukkit.getServer().broadcastMessage("Ein Pferd wurde von einem PigZombie gekillt.");
				spawnHorseWithChance(h.getLocation(), KotHorses.chanceZombieHorsePigZombie, h,
						Variant.UNDEAD_HORSE);
				break;
			case PLAYER:
				if (((Player) killer).getItemInHand().getType() == Material.BONE)
				{
					// Killer ist Player und ItemInHand ist BONE
					String message = String.format("Ein Pferd wurde von %s gekillt mit Bone in der Hand.", 
							((Player)killer).getName());
					Bukkit.getServer().broadcastMessage(message);
					spawnHorseWithChance(h.getLocation(), KotHorses.chanceSkeletonHorsePlayer, h,
							Variant.SKELETON_HORSE);
				}
				break;

			default:
				break;
			}
		}
	}

	private void copyHorseAttributesToNewHorse(Horse h, Horse newh)
	{
		newh.resetMaxHealth();
		newh.setAge(h.getAge());
		newh.setCustomName(h.getCustomName());
		newh.setCustomNameVisible(h.isCustomNameVisible());
		newh.setDomestication(h.getDomestication());
		newh.setMaxDomestication(h.getMaxDomestication());
		newh.setOwner(h.getOwner());
		newh.setPassenger(h.getPassenger());
		newh.setTarget(h.getTarget());
				
		newh.setTamed(h.isTamed());
		if (h.getInventory().contains(Material.SADDLE))
		{
			h.getInventory().setSaddle(null);
			newh.getInventory().setSaddle(new ItemStack(Material.SADDLE));
		}
	}

	private Horse spawnHorseWithChance(Location loc, double chance, Horse oldHorse,
			Horse.Variant newHorseV)
	{
		Random r = new Random();
		double randVal = r.nextDouble();
		if (randVal <= chance)
		{
			World world = org.bukkit.Bukkit.getWorld("world");

			oldHorse.setHealth(0.0);

			Horse h = (Horse) world.spawnEntity(loc, EntityType.HORSE);
			copyHorseAttributesToNewHorse(oldHorse, h);
						
			h.setVariant(newHorseV);
			String message = String.format("%s erschaffen!", newHorseV.toString());
			Bukkit.getServer().broadcastMessage(message);

			return h;
		}
		else
		{
			return null;
		}
	}

	@EventHandler
	public void onSomethingLivingWasDamaged(EntityDamageByEntityEvent e)
	{
		if (e.getEntity() instanceof Player
				&& (e.getDamager() instanceof Zombie || e.getDamager() instanceof PigZombie))
		{
			Player p = (Player) e.getEntity();
			if (p.getVehicle() instanceof Horse
					&& ((Horse) p.getVehicle()).getVariant() == Variant.HORSE)
			{
				// uebertrage Schaden an Horse
				Horse h = (Horse) p.getVehicle();
				h.damage(e.getDamage(), e.getDamager());
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler	
	public void onPlayerInteractHorse(PlayerInteractEntityEvent event)
	{
		Player p = event.getPlayer();
		Entity e = event.getRightClicked();		

		if (e instanceof Horse)
		{
			Horse h = (Horse) e;
			
			if (h.getVariant() == Variant.SKELETON_HORSE || h.getVariant() == Variant.UNDEAD_HORSE)
			{
				LivingEntity livingE = (LivingEntity) e;

				if ( p.getItemInHand().getType() == Material.LEASH && !livingE.isLeashed())
				{
					livingE.setLeashHolder(p);
					if (p.getItemInHand().getAmount() > 1)
					{
						p.getItemInHand().setAmount(p.getItemInHand().getAmount()-1);				
					}
					else
					{
						p.setItemInHand(new ItemStack(Material.AIR));
					}	
					event.setCancelled(true);
				}
				else
				{					
					if (livingE.isLeashed() && livingE.getLeashHolder() == p)
					{
						livingE.setLeashHolder(null);
						livingE.getWorld().dropItemNaturally(livingE.getLocation(), new ItemStack(Material.LEASH));					
						event.setCancelled(true);					
					}
				}		
			}
			else //Pferd ist nicht Skelett oder Zombie
			{
				if (h.getVariant() == Variant.HORSE)//Pferd ist normales Horse
				{

					if (p.getItemInHand().getType() == Material.ROTTEN_FLESH)
					{

						String message = String.format("%s füttert Horse mit Rotten flesh",  p.getName());
						Bukkit.getServer().broadcastMessage(message);
						spawnHorseWithChance(h.getLocation(), KotHorses.chanceZombieHorsePlayer, h,
								Variant.UNDEAD_HORSE);						
						
						if (p.getItemInHand().getAmount() > 1)
						{
							p.getItemInHand().setAmount(p.getItemInHand().getAmount()-1);				
						}
						else
						{
							p.setItemInHand(new ItemStack(Material.AIR));
						}	
						event.setCancelled(true);
					}
				}
			}
		}
	}
}
