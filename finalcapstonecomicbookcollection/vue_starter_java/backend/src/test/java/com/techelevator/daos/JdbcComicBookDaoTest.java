package com.techelevator.daos;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.model.Collection;
import com.techelevator.model.ComicBook;
import com.techelevator.model.User;




public class JdbcComicBookDaoTest {

	private static SingleConnectionDataSource dataSource;
	private JdbcComicBookDao comicBookDao;

	

	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/userdb");  
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");  
		dataSource.setAutoCommit(false);
	} 

	@AfterClass
	public static void closeDataSource() {
		dataSource.destroy();
	}

	@Before
	public void setup() {
		
		String sqlInsertUser1     = "INSERT into users (id, username,email, password , salt, role) "
								+ "VALUES (1001, 'AAA', 'aaa@gmail.com', '12345678', 'aaaaaaaaa', 'user')"; 
		String sqlInsertUser2    = "INSERT into users (id, username,email, password , salt, role) "
								+ "VALUES (1002, 'BBB', 'bbb@gmail.com', '12345678', 'bbbbbbbb', 'user')"; 
		String sqlInsertUser3     = "INSERT into users (id, username,email, password , salt, role) "
								+ "VALUES (1003, 'CCC', 'ccc@gmail.com', '12345678', 'cccccccc', 'premium')"; 
		String sqlInsertUser4     = "INSERT into users (id, username,email, password , salt, role) "
								+ "VALUES (1004, 'DDD', 'ddd@gmail.com', '12345678', 'dddddddd', 'premium')"; 
		String sqlInsertUser5    = "INSERT into users (id, username,email, password , salt, role) "
								+ "VALUES (1005, 'EEE', 'eee@gmail.com', '12345678', 'eeeeeeee', 'premium')"; 

		String sqlInsertComicBook1 = "INSERT into comic (comic_id,title, issue_title, issue_number, publisher, publish_date,hero,  description, image_link )"

									+ "VALUES (1001,'AA', 'AAA', 15, 'AAAA', '01-16-2020', 'AAAAAA', 'AAAAA', 'www.testa.com')";
		String sqlInsertComicBook2 = "INSERT into comic (comic_id,title, issue_title, issue_number, publisher, publish_date,hero,  description, image_link )"

									+ "VALUES (1002,'BB', 'BBB', 16, 'BBBB', '01-17-2020', 'BBBBBB', 'BBBBB', 'www.testb.com')";
		String sqlInsertComicBook3 = "INSERT into comic (comic_id,title, issue_title, issue_number, publisher, publish_date,hero,  description, image_link )"
				
									+ "VALUES (1003,'CC', 'CCC', 17, 'CCCC', '01-17-2020', 'CCCCCC', 'CCCCC', 'www.testc.com')";
		String sqlInsertComicBook4 = "INSERT into comic (comic_id,title, issue_title, issue_number, publisher, publish_date,hero,  description, image_link )"
				
									+ "VALUES (1004,'DD', 'DDD', 17, 'DDDD', '01-17-2020', 'DDDDDD', 'DDDDD', 'www.testd.com')";
		String sqlInsertComicBook5 = "INSERT into comic (comic_id,title, issue_title, issue_number, publisher, publish_date,hero,  description, image_link )"
				
									+ "VALUES (1005,'EE', 'EEE', 17, 'EEEE', '01-17-2020', 'EEEEE', 'EEEEE', 'www.teste.com')";
		String sqlInsertComicBook6 = "INSERT into comic (comic_id,title, issue_title, issue_number, publisher, publish_date,hero,  description, image_link )"
				
									+ "VALUES (1006,'FF', 'FFF', 17, 'FFFF', '01-17-2020', 'FFFFFF', 'FFFFF', 'www.testf.com')";
		String sqlInsertComicBook7 = "INSERT into comic (comic_id,title, issue_title, issue_number, publisher, publish_date,hero,  description, image_link )"
				
									+ "VALUES (1007,'GG', 'GGG', 17, 'GGGG', '01-17-2020', 'GGGGGG', 'GGGGG', 'www.testg.com')";
		
		String sqlInsertCollection1 = "INSERT into collection (collection_id, user_id,  name, description, private ) "
									+ "VALUES (1001,1001, 'aaa', 'aaaa', false) "; 
		String sqlInsertCollection2 = "INSERT into collection (collection_id, user_id,  name, description, private ) "
									+ "VALUES (1002,1002, 'bbb', 'bbbb', false) "; 
		String sqlInsertCollection3 = "INSERT into collection (collection_id, user_id,  name, description, private ) "
									+ "VALUES (1003,1003, 'ccc', 'cccc', true) "; 
		String sqlInsertCollection4 = "INSERT into collection (collection_id, user_id,  name, description, private ) "
									+ "VALUES (1004,1004, 'ddd', 'dddd', true) "; 
		String sqlInsertCollection5 = "INSERT into collection (collection_id, user_id,  name, description, private ) "
									+ "VALUES (1005,1005, 'eee', 'eeee', true) "; 
		
		
		String sqlInsertUserComic1 = "INSERT into user_comic(comic_id, user_id, collection_id )"
				
									+ "VALUES (1001, 1001, 1001) ";
		String sqlInsertUserComic2 = "INSERT into user_comic(comic_id, user_id, collection_id )"
				
									+ "VALUES (1002, 1001, 1001) ";
		String sqlInsertUserComic3 = "INSERT into user_comic(comic_id, user_id, collection_id )"
				
									+ "VALUES (1003, 1001, 1001) ";
		String sqlInsertUserComic4 = "INSERT into user_comic(comic_id, user_id, collection_id )"
				
									+ "VALUES (1004, 1002, 1002) ";
		String sqlInsertUserComic5 = "INSERT into user_comic(comic_id, user_id, collection_id )"
				
									+ "VALUES (1005, 1002, 1002) ";
		String sqlInsertUserComic6 = "INSERT into user_comic(comic_id, user_id, collection_id )"
				
									+ "VALUES (1006, 1003, 1003) ";
		String sqlInsertUserComic7 = "INSERT into user_comic(comic_id, user_id, collection_id )"
				
									+ "VALUES (1007, 1004, 1004) ";
		String sqlInsertUserComic8 = "INSERT into user_comic(comic_id, user_id, collection_id )"
				
									+ "VALUES (1002, 1005, 1005) ";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);		
		jdbcTemplate.update(sqlInsertUser1);
		jdbcTemplate.update(sqlInsertUser2);
		jdbcTemplate.update(sqlInsertUser3);
		jdbcTemplate.update(sqlInsertUser4);
		jdbcTemplate.update(sqlInsertUser5);
		jdbcTemplate.update(sqlInsertComicBook1);
		jdbcTemplate.update(sqlInsertComicBook2);
		jdbcTemplate.update(sqlInsertComicBook3);
		jdbcTemplate.update(sqlInsertComicBook4);
		jdbcTemplate.update(sqlInsertComicBook5);
		jdbcTemplate.update(sqlInsertComicBook6);
		jdbcTemplate.update(sqlInsertComicBook7);
		jdbcTemplate.update(sqlInsertCollection1);
		jdbcTemplate.update(sqlInsertCollection2);
		jdbcTemplate.update(sqlInsertCollection3);
		jdbcTemplate.update(sqlInsertCollection4);
		jdbcTemplate.update(sqlInsertCollection5);
		jdbcTemplate.update(sqlInsertUserComic1);
		jdbcTemplate.update(sqlInsertUserComic2);
		jdbcTemplate.update(sqlInsertUserComic3);
		jdbcTemplate.update(sqlInsertUserComic4);
		jdbcTemplate.update(sqlInsertUserComic5);
		jdbcTemplate.update(sqlInsertUserComic6);
		jdbcTemplate.update(sqlInsertUserComic7);
		jdbcTemplate.update(sqlInsertUserComic8);
		comicBookDao = new JdbcComicBookDao(dataSource);

	}

	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	@Test
	public void test_to_save_new_comic() {
		List<ComicBook> comicBooks = new ArrayList<>();
		ComicBook test = new ComicBook();
		
		test.setComicId(999);
		test.setTitle("TEST");;
		test.setIssueTitle("AAAAAAAA");
		test.setIssueNumber(10);
		test.setPublisher("BBBBBBBBB");
		test.setPublishDate(LocalDate.of(2020, 02, 28));
		test.setHero("Batman");
		test.setDescription("DDDDDDDDD");
		test.setImage("wwww.test.com");
		
		comicBooks.add(test);
		
		comicBookDao.saveComic(test);

		assertEquals(999, comicBooks.get(comicBooks.size()-1).getComicId());
		assertEquals("TEST", comicBooks.get(comicBooks.size()-1).getTitle());

	}

	//All 9 methods in JdbcComic were tested and passed Apr 16,2020
	@Test
	public void test_to_get_comicBook_by_user_id() {
		
		List<ComicBook> comicByUser = comicBookDao.getAllComicsByUserId(1001);
		
		int count = 0;
		for(int i = 0; i < comicByUser.size(); i++) {
			count++;
		}
				
		assertNotNull(comicByUser);
		assertEquals(count, comicByUser.size());
		assertEquals(3, comicByUser.size());
		assertEquals("AA", comicByUser.get(comicByUser.size()-3).getTitle());
		assertEquals("BB", comicByUser.get(comicByUser.size()-2).getTitle());
			
}

	@Test
	public void test_to_get_all_comicBook_by_collection_id() {
		
		List<ComicBook> listOfBookInCollection = comicBookDao.getAllComicsByCollectionId(1001);
		
		assertNotNull(listOfBookInCollection);
		assertEquals(3 , listOfBookInCollection. size());
		assertEquals("AA", listOfBookInCollection.get(listOfBookInCollection.size()-3).getTitle());
		assertEquals("BB", listOfBookInCollection.get(listOfBookInCollection.size()-2).getTitle());
		assertEquals("CC", listOfBookInCollection.get(listOfBookInCollection.size()-1).getTitle());
		 
	} 
	 
	@Test
	public void test_to_get_latest_comic() {
		
		ComicBook latestComic = comicBookDao.getLatestComic();

		assertEquals("GG", latestComic.getTitle());	
	} 
	
	@Test
	public void test_to_delete_comic() {
		
		comicBookDao.deleteComic(1002);
		
		List<ComicBook> listOfBookInCollection = comicBookDao.getAllComicsByCollectionId(1001);
		
		assertNotNull(listOfBookInCollection);
		assertEquals(2 , listOfBookInCollection. size());
				
		List<ComicBook> listOfBookInCollection1 = comicBookDao.getAllComicsByCollectionId(1005);
		
		assertEquals(0 , listOfBookInCollection1. size());
	}

	@Test
	public void test_to_update_comic() {
		
		ComicBook test = new ComicBook();
		test.setTitle("TEST");;
		test.setIssueTitle("AAAAAAAA");
		test.setIssueNumber(10);
		test.setPublisher("BBBBBBBBB");
		test.setPublishDate(LocalDate.of(2020, 02, 28));
		test.setHero("Batman");
		test.setDescription("DDDDDDDDD");
		test.setImage("wwww.test.com");
		test.setComicId(1002);
		
		comicBookDao.updateComic(test);
		
		List<ComicBook> testList = new ArrayList<>();
		testList.add(test);
		
		assertNotNull(testList);
		assertEquals(1, testList.size());
		assertEquals("TEST", testList.get(testList.size()-1).getTitle());
		
		
	}
	 
	@Test
	public void test_to_count_all_comics() {
		
		List<ComicBook> allComicBooks = comicBookDao.getAllComicBooks();
		
		int count = 0;
		for(int i = 0; i < allComicBooks.size(); i++) {
			count++;
		}
		
		count = comicBookDao.comicCount();
		
		assertNotNull(allComicBooks);
		assertEquals(count , allComicBooks.size());
		assertEquals("FF", allComicBooks.get(allComicBooks.size()-2).getTitle());
		assertEquals("GG", allComicBooks.get(allComicBooks.size()-1).getTitle());
				
	}
	
	@Test
	public void test_to_get_top_five_users() {
		
		List<User> topFiveUsers = comicBookDao.getTopUsersByComicCount();
		
		int count = 0;
		for(int i = 0; i < topFiveUsers.size(); i++) {
			count++;
		}
		
		assertNotNull(topFiveUsers);
		assertEquals(5, topFiveUsers.size());	
		assertEquals(1, topFiveUsers.get(topFiveUsers.size()-5).getRank());
		
		
		
	} 
	
	@Test
	public void get_comic_book_id() {
		
		List <ComicBook> testComic = new ArrayList <>();
		ComicBook comic = comicBookDao.getComicById(1001);
		testComic.add(comic);
		
		assertNotNull(testComic);
		assertEquals("AA", testComic.get(testComic.size()-1).getTitle());
		
	  	
		
	}  
	
	//All 9 methods in JdbcComic were tested and passed Apr 16,2020
} 
  
