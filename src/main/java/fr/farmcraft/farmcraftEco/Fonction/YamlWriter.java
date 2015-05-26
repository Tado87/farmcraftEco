package fr.farmcraft.farmcraftEco.Fonction;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.farmcraft.farmcraftEco.FarmcraftEco;

public class YamlWriter {
	
	public static FarmcraftEco Plugin;
	public YamlWriter(FarmcraftEco Instance){
		Plugin = Instance;
	}
	public static Boolean YmlWriter(Double prix, String compte, String time, String World, String RG, String positionX, String positionY, String positionZ) {
			String none = ("none");
			FileConfiguration config = YamlConfiguration.loadConfiguration(new File("/plugins/FarmcraftEco/Rent/" + World + ".yml"));
			config.set(RG +".prix", prix);
			config.set(RG +".time", time);
			config.set(RG +".compte", compte);
			config.set(RG +".RentExpirations", none);
			config.set(RG +".X", positionX);
			config.set(RG +".Y", positionY);
			config.set(RG +".Z", positionZ);
			config.set(RG +"renter", none);
			
			try{
			    config.save(new File("/plugins/FarmcraftEco/Rent/" + World + ".yml"));
			} catch (IOException e) { e.printStackTrace(); }
			
		return Plugin.YmlWriterReturn;
		
	}
	public static Boolean YmlReader( World World, String RG) {
		
			FileConfiguration config = YamlConfiguration.loadConfiguration(new File("/plugins/FarmcraftEco/Rent/" + World + ".yml"));
			Double prix_Reader = config.getDouble(RG +".prix");
			Double time_Reader = config.getDouble(RG +".time");
			Double compte_Reader = config.getDouble(RG +".compte");
			Double SignX = config.getDouble(RG +".X");
			Double SignY = config.getDouble(RG +".Y");
			Double SignZ = config.getDouble(RG +".Z");
			
			
		return Plugin.YmlReaderReturn;
		
	}
}
