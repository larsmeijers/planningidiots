package model;

import java.util.Calendar;
import java.util.Date;

public abstract class Person{
	
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
	
	public int getAge(){
		Calendar dob = Calendar.getInstance();  
		dob.setTime(birth);  
		Calendar today = Calendar.getInstance();  
		int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);  
		if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH)) {
		  age--;  
		} else if (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH)
		    && today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
		  age--;  
		}
		return age;
	}
	
}
