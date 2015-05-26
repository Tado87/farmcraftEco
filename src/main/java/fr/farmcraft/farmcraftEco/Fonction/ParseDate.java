package fr.farmcraft.farmcraftEco.Fonction;

import org.joda.time.DateTime;

import fr.farmcraft.farmcraftEco.FarmcraftEco;

public class ParseDate {


		public static FarmcraftEco Plugin;
		public ParseDate(FarmcraftEco Instance){
			Plugin = Instance;
		}
		
		public static DateTime DateNOW(){
			
			DateTime datenow = new DateTime();
			System.out.println("[" + datenow + "] Debug" ); //debug
			
			return datenow;
		}
		public static DateTime ExpirationDate(int numberofdays){
			
			DateTime datenow = new DateTime();
			DateTime expirdate = datenow.plusDays(numberofdays);
			
			System.out.println("[" + expirdate + "] Debug" ); //debug
			
			return expirdate;
		}
		

}
