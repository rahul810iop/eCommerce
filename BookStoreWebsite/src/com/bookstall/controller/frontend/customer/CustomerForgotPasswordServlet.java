package com.bookstall.controller.frontend.customer;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bookstall.service.CustomerServices;

@WebServlet("/forgot_password")
public class CustomerForgotPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CustomerForgotPasswordServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
    	CustomerServices customerServices = new CustomerServices(request, response);
		customerServices.showForgotPasswordForm();
    }
    
	/*protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		
	}*/

}
