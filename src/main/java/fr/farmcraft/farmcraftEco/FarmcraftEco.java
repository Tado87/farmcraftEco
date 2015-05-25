 package fr.farmcraft.farmcraftEco;
 
 import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;
 

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.permission.Permission;
 

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import org.bukkit.plugin.Plugin;

import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.ApplicableRegionSet;

import static com.sk89q.worldguard.bukkit.BukkitUtil.*;

 public class FarmcraftEco extends org.bukkit.plugin.java.JavaPlugin implements Listener
 {
public static final Logger log = Logger.getLogger("Minecraft");
public static Economy econ = null;
public static Permission perms = null;
public Plugin plugin = this;
   
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
     
     this.getCommand("bank").setExecutor(new BankCommandExecutor(this));
     this.getServer().getPluginManager().registerEvents(new RegionListener(this), this);
   }
   
   public boolean setupEconomy()
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
   
   public boolean setupPermissions() {
    RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
    perms = (Permission)rsp.getProvider();
	return perms != null;
   }
   public WorldGuardPlugin getWorldGuard() {
	    Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
	 
	    // WorldGuard may not be loaded
	    if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
	        return null; // Maybe you want throw an exception instead
	    }
	 
	    return (WorldGuardPlugin) plugin;
	}
   
}