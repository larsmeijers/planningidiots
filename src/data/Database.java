package data;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import com.thoughtworks.xstream.XStream;

import model.Client;

public class Database {
	
	private String filePath;
	private XStream xml_io;
	public ArrayList<Client> clients;
	
	public Database(String _path)
	{
		xml_io = new XStream();
		xml_io.alias("client", Client.class);
		clients = new ArrayList<Client>();
		filePath = _path;
	}
	
	public void exportClientToXML(Client _client)
	{
		FileWriter _writer;
		
		try 
		{	
			_writer = new FileWriter(filePath + _client.name + ".xml");
			xml_io.toXML(_client, _writer);	
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<Client> importClientsFromXML()
	{				
			File dir = new File(filePath);
			if (dir.isDirectory())
			{
				
			  for (File child : dir.listFiles()) {	    
				  clients.add((Client)xml_io.fromXML(child));
			  }
			  
			}
			
			return clients;
		
	}
}
