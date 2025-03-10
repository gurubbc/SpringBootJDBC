package com.opentext.model;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
// JdbcDaoSupport is a class that provides helper methods to access the database
// Convenient super class for JDBC-BASED data access objects
@Repository
public class CustomerDAO extends JdbcDaoSupport{
	
	@Autowired
	DataSource dataSource;
	
	// call back method, this method will be called after the bean
	// has been initialized, to perform any initialization
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
    }
	

	public ResponseEntity<Object> insertCustomer(Customer customer) {
		String sql="INSERT INTO customer " + "(FIRST_NAME, LAST_NAME, PHONE_NUMBER, EMAIL_ID) VALUES (?, ?, ?, ?)";
		JdbcTemplate jdbcTemplate=getJdbcTemplate(); // returns an object of JdbcTemplate class
		
		KeyHolder keyHolder=new GeneratedKeyHolder(); // used to capture the generated primary key value
		// call the update method for insert, delete and update operations
		int rowsUpdated=jdbcTemplate.update(
				connection -> {
					var ps=connection.prepareStatement(sql, new String[] {"CUSTOMER_ID"});
					ps.setString(1, customer.getFirstName());
					ps.setString(2, customer.getLastName());
					ps.setLong(3, customer.getPhoneNumber());
					ps.setString(4, customer.getEmailId());
					return ps;
					
				}, keyHolder);
		if (rowsUpdated > 0) {
			long generatedCustomerId=keyHolder.getKey().longValue();
			return ResponseEntity.status(201).body("Customer object has been inserted successfully into customer table with customer id: " + generatedCustomerId);
			
		}
		else 
		{
			return ResponseEntity.status(500).body("Customer object has not been inserted into customer table");
		}
	}
	// 2nd method
public ArrayList<Customer> getAllCustomers() {
		String sql = "SELECT * FROM customer";
		JdbcTemplate jdbcTemplate=getJdbcTemplate(); // returns an object of JdbcTemplate class
		List <Map <String, Object>> rows=jdbcTemplate.queryForList(sql);
		// Create a list of Customer objects that to be returned from this method
		// Let's iterate this data structure and create a list of Customer objects
		ArrayList<Customer> allCustomers=new ArrayList();
		for (Map<String,Object> row: rows) {
			Customer cust=new Customer(); // For every row, create an Customer object
			cust.setCustomerId((Integer)row.get("CUSTOMER_ID"));
			cust.setFirstName((String)row.get("FIRST_NAME"));
			cust.setLastName((String)row.get("LAST_NAME"));
			cust.setPhoneNumber((Long)row.get("PHONE_NUMBER"));
			cust.setEmailId((String)row.get("EMAIL_ID"));
			allCustomers.add(cust);
			
		}
		// return the list of customer objects
		return allCustomers;
		
		
	}
	
	
	
	
}
