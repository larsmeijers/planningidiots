package model;

import java.util.ArrayList;


public class Group {

	public int maxSize;
    private ArrayList<Client> clients;
    
    private int male;
    private int female;
    
    private char expextedGender;
    
    public Group(){
    	clients = new ArrayList<Client>();
    }
    
	public int getSize()
	{
		return clients.size();
	}

    public void addClientToGroup(Client client)
    {
    	if(client.sex == 'm')
    		male++;
    	if(client.sex == 'f')
    		female++;
    	
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

	public char getExpextedGender() {
		if((male % 2) != 0)
		{
			return 'm';
		}
		else
			return 'f';
	}

	public void setExpextedGender(char expextedGender) {
		this.expextedGender = expextedGender;
	}
	
}
