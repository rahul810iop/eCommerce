package com.bookstall.dao;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bookstoredb.entity2.Book;
import com.bookstoredb.entity2.Category;

public class BookDAOTest {

	private static BookDAO bookDAO;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	    bookDAO = new BookDAO();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	    bookDAO.close();
	}

	@Test
	public void testCreateBook() throws ParseException, IOException {
		Book newBook = new Book();
		
		Category category = new Category("Advanced Java");
		category.setCategoryId(13);
		newBook.setCategory(category);
		
		newBook.setTitle("Effective Java (2nd Edition)");
	    newBook.setAuthor("Joshua Bloch");
	    newBook.setDescription("New coverage of generics, enums, annotations, autoboxing");
		newBook.setPrice(38.87f);
		newBook.setIsbn("0321356683");
		
		DateFormat dateFormat = new SimpleDateFormat("DD/MM/YYYY");
		Date publishDate = dateFormat.parse("28/05/2008");
		newBook.setPublishDate(publishDate);
		
		String imagePath = "D:\\BookStoreWebsite\\Dummy_books\\Effective Java.JPG";
		byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
		newBook.setImage(imageBytes);
		
		Book createdBook = bookDAO.create(newBook);
		
		assertTrue(createdBook.getBookId() > 0);
	}

	@Test
	public void testUpdateBook() throws ParseException, IOException {
        Book existBook = new Book();
        existBook.setBookId(32);
		
		Category category = new Category("Academics");
		category.setCategoryId(25);
		existBook.setCategory(category);
		
		existBook.setTitle("Effective Java (2nd Edition)");
	    existBook.setAuthor("Joshua Bloch");
	    existBook.setDescription("New coverage of generics, enums, annotations, autoboxing");
		existBook.setPrice(40f);
		existBook.setIsbn("0321356683");
		
		DateFormat dateFormat = new SimpleDateFormat("MM/DD/YYYY");
		Date publishDate = dateFormat.parse("05/28/2008");
		existBook.setPublishDate(publishDate);
		
		String imagePath = "D:\\BookStoreWebsite\\Dummy_books\\Effective Java.JPG";
		byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
		existBook.setImage(imageBytes);
		
		Book updatedBook = bookDAO.update(existBook);
		
		assertEquals(updatedBook.getTitle(), "Effective Java (2nd Edition)");
	}
	
	@Test
	public void testCreate2ndBook() throws ParseException, IOException {
		Book newBook = new Book();
		
		Category category = new Category("Core Java");
		category.setCategoryId(26);
		newBook.setCategory(category);
		
		newBook.setTitle("Java 8 in Action");
	    newBook.setAuthor("Raoul-Gabriel Urma, Mario Fusco, Alan Mycroft");
	    newBook.setDescription("ava 8 in Action is a clearly written guide to the new features of Java 8.");
		newBook.setPrice(36.72f);
		newBook.setIsbn("1617291994");
		
		DateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
		Date publishDate = dateFormat.parse("08/28/2014");
		newBook.setPublishDate(publishDate);
		
		String imagePath = "D:\\BookStoreWebsite\\Dummy_books\\Java 8 in Action.JPG";
		byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
		newBook.setImage(imageBytes);
		
		Book createdBook = bookDAO.create(newBook);
		
		assertTrue(createdBook.getBookId() > 0);
	}

	
	@Test(expected = EntityNotFoundException.class)
	public void testDeleteBookFail() {
		Integer bookId = 100;
		bookDAO.delete(bookId);
		
		assertTrue(true);
	}
	
	@Test
	public void testDeleteBookSuccess() {
		Integer bookId = 32;
		bookDAO.delete(bookId);
		
		assertTrue(true);
	}
	
	@Test
	public void testGetBookFail() {
		Integer bookId = 99;
		Book book = bookDAO.get(bookId);
		
		assertNull(book);
	}
	
	@Test
	public void testGetBookSuccess() {
		Integer bookId = 33;
		Book book = bookDAO.get(bookId);
		
		assertNotNull(book);
	}
	
	@Test
	public void testListAll() {
		List<Book> listBooks = bookDAO.listAll();
		for(Book aBook : listBooks) {
			System.out.println(aBook.getTitle() + " . " + aBook.getAuthor());
		}
		
		assertFalse(listBooks.isEmpty());
	}
	
	@Test
	public void testFindByTitleNotExist() {
		String title = "Thinking in Java";
		Book book = bookDAO.findByTitle(title);
		
		assertNull(book);
	}
	
	@Test
	public void testFindByTitleExist() {
		String title = "Java 8 in Action";
		Book book = bookDAO.findByTitle(title);
		
		assertNotNull(book);
	}
	
	@Test
	public void testListByCategory() {
		int categoryId = 13;
		List<Book> listBooks = bookDAO.listByCategory(categoryId);
		
		assertTrue(listBooks.size() > 0);
		
	}
	
	@Test
	public void testListNewBooks() {
		List<Book> listNewBooks = bookDAO.listNewBooks();
		for(Book aBook : listNewBooks) {
			System.out.println(aBook.getTitle() + " " + aBook.getPublishDate());
		}
		assertEquals(4, listNewBooks.size());
	}
	
	@Test
	public void testSearchBookInTitle() {
		String keyword = "kathy";
		List<Book> result = bookDAO.search(keyword);
		
		for(Book aBook : result) {
			System.out.println(aBook.getTitle());
		}
		
		assertTrue(result.size() > 0);
	}
	
	@Test
	public void testCount() {
		long totalBooks = bookDAO.count();
		
		assertEquals(2, totalBooks);
	}
	
	@Test
	public void testCountByCategory() {
		int categoryId = 26;
		long numOfBooks = bookDAO.countByCategory(categoryId);
	
	    assertEquals(6, numOfBooks);
	}
}
