package com.bookstall.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;

import com.bookstall.dao.CustomerDAO;
import com.bookstall.dao.OrderDAO;
import com.bookstall.dao.ReviewDAO;
import com.bookstall.mail.services.SendMail;
import com.bookstoredb.entity2.Book;
import com.bookstoredb.entity2.Customer;

public class CustomerServices extends CommonUtility{
	private CustomerDAO customerDAO;
	private HttpServletResponse response;
    private HttpServletRequest request;
	private SendMail sendMail;
    
    public CustomerServices(HttpServletRequest request, HttpServletResponse response) {
		super();
		this.response = response;
		this.request = request;
        this.customerDAO = new CustomerDAO();
        this.sendMail = new SendMail();
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
		String message="";
		
		if (customer != null) {
			ReviewDAO reviewDAO = new ReviewDAO();
			long reviewCount = reviewDAO.countByCustomer(customerId);
			
			if (reviewCount > 0) {
				message = "Could not delete customer with ID " + customerId
						+ " because he/she posted reviews for books.";
				showMessageBackend(message, request, response);
			} else {
				OrderDAO orderDAO = new OrderDAO();
				long orderCount = orderDAO.countByCustomer(customerId);
				
				if (orderCount > 0) {
					message = "Could not delete customer with ID " + customerId 
							+ " because he/she placed orders.";
					showMessageBackend(message, request, response);
				} else {
					customerDAO.delete(customerId);			
					message = "The customer has been deleted successfully.";
					//listCustomers(message);
				}
			}
		} else {
			message = "Could not find customer with ID " + customerId + ", "
					+ "or it has been deleted by another admin";
			showMessageBackend(message, request, response);
		}
		listCustomers(message);
	}
	
	public void registerCustomer() throws ServletException, IOException {
		String email = request.getParameter("email");
		Customer existCustomer = customerDAO.findByEmail(email);
		String message = "";
		
		if(existCustomer != null) {
			message = "Could not register customer: the email "
		       + email + " is already registered";
		}
		else {
			Customer newCustomer = new Customer();
			updateCustomerFieldsFromForm(newCustomer);			
			customerDAO.create(newCustomer);
			
			message = "You have registered successfully! Thank you "
					 + "<a href='frontend/login.jsp'>Click here to</a> login";
			
			sendMail.sendRegistrationSuccessfull(newCustomer);
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
        
        if(email != null && !email.equals("")) {
        	customer.setEmail(email);
        }
        if(password != null && !password.equals("")) {
        	customer.setPassword(password);
        }
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
			HttpSession session = request.getSession();
			session.setAttribute("loggedCustomer", customer);
			
			Object objRedirectURL = session.getAttribute("redirectURL");
			
			if(objRedirectURL != null) {
				String redirectURL = (String) objRedirectURL;
				session.removeAttribute("redirectURL");
				response.sendRedirect(redirectURL);
			}
			else {
				showCustomerProfile();
			}
		}
	}
    
    public void showCustomerProfile() throws ServletException, IOException {
    	String profilePage = "frontend/customer_profile.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(profilePage);
		dispatcher.forward(request, response);
    }

	public void showCustomerProfileFieldForm() throws ServletException, IOException {
		String editPage = "frontend/edit_profile.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(editPage);
		dispatcher.forward(request, response);
	}

	public void updateCustomerProfile() throws ServletException, IOException {
		Customer customer = (Customer) request.getSession().getAttribute("loggedCustomer");
		
		updateCustomerFieldsFromForm(customer);
		customerDAO.update(customer);
		
		showCustomerProfile();
	}

	public void updateForgotPassword() throws ServletException, IOException {
			String email = request.getParameter("email");
		
			Customer customer = customerDAO.findByEmail(email);
			if(customer == null) {
			String message = "Sorry! This email isn't registered. Register Now";
		
			String forgotPage = "frontend/forgot_password.jsp";
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(forgotPage);
			request.setAttribute("message", message);
			requestDispatcher.forward(request, response);
		}
		else {
			String message = "The new Password has been sent to your mail," 
					+ " kindly login with the new password and then change in My profile section";
			
			String password = RandomStringUtils.randomAlphanumeric(13);
			
			customer.setPassword(password);
			customerDAO.update(customer);
			
			sendMail.sendRandomPasswordPassword(password, customer);
			
			
			String messagePage = "frontend/message.jsp";
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(messagePage);
			request.setAttribute("message", message);
			requestDispatcher.forward(request, response);
		}
	}

	public void showForgotPasswordForm() throws ServletException, IOException {
		
		String forgotPage = "frontend/forgot_password.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(forgotPage);
		dispatcher.forward(request, response);	
		
	}
	
}
