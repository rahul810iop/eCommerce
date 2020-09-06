package com.bookstall.service;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookstall.dao.HashGenerator;
import com.bookstall.dao.UserDAO;
import com.bookstoredb.entity2.Users;

public class UserServices {
    private UserDAO userDAO;
    private HttpServletResponse response;
    private HttpServletRequest request;
    
    public UserServices(HttpServletRequest request, HttpServletResponse response) {
    	this.request = request;
    	this.response = response;
    	userDAO = new UserDAO();
    }
    
    public void listUser() throws ServletException, IOException {
        listUser(null);
    }
    
    public void listUser(String message) throws ServletException, IOException {
    	List<Users> listUsers = userDAO.listAll();
        
    	request.setAttribute("listUsers", listUsers);
	    
    	if(message != null) {
    		request.setAttribute("message", message);
    	}
    	
	    String listPage = "user_list.jsp";
	    RequestDispatcher requestDispatcher = request.getRequestDispatcher(listPage);
	    
	    requestDispatcher.forward(request, response);
    
    }
    
    public void createUser() throws ServletException, IOException {
    	String email = request.getParameter("email");
		String fullName = request.getParameter("fullname");
		String password = request.getParameter("password");
		
		Users existUser = userDAO.findByEmail(email);
		
		if(existUser != null) {
			String message = "The email "+ email + " already exists!";
		    request.setAttribute("message", message);
		    RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
            dispatcher.forward(request, response);
		}
		else {
		    Users newUser = new Users(email,fullName,password);
            userDAO.create(newUser);
            listUser("New user created successfully");
        }
    }

	public void editUser() throws ServletException, IOException {
			int userId = Integer.parseInt(request.getParameter("id"));
			Users user = userDAO.get(userId );

			String destPage = "user_form.jsp";
			
			if (user == null) {
				destPage = "message.jsp";
				String errorMessage = "Could not find user with ID " + userId;
				request.setAttribute("message", errorMessage);
			} else {
				user.setPassword(null);
				request.setAttribute("user", user);			
			}
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(destPage);
			requestDispatcher.forward(request, response);
			
		}

	public void updateUser() throws ServletException, IOException {
		int userId = Integer.parseInt(request.getParameter("userId"));
		String email = request.getParameter("email");
		String fullName = request.getParameter("fullname");
		String password = request.getParameter("password");
		
		Users userById = userDAO.get(userId);
		
		Users userByEmail = userDAO.findByEmail(email);
		
		if (userByEmail != null && userByEmail.getUserId() != userById.getUserId()) {
			String message = "Could not update user. User with email " + email + " already exists.";
			request.setAttribute("message", message);
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("message.jsp");
			requestDispatcher.forward(request, response);			
			
		} else {
			userById.setEmail(email);
			userById.setFullName(fullName);
			
			if (password != null & !password.isEmpty()) {
				String encryptedPassword = HashGenerator.generateMD5(password);
				userById.setPassword(encryptedPassword);				
			}
			
			userDAO.update(userById);

			String message = "User has been updated successfully";
			listUser(message);
		}
	
	}

	public void deleteUser() throws ServletException, IOException {
       int userId = Integer.parseInt(request.getParameter("id"));
		
		Users user = userDAO.get(userId);
		String message = "User has been deleted successfully";
		
		if(userId == 19) {
			message = "The default admin user account cannot be deleted";
		 
		    request.setAttribute("message", message);
		    request.getRequestDispatcher("message.jsp").forward(request, response);;
		    return;
		}
		
		if (user == null) {
			message = "Could not find user with ID " + userId
					+ ", or it might have been deleted by another admin";
			
			request.setAttribute("message", message);
			request.getRequestDispatcher("message.jsp").forward(request, response);			
		} else {
			userDAO.delete(userId);
			listUser(message);
		}	
	}

	public void login() throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		boolean loginResult = userDAO.checkLogin(email, password);
		
		if(loginResult) {
			System.out.println("User is authenticated");
		    
			request.getSession().setAttribute("useremail", email);
		
		    RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/");
		    dispatcher.forward(request, response);
		
		}
		else {
			String message = "Login failed! Please enter correct email or password";
			request.setAttribute("message", message);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
		    dispatcher.forward(request, response);
		}
	}
}
