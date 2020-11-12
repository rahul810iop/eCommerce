package com.bookstall.mail.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Set;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.bookstoredb.entity2.Book;
import com.bookstoredb.entity2.BookOrder;
import com.bookstoredb.entity2.Customer;
import com.bookstoredb.entity2.OrderDetail;


public class SendMail {
	
	//private String to;
	private final String from = "1815904.cse.coe@cgc.edu.in";
	private String message;
	private String subject;
	private final String password = "9718742810";
	//private Customer customer;
	
	public SendMail() {		
	}
	
	public void sendRegistrationSuccessfull(Customer customer) 
	{
		subject = "Registration Successfull";
	    message = "Hello " + customer.getFullname() + " ! You have been successfully registered in BookStall.com"
	    		 + " BookStall provides you the diverse range of books at affordable an cheapest rates" + "\n\n\nWarm Regards,\nTeam BookStall";

	    messageTransport(customer.getEmail());
	}
	
	public void sendPlaceOrderSuccessfull(Customer customer, BookOrder order) {
		subject = "Order Placed";
		message = "Dear Sir/Ma'am,\n" + "               You have successfully placed your Order on BookStall." 
		+ " Thank you for Shopping! Happy Reading!\n"
				+ "You can update and cancel your order anytime before getting delivered/Completed." 
		+ "\nOrder Details" + "\nOrder Date: " + order.getOrderDate() + "\nQuantity: " + order.getBookCopies()
		+ "\nTotal Amount: " + "INR " + order.getTotal() + "\nRecipient Name: " + order.getRecipientName() + "\nRecipient Phone: "
		+ order.getRecipientPhone() + "\nRecipient Name: " + order.getShippingAddress() + "\nfor more details you can go to My Orders section on BookStall.com\n\n" 
		+ "Your Order consists of\n";
		
		Set<OrderDetail> orderDetails = order.getOrderDetails();
		for(OrderDetail orderDetail : orderDetails) {
			message += orderDetail.getQuantity() + " Book(s) " + orderDetail.getBook().getTitle() + " by " + orderDetail.getBook().getAuthor() + "\n";
		}
		
		message += "\n\n\n\nWarm regards,\nTeam BookStall";
		
		messageTransport(customer.getEmail());
	}
	
	public void sendCancelOrderSuccessfull(Customer customer, BookOrder order) {
		subject = "Order Cancelled";
		message = "Dear Sir/Ma'am,\n" + "               Your Order made on " + order.getOrderDate() + " of INR " + order.getTotal() + " on BookStall is Cancelled. " 
		+ " You can also write us at "+ from +" the reason for this cancellation.Your feedback is higly appreciable.\n"
				+ "If the amount for the bill was paid, kindly expect your refund within 3 - 5 business days, " 
		+ "\nfor more details you can go to My Orders section on BookStall.com\n\n"
				+ "\n\n\n\nWarm regards,\nTeam BookStall";
		
		messageTransport(customer.getEmail());
	}
	
	public void sendUpdateOrderSuccessfull(Customer customer, BookOrder order) {
		subject = "Order Updated";
		message = "Dear Sir/Ma'am,\n" + "               Your Order made on BookStall has been updated successfully." 
		+ " Thank you for Shopping! Happy Reading!\n"
				+ "You can further update and cancel your order anytime before getting delivered/Completed." +
		"You can expect a slight delay due to updation in your order." 
		+ "\nOrder Details" + "\nOrder Date: " + order.getOrderDate() + "\nQuantity: " + order.getBookCopies()
		+ "\nTotal Amount: " + "INR " + order.getTotal() + "\nRecipient Name: " + order.getRecipientName() + "\nRecipient Phone: "
		+ order.getRecipientPhone() + "\nRecipient Name: " + order.getShippingAddress() + "\nfor more details you can go to My Orders section on BookStall.com\n\n "
		+ "Your updated Order consists of\n";
		
		Set<OrderDetail> orderDetails = order.getOrderDetails();
		for(OrderDetail orderDetail : orderDetails) {
			message += orderDetail.getQuantity() + " Book(s) " + orderDetail.getBook().getTitle() + " by " + orderDetail.getBook().getAuthor() + "\n";
		}
		
		message += "\n\n\n\nWarm regards,\nTeam BookStall";
		
		messageTransport(customer.getEmail());
		
	}
	
	public void sendRandomPasswordPassword(String password, Customer customer) {
		subject = "Forgot Password";
		message = "Dear Sir/Ma'am,\n" + "               Your new password is " + password 
				+ " Log in through this password and do not forget to change your password on BookStall -- > My Profile\n";
		
		messageTransport(customer.getEmail());
	}
	
	public void messageTransport(String to) {
		
		/*String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);*/

			
		
		try
	    {
	      Properties props = new Properties();
	      props.put("mail.smtp.host", "smtp.gmail.com");
	      props.put("mail.smtp.socketFactory.port", "465");
	      props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	      props.put("mail.smtp.auth", "true");
	      props.put("mail.smtp.port", "465");

	      Authenticator auth = new Authenticator() {
	        public PasswordAuthentication getPasswordAuthentication() {
	          return new PasswordAuthentication(from, password);
	        }
	      };
	      Session session = Session.getInstance(props, auth);

	      MimeMessage msg = new MimeMessage(session);
	      msg.setFrom(new InternetAddress(from));
	      msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
	      msg.setSubject(subject);
	      msg.setText(message);
	      //msg.setSentDate(date);
	      
	      Transport.send(msg);
	      System.out.println("Message delivered successfully , Check your mail ...........");
	    }
	    catch (MessagingException e)
	    {
	      e.printStackTrace();
	
        }
	}


}
	

