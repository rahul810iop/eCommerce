package com.bookstall.controller.admin.book;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookstall.service.BookServices;

@WebServlet("/admin/delete_book")
public class DeleteBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DeleteBookServlet() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		BookServices bookServices = new BookServices(request, response);
		bookServices.deleteBook();
	}

}
