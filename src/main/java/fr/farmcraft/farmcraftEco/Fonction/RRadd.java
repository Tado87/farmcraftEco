package fr.farmcraft.farmcraftEco.Fonction;

import org.bukkit.ChatColor;
import org.bukkit.Location;
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
		
		
		public static Boolean RentRegionAdd(Player player,Event event) {

        	if (FarmcraftEco.perms.has(player, "FarmcraftEco.admin.Region.create")){
        		
        			Location tmpLoc = ((Block) event).getLocation();
        		
        			String positionX = String.valueOf(tmpLoc.getBlockX());
        			String positionY = String.valueOf(tmpLoc.getBlockY());
        			String positionZ = String.valueOf(tmpLoc.getBlockZ());
        		
        		
        			Double prix = Double.parseDouble(((Sign) event).getLine(2));
        			String[] data = ((Sign) event).getLine(3).split("/");
        			String compte = data[1];
        			String time = data[0];
        			
        			World world = player.getWorld();
        			String rg = ((Sign) event).getLine(1);
        			String World = world.toString();
        			

        			YamlWriter.YmlWriter(prix, compte, time, World, rg, positionX, positionY, positionZ);
        			
        			if (FarmcraftEco.YmlWriterReturn == true){
        				
            				((Sign) event).setLine(0, ChatColor.BLUE + "[RentRegion]");
            				((Sign) event).setLine(3, time);
            				
            				player.sendMessage(String.format(ChatColor.RED + "RentRegion panneau creer", new Object[0]));
            			
        			}
        			
        			return true;
        	}
        	
        	else{
        		
        		player.sendMessage(String.format(ChatColor.RED + "Vous navez pas les permissions", new Object[0]));
        		return false;
        		
        	}
			
			
			
			
			
		}
		
}