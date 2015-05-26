package fr.farmcraft.farmcraftEco.Fonction;

import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.block.Sign;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;

import fr.farmcraft.farmcraftEco.FarmcraftEco;

public class BRbuy {
	
		public static FarmcraftEco Plugin;
	
		public BRbuy(FarmcraftEco Instance){
		
			Plugin = Instance;
		}

		public static Boolean BuyRegionBuy(Player player, String playerName, Sign s) {
			
        if(s.getLine(0).equalsIgnoreCase(ChatColor.BLUE + "[BuyRegion]")){
        	
        	
        		if (FarmcraftEco.perms.has(player, "FarmcraftEco.user.Region.buy")){
        			
        				String terrain = s.getLine(1);
        		
        				String prix = s.getLine(2);
        				
        				String compte = s.getLine(3);
        				
        				
        				WorldGuardPlugin worldGuard = Plugin.getWorldGuard();
        				
        				RegionManager regionManager = worldGuard.getRegionManager(player.getWorld());
        		
        				if (FarmcraftEco.econ.has(player, Double.parseDouble(prix))) {
        			
        						EconomyResponse t = FarmcraftEco.econ.withdrawPlayer(player, Double.parseDouble(prix));
					
        						if (t.transactionSuccess()) {
        							
        								EconomyResponse r = FarmcraftEco.econ.depositPlayer(compte, Double.parseDouble(prix));
						
        								if (r.transactionSuccess()) {
        									
        										regionManager.getRegion(terrain).getOwners().addPlayer(player.getName());
        										
        										player.sendMessage(String.format(ChatColor.GREEN + "Vous avez achetez " + terrain));
        										
        										s.setLine(0, ChatColor.BLUE + "[Vendu]");
        										
        										s.setLine(2, ChatColor.BLUE + playerName + "'s");
        										
        										s.update();
							
        								}
        								
        								else {
        									
        										player.sendMessage(String.format(ChatColor.RED + "ERROR: %s", new Object[] { r.errorMessage }));
        								}
        						}
        						
        						else {
        								player.sendMessage(String.format(ChatColor.RED + "ERROR: %s", new Object[] { t.errorMessage }));
        						}
        						
        				}
        				
        				else {
        						player.sendMessage(String.format(ChatColor.RED + "Fonds insuffisant!!!", new Object[0]));
        				}		
        				
        		}
        			else{
        					player.sendMessage(String.format(ChatColor.RED + "Vous navez pas les permissions", new Object[0]));
        			}
        }
        return true;
		
	}
}
