package com.bookstall.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookstall.dao.BookDAO;
import com.bookstall.dao.CategoryDAO;
import com.bookstoredb.entity2.Category;
import static com.bookstall.service.CommonUtility.*;

public class CategoryServices {
	private CategoryDAO categoryDAO;
    private HttpServletResponse response;
    private HttpServletRequest request;
    
    public CategoryServices(HttpServletRequest request, HttpServletResponse response) {
    	this.request = request;
    	this.response = response;
    	categoryDAO = new CategoryDAO();
    }
    
    public void listCategory(String message) throws ServletException, IOException {
        List<Category> listCategory = categoryDAO.listAll();
     
        request.setAttribute("listCategory", listCategory);
        if(message != null) {
            request.setAttribute("message", message);
        }
        forwardToPage("category_list.jsp", request, response);
    }

    public void listCategory() throws ServletException, IOException {
         listCategory(null);
    }    
	public void createCategory() throws ServletException, IOException {
		String name = request.getParameter("name");
		Category existCategory = categoryDAO.findByName(name);
		
		if(existCategory != null) {
			String message = "Could not create category."+"A category with name " 
		+ name + " already exists.";
		showMessageBackend(message, request, response);
		}
		else {
			Category newCategory = new Category(name);
			categoryDAO.create(newCategory);
			String message = "New Category created successfully";
			listCategory(message);
		}
	}
    
	public void editCategory() throws ServletException, IOException {
		int categoryId = Integer.parseInt(request.getParameter("id"));
		Category category = categoryDAO.get(categoryId);

		String editPage = "category_form.jsp";
		
		if (category == null) {
			editPage = "message.jsp";
			String errorMessage = "Could not find category with ID " + categoryId;
			showMessageBackend(errorMessage, request, response);
		} else {
			request.setAttribute("category", category);
			forwardToPage("category_form.jsp", request, response);
		}
		
	}

	public void updateCategory() throws ServletException, IOException {
		int categoryId = Integer.parseInt(request.getParameter("categoryId"));
		String categoryName = request.getParameter("name");
		
		Category categoryById = categoryDAO.get(categoryId);
	    
	    Category categoryByName = categoryDAO.findByName(categoryName);
	    
	    if(categoryByName != null && categoryById.getCategoryId() != categoryByName.getCategoryId()) {
	    	String message = "Could not update category. Category with name " + categoryName + " already exists!";
	    	request.setAttribute("message", message);
	    	
	    	RequestDispatcher requestDispatcher = request.getRequestDispatcher("message.jsp");
		    requestDispatcher.forward(request, response);
	    } 
	    else {
	    	categoryById.setName(categoryName);
		    categoryDAO.update(categoryById);
			
	        String message = "Category has been updated successfully";
	        listCategory(message);
	    }

	}

	public void deleteCategory() throws ServletException, IOException {
        int categoryId = Integer.parseInt(request.getParameter("id"));
		
		Category category = categoryDAO.get(categoryId);
		String message = "Category has been deleted successfully";
		
		BookDAO bookDAO = new BookDAO();
		long numberOfBooks = bookDAO.countByCategory(categoryId);
		
		if(numberOfBooks > 0) {
			message = "Could not delete the category (ID: %d) as it contains some books";
		    message = String.format(message, categoryId);
		}
		else if (category == null) {
			message = "Could not find category with ID " + categoryId
					+ ", or it might have been deleted by another admin";
			
		} else {
			categoryDAO.delete(categoryId);
			//listCategory(message);
		}	
		listCategory(message);
	}
}
		
