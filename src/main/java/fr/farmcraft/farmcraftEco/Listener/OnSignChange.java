package fr.farmcraft.farmcraftEco.Listener;


import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.SignChangeEvent;

import fr.farmcraft.farmcraftEco.FarmcraftEco;



public class OnSignChange implements Listener{
	
	public static FarmcraftEco Plugin;
	public OnSignChange(FarmcraftEco Instance){
		Plugin = Instance;
	}
        
	@EventHandler(priority=EventPriority.HIGH)
    public void onSignChange(SignChangeEvent event) throws SQLException{
		
        Player player = event.getPlayer();
        
        if(event.getLine(0).equalsIgnoreCase("[BuyRegion]")){
        	
        	if (FarmcraftEco.perms.has(player, "FarmcraftEco.admin.Region.create")){
        		
        		String T1 = event.getLine(1);
        		String T2 = event.getLine(2);
        		String T3 = event.getLine(3);
        		
        		if ( T1 != null && T2 != null && T3 != null && !T1.isEmpty() && !T2.isEmpty() && !T3.isEmpty()){
        		        		
        			event.setLine(0, ChatColor.BLUE + "[BuyRegion]");
        			
        			String terrain = event.getLine(1);
        		
        			Plugin.logToFile("[BuyRegion]: Region " + terrain +" a ete creer par: " + player.getName());
        		
        		}
            }
        	
        	else{
        			player.sendMessage(String.format(ChatColor.RED + "Vous navez pas les permissions", new Object[0]));
        			event.setCancelled(true);
        	}
        }
        if(event.getLine(0).equalsIgnoreCase("[RentRegion]")){
        	
        	if (FarmcraftEco.perms.has(player, "FarmcraftEco.admin.Region.create")){
        		
        		String T1 = event.getLine(1);
        		String T2 = event.getLine(2);
        		String T3 = event.getLine(3);
        		
        		if ( T1 != null && T2 != null && T3 != null && !T1.isEmpty() && !T2.isEmpty() && !T3.isEmpty()){
        		
        		
        		
        		
        		
        			Location tmpLoc = event.getBlock().getLocation();
    		
        			int positionX = tmpLoc.getBlockX();
        			int positionY = tmpLoc.getBlockY();
        			int positionZ = tmpLoc.getBlockZ();
    		
    		
        			String prix = event.getLine(2);
        			String[] data = event.getLine(3).split("/");  // Splitting account and time
    				String compte = data[1];
    				String rentTime = data[0];
    			
    				World world = player.getWorld();
    				String rg = event.getLine(1);			// getting rg name and worldname
    				String World = world.getName();
    			
    			
    				Statement addrental = Plugin.connection.createStatement();  // bdd add
					addrental.executeUpdate("INSERT INTO `RentRegion` (`ExpirDate`, `Terrain`, `Compte`, `RentTime`, `Prix`, `Playername`, `World`, `positionX`, `positionY`, `positionZ`) VALUES (NULL, '" + rg + "', '" + compte + "', '" + rentTime + "', '" + prix + "', NULL, '" + World + "', '" + positionX + "', '" + positionY + "', '" + positionZ + "');");
				
					event.setLine(0, ChatColor.BLUE + "[RentRegion]");  // sign update
					
					event.setLine(3, rentTime);
					
					Plugin.logToFile("[RentRegion]: Region " + rg +" a ete creer par: " + player.getName());
        		}

    			
        	}
    	
        	else{
    		
        		player.sendMessage(String.format(ChatColor.RED + "Vous navez pas les permissions", new Object[0]));
        		event.setCancelled(true);
    		
        	}
    		

        }
        else{
            return;
        }
    }
}