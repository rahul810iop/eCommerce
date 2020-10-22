package com.bookstall.controller.frontend.order;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bookstoredb.entity2.BookOrder;
import com.bookstoredb.entity2.OrderDetail;

@WebServlet("/remove_book_from_frontend_order")
public class RemoveBookFromFrontendOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RemoveBookFromFrontendOrderServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		int bookId = Integer.parseInt(request.getParameter("id"));
		
		HttpSession session = request.getSession();
		BookOrder order = (BookOrder) session.getAttribute("order");
		System.out.println(order.getPaymentMethod());
		
		Set<OrderDetail> orderDetails = order.getOrderDetails();
		Iterator<OrderDetail> iterator = orderDetails.iterator();
		
		while(iterator.hasNext()) {
			OrderDetail orderDetail = iterator.next();
			
			if(orderDetail.getBook().getBookId() == bookId) {
				float newTotal = order.getTotal() - orderDetail.getSubtotal();
				order.setTotal(newTotal);
				iterator.remove();
			}
		}
		
		String editOrderFormPage="frontend/order_frontend_form.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(editOrderFormPage);
		dispatcher.forward(request, response);
	}
	
}
