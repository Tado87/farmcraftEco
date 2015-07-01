package fr.farmcraft.farmcraftEco.Fonction;

import fr.farmcraft.farmcraftEco.FarmcraftEco;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class BankInfo {
	
	public static FarmcraftEco Plugin;
	
	public BankInfo(FarmcraftEco Instance){
		
			Plugin = Instance;
	}
	
	
	
	
	public static Boolean Info(Player player, String accountName, OfflinePlayer account) {
		
			String playerName = player.getName();
			
			if ( FarmcraftEco.perms.has(player, "FarmcraftEco.user.bank." + accountName)){
			
					player.sendMessage(String.format(ChatColor.GREEN + "La banque " + accountName + " a actuellement " + ChatColor.BLUE +  "%s", 
    				
								new Object[] {  FarmcraftEco.econ.format( FarmcraftEco.econ.getBalance(account)) }));
    		
					player.sendMessage(String.format(ChatColor.GREEN + "Vous avez actuellement " + ChatColor.BLUE +  "%s", 
    				
								new Object[] {  FarmcraftEco.econ.format( FarmcraftEco.econ.getBalance(playerName)) }));
    		
    		return true;
    		
			}
			
			else {
				
					player.sendMessage(String.format(ChatColor.RED + "Vous navez pas les permissions ", new Object[0]));
					
					return true;
    	}
			
	}


}