package com.opentext.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.opentext.model.Customer;
import com.opentext.model.CustomerDAO;

@Service
public class CustomerService {
	
	@Autowired
	CustomerDAO customerDAO;
	
	public ResponseEntity<Object> insertCustomer(Customer customer) {
		return customerDAO.insertCustomer(customer);
	}

}
