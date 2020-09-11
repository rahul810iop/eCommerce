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
    
	public void createCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
	        String zipCode = request.getParameter("zipCode");
	        String country = request.getParameter("country");
	        String address = request.getParameter("address");
	        
	        Customer newCustomer = new Customer();
	        newCustomer.setEmail(email);
	        newCustomer.setFullname(fullName);
	        newCustomer.setPassword(password);
	        newCustomer.setPhone(phone);
	        newCustomer.setCity(city);
	        newCustomer.setZipcode(zipCode);
	        newCustomer.setCountry(country);
	        newCustomer.setAddress(address);
	        
			customerDAO.create(newCustomer);
			
			listCustomers("New customer has been created successfully");
			}
	}
}
