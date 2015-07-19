package fr.farmcraft.farmcraftEco.Fonction;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Polygonal2DSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.RegionGroup;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.managers.storage.StorageException;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedPolygonalRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion.CircularInheritanceException;

import fr.farmcraft.farmcraftEco.FarmcraftEco;

public class CreateBR implements CommandExecutor {
	
	
	public FarmcraftEco Plugin;
	
	public CreateBR(FarmcraftEco Instance){
		
		Plugin = Instance;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args)
	
	{
	   
		if (!(sender instanceof Player)) {
    	
			FarmcraftEco.log.info("Seuls les joueurs peuvent utilisés cette commande");
      
			return true;
      
		}
		
		
		
		if (command.getName().equalsIgnoreCase("brc")) {
			
			
			if (args.length ==3){
				
			
					String Town = args[0];
					
					String Name = args[1];
					
					String prix = args[2];
					
					Player player = (Player)sender;
					
					World world = player.getWorld();
			
					Location loc = player.getLocation();
					
					WorldGuardPlugin worldGuard = Plugin.getWorldGuard();
					
					WorldEditPlugin worldEdit = Plugin.getWorldEdit();
					
					boolean success = false;
					
							com.sk89q.worldedit.Vector v = BukkitUtil.toVector(loc);
							RegionManager manager = worldGuard.getRegionManager(loc.getWorld());
							ApplicableRegionSet set = manager.getApplicableRegions(v);
							for (ProtectedRegion each : set){
							
									if (each.getId().equalsIgnoreCase(Town)){
					        	
											success = true;
									}
							}
						
							ProtectedRegion nameUseCheck = manager.getRegion(Name);
						
							Boolean nameUseCheckResult;
						
							if( nameUseCheck != null){
							
									nameUseCheckResult = false;
							}
							else {
							
									nameUseCheckResult = true;
							}
							
							if (FarmcraftEco.perms.has(player, "FarmcraftEco.user.BRC." + Town)){
						
									if (nameUseCheckResult == true &&  success == true){
					
											Selection sel = worldEdit.getSelection(player);
						
											ProtectedRegion region;

											if (sel instanceof Polygonal2DSelection) {
							
													Polygonal2DSelection polySel = (Polygonal2DSelection) sel;
						
													int minY = polySel.getNativeMinimumPoint().getBlockY();
						
													int maxY = polySel.getNativeMaximumPoint().getBlockY();
						
													region = new ProtectedPolygonalRegion(Name, polySel.getNativePoints(), minY, maxY);
									
													RegionManager regionManager = worldGuard.getRegionManager(player.getWorld());
													regionManager.addRegion(region);
													regionManager.getRegion(Name).getOwners().addPlayer(player.getName());
							
													region.setFlag(DefaultFlag.USE, StateFlag.State.DENY);
													region.setFlag(DefaultFlag.USE.getRegionGroupFlag(), RegionGroup.NON_MEMBERS);
							
													region.setFlag(DefaultFlag.INTERACT, StateFlag.State.DENY);
													region.setFlag(DefaultFlag.INTERACT.getRegionGroupFlag(), RegionGroup.NON_MEMBERS);
							
													region.setFlag(DefaultFlag.CHEST_ACCESS, StateFlag.State.DENY);
													region.setFlag(DefaultFlag.CHEST_ACCESS.getRegionGroupFlag(), RegionGroup.NON_MEMBERS);
							
													region.setPriority(12);
							
													ProtectedRegion RG = worldGuard.getRegionContainer().get(player.getWorld()).getRegion(Town);
							
													try {
														region.setParent(RG);
													} catch (CircularInheritanceException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													}
													try {
														regionManager.save();
													} catch (StorageException e1) {
														// TODO Auto-generated catch block
														e1.printStackTrace();
													}
							
							
											} 
											else if (sel instanceof CuboidSelection) {
							
													BlockVector min = sel.getNativeMinimumPoint().toBlockVector();
						
													BlockVector max = sel.getNativeMaximumPoint().toBlockVector();
						
													region = new ProtectedCuboidRegion(Name, min, max);
									
													RegionManager regionManager = worldGuard.getRegionManager(player.getWorld());
													regionManager.addRegion(region);
													regionManager.getRegion(Name).getOwners().addPlayer(player.getName());
							
													region.setFlag(DefaultFlag.USE, StateFlag.State.DENY);
													region.setFlag(DefaultFlag.USE.getRegionGroupFlag(), RegionGroup.NON_MEMBERS);
							
													region.setFlag(DefaultFlag.INTERACT, StateFlag.State.DENY);
													region.setFlag(DefaultFlag.INTERACT.getRegionGroupFlag(), RegionGroup.NON_MEMBERS);
							
													region.setFlag(DefaultFlag.CHEST_ACCESS, StateFlag.State.DENY);
													region.setFlag(DefaultFlag.CHEST_ACCESS.getRegionGroupFlag(), RegionGroup.NON_MEMBERS);
							
													region.setPriority(12);
							
													ProtectedRegion RG = worldGuard.getRegionContainer().get(player.getWorld()).getRegion(Town);
							
													try {
														region.setParent(RG);
													} catch (CircularInheritanceException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													}
													try {
														regionManager.save();
													} catch (StorageException e1) {
														// TODO Auto-generated catch block
														e1.printStackTrace();
													}

												}
												
												player.sendMessage(String.format(ChatColor.GREEN + "Region  " + Name + " mis en vente pour un prix de " + prix + " au profit de la ville " + Town));
						
												player.getWorld().getBlockAt(loc).setTypeId(63);
						
												Block block = player.getWorld().getBlockAt(loc);	// update sign
						
												Sign s = (Sign) block.getState();
						
												s.setLine(0, ChatColor.BLUE + "[BuyRegion]");
						
												s.setLine(1, Name);													
						
												s.setLine(2, prix);
						
												s.setLine(3, Town);
						
												s.update();
						
												return true;

									}
									else{
											player.sendMessage(String.format(ChatColor.RED + "Vous n'etes pas dans la region "+ ChatColor.GREEN + Town + ChatColor.RED + " ou la region "+ ChatColor.GREEN + Name + ChatColor.RED + " existe déja"));
											return true;
									}
							}
							else{
									player.sendMessage(String.format(ChatColor.RED + "Vous n'avez pas les permissions d'utilisez cette commande pour cette zone"));
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
