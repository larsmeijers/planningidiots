package model;

import java.util.ArrayList;

public class Group {

	public int getSize()
	{
		return clients.size();
	}
	
	public int maxSize;
	
    private ArrayList<Person> clients;
    
    public void addPersonToGroup(Person pers)
    {
    	clients.add(pers);
    }
    
    public void removePersonFromGroup(Person pers)
    {
    	clients.remove(pers);
    }
	
	
}
