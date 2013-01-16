package model;

import java.beans.PropertyChangeListener;
import java.util.Date;

public abstract class Person implements PropertyChangeListener{
	
	public String name;
	
	public Date birth;
	
	public char sex;

	public String address;
	
	public String city;
	
	public String telephoneNumber;
	
	public String insurenceNumber;
	
	public String email;
	
	public String getName(){
		return name;
	}
	
	public void setName(String _name){
		name = _name;
	}
	
	public char getSex(){
		//giggety
		return sex;
	}
	
	public void setSex(char _sex){
		sex = _sex;
	}
	
}
