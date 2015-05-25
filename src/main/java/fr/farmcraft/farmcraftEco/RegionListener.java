package fr.farmcraft.farmcraftEco;

import java.io.IOException;

import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.bukkit.commands.task.RegionManagerSaver;
import com.sk89q.worldguard.protection.managers.RegionManager;

import static com.sk89q.worldguard.bukkit.BukkitUtil.*;

public class RegionListener implements Listener{
	public FarmcraftEco Plugin;
	public RegionListener(FarmcraftEco Instance){
		Plugin = Instance;
	}
	@EventHandler
    public void onInteract(PlayerInteractEvent e){
		
        Player player = e.getPlayer();
        String playerName = player.getName();
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK){

            if (e.getClickedBlock().getState() instanceof Sign) {
               
                Sign s = (Sign) e.getClickedBlock().getState();
             
                if(s.getLine(0).equalsIgnoreCase(ChatColor.BLUE + "[BuyRegion]")){
                	
                	if (Plugin.perms.has(player, "FarmcraftEco.user.Region.buy")){
                		
                		String prix = s.getLine(2);
                		String compte = s.getLine(3);
                		String terrain = s.getLine(1);
                		WorldGuardPlugin worldGuard = Plugin.getWorldGuard();
                		RegionManager regionManager = worldGuard.getRegionManager(player.getWorld());
                		
                		if (Plugin.econ.has(player, Double.parseDouble(prix))) {
                			
    						EconomyResponse t = Plugin.econ.withdrawPlayer(player, Double.parseDouble(prix));
    						
    						if (t.transactionSuccess()) {
    							EconomyResponse r = Plugin.econ.depositPlayer(compte, Double.parseDouble(prix));
    							
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
            }
        }
        else{
            return;
        }
    }
        
	@EventHandler(priority=EventPriority.HIGH)
    public void onSignChange(SignChangeEvent event){
        Player player = event.getPlayer();
        if(event.getLine(0).equalsIgnoreCase("[BuyRegion]")){
        	if (Plugin.perms.has(player, "FarmcraftEco.admin.Region.create")){
        		event.setLine(0, ChatColor.BLUE + "[BuyRegion]");
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