package com.bookstall.controller.frontend.customer;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookstall.service.CustomerServices;

@WebServlet("/send_password")
public class SendPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SendPasswordServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CustomerServices customerServices = new CustomerServices(request, response);
		customerServices.updateForgotPassword();
	}

}