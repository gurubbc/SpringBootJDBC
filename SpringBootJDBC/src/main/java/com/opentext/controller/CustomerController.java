package com.opentext.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.opentext.model.Customer;
import com.opentext.service.CustomerService;

@RestController
@RequestMapping("/api")
public class CustomerController {

	@Autowired
	CustomerService customerService;
	
	@RequestMapping(value = "/customers", method = RequestMethod.POST)
	public ResponseEntity<Object> addACustomer(@RequestBody Customer newCustomer) {
		System.out.println("CustomerController method is called...");
		return customerService.insertCustomer(newCustomer);
	}
	@RequestMapping(value = "/customers", method = RequestMethod.GET)
	public List<Customer> getAllCustomers() {
		return customerService.getAllCustomers();
	}
	
}
