package com.modelAndcontroller;

import java.math.BigInteger;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JDBCRegisterDao implements RegisterDao {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JDBCRegisterDao(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
 
	private long getNextRegisterId() {
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet(" SELECT nextval('registration_id_seq')");
		if (nextIdResult.next()) {
			return nextIdResult.getInt(1);
		} else {
			throw new RuntimeException("Uhoh!  Something went wrong while getting the next id!");
		}

	}
	
	public String toHex(String email) {
	    return String.format("%040x", new BigInteger(1, email.getBytes()));
	}

	@Override
	public void save(Register newRegister) {
		// TODO Auto-generated method stub
		newRegister.setId(getNextRegisterId());
		
		newRegister.setHexaId(toHex(newRegister.getEmail()));
		
		
		
		String sqlCreateSignUpResult = "INSERT INTO registration (id, email, hexa_id, firstname, lastname, phonenumber)"
				+ "VALUES (?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(sqlCreateSignUpResult, newRegister.getId(), newRegister.getEmail(), newRegister.getHexaId(),
				newRegister.getFirstName(), newRegister.getLastName(), newRegister.getPhoneNumber());
		
	}

}


