package fr.farmcraft.farmcraftEco;


import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
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
import fr.farmcraft.farmcraftEco.Fonction.BRadd;
import fr.farmcraft.farmcraftEco.Fonction.BRbuy;
import fr.farmcraft.farmcraftEco.Fonction.RRadd;



public class FElistener implements Listener{
	public static FarmcraftEco Plugin;
	public FElistener(FarmcraftEco Instance){
		Plugin = Instance;
	}
	@EventHandler
    public void onInteract(PlayerInteractEvent e){
		
        Player player = e.getPlayer();
        String playerName = player.getName();
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK){

            if (e.getClickedBlock().getState() instanceof Sign) {
               
                Sign s = (Sign) e.getClickedBlock().getState();
             
                BRbuy.BuyRegionBuy(player, playerName, s);
                

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
        	
        	BRadd.BuyRegionAdd(player, event);
        	if (BRadd.BuyRegionAdd(player, event) != true){
        		event.setCancelled(true);
        	}
        }
        if(event.getLine(0).equalsIgnoreCase("[RentRegion]")){
        	
    		RRadd.RentRegionAdd(player, event);
    		
    		if (RRadd.RentRegionAdd(player, event) != true){
        		event.setCancelled(true);
        	}
    		

        }
        else{
            return;
        }
    }
}