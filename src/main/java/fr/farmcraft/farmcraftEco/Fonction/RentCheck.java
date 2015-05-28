package fr.farmcraft.farmcraftEco.Fonction;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.joda.time.DateTime;

import fr.farmcraft.farmcraftEco.FarmcraftEco;

public class RentCheck {
		public static FarmcraftEco Plugin;
		public RentCheck(FarmcraftEco Instance){
			Plugin = Instance;
		}
		
		public static Boolean RentExpirDateChecker(Player player, String playerName, Sign s) throws SQLException{
			
		 Statement checkrental = Plugin.connection.createStatement();
		 
		 ResultSet CR = checkrental.executeQuery("SELECT * FROM FarmcraftEco WHERE ExpirDate >= NOW()");
		 
			int test1 = CR.getInt("test1");
			
			String test = CR.getString("test");
			
			//do some work
		     CR.next();
			 return true;
		}
}
