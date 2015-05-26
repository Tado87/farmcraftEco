package fr.farmcraft.farmcraftEco.Fonction;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.Bukkit;
import org.joda.time.DateTime;

import fr.farmcraft.farmcraftEco.FarmcraftEco;

public class RentCheck {
	
		public static FarmcraftEco Plugin;
		public RentCheck(FarmcraftEco Instance){
			Plugin = Instance;
		}
		
		
		FileConfiguration config = YamlConfiguration.loadConfiguration(new File("/plugins/FarmcraftEco/Rent/world.yml"));
		
		
		for(String key : config.getKeys("expirdate")) {
			 String expdate = config.getString("expirdate");
			 DateTime datenow = new DateTime();
			 DateTime expirdate = new DateTime(expdate);
			 if(expirdate >= datenow )
				 then get parent node 
				 evict player
			 
			 }
			 
		}
}