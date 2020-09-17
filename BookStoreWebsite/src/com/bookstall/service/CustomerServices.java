package com.bookstall.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookstall.dao.CustomerDAO;
import com.bookstoredb.entity2.Customer;

public class CustomerServices {
	private CustomerDAO customerDAO;
	private HttpServletResponse response;
    private HttpServletRequest request;
	
    public CustomerServices(HttpServletRequest request, HttpServletResponse response) {
		super();
		this.response = response;
		this.request = request;
        this.customerDAO = new CustomerDAO();
	}
    
    public void listCustomers() throws ServletException, IOException {
    	listCustomers(null);	
    }

    public void listCustomers(String message) throws ServletException, IOException {
    	List<Customer> listCustomer = customerDAO.listAll();
    	
    	if(message != null) {
    		request.setAttribute("message", message);
    	}
    	
        request.setAttribute("listCustomer", listCustomer);
		
		String listPage = "customer_list.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listPage);
		requestDispatcher.forward(request, response);	
    }
    
	public void createCustomer() throws ServletException, IOException {
		String email = request.getParameter("email");
		Customer existCustomer = customerDAO.findByEmail(email);
		if(existCustomer != null) {
			String message = "Could not create customer: the email "
		       + email + "is already registered";
			listCustomers(message);
		}
		else {
			String fullName = request.getParameter("fullName");
			String password = request.getParameter("password");
			String phone = request.getParameter("phone");
			String city = request.getParameter("city");
	        String zipcode = request.getParameter("zipcode");
	        String country = request.getParameter("country");
	        String address = request.getParameter("address");
	        
	        Customer newCustomer = new Customer();
	        newCustomer.setEmail(email);
	        newCustomer.setFullname(fullName);
	        newCustomer.setPassword(password);
	        newCustomer.setPhone(phone);
	        newCustomer.setCity(city);
	        newCustomer.setZipcode(zipcode);
	        newCustomer.setCountry(country);
	        newCustomer.setAddress(address);
	        
			customerDAO.create(newCustomer);
			
			listCustomers("New customer has been created successfully");
			}
	}

	public void editCustomer() throws ServletException, IOException {
		Integer customerId = Integer.parseInt(request.getParameter("id"));
		Customer customer = customerDAO.get(customerId);
	     
		request.setAttribute("customer", customer);
		
		String editPage = "customer_form.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(editPage);
		requestDispatcher.forward(request, response);
	}
	
	public void updateCustomer() throws ServletException, IOException {
		Integer customerId = Integer.parseInt(request.getParameter("customerId"));
		String email = request.getParameter("email");
		
		Customer existCustomer = customerDAO.findByEmail(email);
		String message = null;
		if(existCustomer != null && existCustomer.getCustomerId() != customerId) {
			message = "Could not update the customer ID " + customerId 
					+ "because there's an existing customer having the same email";
		} else {
			String fullName = request.getParameter("fullName");
			String password = request.getParameter("password");
			String phone = request.getParameter("phone");
			String city = request.getParameter("city");
	        String zipcode = request.getParameter("zipcode");
	        String country = request.getParameter("country");
	        String address = request.getParameter("address");
	        
	        Customer customer = new Customer();
	        customer.setCustomerId(customerId);
	        customer.setEmail(email);
	        customer.setFullname(fullName);
	        customer.setPassword(password);
	        customer.setPhone(phone);
	        customer.setCity(city);
	        customer.setZipcode(zipcode);
	        customer.setCountry(country);
	        customer.setAddress(address);
	        
			customerDAO.update(customer);
			
			message = "The customer has been updated successfully";

		}
		listCustomers(message);
	}
}
