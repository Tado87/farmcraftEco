package fr.farmcraft.farmcraftEco;

import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BankCommandExecutor implements CommandExecutor {
		public FarmcraftEco Plugin;
		public BankCommandExecutor(FarmcraftEco Instance){
			Plugin = Instance;
		}
		
	   public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args)
	   {
	    if (!(sender instanceof Player)) {
	      Plugin.log.info("Only players are supported for this Example Plugin, but you should not do this!!!");
	      return true;
	     }
	     
	    Player player = (Player)sender;
	    if (command.getName().equalsIgnoreCase("bank")) {
	    	OfflinePlayer account = Bukkit.getServer().getOfflinePlayer(args[1]);
	    	String accountName = account.getName();
	      if (args.length == 2) {
	        if ((args[0].equalsIgnoreCase("info"))){
	        	if ( Plugin.perms.has(player, "FarmcraftEco.user.bank." + accountName)){
	        		sender.sendMessage(String.format(ChatColor.GREEN + "La banque " + accountName + " a actuellement " + ChatColor.BLUE +  "%s", new Object[] {  Plugin.econ.format( Plugin.econ.getBalance(account)) }));
	        		sender.sendMessage(String.format(ChatColor.GREEN + "Vous avez actuellement " + ChatColor.BLUE +  "%s", new Object[] {  Plugin.econ.format( Plugin.econ.getBalance(player.getName())) }));
	        		return true;
	        	}
	        	else {
	        		sender.sendMessage(String.format(ChatColor.RED + "Vous navez pas les permissions ", new Object[0]));
	        		return true;
	        	}
	        }
	        return true;
	       }
		  else if (args.length == 3) {
	    	  sender.sendMessage(String.format(ChatColor.RED + "Test1"));
	        if (args[0].equalsIgnoreCase("retrait")){
	        	sender.sendMessage(String.format(ChatColor.RED + "Test2"));
	        	if ( Plugin.perms.has(player, "FarmcraftEco.user.bank." + accountName)){
					sender.sendMessage(String.format(ChatColor.GREEN + "Vous aviez actuellement " + ChatColor.BLUE +  "%s", new Object[] {  Plugin.econ.format( Plugin.econ.getBalance(player.getName())) }));
					if ( Plugin.econ.has(account, Double.parseDouble(args[2]))) {
						EconomyResponse t =  Plugin.econ.withdrawPlayer(account, Double.parseDouble(args[2]));
						if (t.transactionSuccess()) {
							EconomyResponse r =  Plugin.econ.depositPlayer(player, Double.parseDouble(args[2]));
							if (r.transactionSuccess()) {
								sender.sendMessage(String.format(ChatColor.GREEN + "Vous avez retirez " + ChatColor.BLUE +  "$" + args[2] + ChatColor.GREEN + " et avez maintenant " + ChatColor.BLUE + " %s", new Object[] {  Plugin.econ.format( Plugin.econ.getBalance(player.getName())) }));
							}
							else {
	                  			sender.sendMessage(String.format(ChatColor.RED + "ERROR: %s", new Object[] { r.errorMessage }));
							}
						}
						else {
							sender.sendMessage(String.format(ChatColor.RED + "ERROR: %s", new Object[] { t.errorMessage }));
						}
					}
					else {
						sender.sendMessage(String.format(ChatColor.RED + "Fonds insuffisant!!!", new Object[0]));
					}
	           }
			    else{
					sender.sendMessage(String.format(ChatColor.RED + "Vous navez pas les permissions pour la bank " + accountName, new Object[0]));
			    }
	          return true;
	         }
	        if (args[0].equalsIgnoreCase("depot")){
	        	if ( Plugin.perms.has(player, "FarmcraftEco.user.bank." + accountName)){
					sender.sendMessage(String.format(ChatColor.GREEN + "Vous aviez actuellement " + ChatColor.BLUE +  "%s", new Object[] {  Plugin.econ.format( Plugin.econ.getBalance(player.getName())) }));
					if ( Plugin.econ.has(player, Double.parseDouble(args[2]))) {
						EconomyResponse t =  Plugin.econ.withdrawPlayer(player, Double.parseDouble(args[2]));
						if (t.transactionSuccess()) {
							EconomyResponse r =  Plugin.econ.depositPlayer(account, Double.parseDouble(args[2]));
							if (r.transactionSuccess()) {
								sender.sendMessage(String.format(ChatColor.GREEN + "Vous avez deposez " + ChatColor.BLUE +  "$" + args[2] + ChatColor.GREEN +  " et avez maintenant " + ChatColor.BLUE +  "%s", new Object[] {  Plugin.econ.format( Plugin.econ.getBalance(player.getName())) }));
							}
							else {
								sender.sendMessage(String.format(ChatColor.RED + "ERROR: %s", new Object[] { r.errorMessage }));
							}
						}
						else {
							sender.sendMessage(String.format(ChatColor.RED + "ERROR: %s", new Object[] { t.errorMessage }));
						}
					}
					else {
					sender.sendMessage(String.format(ChatColor.RED + "Fonds insuffisant!!!", new Object[0]));
					}
				}
	           else {
	            sender.sendMessage(String.format(ChatColor.RED + "Vous navez pas les permissions pour la bank " + accountName, new Object[0]));
	           }
	        return true;
	        }
	       else {
	        sender.sendMessage(String.format(ChatColor.GRAY + "usage /bank [retrait/depot/info] [Ville] ([Montant]) ", new Object[0]));
	        return true;
	       }
	      }
	      return true;
	    }
	    return true;
	}

}
