package net.kotgaming.kothorses;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class KotHorses extends JavaPlugin
{
	private KotHorsesEventHandler eventH = new KotHorsesEventHandler(this);
	private FileConfiguration config;

	static private double chanceSkeletonHorseWither = 1;
	static private double chanceSkeletonHorseWitherskeleton = 0.8;
	static private double chanceSkeletonHorseSkeleton = 0.25;
	static private double chanceSkeletonHorseCreeper = 0.1;
	static private double chanceSkeletonHorsePlayer = 0.2;
	
	static private double chanceZombieHorseWither = 1;
	static private double chanceZombieHorseWitherskeleton = 0.8;
	static private double chanceZombieHorseSkeleton = 0.25;
	static private double chanceZombieHorseCreeper = 0.1;
	static private double chanceZombieHorsePlayer = 0.2;
	
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
		config.addDefault("chanceSkeletonHorse.creeper", KotHorses.chanceSkeletonHorseCreeper);
		config.addDefault("chanceSkeletonHorse.player", KotHorses.chanceSkeletonHorsePlayer);

		config.addDefault("chanceZombieHorse.wither", KotHorses.chanceZombieHorseWither);
		config.addDefault("chanceZombieHorse.witherskeleton", KotHorses.chanceZombieHorseWitherskeleton);
		config.addDefault("chanceZombieHorse.skeleton", KotHorses.chanceZombieHorseSkeleton);
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
		KotHorses.chanceSkeletonHorseCreeper = getConfig().getDouble("chanceSkeletonHorse.creeper");
		KotHorses.chanceSkeletonHorsePlayer = getConfig().getDouble("chanceSkeletonHorse.player");
		
		KotHorses.chanceZombieHorseWither = getConfig().getDouble("chanceZombieHorse.wither");
		KotHorses.chanceZombieHorseWitherskeleton = getConfig().getDouble("chanceZombieHorse.witherskeleton");
		KotHorses.chanceZombieHorseSkeleton = getConfig().getDouble("chanceZombieHorse.skeleton");
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
