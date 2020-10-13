package com.bookstall.controller.frontend.shoppingcart;

import java.util.HashMap;
import java.util.Map;

import com.bookstall.dao.BookDAO;
import com.bookstoredb.entity2.Book;

public class ShoppingCart {
	private Map<Book, Integer> cart = new HashMap<>();
	
	public void addItem(Book book) {
		if(cart.containsKey(book)) {
			Integer quantity = cart.get(book) + 1;
			cart.put(book, quantity);
		}
		else {
			cart.put(book, 1);
		}
	}
	
	
	
	public void clear() {
		cart.clear();
	}
	
	public int getTotalItems() {
		return cart.size();
	}
	
	public int getTotalQuantity() {
		int total = 0;
		for(Book book : cart.keySet()) {
			total += cart.get(book);
		}
		return total;
	}
	
	public float getTotalAmount() {
		float totalSum = 0.0f;
		
		for(Book book : cart.keySet()) {
			totalSum += (cart.get(book) * book.getPrice());
		}
		return totalSum; 
	}
	
	public Map<Book, Integer> getItems() {
		return this.cart;
	}
	
	public void updateCart(int bookIds[], int quantities[]) {
		
		for(int i=0;i<bookIds.length;i++) {
			
			Book book = new Book(bookIds[i]);
			
			cart.put(book, quantities[i]);
		}
	}

	public void removeItem(Book book) {
		cart.remove(book);
	}
}
