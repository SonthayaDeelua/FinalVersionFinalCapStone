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

public class JdbcCollectionDaoTest {

	private static SingleConnectionDataSource dataSource;
	private JdbcCollectionDao collectionDao;
	private JdbcComicBookDao comicBookDao;
	private JdbcUserDao userDao;

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

		String sqlInsertUser1 = "INSERT into users (id, username,email, password , salt, role) "
				+ "VALUES (1001, 'AAA', 'aaa@gmail.com', '12345678', 'aaaaaaaaa', 'user')";
		String sqlInsertUser2 = "INSERT into users (id, username,email, password , salt, role) "
				+ "VALUES (1002, 'BBB', 'bbb@gmail.com', '12345678', 'bbbbbbbb', 'user')";
		String sqlInsertUser3 = "INSERT into users (id, username,email, password , salt, role) "
				+ "VALUES (1003, 'CCC', 'ccc@gmail.com', '12345678', 'cccccccc', 'premium')";
		String sqlInsertUser4 = "INSERT into users (id, username,email, password , salt, role) "
				+ "VALUES (1004, 'DDD', 'ddd@gmail.com', '12345678', 'dddddddd', 'premium')";
		String sqlInsertUser5 = "INSERT into users (id, username,email, password , salt, role) "
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
		String sqlInsertCollection6 = "INSERT into collection (collection_id, user_id,  name, description, private ) "
				+ "VALUES (1006,1001, 'fff', 'ffff', true) ";

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
		jdbcTemplate.update(sqlInsertCollection6);
		jdbcTemplate.update(sqlInsertUserComic1);
		jdbcTemplate.update(sqlInsertUserComic2);
		jdbcTemplate.update(sqlInsertUserComic3);
		jdbcTemplate.update(sqlInsertUserComic4);
		jdbcTemplate.update(sqlInsertUserComic5);
		jdbcTemplate.update(sqlInsertUserComic6);
		jdbcTemplate.update(sqlInsertUserComic7);
		jdbcTemplate.update(sqlInsertUserComic8);
		collectionDao = new JdbcCollectionDao(dataSource);

	}

	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	//1th test
	@Test
	public void test_to_save_new_collection() {

		Collection test = new Collection();

		test.setUserId(1001);
		test.setName("TEST");
		test.setDescription("TESTTEST");
		test.setPrivate(false);

		collectionDao.saveCollection(test);

		assertEquals("TEST", test.getName());
		assertEquals("TESTTEST", test.getDescription());
		assertEquals(false, test.isPrivate());

	}
	//2nd test
	@Test
	public void test_to_get_all_collection_by_user_id() {

		List<Collection> collectionOf1001 = collectionDao.getAllCollectionsByUserId(1001l);

		int count = 0;
		for (int i = 0; i < collectionOf1001.size(); i++) {
			count++;
		}

		assertNotNull(collectionOf1001);
		assertEquals(count, collectionOf1001.size());
		assertEquals("fff", collectionOf1001.get(collectionOf1001.size() - 1).getName());
		assertEquals("aaa", collectionOf1001.get(collectionOf1001.size() - 2).getName());

	}
	//3rd test
	@Test
	public void test_to_add_comic_into_collection() {

		ComicBook test = new ComicBook();

		test.setComicId(1003);

		User user = new User();
		user.setId(1001);

		Collection collection = new Collection();
		collection.setCollectionId(1001);

		collectionDao.addComicToCollection(1003, user, 1001);
		

		assertEquals(1001, collection.getCollectionId());
		assertEquals(1003, test.getComicId());
		assertEquals(1001, user.getId());

	}
	//4th method test
	@Test
	public void test_to_get_collection_by_collection_id() {

		Collection testCollection = collectionDao.getCollectionByCollectionId(1001);
		assertEquals("aaa", testCollection.getName());

		Collection testCollection2 = collectionDao.getCollectionByCollectionId(1002);
		assertEquals("bbb", testCollection2.getName());
	}
	//5th method test
	@Test
	public void test_to_delete_collection() {

		collectionDao.deleteCollection(1001);

		List<Collection> collectionOf1001 = collectionDao.getAllCollectionsByUserId(1001l);

		assertNotNull(collectionOf1001);

		assertEquals(1, collectionOf1001.size());

	}
	//6th method test
	@Test
	public void test_to_update_collection() {

		Collection test = new Collection();
		test.setCollectionId(1001);
		test.setUserId(1001);
		test.setName("TEST");
		test.setDescription("TEST");
		test.setPrivate(true);

		collectionDao.updateCollection(test);

		assertEquals(1001, test.getCollectionId());
		assertEquals("TEST", test.getName());
	}
	//7th method test
	@Test
	public void test_to_get_public_collection() {

		List<Collection> publicCollection = collectionDao.getPublicCollection();

		int count = 0;
		for (int i = 0; i < publicCollection.size(); i++) {
			count++;
		}

		assertNotNull(publicCollection);
		assertEquals(count, publicCollection.size());
		assertEquals(1002, publicCollection.get(publicCollection.size() - 1).getCollectionId());
		assertEquals(1001, publicCollection.get(publicCollection.size() - 2).getCollectionId());

	}
	//8th method test
	@Test
	public void test_to_get_all_public_collection_by_user_name() {

		List<Collection> publicCollectionOfEachUser = collectionDao.getPublicCollectionsByUsername("AAA");
		int count = 0;
		for (int i = 0; i < publicCollectionOfEachUser.size(); i++) {
			count++;
		}

		assertNotNull(publicCollectionOfEachUser);
		assertEquals(count, publicCollectionOfEachUser.size());
		assertEquals(1001, publicCollectionOfEachUser.get(publicCollectionOfEachUser.size() - 1).getCollectionId());

	}
	
	//9th method test
		@Test
		public void test_to_get_top5_users_by_collection_count() {
			
		List<User> topFiveUsers = collectionDao.getTopUsersByCollectionCount();
		
		int count = 0;
		for(int i = 0; i < topFiveUsers.size(); i++) {
			count++;
		}
		
		assertNotNull(topFiveUsers);
		assertEquals(5, topFiveUsers.size());
		assertEquals(1, topFiveUsers.get(topFiveUsers.size()-5).getRank());
			
			
		}
   
}
