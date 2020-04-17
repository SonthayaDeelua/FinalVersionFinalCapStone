package com.techelevator.daos;

import java.util.List;

import com.techelevator.model.ComicBook;
import com.techelevator.model.User;

public interface ComicBookDao {
	
	public void saveComic(ComicBook comic);
	
	public List<ComicBook> getAllComicsByUserId(int userId);
	
	public List<ComicBook> getAllComicsByCollectionId(int collectionId);
	
	public ComicBook getLatestComic();
	
	public void deleteComic(int comicId);
	
	public void updateComic(ComicBook comicBook);

	public ComicBook getComicById(int comicId);
	
	public int comicCount();

	List<User> getTopUsersByComicCount();
	
	List<ComicBook> getAllComicBooks();

}
