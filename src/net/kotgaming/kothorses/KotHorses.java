package net.kotgaming.kothorses;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class KotHorses extends JavaPlugin
{
	private KotHorsesEventHandler eventH = new KotHorsesEventHandler(this);
	private FileConfiguration config;

	static public double chanceSkeletonHorseWither = 1;
	static public double chanceSkeletonHorseWitherskeleton = 0.8;
	static public double chanceSkeletonHorseSkeleton = 0.25;
	static public double chanceSkeletonHorsePlayer = 0.2;
	
	static public double chanceZombieHorseCreeper = 0.1;
	static public double chanceZombieHorsePlayer = 0.2;
	static public double chanceZombieHorseZombie = 0.1;
	static public double chanceZombieHorsePigZombie = 0.1;

	@Override
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(eventH, this);

		if (config == null)
		{
			config = this.getConfig();
		}

		config.addDefault("chanceSkeletonHorse.wither", KotHorses.chanceSkeletonHorseWither);
		config.addDefault("chanceSkeletonHorse.witherskeleton", KotHorses.chanceSkeletonHorseWitherskeleton);
		config.addDefault("chanceSkeletonHorse.skeleton", KotHorses.chanceSkeletonHorseSkeleton);
		config.addDefault("chanceSkeletonHorse.player", KotHorses.chanceSkeletonHorsePlayer);

		config.addDefault("chanceZombieHorse.zombie", KotHorses.chanceZombieHorseZombie);
		config.addDefault("chanceZombieHorse.pigzombie", KotHorses.chanceZombieHorsePigZombie);
		config.addDefault("chanceZombieHorse.creeper", KotHorses.chanceZombieHorseCreeper);
		config.addDefault("chanceZombieHorse.player", KotHorses.chanceZombieHorsePlayer);
		
		loadConfigFromDisk();
	}

	void loadConfigFromDisk()
	{
		getConfig().options().copyDefaults(true);
		saveConfig();

		KotHorses.chanceSkeletonHorseWither = getConfig().getDouble("chanceSkeletonHorse.wither");
		KotHorses.chanceSkeletonHorseWitherskeleton = getConfig().getDouble("chanceSkeletonHorse.witherskeleton");
		KotHorses.chanceSkeletonHorseSkeleton = getConfig().getDouble("chanceSkeletonHorse.skeleton");
		KotHorses.chanceSkeletonHorsePlayer = getConfig().getDouble("chanceSkeletonHorse.player");
		
		KotHorses.chanceZombieHorseZombie = getConfig().getDouble("chanceZombieHorse.zombie");
		KotHorses.chanceZombieHorsePigZombie = getConfig().getDouble("chanceZombieHorse.pigzombie");
		KotHorses.chanceZombieHorseCreeper = getConfig().getDouble("chanceZombieHorse.creeper");
		KotHorses.chanceZombieHorsePlayer = getConfig().getDouble("chanceZombieHorse.player");

	}

	void reloadConfigFromDisk()
	{
		reloadConfig();
		loadConfigFromDisk();
	}

	@Override
	public void onDisable()
	{
		this.config = null;
	}

}
