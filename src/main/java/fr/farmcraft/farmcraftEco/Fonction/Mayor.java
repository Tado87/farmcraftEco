package fr.farmcraft.farmcraftEco.Fonction;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;

import fr.farmcraft.farmcraftEco.FarmcraftEco;

public class Mayor implements CommandExecutor{
	
	public FarmcraftEco Plugin;
	
	public Mayor(FarmcraftEco Instance){
		
		Plugin = Instance;
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args)
	
	{
		
		if (!(sender instanceof Player)) {
	    	
			FarmcraftEco.log.info("Seuls les joueurs peuvent utilisés cette commande");
      
			return true;
      
		}
	   
		if (command.getName().equalsIgnoreCase("Mayor")) {
			
		
			if (args.length == 3){
				
				String Town = args[1];
				
				String PlayerName = args[2];
				
				
				Player player = (Player)sender;
				
				WorldGuardPlugin worldGuard = Plugin.getWorldGuard();
				
				if (FarmcraftEco.perms.has(player, "FarmcraftEco.admin.mayor")){
					
					if (args[0].equalsIgnoreCase("add")){
					
						FarmcraftEco.perms.playerAdd(player.getWorld(), PlayerName, "FarmcraftEco.user.RRC." + Town);
						FarmcraftEco.perms.playerAdd(player.getWorld(), PlayerName, "farmcraft.rrc");
					
						FarmcraftEco.perms.playerAdd(player.getWorld(), PlayerName, "FarmcraftEco.user.BRC." + Town);
						FarmcraftEco.perms.playerAdd(player.getWorld(), PlayerName, "farmcraft.brc");
					
						FarmcraftEco.perms.playerAdd(player.getWorld(), PlayerName, "FarmcraftEco.user.bank." + Town);
						FarmcraftEco.perms.playerAdd(player.getWorld(), PlayerName, "farmcraft.bank");
					
						FarmcraftEco.perms.playerAdd(player.getWorld(), PlayerName, "worldedit.selection.pos");
						FarmcraftEco.perms.playerAdd(player.getWorld(), PlayerName, "worldedit.wand");
						FarmcraftEco.perms.playerAdd(player.getWorld(), PlayerName, "worldedit.wand.toggle");
						FarmcraftEco.perms.playerAdd(player.getWorld(), PlayerName, "worldedit.selection.expand");
						FarmcraftEco.perms.playerAdd(player.getWorld(), PlayerName, "worldguard.region.wand");
						
						RegionManager regionManager = worldGuard.getRegionManager(player.getWorld());
						regionManager.getRegion(Town).getOwners().addPlayer(player.getName());
						
						player.sendMessage(String.format(ChatColor.GREEN + "Player " + PlayerName + "a ete fait maire de la ville " + Town));
					
						Plugin.logToFile("[Mayor]: player " + PlayerName +" a ete fait maire par : " + player.getName());
					
						return true;
					}
					
					else if(args[0].equalsIgnoreCase("remove")){
						
						FarmcraftEco.perms.playerRemove(player.getWorld(), PlayerName, "FarmcraftEco.user.RRC." + Town);
						FarmcraftEco.perms.playerRemove(player.getWorld(), PlayerName, "farmcraft.rrc");
					
						FarmcraftEco.perms.playerRemove(player.getWorld(), PlayerName, "FarmcraftEco.user.BRC." + Town);
						FarmcraftEco.perms.playerRemove(player.getWorld(), PlayerName, "farmcraft.brc");
					
						FarmcraftEco.perms.playerRemove(player.getWorld(), PlayerName, "FarmcraftEco.user.bank." + Town);
						FarmcraftEco.perms.playerRemove(player.getWorld(), PlayerName, "farmcraft.bank");
					
						FarmcraftEco.perms.playerRemove(player.getWorld(), PlayerName, "worldedit.selection.pos");
						FarmcraftEco.perms.playerRemove(player.getWorld(), PlayerName, "worldedit.wand");
						FarmcraftEco.perms.playerRemove(player.getWorld(), PlayerName, "worldedit.wand.toggle");
						FarmcraftEco.perms.playerRemove(player.getWorld(), PlayerName, "worldedit.selection.expand");
						
						FarmcraftEco.chat.setPlayerPrefix(player.getWorld(), PlayerName, "[Membre]" );
						
						RegionManager regionManager = worldGuard.getRegionManager(player.getWorld());
						regionManager.getRegion(Town).getOwners().addPlayer(player.getName());
						
						player.sendMessage(String.format(ChatColor.GREEN + "Player " + PlayerName + "a ete defait de sa fonction de maire de la ville " + Town));
					
						Plugin.logToFile("[Mayor]: player " + PlayerName +" a ete defait de ses fonctions par par : " + player.getName());
					
						return true;
					}
					else{
						return false;
					}
					
				}
				else{
					player.sendMessage(String.format(ChatColor.RED + "Vous n'avez pas les permissions d'utilisez cette commande"));
					return true;
				}
				
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
	}
		
}
