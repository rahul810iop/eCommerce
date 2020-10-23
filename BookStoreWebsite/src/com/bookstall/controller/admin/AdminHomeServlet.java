package com.bookstall.controller.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookstall.dao.BookDAO;
import com.bookstall.dao.CustomerDAO;
import com.bookstall.dao.OrderDAO;
import com.bookstall.dao.ReviewDAO;
import com.bookstall.dao.UserDAO;
import com.bookstoredb.entity2.BookOrder;
import com.bookstoredb.entity2.Review;

@WebServlet("/admin/")
public class AdminHomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;    
	
    public AdminHomeServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	OrderDAO orderDAO = new OrderDAO();
    	ReviewDAO reviewDAO = new ReviewDAO();
    	BookDAO bookDAO = new BookDAO();
    	CustomerDAO customerDAO = new CustomerDAO();
    	UserDAO userDAO = new UserDAO();
    	
    	List<BookOrder> listMostRecentSales = orderDAO.listMostRecentSales();
    	List<Review> listMostRecentReviews = reviewDAO.listMostRecentReviews();
    	
    	long totalBooksOnStrore = bookDAO.count();
    	long totalCustomersOnBookstall = customerDAO.count();
    	long totalAdmins = userDAO.count();
    	long totalOrders = orderDAO.count();
    	long totalReviews = reviewDAO.count();
    	
    	String homepage = "index.jsp";
		System.out.println("AdminHomeServlet");
		
		request.setAttribute("listMostRecentSales", listMostRecentSales);
		request.setAttribute("listMostRecentReviews", listMostRecentReviews);
		
		request.setAttribute("totalBooksOnStrore", totalBooksOnStrore);
		request.setAttribute("totalCustomersOnBookstall", totalCustomersOnBookstall);
		request.setAttribute("totalAdmins", totalAdmins);
		request.setAttribute("totalOrders", totalOrders);
		request.setAttribute("totalReviews", totalReviews);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(homepage);
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
