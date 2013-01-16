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
	
	public void addParticipant(Client participant)
	{
		participants.addClientToGroup(participant);
	}
	
	public void removeParticipant(Client participant)
	{
		participants.removeClientFromGroup(participant);
	}

}
