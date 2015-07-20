package fr.farmcraft.farmcraftEco.Fonction;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.farmcraft.farmcraftEco.FarmcraftEco;

public class Mayor implements CommandExecutor{
	
	public FarmcraftEco Plugin;
	
	public Mayor(FarmcraftEco Instance){
		
		Plugin = Instance;
	}

	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args)
	
	{
	   
		if (command.getName().equalsIgnoreCase("Mayor")) {
			
		
			if (args.length == 3){
				
				String Town = args[1];
				
				String PlayerName = args[2];
				
				
				Player player = (Player)sender;
				
				if (Plugin.perms.has(player, "FarmcraftEco.admin.mayor")){
					
					if (args[0].equalsIgnoreCase("add")){
					
						Plugin.perms.playerAdd(player.getWorld(), PlayerName, "FarmcraftEco.user.RRC." + Town);
						Plugin.perms.playerAdd(player.getWorld(), PlayerName, "farmcraft.rrc");
					
						Plugin.perms.playerAdd(player.getWorld(), PlayerName, "FarmcraftEco.user.BRC." + Town);
						Plugin.perms.playerAdd(player.getWorld(), PlayerName, "farmcraft.brc");
					
						Plugin.perms.playerAdd(player.getWorld(), PlayerName, "FarmcraftEco.user.bank." + Town);
						Plugin.perms.playerAdd(player.getWorld(), PlayerName, "farmcraft.bank");
					
						Plugin.perms.playerAdd(player.getWorld(), PlayerName, "worldedit.selection.pos");
						Plugin.perms.playerAdd(player.getWorld(), PlayerName, "worldedit.wand");
						Plugin.perms.playerAdd(player.getWorld(), PlayerName, "worldedit.wand.toggle");
						Plugin.perms.playerAdd(player.getWorld(), PlayerName, "worldedit.selection.expand");
						
						Plugin.chat.setPlayerPrefix(player.getWorld(), PlayerName, "[Mayor" + Town +"]" );
					
					
						Plugin.logToFile("[Mayor]: player " + PlayerName +" a ete fait maire par : " + player.getName());
					
						return true;
					}
					
					else if(args[0].equalsIgnoreCase("remove")){
						
						Plugin.perms.playerRemove(player.getWorld(), PlayerName, "FarmcraftEco.user.RRC." + Town);
						Plugin.perms.playerRemove(player.getWorld(), PlayerName, "farmcraft.rrc");
					
						Plugin.perms.playerRemove(player.getWorld(), PlayerName, "FarmcraftEco.user.BRC." + Town);
						Plugin.perms.playerRemove(player.getWorld(), PlayerName, "farmcraft.brc");
					
						Plugin.perms.playerRemove(player.getWorld(), PlayerName, "FarmcraftEco.user.bank." + Town);
						Plugin.perms.playerRemove(player.getWorld(), PlayerName, "farmcraft.bank");
					
						Plugin.perms.playerRemove(player.getWorld(), PlayerName, "worldedit.selection.pos");
						Plugin.perms.playerRemove(player.getWorld(), PlayerName, "worldedit.wand");
						Plugin.perms.playerRemove(player.getWorld(), PlayerName, "worldedit.wand.toggle");
						Plugin.perms.playerRemove(player.getWorld(), PlayerName, "worldedit.selection.expand");
						
						Plugin.chat.setPlayerPrefix(player.getWorld(), PlayerName, "[Membre]" );
						
						
					
					
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
