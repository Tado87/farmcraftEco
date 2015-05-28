package fr.farmcraft.farmcraftEco.Fonction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.joda.time.DateTime;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;

import fr.farmcraft.farmcraftEco.FarmcraftEco;

public class RRbuy {
	public static FarmcraftEco Plugin;

	public RRbuy(FarmcraftEco Instance){

			Plugin = Instance;
	}
	
	
	
	
		public static Boolean RentRegionBuy(Player player,String playerName, Sign s) throws SQLException{
		
			if (FarmcraftEco.perms.has(player, "FarmcraftEco.admin.Region.buy")){
				
				String terrain = s.getLine(1);
				String rentTime = s.getLine(3);
				String prix = s.getLine(2);
				
					
					Statement getRental = Plugin.connection.createStatement();
					ResultSet GR = getRental.executeQuery("SELECT * FROM FarmcraftEco WHERE Terrain =" + terrain + "");
					String compte = GR.getString("Compte");
				
				WorldGuardPlugin worldGuard = Plugin.getWorldGuard();
				
				RegionManager regionManager = worldGuard.getRegionManager(player.getWorld());
				
				if (FarmcraftEco.econ.has(player, Double.parseDouble(prix))) {
        			
					EconomyResponse t = FarmcraftEco.econ.withdrawPlayer(player, Double.parseDouble(prix));
		
					if (t.transactionSuccess()) {
						
							EconomyResponse r = FarmcraftEco.econ.depositPlayer(compte, Double.parseDouble(prix));
			
							if (r.transactionSuccess()) {
								
									regionManager.getRegion(terrain).getMembers().addPlayer(player.getName());
									
									Statement BuyRental = Plugin.connection.createStatement();
									BuyRental.executeUpdate("UPDATE `RentRegion` SET `ExpirDate`=NOW()+INTERVAL " + rentTime + " DAY, `Playername`=" +playerName +" WHERE Terrain=" + terrain +";");
									
									Statement expirtDate = Plugin.connection.createStatement();
									ResultSet ED = getRental.executeQuery("SELECT * FROM FarmcraftEco WHERE Terrain =" + terrain + "");
									
									String expirDate = ED.getString("ExpirDate");
									
									player.sendMessage(String.format(ChatColor.GREEN + "Vous avez achetez " + terrain));
									
									s.setLine(0, ChatColor.BLUE + "[RentedRegion]");
									
									s.setLine(2, ChatColor.YELLOW + playerName);
									
									s.setLine(3, ChatColor.BLUE + expirDate);
									
									s.update();
				
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
				player.sendMessage(String.format(ChatColor.RED + "Vous navez pas les permissions", new Object[0]));
		}
return true;

}
}

