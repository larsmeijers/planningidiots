package model;

import java.util.ArrayList;

public class Group {

	public int maxSize;
    private ArrayList<Client> clients;
    
    public Group(){
    	clients = new ArrayList<Client>();
    }
    
	public int getSize()
	{
		return clients.size();
	}

    public void addClientToGroup(Client client)
    {
    	clients.add(client);
    }
    
    public void removeClientFromGroup(Client client)
    {
    	clients.remove(client);
    }
	
    public ArrayList<Client> getClients(){
    	return clients;
    }
    
    public Client getClient(int index){
    	return clients.get(index);
    }
	
}
