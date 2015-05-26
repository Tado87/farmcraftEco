package fr.farmcraft.farmcraftEco.Fonction;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import fr.farmcraft.farmcraftEco.FarmcraftEco;

public class BRadd {
	
		public static FarmcraftEco Plugin;
	
		public BRadd(FarmcraftEco Instance){
	
				Plugin = Instance;
		}
		
		public static Boolean BuyRegionAdd(Player player,Event event) {
			
        	if (FarmcraftEco.perms.has(player, "FarmcraftEco.admin.Region.create")){
        		
        			((Sign) event).setLine(0, ChatColor.BLUE + "[BuyRegion]");
        		
        			return true;
            }
        	
        	else{
        			player.sendMessage(String.format(ChatColor.RED + "Vous navez pas les permissions", new Object[0]));
        			return false;
        	}

		}		
}