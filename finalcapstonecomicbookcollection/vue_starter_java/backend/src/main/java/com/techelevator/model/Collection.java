package com.techelevator.model;

import java.util.List;

public class Collection {
	
	private int collectionId;
	private int userId;
	private String name;
	private String description;
	private boolean isPrivate;
	private List<ComicBook> comicsInCollection;
	private String username;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isPrivate() {
		return isPrivate;
	}
	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

	public int getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(int collectionId) {
		this.collectionId = collectionId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	public List<ComicBook> getComicsInCollection() {
		return comicsInCollection;
	}
	public void setComicsInCollection(List<ComicBook> comicsInCollection) {
		this.comicsInCollection = comicsInCollection;
	}
	public String getUsername() {
		return username;
	} 
	public void setUsername(String username) {
		this.username = username;
	}
	
	
 
}
