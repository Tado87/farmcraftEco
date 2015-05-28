package fr.farmcraft.farmcraftEco.Fonction;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.joda.time.DateTime;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;

import fr.farmcraft.farmcraftEco.FarmcraftEco;

public class RentCheck {
		public static FarmcraftEco Plugin;
		public RentCheck(FarmcraftEco Instance){
			Plugin = Instance;
		}
		
		public static Boolean RentExpirDateChecker() throws SQLException{
			
		 Statement checkrental = Plugin.connection.createStatement();
		 
		 ResultSet CR = checkrental.executeQuery("SELECT * FROM FarmcraftEco WHERE ExpirDate <= NOW()"); // check if an expirdate is before today's one
		 
			
			
			String Terrain = CR.getString("Terrain");
			String playername = CR.getString("Playername");   
			String worldBdd = CR.getString("World");            // get the string we need
			String Prix = CR.getString("Prix");
			String RentTime = CR.getString("RentTime");
			
			
			World world = Bukkit.getServer().getWorld(worldBdd);						// get the object we need
			
			OfflinePlayer player = Bukkit.getServer().getOfflinePlayer(playername);   
			
			
			int positionX = CR.getInt("positionX");
			int positionY = CR.getInt("positionY");						// get poition of the sign
			int positionZ = CR.getInt("positionZ");
			

			
			WorldGuardPlugin worldGuard = Plugin.getWorldGuard();
			
			RegionManager regionManager = worldGuard.getRegionManager(world);           // remove owner
			
			regionManager.getRegion(Terrain).getMembers().removePlayer(player.getName()); 	
			
			
			
			
			Statement removeRental = Plugin.connection.createStatement();
			
			removeRental.executeUpdate("UPDATE `RentRegion` SET `ExpirDate`=NULL, `Playername`=NULL WHERE Terrain=" + Terrain + ";");   // update DB
			
			Block block = world.getBlockAt(positionX, positionY, positionZ);	// update sign
			Sign s = (Sign) block.getState();
			
			s.setLine(0, ChatColor.BLUE + "[RentRegion]");
			
			s.setLine(1, Terrain);													
			
			s.setLine(2, Prix);
			
			s.setLine(3, RentTime);
			
			s.update();
			
		CR.next();   // go to next
		     
	    return true;
		}
}
