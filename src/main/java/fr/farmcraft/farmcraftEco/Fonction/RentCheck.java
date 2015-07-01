package fr.farmcraft.farmcraftEco.Fonction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;

import fr.farmcraft.farmcraftEco.FarmcraftEco;

public class RentCheck implements CommandExecutor {
	
		public static FarmcraftEco Plugin;
		public RentCheck(FarmcraftEco Instance){
			Plugin = Instance;
		
		}
		
		public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args)
		{
			
			
			if (!(sender instanceof Player)) {
		    	
				FarmcraftEco.log.info("Seuls les joueurs peuvent utilisés cette commande");
	      
				return true;
	      
			}
	    
			if (command.getName().equalsIgnoreCase("debug")) {
				
				try {
					RentExpirDateChecker();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			return true;
			}
			
		return false;
		}
public static void  RentExpirDateChecker() throws SQLException{
			
			String Terrain = "";
			String playername = "";   
			String worldBdd = "";            // get the string we need
			String Prix = "";
			String RentTime = "";
			int positionX = 0;
			int positionY = 0;
			int positionZ = 0;
		
		 Statement RentCheck = Plugin.connection.createStatement();
		 ResultSet CR = RentCheck.executeQuery("SELECT * FROM RentRegion WHERE ExpirDate <= NOW()"); // check if an expirdate is before today's one
		 
		 while (CR.next()) {
			 if(CR.getString("Playername") != null){   			// get the string we need
				playername = CR.getString("Playername"); 
			 }
			 if(CR.getString("World") != null){
				worldBdd = CR.getString("World");
			 }
			 if(CR.getString("Prix") != null){  
				Prix = CR.getString("Prix");
			 }
			if(CR.getString("RentTime") != null){  
				RentTime = CR.getString("RentTime");
			}
			if(CR.getString("Terrain") != null){  
				Terrain = CR.getString("Terrain");
			}
			
			
			World world = Bukkit.getServer().getWorld(worldBdd);						// get the object we need
			
			OfflinePlayer player = Bukkit.getServer().getOfflinePlayer(playername);   
			
			 
			positionX = CR.getInt("positionX");
	
			positionY = CR.getInt("positionY");	
                    											// get position of the sign
			positionZ = CR.getInt("positionZ");

			

			
			WorldGuardPlugin worldGuard = Plugin.getWorldGuard();
			
			RegionManager regionManager = worldGuard.getRegionManager(world);           // remove owner
			
			regionManager.getRegion(Terrain).getMembers().removePlayer(playername); 	
			
			Statement RentCheckUpadte = Plugin.connection.createStatement();
			
			RentCheckUpadte.executeUpdate("UPDATE `RentRegion` SET `ExpirDate`=NULL, `Playername`=NULL WHERE Terrain='" + Terrain + "'");   // update DB
			
			Block block = world.getBlockAt(positionX, positionY, positionZ);	// update sign
			Sign s = (Sign) block.getState();
			
			s.setLine(0, ChatColor.BLUE + "[RentRegion]");
			
			s.setLine(1, Terrain);													
			
			s.setLine(2, Prix);
			
			s.setLine(3, RentTime);
			
			s.update();
			
            // go to next
		 }
		 return;
	 }
	

}
