package com.bookstall.controller.admin.category;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookstall.service.CategoryServices;

@WebServlet("/admin/create_category")
public class CreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public CreateServlet() {
    
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    CategoryServices customerServices = new CategoryServices(request, response);
	    customerServices.createCategory();
	}

}
