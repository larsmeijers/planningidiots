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
	private int oneToOneCount;
	
	private char expectedGender;
	
	
	private Group participants;
	private PropertyChangeSupport pcs;
	
	public Holiday()
	{	pcs = new PropertyChangeSupport(this);
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
		participant.setIsPlanned(true);
		numberOfParticipants++;
		if(participant.getNeedsOneToOneGuidance())
		{
			oneToOneCount++;
			pcs.firePropertyChange("oneToOneCount", oneToOneCount-1, oneToOneCount);
		}
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

		public int getOneToOneCount() {
			return oneToOneCount;
		}

		public void setOneToOneCount(int oneToOneCount) {
			this.oneToOneCount = oneToOneCount;
		}

		public char getExpectedGender() {
			return participants.getExpextedGender();
		}
		
		private int calculateAverageIQ()
		{
			int sum = 0;
			for(int i=0; i < participants.getSize(); i++)
			{
				sum = sum + participants.getClient(i).getIq();
			}
			if(participants.getSize() == 0)
				return 0;
			else
			return sum / participants.getSize();
		}
		
		public int getMaxAverageIQ()
		{
			if(calculateAverageIQ() == 0)
				return 100;
			else
			return calculateAverageIQ() +15;
		}
		
		public int getMinAverageIQ()
		{
			return calculateAverageIQ() -15;
		}
		
		private int calculateAverageCommunicationLvl()
		{
			int sum = 0;
			for(int i=0; i < participants.getSize(); i++)
			{
				sum = sum + participants.getClient(i).getCommunicativeSkill();
			}
			if(participants.getSize() == 0)
				return 0;
			else
			return sum / participants.getSize();
		}
		
		public int getMaxAvgCommunicationlvl()
		{
			if(calculateAverageIQ() == 0)
				return 10;
			else
			return calculateAverageCommunicationLvl() +1;
		}
		
		public int getMinAvgCommunicationlvl()
		{
			return calculateAverageCommunicationLvl() -1;
		}


}
