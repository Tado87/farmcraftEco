 package fr.farmcraft.farmcraftEco;
 
 import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;
 



 import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.permission.Permission;
 
 public class FarmcraftEco extends org.bukkit.plugin.java.JavaPlugin implements org.bukkit.event.Listener
 {
private static final Logger log = Logger.getLogger("Minecraft");
public static Economy econ = null;
public static Permission perms = null;
   
   public void onDisable()
   {
System.out.println("Lancement de FarmcraftEco  Version 0.0.1");
log.info(String.format("[%s] Disabled Version %s", new Object[] { getDescription().getName(), getDescription().getVersion() }));
   }
   
   public void onEnable()
   {
	setupEconomy();
	if (!setupEconomy()) {
       System.out.println("Le plugin FarmCraftEco se coupe");
       log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", new Object[] { getDescription().getName() }));
       getServer().getPluginManager().disablePlugin(this);
       return;
     }
     setupPermissions();
   }
   
   private boolean setupEconomy()
   {
    if (getServer().getPluginManager().getPlugin("Vault") == null) {
       return false;
     }
    RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
    if (rsp == null) {
       return false;
     }
    econ = (Economy)rsp.getProvider();
    return econ != null;
   }
   
   private boolean setupPermissions() {
    RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
    perms = (Permission)rsp.getProvider();
	return perms != null;
   }
   
 
   public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args)
   {
    if (!(sender instanceof Player)) {
      log.info("Only players are supported for this Example Plugin, but you should not do this!!!");
      return true;
     }
     
    Player player = (Player)sender;
    if (command.getName().equalsIgnoreCase("bank")) {
    	OfflinePlayer account = Bukkit.getServer().getOfflinePlayer(args[1]);
    	String accountName = account.getName();
      if (args.length == 2) {
        if ((args[0].equalsIgnoreCase("info"))){
        	if (perms.has(player, "FarmcraftEco.user.bank." + accountName)){
        		sender.sendMessage(String.format(ChatColor.GREEN + "La banque " + accountName + " a actuellement " + ChatColor.BLUE +  "%s", new Object[] { econ.format(econ.getBalance(account)) }));
        		sender.sendMessage(String.format(ChatColor.GREEN + "Vous avez actuellement " + ChatColor.BLUE +  "%s", new Object[] { econ.format(econ.getBalance(player.getName())) }));
        	}
        	else {
        		sender.sendMessage(String.format(ChatColor.RED + "Vous navez pas les permissions ", new Object[0]));
        	}
        }
        return true;
       }
      if (args.length == 3) {
        if (args[0].equalsIgnoreCase("retrait")){
        	if (perms.has(player, "FarmcraftEco.user.bank." + accountName)){
				sender.sendMessage(String.format(ChatColor.GREEN + "Vous aviez actuellement " + ChatColor.BLUE +  "%s", new Object[] { econ.format(econ.getBalance(player.getName())) }));
				if (econ.has(account, Double.parseDouble(args[2]))) {
					EconomyResponse t = econ.withdrawPlayer(account, Double.parseDouble(args[2]));
					if (t.transactionSuccess()) {
						EconomyResponse r = econ.depositPlayer(player, Double.parseDouble(args[2]));
						if (r.transactionSuccess()) {
							sender.sendMessage(String.format(ChatColor.GREEN + "Vous avez retirez " + ChatColor.BLUE +  "$" + args[2] + ChatColor.GREEN + " et avez maintenant " + ChatColor.BLUE + " %s", new Object[] { econ.format(econ.getBalance(player.getName())) }));
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
        	if (perms.has(player, "FarmcraftEco.user.bank." + accountName)){
				sender.sendMessage(String.format(ChatColor.GREEN + "Vous aviez actuellement " + ChatColor.BLUE +  "%s", new Object[] { econ.format(econ.getBalance(player.getName())) }));
				if (econ.has(player, Double.parseDouble(args[2]))) {
					EconomyResponse t = econ.withdrawPlayer(player, Double.parseDouble(args[2]));
					if (t.transactionSuccess()) {
						EconomyResponse r = econ.depositPlayer(account, Double.parseDouble(args[2]));
						if (r.transactionSuccess()) {
							sender.sendMessage(String.format(ChatColor.GREEN + "Vous avez deposez " + ChatColor.BLUE +  "$" + args[2] + ChatColor.GREEN +  " et avez maintenant " + ChatColor.BLUE +  "%s", new Object[] { econ.format(econ.getBalance(player.getName())) }));
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
    }
    return false;
}}