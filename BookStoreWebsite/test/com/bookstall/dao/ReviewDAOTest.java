package com.bookstall.dao;

import static org.junit.Assert.*;

import java.util.List;

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
		int customerId = 18;
		customer.setCustomerId(customerId);
		
		review.setBook(book);
		review.setCustomer(customer);
		
		review.setHeadline("Well 4 Star");
		review.setRating(5);
		review.setComment("Beyond expectations");
	
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
	
	@Test
	public void testListAll() {
		List<Review> listReview = reviewDAO.listAll(); 
		
		for(Review r : listReview) {
			System.out.println("Review  " +r.getHeadline() + " comment " + r.getComment());
			System.out.println("Cutsomer Name: " +r.getCustomer().getFullname());
			System.out.println("Book Name: " + r.getBook().getTitle());
		}
		
		assertTrue(listReview.size() > 0);
	}
	
	@Test
	public void testCountAll() {
		long totalReviews = reviewDAO.count();
		System.out.println("Total reviews: " + totalReviews);
		assertEquals(totalReviews, 1);
	}
	
	@Test
	public void testDeleteReview() {
		int reviewId = 15;
		reviewDAO.delete(reviewId);
		
		Review review = reviewDAO.get(reviewId);
		assertNull(review);
	}
	
	@Test
	public void testCountByBook() {
		int bookId = 42;
		long countReviewsByBook = reviewDAO.countByBook(bookId);
		
		assertEquals(countReviewsByBook, 2);
	}
}
