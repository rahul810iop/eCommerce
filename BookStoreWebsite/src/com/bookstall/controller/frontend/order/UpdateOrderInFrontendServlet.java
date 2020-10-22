package com.bookstall.controller.frontend.order;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookstall.service.OrderServices;

@WebServlet("/update_frontend_order")
public class UpdateOrderInFrontendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdateOrderInFrontendServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		OrderServices orderServices = new OrderServices(request, response);
		orderServices.updateOrderInFrontend();
	}

}
