package com.bookstall.dao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bookstoredb.entity2.Book;
import com.bookstoredb.entity2.Customer;
import com.bookstoredb.entity2.Review;

public class ReviewDAOTest {
	
	private static ReviewDAO reviewDAO;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		reviewDAO = new ReviewDAO();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		reviewDAO.close();
	}

	@Test
	public void testCreateReview() {
		Review review = new Review();
		
		Book book = new Book();
		int bookId = 44;
		book.setBookId(bookId);
		
		Customer customer = new Customer();
		int customerId = 11;
		customer.setCustomerId(customerId);
		
		review.setBook(book);
		review.setCustomer(customer);
		
		review.setHeadline("This is the most creative fiction i have ever read. Deserves 5 stars");
		review.setRating(5);
		review.setComment("The magnificient Writting. Hats off to the author, the way evrey thing comes before eyes is amazing");
	
		Review savedReview = reviewDAO.create(review);
		
		assertTrue(savedReview.getReviewId() > 0);
	}

	@Test
	public void testGet() {
		Integer reviewId = 15;
		Review review = reviewDAO.get(reviewId);
		
		assertTrue(review != null);
	}
	
	@Test
	public void testUpdateReview() {
		Integer reviewId = 15;
		Review review = reviewDAO.get(reviewId);
		review.setHeadline("Words are not enough! Ineffable");
		
		Review updatedReview = reviewDAO.update(review);
		
		assertEquals(updatedReview.getHeadline(), review.getHeadline());
	}
}
