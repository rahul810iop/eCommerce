package com.bookstall.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.bookstall.dao.BookDAO;
import com.bookstall.dao.CategoryDAO;
import com.bookstoredb.entity2.Book;
import com.bookstoredb.entity2.Category;

public class BookServices {
	private BookDAO bookDAO;
	private CategoryDAO categoryDAO;
    private HttpServletResponse response;
    private HttpServletRequest request;
    
    public BookServices(HttpServletRequest request, HttpServletResponse response) {
		super();
		this.response = response;
		this.request = request;
		bookDAO = new BookDAO();
		categoryDAO = new CategoryDAO();
	}

    public void listBooks() throws ServletException, IOException {
		listBooks(null);
    }
    
	public void listBooks(String message) throws ServletException, IOException {
		List<Book> listBooks = bookDAO.listAll();
		request.setAttribute("listBooks", listBooks);
		
		if(message != null) {
			request.setAttribute("message", message);
		}
		
		String listPage = "book_list.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listPage);
		requestDispatcher.forward(request, response);
    }
	
	public void showBookNewForm() throws ServletException, IOException {
	     List<Category> listCategory = categoryDAO.listAll();
	     
	     request.setAttribute("listCategory", listCategory);
			
		 String newPage = "book_form.jsp";
		 RequestDispatcher requestDispatcher = request.getRequestDispatcher(newPage);
		 requestDispatcher.forward(request, response);
	}

	public void createBook() throws ServletException, IOException {
		
		String title = request.getParameter("title");
		
		Book existBook = bookDAO.findByTitle(title);
		
		if(existBook != null) {
			String message = "Could not create new book because the title " + title + " already exists.";
		    listBooks(message);
		    return;
		}
		
		Book newBook = new Book();
		readBookFields(newBook);
		
		Book createdBook = bookDAO.create(newBook);
	    
	    if(createdBook.getBookId() > 0) {
	    	String message = "A new book has been created successfully";
	    	
	    	listBooks(message);
	    }
	}

	public void editBook() throws ServletException, IOException {
		Integer bookId = Integer.parseInt(request.getParameter("id"));
		Book book = bookDAO.get(bookId);
		String destPage = "book_form.jsp";
		
		if (book != null) {
			List<Category> listCategory = categoryDAO.listAll();
			
			request.setAttribute("book", book);
			request.setAttribute("listCategory", listCategory);
			
		} else {
			destPage = "message.jsp";
			String message = "Could not find book with ID " + bookId;
			request.setAttribute("message", message);			
		}
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(destPage);
		requestDispatcher.forward(request, response);		
			
	}

	public void readBookFields(Book book) throws ServletException, IOException {
		
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		String description = request.getParameter("description");
		String isbn = request.getParameter("isbn");
		float price = Float.parseFloat(request.getParameter("price"));
		
		DateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
		Date publishDate = null;
	
	    try {
	         publishDate = dateFormat.parse(request.getParameter("publishDate"));
	    }
	    catch(ParseException ex) {
	    	ex.printStackTrace();
	    	throw new ServletException("Error parsing publish date (format is mm/dd/yyyy)");
	    }
	    
	 
	    book.setTitle(title);
	    book.setAuthor(author);
	    book.setDescription(description);
	    book.setIsbn(isbn);
	    book.setPrice(price);
	    book.setPublishDate(publishDate);
	    
	    Integer categoryId = Integer.parseInt(request.getParameter("category"));
	    Category category = categoryDAO.get(categoryId);
	    book.setCategory(category);
	    
	    Part part = request.getPart("bookImage");
	    
	    if(part != null && part.getSize() > 0) {
	    	long size = part.getSize();
	    	byte[] imageBytes = new byte[(int) size];
	    	
	    	InputStream inputStream = part.getInputStream();
	    	inputStream.read(imageBytes);
	    	inputStream.close();
	    	
	    	book.setImage(imageBytes);
	    }
	    
	
	}
	public void updateBook() throws ServletException, IOException {
		Integer bookId = Integer.parseInt(request.getParameter("bookId"));
		String title = request.getParameter("title");
		
		Book existBook = bookDAO.get(bookId);
		Book bookByTitle = bookDAO.findByTitle(title);
		
		if (bookByTitle != null && !existBook.equals(bookByTitle)) {
			String message = "Could not update book because there's another book having same title.";
			listBooks(message);
			return;
		}
		
		readBookFields(existBook);
		
		bookDAO.update(existBook);
		
		String message = "The book has been updated successfully.";
		listBooks(message);

	}

	public void deleteBook() throws ServletException, IOException {
		Integer bookId = Integer.parseInt(request.getParameter("id"));
		Book book =  bookDAO.get(bookId);
		
		if (book == null) {
			String message = "Could not find book with ID " + bookId 
					+ ", or it might have been deleted";
			request.setAttribute("message", message);
			request.getRequestDispatcher("message.jsp").forward(request, response);
			
		} else {
			if (!book.getReviews().isEmpty()) {
				String message = "Could not delete the book with ID " + bookId
						+ " because it has reviews";
				listBooks(message);
			} else {
				String message = "The book has been deleted successfully.";
				bookDAO.delete(bookId);			
				listBooks(message);		
		    }
		}
	  }

	public void listBookByCategory() throws ServletException, IOException {
		int categoryId = Integer.parseInt(request.getParameter("id"));
		Category category = categoryDAO.get(categoryId);
		
		if (category == null) {
			String message = "Sorry, the category ID " + categoryId + " is not available.";
			request.setAttribute("message", message);
			request.getRequestDispatcher("frontend/message.jsp").forward(request, response);
			
			return;
		}
		
		List<Book> listBooks = bookDAO.listByCategory(categoryId);
		
		request.setAttribute("listBooks", listBooks);
		request.setAttribute("category", category);
		
		String listPage = "frontend/books_list_by_category.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listPage);
		requestDispatcher.forward(request, response);
	}

	public void viewBookDetail() throws ServletException, IOException {
		int bookId = Integer.parseInt(request.getParameter("id"));
		Book book = bookDAO.get(bookId);
		
		String detailPage = "frontend/book_detail.jsp";
		
        
		if(book == null) {
			String message = "Sorry! the book with ID " + bookId + " is not available.";
		    request.setAttribute("message", message);
            request.getRequestDispatcher("frontend/message.jsp").forward(request, response);
			                                                      
			return;
		}
		
        request.setAttribute("book", book);
		
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(detailPage);
		requestDispatcher.forward(request, response);
	}

	public void search() throws ServletException, IOException {
		String keyword = request.getParameter("keyword");
		List<Book> result = null; 
		
		if(keyword.equals("")) {
			result = bookDAO.listAll();
		} else {
			result = bookDAO.search(keyword);
		}
		
		String resultPage = "frontend/search_result.jsp";
		
		request.setAttribute("keyword", keyword);
		request.setAttribute("result", result);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(resultPage);
        requestDispatcher.forward(request, response);		
	}
	}
