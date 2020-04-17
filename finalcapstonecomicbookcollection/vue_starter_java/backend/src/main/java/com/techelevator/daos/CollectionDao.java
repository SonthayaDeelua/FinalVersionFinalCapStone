package com.techelevator.daos;

import java.util.List;

import com.techelevator.model.Collection;
import com.techelevator.model.ComicBook;
import com.techelevator.model.User;



public interface CollectionDao {
	
	public void saveCollection(Collection collection);
	
	public List<Collection> getAllCollectionsByUserId(Long userId);
	
	public void addComicToCollection(int comicId, User user, int collectionId);

	public Collection getCollectionByCollectionId(int collectionId);
	
	public void deleteCollection(int collectionId);
	
	public void updateCollection(Collection collection);
	
	public List<Collection> getPublicCollection();

	List<Collection> getPublicCollectionsByUsername(String username);
	
	public List<User> getTopUsersByCollectionCount();
	
}
