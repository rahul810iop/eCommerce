package com.bookstall.dao;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bookstoredb.entity2.Book;
import com.bookstoredb.entity2.BookOrder;
import com.bookstoredb.entity2.Customer;
import com.bookstoredb.entity2.OrderDetail;
import com.bookstoredb.entity2.OrderDetailId;

public class OrderDAOTest {

	private static OrderDAO orderDAO;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		orderDAO = new OrderDAO();
	}

	/*@AfterClass
	public static void tearDownAfterClass() throws Exception {
		orderDAO.
	}*/

	@Test
	public void testCreateBookOrder() {
		BookOrder order = new BookOrder();
		Customer customer = new Customer();
		customer.setCustomerId(11);
		
		order.setCustomer(customer);
		order.setRecipientName("Rahul");
		order.setRecipientPhone("6283662284");
		order.setShippingAddress("Moh-Bhathiyarisarai, CBM Campus, Darbhanga");
	
		Set<OrderDetail> orderDetails = new HashSet<>();
		OrderDetail orderDetail = new OrderDetail();
		
		Book book = new Book(42);
		orderDetail.setBook(book);
		
		OrderDetailId orderDetailId = new OrderDetailId();
		orderDetailId.setQuantity(2);
		orderDetailId.setSubTotal(240.0f);
		
		orderDetail.setId(orderDetailId);
		
		orderDetails.add(orderDetail);
		
		order.setOrderDetails(orderDetails);
		
		BookOrder savedOrder = orderDAO.create(order);
		
		assertNotNull(savedOrder);
	}

	@Test
	public void testUpdateBookOrder() {
		fail("Not yet implemented");
	}

	@Test
	public void testGet() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testListAll() {
		fail("Not yet implemented");
	}

	@Test
	public void testCount() {
		fail("Not yet implemented");
	}

}
