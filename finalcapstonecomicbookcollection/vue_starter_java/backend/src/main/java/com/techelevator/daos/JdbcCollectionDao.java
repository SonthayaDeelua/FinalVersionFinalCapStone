package com.techelevator.daos;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.model.Collection;
import com.techelevator.model.ComicBook;
import com.techelevator.model.User;

@Component
public class JdbcCollectionDao implements CollectionDao {
	
	private JdbcTemplate jdbcTemplate;
	@Autowired
	public JdbcCollectionDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		}; 
	//1st method
	@Override
	public void saveCollection(Collection collection) {
		String sqlSaveCollection = "INSERT INTO collection (user_id, name, description, private) " +
				"VALUES (?,?,?,?)";
		
		jdbcTemplate.update(sqlSaveCollection,

				collection.getUserId(),
				collection.getName(),
				collection.getDescription(), 
				collection.isPrivate()); 
	}
	
		
	//2nd method
	@Override
	public List<Collection> getAllCollectionsByUserId(Long userId) {
		List <Collection> collections = new ArrayList <> ();
		String sqlFindCollectionsByUserId = "SELECT * FROM collection WHERE user_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlFindCollectionsByUserId, userId);
			while(results.next()) {
				collections.add(mapResultToCollection(results));
			}
			return collections;	
	} 
	
	private Collection mapResultToCollection(SqlRowSet results) {
		Collection collection = new Collection();
			collection.setCollectionId(results.getInt("collection_id"));
			collection.setUserId(results.getInt("user_id"));
			collection.setName(results.getString("name"));
			collection.setDescription(results.getString("description"));
			collection.setPrivate(results.getBoolean("private"));
	        return collection;
	    }
	//3rd method
	@Override
	public void addComicToCollection(int comicId, User user, int collectionId) {
		System.out.println("in dao: " + comicId + " " + user.getId() + " " + collectionId);
		String sqlAddComicToCollection = "INSERT INTO user_comic (collection_id, user_id, comic_id) " +
				"VALUES (?,?,?)";
		jdbcTemplate.update(sqlAddComicToCollection,
							collectionId,
							user.getId(),
							comicId);
	}
	//4th method
	@Override
	public Collection getCollectionByCollectionId(int collectionId) {
		Collection collection = new Collection();
		String sql = "SELECT * FROM collection WHERE collection_id = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, collectionId);
		if(result.next()) {
			collection = mapResultToCollection(result);
		} else {
			System.out.println("Collection not found");
		}
		return collection; 
	} 
	//5th method
	@Override
	public void deleteCollection(int collectionId) {
		
		String sqlDeleteFromUserComic = "DELETE FROM user_comic WHERE collection_id = ? ";
		jdbcTemplate.update(sqlDeleteFromUserComic, collectionId);
		
		String sqlDeleteCollection = "DELETE FROM collection WHERE collection_id = ? ";
		jdbcTemplate.update(sqlDeleteCollection, collectionId);
		
	}
	//6th method
	@Override
	public void updateCollection(Collection collection) {
		
		String sqlUpdateCollection  = "UPDATE collection "
									+ "SET user_id = ?, name = ?, description = ?, private = ? "
									+ "WHERE collection_id = ? ";
		
		jdbcTemplate.update(sqlUpdateCollection, collection.getUserId(), collection.getName(), collection.getDescription(),
							collection.isPrivate(), collection.getCollectionId()); 
							
	}
	//7th method
	@Override
	public List<Collection> getPublicCollection() {
		
		List <Collection> collections = new ArrayList <> ();
		String sqlPublicCollection = "SELECT * "
				+ "FROM users JOIN collection ON collection.user_id = users.id "
				+ "WHERE collection.private = false";
		
		SqlRowSet result = jdbcTemplate.queryForRowSet(sqlPublicCollection);
		while(result.next()) {
			Collection collection = new Collection();
			collection.setCollectionId(result.getInt("collection_id"));
			collection.setUserId(result.getInt("user_id"));
			collection.setName(result.getString("name"));
			collection.setDescription(result.getString("description"));
			collection.setPrivate(result.getBoolean("private"));
			collection.setUsername(result.getString("username"));
			collections.add(collection);
		} 
		return collections;		
	} 
	//8th method
	@Override
	public List<Collection> getPublicCollectionsByUsername(String username) {
		
		List <Collection> collections = new ArrayList <> ();
		String sqlPublicCollection = "SELECT * "
				+ "FROM users JOIN collection ON collection.user_id = users.id "
				+ "WHERE collection.private = false AND users.username = ?";
		
		SqlRowSet result = jdbcTemplate.queryForRowSet(sqlPublicCollection, username);
		while(result.next()) {
			Collection collection = new Collection();
			collection.setCollectionId(result.getInt("collection_id"));
			collection.setUserId(result.getInt("user_id"));
			collection.setName(result.getString("name"));
			collection.setDescription(result.getString("description"));
			collection.setPrivate(result.getBoolean("private"));
			collection.setUsername(result.getString("username"));
			collections.add(collection);
		} 
		return collections;		
	}  
	//9th method
	@Override
	public List<User> getTopUsersByCollectionCount() {
		List <User> users = new ArrayList <> ();
		String sql = "select users.username, count(collection.user_id), rank() OVER (Order by count (collection.user_id) desc) FROM collection " + 
				"JOIN users on users.id=collection.user_id GROUP BY users.username " + 
				"Order by count desc LIMIT 5";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		while(result.next()) {
			User user = new User();
			user.setUsername(result.getString("username"));
			user.setCollectionCount(result.getInt("count"));
			user.setRank(result.getInt("rank"));
			users.add(user);
		} 
		return users;	
	} 
} 
 