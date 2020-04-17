package com.techelevator.model;


import java.time.LocalDate;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ComicBook {
	
	private int comicId;
	private String title;
	private String issueTitle;
	private int issueNumber;
	private String publisher;
	private LocalDate publishDate;
	private String image;
	private String description;
	private String hero;
	private int volumeNumber;

	public int getComicId() { 
		return comicId;
	}

	public void setComicId(int comicId) {
		this.comicId = comicId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIssueTitle() {
		return issueTitle;
	}

	public void setIssueTitle(String issueTitle) {
		this.issueTitle = issueTitle;
	}

	public int getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(int issueNumber) {
		this.issueNumber = issueNumber;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPublishDate() {
		return publishDate.toString();
	}

	public void setPublishDate(LocalDate publishDate) {
		this.publishDate = publishDate;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHero() {
		return hero;
	} 

	public int getVolumeNumber() {
		return volumeNumber;
	}

	public void setVolumeNumber(int volumeNumber) {
		this.volumeNumber = volumeNumber;
	}

	public void setHero(String hero) {
		this.hero = hero;
	}



} 
