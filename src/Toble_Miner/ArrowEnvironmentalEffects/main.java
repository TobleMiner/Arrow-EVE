package Toble_Miner.ArrowEnvironmentalEffects;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


public class main extends JavaPlugin
{
	private final entityListener entityListener = new entityListener();
	@Override
	public void onEnable()
	{
		Bukkit.getLogger().log(Level.INFO,"Enabling ArrowEnvironmentalEffects");
		Bukkit.getPluginManager().registerEvents(entityListener,this);
	}
	
	@Override
	public void onDisable()
	{
		Bukkit.getLogger().log(Level.INFO,"Disabling ArrowEnvironmentalEffects");
	}	
}
