package com.techelevator.controller;

import com.techelevator.authentication.AuthProvider;
import com.techelevator.authentication.RequestAuthProvider;
import com.techelevator.authentication.UnauthorizedException;
import com.techelevator.daos.CollectionDao;
import com.techelevator.daos.ComicBookDao;
import com.techelevator.daos.UserDao;
import com.techelevator.model.Collection;
import com.techelevator.model.ComicBook;
import com.techelevator.model.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * ApiController
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiController {

    @Autowired
    private AuthProvider authProvider;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CollectionDao collectionDao;
    @Autowired
    private ComicBookDao comicBookDao;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String authorizedOnly() throws UnauthorizedException {
        /*
        You can lock down which roles are allowed by checking
        if the current user has a role.
        
        In this example, if the user does not have the admin role
        we send back an unauthorized error.
        */
        if (!authProvider.userHasRole(new String[] { "admin" })) {
            throw new UnauthorizedException();
        }
        return "Success";
    }
    
    @GetMapping("/collections")
    public List<Collection> getAllPublicCollections() {
    	List<Collection> collections = collectionDao.getPublicCollection();
    	for	(Collection collection: collections) {
			List<ComicBook> comicsInCollection = comicBookDao.getAllComicsByCollectionId(collection.getCollectionId());
			collection.setComicsInCollection(comicsInCollection);
		}
    	return collections;
    }
    
    @GetMapping("user/{username}/collections")
    public List<Collection> getAllPublicCollectionsByUser(@PathVariable String username) {
    	List<Collection> collections = collectionDao.getPublicCollectionsByUsername(username);
    	for	(Collection collection: collections) {
			List<ComicBook> comicsInCollection = comicBookDao.getAllComicsByCollectionId(collection.getCollectionId());
			collection.setComicsInCollection(comicsInCollection);
		}
    	return collections;
    }
    
    @GetMapping("/user/single/{id}")
    public User getUserById(@PathVariable int id)  {
    	User user = userDao.getUserById((long)id);
    	List<Collection> collections = collectionDao.getAllCollectionsByUserId(user.getId());
    	for(Collection collection: collections) {
        List<ComicBook> comicsInCollection = comicBookDao.getAllComicsByCollectionId(collection.getCollectionId());
    	collection.setComicsInCollection(comicsInCollection);
    	}
    	user.setCollections(collections);
    	return user;
    }
    
    @GetMapping("/users")
    public List<User> getAllUsers() throws UnauthorizedException {
    	List<User> users = userDao.getAllUsers();
    	for (User user: users) {
    		List<Collection> collections = collectionDao.getAllCollectionsByUserId(user.getId());
    		for	(Collection collection: collections) {
    			List<ComicBook> comicsInCollection = comicBookDao.getAllComicsByCollectionId(collection.getCollectionId());
    			collection.setComicsInCollection(comicsInCollection);
    		}
    		user.setCollections(collections);
       	}
    	return users;
    }
    
    @GetMapping("/users/{username}")
    public User getUserByName(@PathVariable String username) throws UnauthorizedException {
    	User user = userDao.getUserByUsername(username);
    	List<Collection> collections = collectionDao.getAllCollectionsByUserId(user.getId());
    	user.setCollections(collections);
    	for (Collection collection: collections) {
    		List<ComicBook> comics = comicBookDao.getAllComicsByCollectionId(collection.getCollectionId());
    		collection.setComicsInCollection(comics);
    	}
       	return user;
    } 
    
    @GetMapping("/comic/{comicId}")
    public ComicBook getComicById(@PathVariable int comicId) {
    	ComicBook comic = comicBookDao.getComicById(comicId);
    	return comic;
    }
    
    @PutMapping("/comic/{comicId}")
    public void updateComic(@RequestBody ComicBook comic) {
    	comicBookDao.updateComic(comic);
    }
    
    @DeleteMapping("/comic/{comicId}")
    public void DeleteComic(@PathVariable int comicId) {
    	comicBookDao.deleteComic(comicId);
    }
    
    @PutMapping("/collection/{collectionId}")
    public void updateCollection(@RequestBody Collection collection) {
    	collectionDao.updateCollection(collection);
    }
    
    @DeleteMapping("/collection/{collectionId}")
    public void deleteCollection(@PathVariable int collectionId) {
    	System.out.println("I hath made it here: " + collectionId);
    	collectionDao.deleteCollection(collectionId);
    }
    
    @PostMapping("/users/{username}/collection")
    public void addCollection(@RequestBody Collection collection) {
    	collectionDao.saveCollection(collection);
    }
    
    @GetMapping("/collection/{collectionId}")
    public List<ComicBook> getAllComicsByCollection(@PathVariable int collectionId) {
    	List<ComicBook> collection = comicBookDao.getAllComicsByCollectionId(collectionId);
		return collection;
    }
    
    @GetMapping("/collection/{collectionId}/single")
    public Collection getCollectionByCollectionId(@PathVariable int collectionId) {
    	Collection collection = collectionDao.getCollectionByCollectionId(collectionId);
    	List<ComicBook> comics = comicBookDao.getAllComicsByCollectionId(collectionId);
    	collection.setComicsInCollection(comics);
    	return collection;
    }
    
    @GetMapping("/comic/new")
    public ComicBook getLatestComic() {
    	return comicBookDao.getLatestComic();
    }
    
    @PostMapping("/comic/save")
    public void addComic(@RequestBody ComicBook comic) {
    	comicBookDao.saveComic(comic);
    }
    
    @PostMapping("/users/{username}/collection/{collectionId}/{comicId}/save")
    public void addComicToCollection(@PathVariable int comicId, 
    		@PathVariable String username, @PathVariable int collectionId) {
    	User user = userDao.getUserByUsername(username);
    	collectionDao.addComicToCollection(comicId, user, collectionId);
    }
    
    @GetMapping("/logoff")
    public void logoff() {
    	authProvider.logOff();
    }
    
    @GetMapping("/comic/count")
    public int comicCount() {
    	return comicBookDao.comicCount();
    }
    
    @GetMapping("/collection/topUsers")
    public List<User> getTopUsersByCollectionCount() {
    	return collectionDao.getTopUsersByCollectionCount();
    }
    
    @GetMapping("/comic/topUsers")
    public List<User> getTopUsersByComicCount() {
    	return comicBookDao.getTopUsersByComicCount();
    }
}