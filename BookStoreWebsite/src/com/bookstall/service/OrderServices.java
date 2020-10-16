package com.bookstall.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookstall.dao.OrderDAO;
import com.bookstoredb.entity2.BookOrder;

public class OrderServices extends CommonUtility {
	private OrderDAO orderDAO;
	private HttpServletResponse response;
    private HttpServletRequest request;
    
	public OrderServices(HttpServletRequest request, HttpServletResponse response) {
		this.response = response;
		this.request = request;
		this.orderDAO = new OrderDAO();
	}

	public void listAllOrder() throws ServletException, IOException {
		List<BookOrder> listOrder = orderDAO.listAll();
		
		request.setAttribute("listOrder", listOrder);
		
		String listPage = "order_list.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(listPage);
		dispatcher.forward(request, response);
	}

	public void viewOrderDetailForAdmin() throws ServletException, IOException {
		int orderId = Integer.parseInt(request.getParameter("id"));
				
		BookOrder order = orderDAO.get(orderId);
				
		if (order != null) {
			request.setAttribute("order", order);
			forwardToPage("order_detail.jsp", request, response);
		} else {
			String message = "Could not find order with ID " + orderId;
			showMessageBackend(message, request, response);
		}
    }

	public void showCheckoutForm() throws ServletException, IOException {
		String checkOutPage = "frontend/checkout.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(checkOutPage);
		dispatcher.forward(request, response);
	}
}
