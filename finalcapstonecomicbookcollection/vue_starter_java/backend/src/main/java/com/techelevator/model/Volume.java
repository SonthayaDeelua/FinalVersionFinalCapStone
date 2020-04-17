package com.techelevator.model;

public class Volume {
	
	private String volumeNumber;
	private String volumeImg;
	private String volumeName;
	private String publisher;
	private String VolumeDescription;
	private String NumberOfIssues;
	private String StartYear;
	
	public String getStartYear() {
		return StartYear;
	}
	public void setStartYear(String startYear) {
		StartYear = startYear;
	}
	public String getVolumeNumber() {
	
		return volumeNumber;
	}
	public void setVolumeNumber(String volumeNumber) {
		this.volumeNumber = volumeNumber;
	}
	public String getVolumeImg() {
		return volumeImg;
	}
	public void setVolumeImg(String volumeImg) {
		this.volumeImg = volumeImg;
	}
	public String getVolumeName() {
		return volumeName;
	}
	public void setVolumeName(String volumeName) {
		this.volumeName = volumeName;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getVolumeDescription() {
		return VolumeDescription;
	}
	public void setVolumeDescription(String volumeDescription) {
		VolumeDescription = volumeDescription;
	}
	public String getNumberOfIssues() {
		return NumberOfIssues;
	}
	public void setNumberOfIssues(String numberOfIssues) {
		NumberOfIssues = numberOfIssues;
	}

	
	

}

//volumeList.add(volumeImg);
//volumeList.add(volumeName);
//volumeList.add(publisher);
//volumeList.add(volumeDescription)