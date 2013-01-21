package planner;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

import jess.Filter;
import jess.JessException;
import jess.Rete;
import jess.Filter.ByClass;


import model.Client;
import model.Group;
import model.MusicTaste;
import model.Person;
import model.Theme;
import data.Database;

public class Planner {
	
	private Database data;
	private Rete engine;
	
	public static void main(String[] args) {
		
		Planner plan = new Planner(args);
		
			System.out.println("################################");
			System.out.println("#   VU Knowledge engineering   #");
		    System.out.println("#    	  Planning Tool	       #");
			System.out.println("#              By              #");
			System.out.println("#     Lars, Floris en Sanne    #");
			System.out.println("################################");
			System.out.println("");

			
			boolean exit = false;
			Scanner readUserInput=new Scanner(System.in);

		while(!exit)
		{
			System.out.println("");
			System.out.println("What do you want to do: import, export, plan or quit ?");
			System.out.println("");

				
		        String command = readUserInput.nextLine();
		        
		        switch (command.toLowerCase()) {
				case "import":
					plan.importData();
					break;
				case "export":
				    plan.exportExampleClient();
					break;
				case "plan":
					plan.planGroups();
					break;
				case "quit":
				exit = true;
				break;
				
				default:
					System.out.println("Not a valid command.");
					break;
				}		        	        
		}
				
	}
	
	private void planGroups(){
		if(data.clients.isEmpty()){
			System.out.println("Import data before planning!");
		} else{
			try {
				Group group = new Group();
				for(int i=0; i < data.clients.size(); i++){
					group.addClientToGroup(data.clients.get(i));
				}

				engine.reset();
				engine.batch("rules.clp");
				engine.addAll(data.clients);
				engine.add(group);
				engine.run();
				
				
				
			} catch (JessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	private void importData() {
		
		System.out.println("*** Starting client import ***");
		System.out.println("");
		
		ArrayList<Client> clients = data.importClientsFromXML();
		
		 for (int i = 0; i < clients.size(); i++) {	
		 
			 System.out.println("--> Imported client: " + clients.get(i).name);
		 }
		 
		 System.out.println("");
		 System.out.println("*** client import finished ***");
	}
	
	private void exportExampleClient()
	{	
		try {
			Client client = new Client();
			client.name = "Henk";
			client.address = "verre wegstraat 3";
			client.city = "Amsterdam";
		    SimpleDateFormat formatter = new SimpleDateFormat("DD-MM-yyyy"); 
			client.birth = formatter.parse("15-09-1980");
			client.setCommunicativeSkill(4);
			client.email = "Henk@hotmail.com";
			client.setFishingSeriousness(-1);
			client.setHasAllergy(false);
			client.setHasDiet(false);
			client.setHorseRidingSkill(-1);
			client.setHoursOfCarePerDay(3);
			client.setIndependenceLevel(4);
			client.insurenceNumber = "123214";
			client.setIq(75);
			client.setMobilityLevel(5);
			client.setMusicPreference(MusicTaste.Top40);
			client.setNeedsOneToOneGuidance(false);
			client.setPresenceLevel(3);
			client.setSailingIQ(-1);
			client.sex = 'm';
			client.setSocialSkill(4);
			client.telephoneNumber = "1234981423";
			client.setToleranceOfStress(5);
			client.setWeight(95);
			client.setPrefferedHoliday(Theme.Music);
			
			data.exportClientToXML(client);		
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public Planner(String[] args) 
	{
		data = new Database(args[0]);
		
		engine = new Rete();
	}

}
