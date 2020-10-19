<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>My Orders Details - Bookstall</title>
<link rel="icon" type="image/jpg" href="images/book.jpg">
<link rel="stylesheet" href="css/style.css">
<script type="text/javascript" src="../js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
</head>
<body>
     <jsp:directive.include file="header.jsp" />
     
     <c:if test="${order == null}">
     	<div align="center">
     		<h2 class="pageheading">Sorry, You are not authorized to view this</h2>
     	</div>
     </c:if>
     <c:if test="${order != null}">
     <div align="center">
          <h2 class="pageheading">Your Order ID: ${order.orderId}</h2>
     </div>
     
     <div align="center">
     	<h2>Order Overview</h2>
     	<table>
     		<tr>
     			<td><b>Order Status: </b></td>
		     	<td>
		   			${order.status}
	 			</td>
     		</tr>
     		<tr>
     			<td><b>Order Date: </b></td>
     			<td>${order.orderDate}</td>
     		</tr>
     		<tr>
     			<td><b>Quantity: </b></td>
     			<td>${order.bookCopies}</td>
     		</tr>
     		<tr>
     			<td><b>Total Amount: </b></td>
     			<td>&#8377; <fmt:formatNumber value="${order.total}"/></td>
     		</tr>
     		<tr>
     			<td><b>Recipient Name: </b></td>
     			<td>${order.recipientName}</td>
     		</tr>
     		<tr>
     			<td><b>Recipient Phone: </b></td>
     			<td>${order.recipientPhone}</td>
     		</tr>
     		<tr>
     			<td><b>Ship to: </b></td>
     			<td>${order.shippingAddress}</td>
     		</tr>
     		<tr>
     			<td><b>Payment Method: </b></td>
     			<td>${order.paymentMethod}</td>
     		</tr>
     	</table>
     </div>
     <div align="center">
     	<h2>Ordered Books: </h2>
     	<table border="1">
     		<tr>
     			<th>No</th>
     			<th>Book</th>
     			<th>Author</th>
     			<th>Price</th>
     			<th>Quantity</th>
     			<th>Subtotal</th>
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
	     			<td>${orderDetail.quantity}</td>
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
    </c:if>
    
    <c:if test="${order.status eq 'Processing' || order.status eq 'Shipping'}">
	    <div align="center">
	    	<br/>
	    	<a href="edit_frontend_order?id=${order.orderId}">Update Order</a>
	    	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    	<a href="cancel_order">Cancel Order</a>
	    </div>
    </c:if>
    
     <jsp:directive.include file="footer.jsp" />
     
</body>
</html>