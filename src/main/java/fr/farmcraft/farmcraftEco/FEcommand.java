package fr.farmcraft.farmcraftEco;

import net.milkbowl.vault.economy.EconomyResponse;
import fr.farmcraft.farmcraftEco.Fonction.*;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FEcommand implements CommandExecutor {
		public FarmcraftEco Plugin;
		public FEcommand(FarmcraftEco Instance){
			Plugin = Instance;
		}
		
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args)
	{
		   
		if (!(sender instanceof Player)) {
	    	
				FarmcraftEco.log.info("Seuls les joueurs peuvent utilisés cette commande");
	      
				return true;
	      
		}
	     
		Player player = (Player)sender;
	    
		if (command.getName().equalsIgnoreCase("bank")) {
	    	
				
				OfflinePlayer account = Bukkit.getServer().getOfflinePlayer(args[1]);
	    	
				String accountName = account.getName();
	    	
				Double Montant = Double.parseDouble(args[2]);
	    	
				String playerName = player.getName();
	    	
				if (args.length == 2) {
	    	  
						if ((args[0].equalsIgnoreCase("info"))){
	        	
						BankInfo.Info(player, accountName,  account);
						
						}
						
				return true;
				
				}	
				
				else if (args.length == 3) {
			  
						if (args[0].equalsIgnoreCase("retrait")){
	        	
								BankRetrait.Retrait(player, accountName,  account, Montant, playerName);
						
						}
	        	
						if (args[0].equalsIgnoreCase("depot")){
	        	
								BankDepot.Depot(player, accountName,  account, Montant, playerName);
	        	
						}
						
						else {
	    	   
								player.sendMessage(String.format(ChatColor.GRAY + "usage /bank [retrait/depot/info] [Ville] ([Montant]) ", new Object[0]));
	        
								return true;
						}
	        
				}
				return true;
	    }
	    return true;
	}
}
