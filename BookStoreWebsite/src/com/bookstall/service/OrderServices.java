package com.bookstall.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookstall.dao.OrderDAO;
import com.bookstoredb.entity2.BookOrder;

public class OrderServices {
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
}
