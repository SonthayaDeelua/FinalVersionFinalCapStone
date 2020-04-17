package com.techelevator.daos;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.authentication.PasswordHasher;
import com.techelevator.model.User;

public class JdbcUserDaoTest {
	
	private static SingleConnectionDataSource dataSource;
	
	private JdbcUserDao  userDao;
	private PasswordHasher passwordHasher;
	
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
		
		String sqlInsertUser1     = "INSERT into users (id, username, password , salt, role) "
								+ "VALUES (1001, 'AAA', '12345678', 'aaaaaaaaaa', 'user')"; 
		String sqlInsertUser2     = "INSERT into users (id, username, password , salt, role) "
								+ "VALUES (1002, 'BBB', '12345678', 'bbbbbbbb', 'premium')"; 

		
		

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);		
		jdbcTemplate.update(sqlInsertUser1);
		jdbcTemplate.update(sqlInsertUser2);	
		userDao = new JdbcUserDao(dataSource, passwordHasher);

	}

	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback(); 
	}
	
	
	

	@Test
	public void test_to_update_user() {
		User user = new User();
		user.setId(1001);
		user.setUsername("TEST");
		user.setEmail("");
		user.setPassword("12345");
		user.setRole("user");
		
		userDao.updateUser(user);
		
		assertEquals(1001, user.getId());
		assertEquals("TEST", user.getUsername());
	}
	
	@Test
	public void test_to_get_user_by_id() {
		
		User user = userDao.getUserById(1002);
		
		assertEquals("BBB", user.getUsername());
				
	}
	
	@Test
	public void test_to_get_user_by_username() {
		
		User user = userDao.getUserByUsername("AAA");
		
		assertEquals(1001, user.getId());		
		
	}
	
	@Test
	public void test_to_get_all_users() {
		List<User> allUsers = userDao.getAllUsers();
		
		int count = 0;
		for(int i = 0; i < allUsers.size(); i++) {
			count++;
		}
		
		assertNotNull(allUsers);
		assertEquals(count, allUsers.size());
		assertEquals("BBB", allUsers.get(allUsers.size()-1).getUsername());
		assertEquals("AAA", allUsers.get(allUsers.size()-2).getUsername());
	}
	 

} 
