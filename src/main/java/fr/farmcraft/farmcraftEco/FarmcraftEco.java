 package fr.farmcraft.farmcraftEco;
 import fr.farmcraft.farmcraftEco.Fonction.*;
import fr.farmcraft.farmcraftEco.Listener.OnInteractEvent;
import fr.farmcraft.farmcraftEco.Listener.OnSignChange;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.Plugin;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
 
















import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

 public class FarmcraftEco extends JavaPlugin implements Listener
 {
	 
	 
public static final Logger log = Logger.getLogger("Minecraft");

public static Chat chat = null;
public static Economy econ = null;
public static Permission perms = null;

public Plugin plugin = this;

String host = getConfig().getString("Mysql.host");
String port = getConfig().getString("Mysql.port");
String db = getConfig().getString("Mysql.db");
String accountName = getConfig().getString("Mysql.accountName");
String accountPassword = getConfig().getString("Mysql.accountPassword");

public Mysql MySQL = new Mysql(plugin, host, port, db, accountName, accountPassword);
public Connection connection = null;




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
	 setupChat();
     setupPermissions();
     initialiseConfig();
     
     this.getCommand("RentCheck").setExecutor(new RentCheck(this));
     this.getCommand("bank").setExecutor(new FEcommand(this));
     this.getCommand("brc").setExecutor(new CreateBR(this));
     this.getCommand("rrc").setExecutor(new CreateRR(this));
     this.getCommand("mayor").setExecutor(new Mayor(this));
     this.getServer().getPluginManager().registerEvents(new OnSignChange(this), this);
     this.getServer().getPluginManager().registerEvents(new OnInteractEvent(this), this);
     
     
     
     try {
		initialiseMysql();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
     
     getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
     {
         public void run()
         {
        	 log.info(String.format("[FarmCraftEco] - RentChecker Started"));
        	 try {
				RentCheck.RentExpirDateChecker();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         }
     }, 600L, 72000L);  //20 ticks = 1 seconds
     
     
     
     

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
   private boolean setupChat()
   
   {
       RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
       if (chatProvider != null) {
           chat = chatProvider.getProvider();
       }
       return (chat != null);
   }
   public WorldGuardPlugin getWorldGuard() {
	    Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
	 
	    // WorldGuard may not be loaded
	    if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
	        return null; // Maybe you want throw an exception instead
	    }
	 
	    return (WorldGuardPlugin) plugin;
	}
   public WorldEditPlugin getWorldEdit() {
	   
	   Plugin plugin = getServer().getPluginManager().getPlugin("WorldEdit");
	   
	   if (plugin == null || !(plugin instanceof WorldEditPlugin)) {
	        return null; // Maybe you want throw an exception instead
	    }
	 
	    return (WorldEditPlugin) plugin;
	}
   private void initialiseConfig(){
	   FileConfiguration cfg = getConfig(); 
	   
	   cfg.addDefault("Mysql.host", "");
	   cfg.addDefault("Mysql.port", "");
	   cfg.addDefault("Mysql.db", "");
	   cfg.addDefault("Mysql.accountName", "");
	   cfg.addDefault("Mysql.accountPassword", "");
	   
	   cfg.options().copyDefaults(true);
	   saveConfig();
	   
   }
   public void initialiseMysql() throws SQLException{
	     try {
	 		connection = MySQL.openConnection();
	 	} catch (ClassNotFoundException e) {
	 		// TODO Auto-generated catch block
	 		e.printStackTrace();
	 	} catch (SQLException e) {
	 		// TODO Auto-generated catch block
	 		e.printStackTrace();
	 	}
	   log.info(String.format("[FarmCraftEco] - Mysql Started"));
	   Statement createTable = connection.createStatement();
	     createTable.executeUpdate("CREATE TABLE IF NOT EXISTS RentRegion (id INT NOT NULL AUTO_INCREMENT,ExpirDate DATETIME, Terrain VARCHAR(25), Compte VARCHAR(25), RentTime VARCHAR(25), Prix VARCHAR(25), Playername VARCHAR(25), World VARCHAR(25), positionX INT(35),  positionY INT(35),  positionZ INT(35), PRIMARY KEY (id))");
	   
   }
   public void logToFile(String message)  //logToFile("MESSAGES");
   
   {

       try
       {
           File dataFolder = getDataFolder();
           if(!dataFolder.exists())
           {
               dataFolder.mkdir();
           }

           File saveTo = new File(getDataFolder(), "FElog.txt");
           if (!saveTo.exists())
           {
               saveTo.createNewFile();
           }


           FileWriter fw = new FileWriter(saveTo, true);

           PrintWriter pw = new PrintWriter(fw);

           pw.println(message);

           pw.flush();

           pw.close();

       } catch (IOException e)
       {

           e.printStackTrace();

       }

   }



 }