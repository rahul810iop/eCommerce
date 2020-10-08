package com.bookstall.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bookstall.dao.BookDAO;
import com.bookstall.dao.ReviewDAO;
import com.bookstoredb.entity2.Book;
import com.bookstoredb.entity2.Customer;
import com.bookstoredb.entity2.Review;

public class ReviewServices extends CommonUtility{
	private ReviewDAO reviewDAO;
	private HttpServletResponse response;
    private HttpServletRequest request;
    
    public ReviewServices(HttpServletRequest request, HttpServletResponse response) {
    	this.request = request;
    	this.response = response;
    	reviewDAO = new ReviewDAO();
    }
    
    public void listAllReviews(String message) throws ServletException, IOException {
    	List<Review> listReviews = reviewDAO.listAll();
    	
    	request.setAttribute("listReviews", listReviews);
    	if(message != null) {
    		request.setAttribute("message", message);
    	}
    	String listPage = "review_list.jsp";
    	RequestDispatcher requestDispatcher = request.getRequestDispatcher(listPage);
    	requestDispatcher.forward(request, response);
    }
    
    public void listAllReviews() throws ServletException, IOException {
    	listAllReviews(null);
    }

    public void editReview() throws ServletException, IOException {
		Integer reviewId = Integer.parseInt(request.getParameter("id"));
		Review review = reviewDAO.get(reviewId);
		
		if (review != null) {		
			request.setAttribute("review", review);		
			forwardToPage("review_form.jsp", request, response);
		} else {
			String message = "Could not find review with ID " + reviewId;
			showMessageBackend(message, request, response);
		}
	}

	public void updateReview() throws ServletException, IOException {
		Integer reviewId = Integer.parseInt(request.getParameter("reviewId"));
		
		String headline = request.getParameter("headline");
		String comment = request.getParameter("comment");
		
		Review review = reviewDAO.get(reviewId);
		review.setHeadline(headline);
		review.setComment(comment);
		
		reviewDAO.update(review);
		
		String message = "The review Id "+ reviewId +" has been successfully updated";
		listAllReviews(message);
	}

	public void deleteReview() throws ServletException, IOException {
		Integer reviewId = Integer.parseInt(request.getParameter("id"));
		Review review = reviewDAO.get(reviewId);
		
		if (review != null) {
			reviewDAO.delete(reviewId);
			String message = "The review has been deleted successfully.";
			listAllReviews(message);
		} else {
			String message = "Could you find review with ID " + reviewId
					+ ", or it might have been deleted by another admin";
			showMessageBackend(message, request, response);
		}		
	}

	public void showReviewForm() throws ServletException, IOException {
		Integer bookId = Integer.parseInt(request.getParameter("book_id"));
		BookDAO bookDAO = new BookDAO();
		Book book = bookDAO.get(bookId);
		
		HttpSession session = request.getSession();
		request.getSession().setAttribute("book", book);
		
		Customer customer = (Customer) session.getAttribute("loggedCustomer");
		
		Review existReview = reviewDAO.findByCustomerAndBook(customer.getCustomerId(), bookId);
		
		String targetPage = "frontend/review_form.jsp";
		
		if(existReview != null) {
			request.setAttribute("review", existReview);
			targetPage = "frontend/review_info.jsp";
		}

			RequestDispatcher requestDispatcher = request.getRequestDispatcher(targetPage);
			requestDispatcher.forward(request, response);
	}

	public void submitReview() throws ServletException, IOException {
		Integer bookId = Integer.parseInt(request.getParameter("bookId"));
		Integer rating = Integer.parseInt(request.getParameter("rating"));
		String headline = request.getParameter("headline");
		String comment = request.getParameter("comment");
	
		Review newReview = new Review();
		newReview.setHeadline(headline);
		newReview.setComment(comment);
		newReview.setRating(rating);
		
		Book book = new Book();
		book.setBookId(bookId);
		
		newReview.setBook(book);
		
		Customer customer = (Customer) request.getSession().getAttribute("loggedCustomer");
		newReview.setCustomer(customer);
		
		reviewDAO.create(newReview);
		
		String messagePage = "frontend/review_done.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(messagePage);
		dispatcher.forward(request, response);
	}
}
