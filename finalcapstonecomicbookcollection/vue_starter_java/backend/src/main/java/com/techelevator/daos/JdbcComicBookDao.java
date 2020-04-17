package com.techelevator.daos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import com.techelevator.model.ComicBook;
import com.techelevator.model.User;

@Component 
public class JdbcComicBookDao implements ComicBookDao {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JdbcComicBookDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		}; 
	
	@Override
	public void saveComic(ComicBook comic) {		
		String theDate = comic.getPublishDate();
		int year = Integer.parseInt(theDate.substring(0,4));
		int mon = Integer.parseInt(theDate.substring(5,7));
		int day = Integer.parseInt(theDate.substring(8));
		LocalDate publishDateDt = LocalDate.of(year, mon , day);
		String sqlSaveComic = "INSERT into comic (title, issue_title, issue_number, publisher, publish_date, " +
							"image_link, description, hero)  VALUES (?,?,?,?,?,?,?,?)";
		jdbcTemplate.update(sqlSaveComic,
				comic.getTitle(),
				comic.getIssueTitle(),
				comic.getIssueNumber(),
				comic.getPublisher(),
				publishDateDt,
				comic.getImage(),
				comic.getDescription(),
				comic.getHero());
	}
	
	@Override
	public List<ComicBook> getAllComicsByUserId(int userId) {
		List <ComicBook> comicBooks = new ArrayList <> ();
		String sqlFindComicBookByUserId = "SELECT c.comic_id,c.title, c.issue_title, c.issue_number, " +
				"c.publisher, c.publish_date, c.hero, c.description,  c.image_link " +
				"FROM comic c JOIN user_comic ON c.comic_id = user_comic.comic_id " +
				"JOIN users ON user_comic.user_id = users.id WHERE users.id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlFindComicBookByUserId, userId);
			while(results.next()) {
				comicBooks.add(mapResultToComicBook(results));
			}
			return comicBooks;	
	} 
	
	@Override
	public List<ComicBook> getAllComicsByCollectionId(int collectionId) {
		List <ComicBook> comicBooks = new ArrayList <> ();
		String sqlFindComicBookByUserId = "SELECT c.comic_id, c.title, c.issue_title, c.issue_number, " +
				"c.publisher, c.publish_date, c.image_link, c.description, c.hero " +
				"FROM comic c JOIN user_comic ON c.comic_id = user_comic.comic_id " +
				"JOIN collection ON user_comic.collection_id = collection.collection_id " +
				"WHERE collection.collection_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlFindComicBookByUserId, collectionId);
			while(results.next()) {
				comicBooks.add(mapResultToComicBook(results));
			}
			return comicBooks;	
	}  

	
	  private ComicBook mapResultToComicBook(SqlRowSet results) {
		  ComicBook comicBook = new ComicBook();
		  comicBook.setComicId(results.getInt("comic_id"));
		  comicBook.setTitle(results.getString("title"));
		  comicBook.setIssueTitle(results.getString("issue_title"));
		  comicBook.setIssueNumber(results.getInt("issue_number"));
		  comicBook.setPublisher(results.getString("publisher"));
		  comicBook.setPublishDate(results.getDate("publish_date").toLocalDate());
		  comicBook.setImage(results.getString("image_link"));
		  comicBook.setDescription(results.getString("description"));
		  comicBook.setHero(results.getString("hero"));
	        return comicBook;
	    }

	@Override
	public ComicBook getLatestComic() {
		ComicBook comic = new ComicBook();
		String sql = "SELECT * FROM comic ORDER BY comic_id DESC LIMIT 1";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		if(result.next()) {
			comic = mapResultToComicBook(result);
		} else {
			System.out.println("Comic not found");
		}
		return comic; 
	}  
	
	@Override
	public void deleteComic(int comicId) {
		
		String sqlDeleteComic = "DELETE FROM user_comic WHERE comic_id = ? ";
		jdbcTemplate.update(sqlDeleteComic, comicId);
		
		String sqlDeleteComic1 = "DELETE FROM comic WHERE comic_id = ? ";
		jdbcTemplate.update(sqlDeleteComic1, comicId);
		
	}
	
	@Override
	public ComicBook getComicById(int comicId) {
		ComicBook comic = new ComicBook();
		String sql = "SELECT * FROM comic WHERE comic_id = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, comicId);
		if(result.next()) {
			comic = mapResultToComicBook(result);
		} else {
			System.out.println("Comic not found");
		}
		return comic; 
	}  
	
	@Override
	public void updateComic (ComicBook comic) {
		System.out.println(comic.getPublishDate());
		String theDate = comic.getPublishDate();
		int year = Integer.parseInt(theDate.substring(0,4));
		int mon = Integer.parseInt(theDate.substring(5,7));
		int day = Integer.parseInt(theDate.substring(8));
		LocalDate publishDateDt = LocalDate.of(year, mon , day);
		String sqlUpdateComic = "UPDATE comic SET title = ?, "
				+ "issue_title = ?, issue_number = ?, publisher = ?, publish_date = ?,"
				+ "hero = ?, description = ?, image_link = ? "
				+ "WHERE comic_id = ? ";
		
		jdbcTemplate.update(sqlUpdateComic, comic.getTitle(), comic.getIssueTitle(), comic.getIssueNumber(),
					  comic.getPublisher(), publishDateDt, comic.getHero(), comic.getDescription(),
					  comic.getImage(), comic.getComicId());
		
	}
	
	@Override
	public int comicCount() {
		SqlRowSet count = jdbcTemplate.queryForRowSet("SELECT COUNT(*) FROM comic");
		if (count.next()) {  // only expecting one value back, so this is an IF statement
			return count.getInt(1); // get what comes back in the first position and turn it into a integer
		} else {
			throw new RuntimeException("Uhoh!  Something went wrong getting the comic count!");
		}
	}
	
	@Override
	public List<User> getTopUsersByComicCount() {
		List <User> users = new ArrayList <> ();
		String sql = "select users.username, count(user_comic.user_id), rank() OVER (Order by count (user_comic.user_id) desc) FROM user_comic " + 
				"JOIN users on users.id=user_comic.user_id GROUP BY users.username " + 
				"Order by count desc LIMIT 5";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		while(result.next()) {
			User user = new User();
			user.setUsername(result.getString("username"));
			user.setComicCount(result.getInt("count"));
			user.setRank(result.getInt("rank"));
			users.add(user);
		} 
		return users;	
	} 
	
	@Override
	public List<ComicBook> getAllComicBooks() {
		
		List<ComicBook> allComicBooks = new ArrayList<>();
		String sqlGetAllComicBooks = "SELECT * FROM comic";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllComicBooks);
			while(results.next()) {
				allComicBooks.add(mapResultToComicBook(results));
			}
			return allComicBooks;			
	}
		
} 
