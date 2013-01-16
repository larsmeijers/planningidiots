package model;

import java.util.Date;

public class Holiday {
	
	public Theme holidayTheme;
	
	public int durationInDays;
	
	public Date from;
	
	public Date to;
	
	public int maxParticipants;
	
	private Group participants;
	
	public Holiday()
	{	
		participants = new Group();
	}
	
	public void addParticipant(Person participant)
	{
		participants.addPersonToGroup(participant);
	}
	
	public void removeParticipant(Person participant)
	{
		participants.removePersonFromGroup(participant);
	}

}
