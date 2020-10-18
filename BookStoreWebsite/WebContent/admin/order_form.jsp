<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Edit Orders - Bookstall Administration</title>
<link rel="icon" type="image/jpg" href="../images/book.jpg">
<link rel="stylesheet" href="../css/style.css">
<script type="text/javascript" src="../js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
</head>
<body>
     <jsp:directive.include file="header.jsp" />
     
     <div align="center">
          <h2 class="pageheading">Edit Order ID: ${order.orderId}</h2>
     </div>
     
     <c:if test="${message != null }">
	     <div align="center">
	         <h4 class="message">${message}</h4>
	     </div>
	 </c:if>
     
     <form action="update_order" method="post" id="orderForm">
     <div align="center">
     	<h2>Order Overview</h2>
     	<table>
     		<tr>
     			<td><b>Ordered By: </b></td>
     			<td>${order.customer.fullname}</td>
     		</tr>
     		<tr>
     			<td><b>Order Date: </b></td>
     			<td>${order.orderDate}</td>
     		</tr>
     		<tr>
     			<td><b>Recipient Name: </b></td>
     			<td><input type="text" name="recipientName" value="${order.recipientName}" size="45"/></td>
     		</tr>
     		<tr>
     			<td><b>Recipient Phone: </b></td>
     			<td><input type="text" name="recipientPhone" value="${order.recipientPhone}" size="45"/></td>
     		</tr>
     		<tr>
     			<td><b>Shipping Address: </b></td>
     			<td><input type="text" name="shippingAddress" value="${order.shippingAddress}" size="45"/></td>
     		</tr>
     		<tr>
     			<td><b>Payment Method: </b></td>
     			<td>
     				<select name="paymentMethod">
      						<option value="Cash On Delivery">Cash On Delivery</option>
      						<option value="Credit Card">Credit Card</option>
      						<option value="Debit Card">Debit Card</option>
      						<option value="UPI">UPI</option>
      						<option value="Wallets">Wallets</option>
     				</select>	
     			</td>
     		</tr>
     		<tr>
     			<td><b>Order Status: </b></td>
     			<td>
     				<select name="orderStatus">
     					<option value="Processing">Processing</option>
     					<option value="Shipping">Shipping</option>
     					<option value="Cancelled">Cancelled</option>
     					<option value="Completed">Completed</option>
     				</select>
     			</td>
     		</tr>
     	</table>
     </div>
     <div align="center">
     	<h2>Ordered Books: </h2>
     	<table border="1">
     		<tr>
     			<th>Index</th>
     			<th>Book Title</th>
     			<th>Author</th>
     			<th>Price</th>
     			<th>Quantity</th>
     			<th>Subtotal</th>
     			<th><a href="">Add Books</a></th>
     		</tr>
     		<c:forEach items="${order.orderDetails}" var="orderDetail" varStatus="status">
     			<tr>
	     			<td>${status.index + 1}</td>
	     			<td>
	     				<img style="vertical-align: middle;" src="data:image/jpg;base64,${orderDetail.book.base64Image}" width="48" height="64" />
	     				${orderDetail.book.title}
	     			</td>
	     			<td>${orderDetail.book.author}</td>
	     			<td>&#8377; <fmt:formatNumber value="${orderDetail.book.price}"/></td>
	     			<td><input type="text" name="quantity" value="${orderDetail.quantity}" size="5" /></td>
	     			<td>&#8377; <fmt:formatNumber value="${orderDetail.subtotal}"/></td>
	     	    </tr>
     		</c:forEach>
     		<tr>
     			<td colspan="4" align="right"><i><b>Total: </b></i></td>
     			<td><b>${order.bookCopies}</b></td>
     			<td><b>&#8377; <fmt:formatNumber value="${order.total}"/></b></td>
     		</tr>
     	</table>
     </div>
    <div align="center">
    	<br>
    	    <a href="javascript:showAddBookPopup()"><b>Add Books</b></a>
    	    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    	    <input type="submit" value="Save" />
    	     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    	    <input type="button" value="Cancel" />    
    </div>
    </form>
     <jsp:directive.include file="footer.jsp" />
     
     <script>
     	function showAddBookPopup() {
     		var width = 500;
     		var height = 200;
     		var left = (screen.width - width) / 2;
     		var top = (screen.height - height) / 2;
     		
     		window.open('add_book_form', '_blank', 
					'width=' + width + ', height=' + height + ', top=' + top + ', left=' + left);
     	}
     </script>
</body>
</html>