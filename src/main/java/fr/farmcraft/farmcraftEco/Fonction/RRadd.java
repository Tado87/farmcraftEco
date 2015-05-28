package fr.farmcraft.farmcraftEco.Fonction;

import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import fr.farmcraft.farmcraftEco.FarmcraftEco;

public class RRadd {
	
	
		public static FarmcraftEco Plugin;

		public RRadd(FarmcraftEco Instance){

				Plugin = Instance;
		}
		
		
		public static Boolean RentRegionAdd(Player player,Event event) throws SQLException {
			
			
        	if (FarmcraftEco.perms.has(player, "FarmcraftEco.admin.Region.create")){
        		
        			Location tmpLoc = ((Block) event).getLocation();
        		
        			String positionX = String.valueOf(tmpLoc.getBlockX());
        			String positionY = String.valueOf(tmpLoc.getBlockY());
        			String positionZ = String.valueOf(tmpLoc.getBlockZ());
        		
        		
        			String prix = (((Sign) event).getLine(2));
        			String[] data = ((Sign) event).getLine(3).split("/");
        			String compte = data[1];
        			String rentTime = data[0];
        			
        			World world = player.getWorld();
        			String rg = ((Sign) event).getLine(1);
        			String World = world.toString();
        			
        			
        				Statement addrental = Plugin.connection.createStatement();
						addrental.executeUpdate("INSERT INTO `FarmcraftEco`.`RentRegion` (`ExpirDate`, `Terrain`, `Compte`, `RentTime`, `Prix`, `Playername`, `World`, `positionX`, `positionY`, `positionZ`) VALUES (NULL, " + rg + ", " + compte + ", " + rentTime + ", " + prix + ", NULL, " + World + ", " + positionX + ", " + positionY + ", " + positionZ + ");");
						
        			
        			return true;
        	}
        	
        	else{
        		
        		player.sendMessage(String.format(ChatColor.RED + "Vous navez pas les permissions", new Object[0]));
        		return false;
        		
        	}
			
			
			
			
			
		}
		
}