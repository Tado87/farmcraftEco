package fr.farmcraft.farmcraftEco.Listener;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;

import fr.farmcraft.farmcraftEco.FarmcraftEco;

public class OnInteractEvent implements Listener{
	
	public static FarmcraftEco Plugin;
	public OnInteractEvent(FarmcraftEco Instance){
		Plugin = Instance;
	}
	@SuppressWarnings("deprecation")
	@EventHandler
    public void onInteract(PlayerInteractEvent e) throws SQLException{
		
        Player player = e.getPlayer();
        String playerName = player.getName();
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK){

            if (e.getClickedBlock().getState() instanceof Sign) {
               
                Sign s = (Sign)e.getClickedBlock().getState();
                
                if(s.getLine(0).equalsIgnoreCase(ChatColor.BLUE + "[BuyRegion]")){
                	
            		if (FarmcraftEco.perms.has(player, "FarmcraftEco.user.Region.buy")){
            			
        				String terrain = s.getLine(1);
        		
        				String prix = s.getLine(2);
        				
        				String compte = s.getLine(3);
        				
        				
        				WorldGuardPlugin worldGuard = Plugin.getWorldGuard();
        				
        				RegionManager regionManager = worldGuard.getRegionManager(player.getWorld());
        				
        		
        				if (FarmcraftEco.econ.has(player, Double.parseDouble(prix))) {  												// player transfert
        			
        						EconomyResponse t = FarmcraftEco.econ.withdrawPlayer(player, Double.parseDouble(prix));
					
        						if (t.transactionSuccess()) {   																		// compte transfert
        							
        								EconomyResponse r = FarmcraftEco.econ.depositPlayer(compte, Double.parseDouble(prix));
						
        								if (r.transactionSuccess()) {
        									
        										regionManager.getRegion(terrain).getOwners().addPlayer(player.getName()); 				// add owner
        										 
        										player.sendMessage(String.format(ChatColor.GREEN + "Vous avez achetez " + terrain + " pour " + prix+ "$"));
        										
        										s.setLine(0, ChatColor.BLUE + "[Vendu]");  												// update panneau
        										
        										s.setLine(2, ChatColor.BLUE + playerName + "'s");
        										
        										s.update();
        										
        										Plugin.logToFile("[BuyRegion]: Region " + terrain +" a ete acheter par: " + player.getName());
							
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
						
            		return;
                }
                
                
                
                else if(s.getLine(0).equalsIgnoreCase(ChatColor.BLUE + "[RentRegion]")){
				
        			if (FarmcraftEco.perms.has(player, "FarmcraftEco.user.Region.rent")){
        				
        				String terrain = s.getLine(1);
        				String rentTime = s.getLine(3);
        				String prix = s.getLine(2);
        				
        				String compte = "";
        				String expirDate ="";	
        				
        				
        				Statement BuyRentRegion = Plugin.connection.createStatement();
        				
        				ResultSet GR = BuyRentRegion.executeQuery("SELECT * FROM `RentRegion` WHERE Terrain ='" + terrain + "'");
        				GR.next();
        				if(GR.getString("Compte") != null){
        					
        				compte = GR.getString("Compte");
        				
        				}
        				
        				
        				
        				WorldGuardPlugin worldGuard = Plugin.getWorldGuard();
        				
        				RegionManager regionManager = worldGuard.getRegionManager(player.getWorld());
        				
        				if (FarmcraftEco.econ.has(player, Double.parseDouble(prix))) {
                			
        					EconomyResponse t = FarmcraftEco.econ.withdrawPlayer(player, Double.parseDouble(prix));
        		
        					if (t.transactionSuccess()) {
        						
        							EconomyResponse r = FarmcraftEco.econ.depositPlayer(compte, Double.parseDouble(prix));
        			
        							if (r.transactionSuccess()) {
        								
        									regionManager.getRegion(terrain).getMembers().addPlayer(player.getName());
        									
        									BuyRentRegion.executeUpdate("UPDATE `RentRegion` SET `ExpirDate`=NOW()+INTERVAL '" + rentTime + "' DAY, `Playername`='" +playerName +"' WHERE Terrain='"+ terrain +"';");
        									
        									ResultSet ED = BuyRentRegion.executeQuery("SELECT * FROM `RentRegion` WHERE Terrain ='" + terrain + "'");
        									ED.next();
        			        				if(ED.getString("Compte") != null){
        			        					
        			        					expirDate = ED.getString("ExpirDate");
        			            				
        			            				}
        									
        									player.sendMessage(String.format(ChatColor.GREEN + "Vous avez louez " + terrain + " pour " + prix+ "$"));
        									
        									s.setLine(0, ChatColor.BLUE + "[RentedRegion]");
        									
        									s.setLine(2, ChatColor.GREEN + playerName);
        									
        									s.setLine(3, ChatColor.BLUE + expirDate);
        									
        									s.update();
        									
        									Plugin.logToFile("[RentRegion]: Region " + terrain +" a ete louer par: " + player.getName());
        				
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
				return ;

				}

            }
			else{
				return;
			}
        }
    }
}