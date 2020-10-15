package com.bookstall.dao;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
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

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		orderDAO.close();
	}

	@Test
	public void testCreateBookOrder() {
		BookOrder order = new BookOrder();
		Customer customer = new Customer();
		customer.setCustomerId(16);
		
		order.setCustomer(customer);
		order.setRecipientName("Rahul");
		order.setRecipientPhone("123456789");
		order.setShippingAddress("Donar Darbhanga");
		
		Set<OrderDetail> orderDetails = new HashSet<>();
		OrderDetail orderDetail = new OrderDetail();
		
		Book book = new Book(42);
		orderDetail.setBook(book);
		orderDetail.setQuantity(2);
		orderDetail.setSubtotal(240.0f);
		orderDetail.setBookOrder(order);
		
		orderDetails.add(orderDetail);
		
		order.setOrderDetails(orderDetails);
		
		orderDAO.create(order);
		
		assertTrue(order.getOrderId() > 0);

	}

	@Test
	public void testCreateBookOrder2() {
		BookOrder order = new BookOrder();
		Customer customer = new Customer();
		customer.setCustomerId(18);
		
		order.setCustomer(customer);
		order.setRecipientName("Mohan");
		order.setRecipientPhone("6532145236");
		order.setShippingAddress("Kathalbari, Darbhanga");
		
		Set<OrderDetail> orderDetails = new HashSet<>();
		OrderDetail orderDetail1 = new OrderDetail();
		
		Book book1 = new Book(46);
		orderDetail1.setBook(book1);
		orderDetail1.setQuantity(2);
		orderDetail1.setSubtotal(440.0f);
		orderDetail1.setBookOrder(order);
		
		orderDetails.add(orderDetail1);
		
		OrderDetail orderDetail2 = new OrderDetail();
		
		Book book2 = new Book(48);
		orderDetail2.setBook(book2);
		orderDetail2.setQuantity(1);
		orderDetail2.setSubtotal(1431.0f);
		orderDetail2.setBookOrder(order);
		
		orderDetails.add(orderDetail2);
		
		order.setOrderDetails(orderDetails);
		
		orderDAO.create(order);
		
		assertTrue(order.getOrderId() > 0 && order.getOrderDetails().size() == 2);

	}

	
	@Test
	public void testUpdateBookOrderShippingAddress() {
		Integer orderId = 26;
		BookOrder order = orderDAO.get(orderId);
		
		System.out.println(order.getShippingAddress());
		order.setShippingAddress("Patna Bihar");
		
		orderDAO.update(order);
		System.out.println(order.getShippingAddress());
		assertNotEquals(order.getShippingAddress(), "Patna Bihar");
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testUpdateBookOrderDetail() {
		Integer orderId = 27;
		BookOrder order = orderDAO.get(orderId);
		Iterator<OrderDetail> iterator = order.getOrderDetails().iterator();

		while(iterator.hasNext()) {
			OrderDetail orderDetail = iterator.next();
			if(orderDetail.getBook().getBookId() == 48) {
				orderDetail.setQuantity(2);
				orderDetail.setSubtotal(2862.0f);
			}
		}
		
		orderDAO.update(order);
		
		BookOrder updatedOrder = orderDAO.get(orderId);

		iterator = order.getOrderDetails().iterator();

		int expQ = 2, actualQ = 0;
		float expS = 2862.0f, actualS = 0;
		
		while(iterator.hasNext()) {
			OrderDetail orderDetail = iterator.next();
			if(orderDetail.getBook().getBookId() == 48) {
				actualQ = orderDetail.getQuantity();
				actualS = orderDetail.getSubtotal();
			}
		}

		
		assertEquals(expQ, actualQ);
		assertEquals(expS, actualS, 0.0f);
	}

	@Test
	public void testGet() {
		Integer orderId = 27;
		BookOrder order = orderDAO.get(orderId);
		System.out.println(order.getRecipientName());
		System.out.println(order.getRecipientPhone());
		System.out.println(order.getShippingAddress());
		System.out.println(order.getStatus());
		assertEquals(2, order.getOrderDetails().size());
	}

	@Test
	public void testDeleteOrder() {
		int orderId = 26;
		orderDAO.delete(orderId);
		
		BookOrder order = orderDAO.get(orderId);
		
		assertNull(order);
	}

	@Test
	public void testListAll() {
		List<BookOrder> listOrders = orderDAO.listAll();
		
		for(BookOrder order : listOrders) {
			System.out.println(order.getOrderId());
			System.out.println(order.getCustomer().getFullname());
		}
		
		assertTrue(listOrders.size() > 0);
	}

	@Test
	public void testCount() {
		long totalOrders = orderDAO.count(); 
		
		assertEquals(totalOrders, 2);
	}

}
