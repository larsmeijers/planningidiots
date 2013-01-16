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


import model.Client;
import model.Group;
import model.MusicTaste;
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
			client.communicativeSkill = 4;
			client.email = "Henk@hotmail.com";
			client.fishingSeriousness = -1;
			client.hasAllergy = false;
			client.hasDiet = false;
			client.horseRidingSkill = -1;
			client.hoursOfCarePerDay = 3;
			client.independenceLevel = 4;
			client.insurenceNumber = "123214";
			client.iq = 75;
			client.mobilityLevel = 5;
			client.musicPreference = MusicTaste.Top40;
			client.needsOneToOneGuidance= false;
			client.presenceLevel = 3;
			client.sailingIQ = -1;
			client.sex = 'm';
			client.socialSkill = 4;
			client.telephoneNumber = "1234981423";
			client.toleranceOfStress = 5;
			client.weight = 95;
			client.prefferedHoliday = Theme.Music;
			
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
