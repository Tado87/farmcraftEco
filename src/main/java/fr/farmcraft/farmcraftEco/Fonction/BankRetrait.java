package fr.farmcraft.farmcraftEco.Fonction;

import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import fr.farmcraft.farmcraftEco.FarmcraftEco;

public class BankRetrait {
	
		public static FarmcraftEco Plugin;
		
		public BankRetrait(FarmcraftEco Instance){
			
			Plugin = Instance;
		
		}
	
	
	
		public static Boolean Retrait(Player player, String accountName, OfflinePlayer account, Double Montant, String playerName) {
			
				if ( FarmcraftEco.perms.has(player, "FarmcraftEco.user.bank." + accountName)){
					
						player.sendMessage(String.format(ChatColor.GREEN + "Vous aviez actuellement " + ChatColor.BLUE +  "%s", 
								
								new Object[] {  FarmcraftEco.econ.format( FarmcraftEco.econ.getBalance(playerName)) }));
						
						if ( FarmcraftEco.econ.has(account, Montant)) {
							
								EconomyResponse t =  FarmcraftEco.econ.withdrawPlayer(account, (Montant));
								
								if (t.transactionSuccess()) {
									
										EconomyResponse r =  FarmcraftEco.econ.depositPlayer(player, (Montant));
										
										if (r.transactionSuccess()) {
											
												player.sendMessage(String.format(ChatColor.GREEN + "Vous avez retirez " + ChatColor.BLUE +  "$" + Montant + ChatColor.GREEN + " et avez maintenant " + ChatColor.BLUE + " %s",
														
															new Object[] {  FarmcraftEco.econ.format( FarmcraftEco.econ.getBalance(playerName)) }));
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
					
						player.sendMessage(String.format(ChatColor.RED + "Vous navez pas les permissions pour la bank " + accountName, new Object[0]));
				}
				
				return true;
		}
}
