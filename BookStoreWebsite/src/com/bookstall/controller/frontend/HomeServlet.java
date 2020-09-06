package com.bookstall.controller.frontend;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookstall.dao.BookDAO;
import com.bookstall.dao.CategoryDAO;
import com.bookstoredb.entity2.Book;
import com.bookstoredb.entity2.Category;

@WebServlet("")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public HomeServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		BookDAO bookDAO = new BookDAO();
		
		List<Book> listNewBooks = bookDAO.listNewBooks();
		
		request.setAttribute("listNewBooks", listNewBooks);
		
		String homepage = "frontend/index.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(homepage);
		dispatcher.forward(request, response);
	}

}
