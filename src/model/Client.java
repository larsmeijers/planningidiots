package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Client extends Person{
	
private double weight;
	
private int iq;
	
private int socialSkill;
	
private int communicativeSkill;
	
private int horseRidingSkill;
	
private int independenceLevel;
	
private int mobilityLevel;
	
private int toleranceOfStress;
	
private int presenceLevel;
	
private int sailingIQ;
	
private int hoursOfCarePerDay;
	
private int fishingSeriousness;
	
private Boolean needsOneToOneGuidance;
	
private Boolean hasDiet;
	
private Boolean hasAllergy;
	
private MusicTaste musicPreference;
	
private Theme prefferedHoliday;

private boolean isPlanned;

private PropertyChangeSupport pcs;

public Client()
{
	pcs = new PropertyChangeSupport(this);
	}

public double getWeight() {
	return weight;
}

public void setWeight(double weight) {
	this.weight = weight;
}

public int getIq() {
	return iq;
}

public void setIq(int iq) {
	this.iq = iq;
}

public int getSocialSkill() {
	return socialSkill;
}

public void setSocialSkill(int socialSkill) {
	this.socialSkill = socialSkill;
}

public int getCommunicativeSkill() {
	return communicativeSkill;
}

public void setCommunicativeSkill(int communicativeSkill) {
	this.communicativeSkill = communicativeSkill;
}

public int getHorseRidingSkill() {
	return horseRidingSkill;
}

public void setHorseRidingSkill(int horseRidingSkill) {
	this.horseRidingSkill = horseRidingSkill;
}

public int getIndependenceLevel() {
	return independenceLevel;
}

public void setIndependenceLevel(int independenceLevel) {
	this.independenceLevel = independenceLevel;
}

public int getMobilityLevel() {
	return mobilityLevel;
}

public void setMobilityLevel(int mobilityLevel) {
	this.mobilityLevel = mobilityLevel;
}

public int getToleranceOfStress() {
	return toleranceOfStress;
}

public void setToleranceOfStress(int toleranceOfStress) {
	this.toleranceOfStress = toleranceOfStress;
}

public int getPresenceLevel() {
	return presenceLevel;
}

public void setPresenceLevel(int presenceLevel) {
	this.presenceLevel = presenceLevel;
}

public int getSailingIQ() {
	return sailingIQ;
}

public void setSailingIQ(int sailingIQ) {
	this.sailingIQ = sailingIQ;
}

public int getHoursOfCarePerDay() {
	return hoursOfCarePerDay;
}

public void setHoursOfCarePerDay(int hoursOfCarePerDay) {
	this.hoursOfCarePerDay = hoursOfCarePerDay;
}

public int getFishingSeriousness() {
	return fishingSeriousness;
}

public void setFishingSeriousness(int fishingSeriousness) {
	this.fishingSeriousness = fishingSeriousness;
}

public Boolean getNeedsOneToOneGuidance() {
	return needsOneToOneGuidance;
}

public void setNeedsOneToOneGuidance(Boolean needsOneToOneGuidance) {
	this.needsOneToOneGuidance = needsOneToOneGuidance;
}

public Boolean getHasDiet() {
	return hasDiet;
}

public void setHasDiet(Boolean hasDiet) {
	this.hasDiet = hasDiet;
}

public Boolean getHasAllergy() {
	return hasAllergy;
}

public void setHasAllergy(Boolean hasAllergy) {
	this.hasAllergy = hasAllergy;
}

public MusicTaste getMusicPreference() {
	return musicPreference;
}

public void setMusicPreference(MusicTaste musicPreference) {
	this.musicPreference = musicPreference;
}

public Theme getPrefferedHoliday() {
	return prefferedHoliday;
}

public void setPrefferedHoliday(Theme prefferedHoliday) {
	this.prefferedHoliday = prefferedHoliday;
}

public boolean getIsPlanned() {
	return isPlanned;
}

public void setIsPlanned(boolean isPlanned) {
	this.isPlanned = isPlanned;
	pcs.firePropertyChange("isPlanned", !isPlanned, isPlanned);
}

public void addPropertyChangeListener(PropertyChangeListener pcl) {
	if(pcs == null)
	{
		pcs = new PropertyChangeSupport(this);
	}
    pcs.addPropertyChangeListener(pcl);
}
public void removePropertyChangeListener(PropertyChangeListener pcl) {
	if(pcs == null)
	{
		pcs = new PropertyChangeSupport(this);
	}
    pcs.removePropertyChangeListener(pcl);
}

}
