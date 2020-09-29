package com.bookstall.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookstall.dao.CustomerDAO;
import com.bookstoredb.entity2.Book;
import com.bookstoredb.entity2.Customer;

public class CustomerServices extends CommonUtility{
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
			Customer newCustomer = new Customer();
			updateCustomerFieldsFromForm(newCustomer);
			customerDAO.create(newCustomer);
			
			listCustomers("New customer has been created successfully");
			}
	}

	public void editCustomer() throws ServletException, IOException {
		Integer customerId = Integer.parseInt(request.getParameter("id"));
		Customer customer = customerDAO.get(customerId);
		
		if (customer == null) {
			String message = "Could not find customer with ID " + customerId;
			showMessageBackend(message, request, response);
		} else {
			request.setAttribute("customer", customer);		
			forwardToPage("customer_form.jsp", request, response);			
		}
	}
	
	public void updateCustomer() throws ServletException, IOException {
		Integer customerId = Integer.parseInt(request.getParameter("customerId"));
		String email = request.getParameter("email");
		
		Customer customerByEmail = customerDAO.findByEmail(email);
		String message = null;
		if(customerByEmail != null && customerByEmail.getCustomerId() != customerId) {
			message = "Could not update the customer ID " + customerId 
					+ "because there's an existing customer having the same email";
		} else {
			Customer customerById = customerDAO.get(customerId);
			updateCustomerFieldsFromForm(customerById);
			customerDAO.update(customerById);
			
			message = "The customer has been updated successfully";

		}
		listCustomers(message);
	}

	public void deleteCustomer() throws ServletException, IOException {
		Integer customerId = Integer.parseInt(request.getParameter("id"));
		Customer customer = customerDAO.get(customerId);
			
			if (customer != null) {
				customerDAO.delete(customerId);
				
				String message = "The customer has been deleted successfully.";
				listCustomers(message);			
			} else {
				String message = "Could not find customer with ID " + customerId + ", "
						+ "or it has been deleted by another admin";
				showMessageBackend(message, request, response);
				listCustomers(message);
			}
		}
	
	public void registerCustomer() throws ServletException, IOException {
		String email = request.getParameter("email");
		Customer existCustomer = customerDAO.findByEmail(email);
		String message = "";
		
		if(existCustomer != null) {
			message = "Could not register customer: the email "
		       + email + "is already registered";
		}
		else {
			Customer newCustomer = new Customer();
			updateCustomerFieldsFromForm(newCustomer);			
			customerDAO.create(newCustomer);
			
			message = "You have regsitered successfully! Thank you "
					 + "<a href=''>Click here to</a> login";
			}
		
		String messagePage = "frontend/message.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(messagePage);
		request.setAttribute("message", message);
		requestDispatcher.forward(request, response);
	}

    private void updateCustomerFieldsFromForm(Customer customer) {
    	String email = request.getParameter("email");
    	String fullName = request.getParameter("fullName");
		String password = request.getParameter("password");
		String phone = request.getParameter("phone");
		String city = request.getParameter("city");
        String zipcode = request.getParameter("zipcode");
        String country = request.getParameter("country");
        String address = request.getParameter("address");
        
        
        customer.setEmail(email);
        customer.setFullname(fullName);
        customer.setPassword(password);
        customer.setPhone(phone);
        customer.setCity(city);
        customer.setZipcode(zipcode);
        customer.setCountry(country);
        customer.setAddress(address);
        	
    }

	public void showLogin() throws ServletException, IOException {
		String loginPage = "frontend/login.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(loginPage);
	    dispatcher.forward(request, response);
	}

	public void doLogin() throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
	
		Customer customer = customerDAO.checkLogin(email, password);
     
		if(customer == null) {
			String message = "Login failed! Please check your email and password";
		    request.setAttribute("message", message);
		    showLogin();
		} else {
			String profilePage = "customer_profile.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(profilePage);
			dispatcher.forward(request, response);
		}
	}
    
    
	}
