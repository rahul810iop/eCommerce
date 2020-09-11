package com.bookstall.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bookstoredb.entity2.Customer;

public class CustomerDAOTest {

	private static CustomerDAO customerDAO;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		customerDAO = new CustomerDAO();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		customerDAO.close();
	}

	@Test
	public void testCreateCustomer () {
		Customer customer = new Customer();
		customer.setEmail("Saroj1234@gmail.com");
		customer.setFullname("Saroj Kumar");
		customer.setCity("Darbhanga");
		customer.setCountry("India");
		customer.setAddress("Moh - Gharaul, Chakka");
		customer.setZipcode("846214");
		customer.setPassword("saroj1234");
		customer.setPhone("9876543210");
		
		Customer savedCustomer = customerDAO.create(customer);
		
		assertTrue(savedCustomer.getFullname() != null);
	}

	@Test
	public void testGet() {
		Integer customerId = 11;
		Customer customer = customerDAO.get(customerId);
		
		System.out.println("CustomerName : " + customer.getFullname());
		
		assertNotNull(customer);
	}
	
	@Test
	public void testUpdate() {
		Customer customer = customerDAO.get(11);
		customer.setZipcode("846231");
		
		Customer updatedCustomer = customerDAO.update(customer);
		
		assertTrue(updatedCustomer.getZipcode().equals("846231"));
	}
	
	@Test
	public void testDeleteCustomer() {
		Integer customerId = 11;
		customerDAO.delete(customerId);
		Customer customer = customerDAO.get(11);
		
		assertNull(customer);
	}
	
	@Test
	public void testListAll() {
		List<Customer> listCustomers = customerDAO.listAll();
	    for(Customer aCustomer : listCustomers) {
	    	System.out.println(aCustomer.getFullname());
	    }
	
	    assertTrue(listCustomers.size() > 0);
	}
	
	@Test
	public void testCountAll() {
		long totalCustomers = customerDAO.count();
		
		assertEquals(2, totalCustomers);
	}
}
