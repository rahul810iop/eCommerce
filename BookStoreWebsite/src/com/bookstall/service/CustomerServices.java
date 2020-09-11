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
	private HttpServletResponse response;
    private HttpServletRequest request;
	
    public CustomerServices(HttpServletRequest request, HttpServletResponse response) {
		super();
		this.response = response;
		this.request = request;
	}
    
    public void listCustomers() throws ServletException, IOException {
    	CustomerDAO customerDAO = new CustomerDAO();
    	List<Customer> listCustomer = customerDAO.listAll();
    	
        request.setAttribute("listCustomer", listCustomer);
		
		String listPage = "customer_list.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listPage);
		requestDispatcher.forward(request, response);	
    }
}
