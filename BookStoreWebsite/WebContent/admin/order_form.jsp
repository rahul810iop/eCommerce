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
      						<option value="Cash On Delivery" <c:if test="${order.paymentMethod eq 'Cash on Delivery' }">selected='selected'</c:if> >Cash on delivery</option>
      						<option value="Credit Card" <c:if test="${order.paymentMethod eq 'Credit Card' }">selected='selected'</c:if> >Credit Card</option>
      						<option value="Debit Card" <c:if test="${order.paymentMethod eq 'Debit Card' }">selected='selected'</c:if> >Debit Card</option>
      						<option value="UPI" <c:if test="${order.paymentMethod eq 'UPI' }">selected='selected'</c:if> >UPI</option> 
      						<option value="Wallets" <c:if test="${order.paymentMethod eq 'Wallets' }">selected='selected'</c:if> >Wallets</option>
     				</select>	
     			</td>
     		</tr>
     		<tr>
     			<td><b>Order Status: </b></td>
     			<td>
     				<select name="orderStatus" >
     					<option value="Processing" <c:if test="${order.status eq 'Processing' }">selected='selected'</c:if> >Processing</option>
						<option value="Shipping" <c:if test="${order.status eq 'Shipping' }">selected='selected'</c:if>>Shipping</option>
						<option value="Delivered" <c:if test="${order.status eq 'Delivered' }">selected='selected'</c:if>>Delivered</option>
						<option value="Completed" <c:if test="${order.status eq 'Completed' }">selected='selected'</c:if>>Completed</option>
						<option value="Cancelled" <c:if test="${order.status eq 'Cancelled' }">selected='selected'</c:if>>Cancelled</option>
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
	     			<td>
	     				<input type="hidden" name="price" value="${orderDetail.book.price}" />
	     				&#8377; <fmt:formatNumber value="${orderDetail.book.price}"/>
	     			</td>
	     			<td>
	     				<input type="hidden" name="bookId" value="${orderDetail.book.bookId}" />
	     				<input type="text" name="quantity${status.index + 1}" value="${orderDetail.quantity}" size="5" />
	     			</td>
	     			<td>&#8377; <fmt:formatNumber value="${orderDetail.subtotal}"/></td>
	     	    	<td><a href="remove_book_from_order?id=${orderDetail.book.bookId}">Remove</a></td>
	     	    </tr>
     		</c:forEach>
     		<tr>
     			<td colspan="4" align="right"><i><b>Total: </b></i></td>
     			<td><b>${order.bookCopies}</b></td>
     			<td colspan="2"><b>&#8377; <fmt:formatNumber value="${order.total}"/></b></td>
     		</tr>
     	</table>
     </div>
    <div align="center">
    	<br>
    	    <a href="javascript:showAddBookPopup()"><b>Add Books</b></a>
    	    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    	    <input type="submit" value="Save" />
    	     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    	    <input type="button" value="Cancel" onClick="javascript:window.location.href='list_order';"/>    
    </div>
    </form>
     <jsp:directive.include file="footer.jsp" />
     
     <script>
     	function showAddBookPopup() {
     		var width = 600;
     		var height = 300;
     		var left = (screen.width - width) / 2;
     		var top = (screen.height - height) / 2;
     		
     		window.open('add_book_form', '_blank', 
					'width=' + width + ', height=' + height + ', top=' + top + ', left=' + left);
     	}
     $(document).ready(function() {	
     	$("#orderForm").validate({
    		rules: {	
    			recipientName: "required",
    			recipientPhone: "required",
    			shippingAddress: "required",
    			
    			<c:forEach items="${order.orderDetails}" var="book" varStatus="status">
					quantity${status.index + 1}: {
						required: true, number: true, min: 1, max: 4
					},
				</c:forEach>
    		},
    		
    		messages: {
    			recipientPhone: "Please enter Recipient Phone",
    			shippingAddress: "Please enter Shipping Address",
    			recipientName: "Please enter Recipient Name",
    			
    			<c:forEach items="${order.orderDetails}" var="book" varStatus="status">
					quantity${status.index + 1}: { 
						required: "Please enter quantity",
						number: "Quantity must be a number",
						min: "Quantity must be greater than 0",
						max: "Quantity must be lesser than 5"
					},
				</c:forEach>
    		}
    	});
     });	
     </script>
</body>
</html>