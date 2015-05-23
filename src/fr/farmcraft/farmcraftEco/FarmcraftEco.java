 package fr.farmcraft.farmcraftEco;
 
 import java.util.logging.Logger;
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
      String bank = args[2];
      Double Montant = Double.valueOf(Double.parseDouble(args[3]));
      if (args.length == 2) {
        if ((args[1].equalsIgnoreCase("info")) && 
          (perms.has(player, "FarmcraftEco.user.bank.info." + args[2]))) {
          sender.sendMessage(String.format("Cette banque a actuellement %s", new Object[] { econ.format(econ.getBalance(args[3])) }));
         }
         
        return true;
       }
      if (args.length == 3) {
        if (args[0].equalsIgnoreCase("retrait")) {
          if (perms.has(player, "FarmcraftEco.user.bank." + args[2])) {
            sender.sendMessage(String.format("Vous avez acutellement %s", new Object[] { econ.format(econ.getBalance(player.getName())) }));
            if (econ.has(player, Double.parseDouble(args[3]))) {
              EconomyResponse t = econ.withdrawPlayer(args[2], Double.parseDouble(args[3]));
              if (t.transactionSuccess()) {
                EconomyResponse r = econ.depositPlayer(player, Double.parseDouble(args[3]));
                if (r.transactionSuccess()) {
                  sender.sendMessage(String.format("Vous avez retirez %s et avez maintenant %s", new Object[] { econ.format(r.amount), econ.format(r.balance) }));
                 }
                 else {
                  sender.sendMessage(String.format("ERROR: %s", new Object[] { r.errorMessage }));
                 }
               }
               else {
                sender.sendMessage(String.format("ERROR: %s", new Object[] { t.errorMessage }));
               }
             }
             else {
              sender.sendMessage(String.format("Fonds insuffisant!!!", new Object[0]));
             }
           }
           else {
            sender.sendMessage(String.format("Vous navez pas les permissions ", new Object[0]));
           }
          return true;
         }
        if (args[0].equalsIgnoreCase("depot")) {
          if (perms.has(player, "FarmcraftEco.user.bank." + args[2])) {
            sender.sendMessage(String.format("Vous avez acutellement %s et La bank a %s", new Object[] { econ.format(econ.getBalance(player.getName(), econ.format(econ.getBalance(args[3])))) }));
            if (econ.has(args[2], Double.parseDouble(args[3]))) {
              EconomyResponse t = econ.withdrawPlayer(player, Double.parseDouble(args[3]));
              if (t.transactionSuccess()) {
                EconomyResponse r = econ.depositPlayer(args[2], Double.parseDouble(args[3]));
                if (r.transactionSuccess()) {
                  sender.sendMessage(String.format("Vous avez deposez %s et avez maintenant %s", new Object[] { econ.format(r.amount), econ.format(r.balance) }));
                 }
                 else {
                  sender.sendMessage(String.format("ERROR: %s", new Object[] { r.errorMessage }));
                 }
               }
               else {
                sender.sendMessage(String.format("ERROR: %s", new Object[] { t.errorMessage }));
               }
             }
             else {
              sender.sendMessage(String.format("Fonds insuffisant!!!", new Object[0]));
             }
           }
           else {
            sender.sendMessage(String.format("Vous navez pas les permissions ", new Object[0]));
           }
          return true;
         }
       }
       else {
        sender.sendMessage(String.format("usage /bank [retrait/depot] [Ville] [Montant] ", new Object[0]));
        return true;
       }
     }
    return false;
   }
 }