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
	
	private boolean alwaysFalse = false;
	
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
		
		reCalculateProperties();
	}
	
	private void reCalculateProperties() {
		this.getMaxAverageIQ();
		this.getMinAverageIQ();
		
		this.getMaxAvgCommunicationlvl();
		this.getMinAvgCommunicationlvl();
		
		this.getMaxPresenceLvl();
		this.getMaxSensLvl();
		
		this.getMinAvgAge();
		this.getMaxAvgAge();
	
		this.getMinMobilityLevel();
		this.getExpectedGender();
		
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
			char prev = expectedGender;
			expectedGender =  participants.getExpextedGender();
			
			pcs.firePropertyChange("expectedGender", prev, expectedGender);
			return expectedGender;
		}
		
		private int calculateAverageIQ()
		{
			int sum = 0;
			for(int i=0; i < participants.getSize(); i++)
			{
				sum = sum + participants.getClient(i).getIq();
			}
			if(participants.getSize() == 0)
			{
				return 0;
			}
			else
			return sum / participants.getSize();
		}
		
		private int maxAverageIQ;
		public int getMaxAverageIQ()
		{
			int prevValue = maxAverageIQ;
			
			if(calculateAverageIQ() == 0)
				maxAverageIQ =  100;
			else
				maxAverageIQ =  calculateAverageIQ() +15;
			
			pcs.firePropertyChange("maxAverageIQ", prevValue, maxAverageIQ);
			return maxAverageIQ;
		}
		
		private int minAverageIQ;
		public int getMinAverageIQ()
		{
			int prevValue = minAverageIQ;
			minAverageIQ = calculateAverageIQ() -15;
			pcs.firePropertyChange("minAverageIQ", prevValue, minAverageIQ);
			
			return minAverageIQ;
		}
		
		private int calculateAverageAge()
		{
			int sum = 0;
			for(int i=0; i < participants.getSize(); i++)
			{
				sum = sum + participants.getClient(i).getAge();
			}
			if(participants.getSize() == 0)
				return 0;
			else
			return sum / participants.getSize();
		}
		
		private int maxAvgAge;
		public int getMaxAvgAge()
		{
			int prevage = maxAvgAge;
			
			if(calculateAverageIQ() == 0)
				maxAvgAge =  200;
			else
				maxAvgAge =  calculateAverageAge() + 20 ;
			
			pcs.firePropertyChange("maxAvgAge", prevage, maxAvgAge);
			
			return maxAvgAge;
		}
		
		private int minAvgAge;
		public int getMinAvgAge()
		{
			int prevage = minAvgAge;
			minAvgAge = calculateAverageAge() - 20;
			
			pcs.firePropertyChange("minAvgAge", prevage, minAvgAge);
			return minAvgAge;
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
		
		private int maxAvgCommunicationlvl;
		public int getMaxAvgCommunicationlvl()
		{
			int prev = maxAvgCommunicationlvl;
			if(calculateAverageIQ() == 0)
				maxAvgCommunicationlvl = 10;
			else
				maxAvgCommunicationlvl =  calculateAverageCommunicationLvl() +1;
			
			pcs.firePropertyChange("maxAvgCommunicationlvl", prev, maxAvgCommunicationlvl);
			
			return maxAvgCommunicationlvl;
		}
		
		private int minAvgCommunicationlvl;
		public int getMinAvgCommunicationlvl()
		{
			int prev = minAvgCommunicationlvl;
			minAvgCommunicationlvl = calculateAverageCommunicationLvl() -1;
			
			pcs.firePropertyChange("minAvgCommunicationlvl", prev, minAvgCommunicationlvl);
			
			return minAvgCommunicationlvl;
		}
		
		private int calculateAverageMobilityLvl()
		{
			int sum = 0;
			for(int i=0; i < participants.getSize(); i++)
			{
				sum = sum + participants.getClient(i).getMobilityLevel();
			}
			if(participants.getSize() == 0)
				return 0;
			else
			return sum / participants.getSize();
		}
		
		//Calculates the minimum mobility level for the next candidate of this group,
		//based on the current average of this group, we try to keep this average around 3.
		// so if average below 3 then we want a member with current group mobility average or higher
		private int minMobilityLevel;
		public int getMinMobilityLevel()
		{
			int prev = minMobilityLevel;
			if(calculateAverageMobilityLvl() == 1) // if average is 1 then the candidate has to be 2 or higher.
				minMobilityLevel =  2;
			else
				minMobilityLevel =  calculateAverageMobilityLvl();
			
			pcs.firePropertyChange("minMobilityLevel", prev, minMobilityLevel);
			
			return minMobilityLevel;
		}
		
		private int calculateAverageSensibility()
		{
			int sum = 0;
			for(int i=0; i < participants.getSize(); i++)
			{
				sum = sum + participants.getClient(i).getSensibilityForStress();
			}
			if(participants.getSize() == 0)
				return 0;
			else
			return sum / participants.getSize();
			
		}
		
		private int maxPresenceLvl;
		public int getMaxPresenceLvl()
		{
			int prev = maxPresenceLvl;
			int sensOfGroup = calculateAverageSensibility();
			if(sensOfGroup == 5)
				maxPresenceLvl =  2;
			else if (sensOfGroup == 4)
				maxPresenceLvl =  3;
			else if (sensOfGroup == 3)
				maxPresenceLvl =  4;
			else maxPresenceLvl =  5;
			
			pcs.firePropertyChange("maxPresenceLvl", prev, maxPresenceLvl);
			return maxPresenceLvl;
		}
		
		private int calculateAveragePresencelvl()
		{
			int sum = 0;
			for(int i=0; i < participants.getSize(); i++)
			{
				sum = sum + participants.getClient(i).getPresenceLevel();
			}
			if(participants.getSize() == 0)
				return 0;
			else
			return sum / participants.getSize();
			
			
		}
		
		private int maxSensLvl;
		public int getMaxSensLvl()
		{
			int prev = maxSensLvl;
			int presslvl =calculateAveragePresencelvl();
			if(presslvl == 5)
				maxSensLvl =  2;
			else if(presslvl == 4)
				maxSensLvl =  3;
			else if (presslvl == 3)
				maxSensLvl =  4;
			else
				maxSensLvl =  5;
			
			pcs.firePropertyChange("maxSensLvl", prev, maxSensLvl);
			return maxSensLvl;
		}

		public boolean isAlwaysFalse() {
			return alwaysFalse;
		}

		
		


}
