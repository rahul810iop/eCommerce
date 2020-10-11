package com.bookstall.controller.frontend.shoppingcart;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bookstoredb.entity2.Book;

public class ShoppingCartTest {

	private static ShoppingCart cart;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		cart = new ShoppingCart();
		
		Book book = new Book(42);
		book.setPrice(100.0f);
		cart.addItem(book);
		cart.addItem(book);
		
	}
	
	@Test
	public void testAddItems() {
		
		Map<Book, Integer> items = cart.getItems();
		int quantity = items.get(new Book(42));
		
		assertEquals(2, quantity);
	}
	
	@Test
	public void testRemoveItems() {
		cart.removeItem(new Book(42));
		
		assertTrue(cart.getItems().isEmpty());
	}
	
	@Test
	public void testRemoveItems2() {
		Book book2 = new Book(43);
		cart.addItem(book2);
		
		cart.removeItem(new Book(43));
		
		assertNull(cart.getItems().get(book2));
	}

	@Test
	public void testGetTotalQuantity() {
		cart.clear();
		Book book3 = new Book(43);
		cart.addItem(book3);
		cart.addItem(book3);
		cart.addItem(book3);
		
		Book book4 = new Book(42);
		cart.addItem(book4);
		cart.addItem(book4);
		
		System.out.println(cart.getTotalQuantity());
		
		assertTrue(cart.getTotalQuantity() > 0);
	}
	
	@Test
	public void testGetTotalAmount() {
		ShoppingCart cart = new ShoppingCart();
		
		System.out.println(cart.getTotalAmount());
	
		assertEquals(0.0f, cart.getTotalAmount(), 0.0f);
	}
	
	@Test
	public void testGetTotalAmount2() {
		System.out.println(cart.getTotalAmount());
	
		assertEquals(200.0f, cart.getTotalAmount(), 0.0f);
	}
}
