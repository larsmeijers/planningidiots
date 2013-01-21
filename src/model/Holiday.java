package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.Date;

public class Holiday {
	
	private Theme holidayTheme;
	
	private int durationInDays;
	
	private Date from;
	
	private Date to;
	
	private int maxParticipants;
	
	private int numberOfParticipants;
	
	private Group participants;
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	public Holiday()
	{	
		participants = new Group();
		maxParticipants = 12;
	}
	
	public int getNumberOfParticipants()
	{	
		return numberOfParticipants;
	}
	
	public void addParticipant(Client participant)
	{
		participants.addClientToGroup(participant);
		numberOfParticipants++;
		pcs.firePropertyChange("numberOfParticipants", numberOfParticipants-1, numberOfParticipants);
	}
	
	public void removeParticipant(Client participant)
	{
		participants.removeClientFromGroup(participant);
	}

	public Theme getHolidayTheme() {
		return holidayTheme;
	}

	public void setHolidayTheme(Theme holidayTheme) {
		this.holidayTheme = holidayTheme;
	}

	public int getDurationInDays() {
		return durationInDays;
	}

	public void setDurationInDays(int durationInDays) {
		this.durationInDays = durationInDays;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public int getMaxParticipants() {
		return maxParticipants;
	}

	public Client getParticipant(int i) {
		return participants.getClient(i);
	}

	 public void addPropertyChangeListener(PropertyChangeListener pcl) {
	        pcs.addPropertyChangeListener(pcl);
	    }
	    public void removePropertyChangeListener(PropertyChangeListener pcl) {
	        pcs.removePropertyChangeListener(pcl);
	    }

}
