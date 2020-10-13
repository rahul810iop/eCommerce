<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
	<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
   <meta charset="ISO-8859-1">
   <title>Your Shopping Cart</title>
   <link rel="icon" type="image/jpg" href="images/book.jpg">
   <link rel="stylesheet" href="css/style.css">
   <script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
   <script type="text/javascript" src="js/jquery.validate.min.js"></script>
</head>
<body>
	
	<jsp:directive.include file="header.jsp" />

    <div align="center">
        
        <h2>Your Shopping Cart Details</h2>

       <c:if test="${message != null }">
         <div align="center">
             <h4 class="message">${message}</h4>
         </div>
      </c:if>
      
      <c:set var="cart" value="${sessionScope['cart']}"/>
      		
      <c:if test="${cart.totalItems == 0}">
      		<h3>There's no items in your cart</h3>
      </c:if>				
      <c:if test="${cart.totalItems > 0}">
      		
      			<form action="update_cart" method="post" id="cartForm">
      			<div>
      				<table border="1">
      					<tr>
      						<th>No</th>
      						<th colspan="2">Book</th>
      						<th>Quantity</th>
      						<th>Price</th>
      						<th>Subtotal</th>
      						<th>
      							<a href="clear_cart" style="color:#d0d0d0"><b>Clear Cart</b></a>
      						</th>
      						<c:forEach items="${cart.items}" var="item" varStatus="status">
      							<tr>
      								<td>${status.index + 1}</td>
      								<td>
      									<img src="data:image/jpg;base64,${item.key.base64Image}" width="84" height="110"/>
      								</td>
      								<td>
      									<b>${item.key.title}</b>
      								</td>
      								<td>
      									<input type="hidden" name="bookId" value="${item.key.bookId}" />
      									<input type="text" name="quantity${status.index + 1}" value="${item.value}" size="5" />
      								</td>
      								<td>&#8377;<fmt:formatNumber value="${item.key.price}" /></td>
      								<td>&#8377;<fmt:formatNumber value="${item.value * item.key.price}"/></td>
      								<td>
      									<a href="remove_from_cart?book_id=${item.key.bookId}">Remove</a>
      								</td>
      							</tr>
      						</c:forEach>
      						
      						<tr>
      							<td></td>
      							<td></td>
      							<td><b>${cart.totalQuantity} book(s)</b></td>
      							<td colspan="2">Total:</td>
      							<td colspan="2"><b>&#8377;<fmt:formatNumber value="${cart.totalAmount}" /></b></td>
      						</tr>
      				</table>
      			
      			<div>
      				<table class="normal">
      					<tr><td>&nbsp;</td></tr>
      					<tr>
      						<td><button type="submit">Update</button></td> 
      						<td><button id="clearCart">Clear Cart</button>
      						<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
      						<td><a href="${pageContext.request.contextPath}/" >Continue Shopping</a></td>
      						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>	
      						<td><a href="checkout">Checkout</a></td>
      					</tr>
      				</table>
      			</div>
      		</div>	
      		</form>
      </c:if>
      
    </div>
    
    <jsp:directive.include file="footer.jsp" />
    
<script type="text/javascript">

$(document).ready(function() {
	$("#cartForm").validate({
		rules : {
			<c:forEach items="${cart.items}" var="item" varStatus="status">
				quantity${status.index + 1}: {
					required: true, number: true, min: 1, max: 4
				},
			</c:forEach>
		},

		messages : {
			<c:forEach items="${cart.items}" var="item" varStatus="status">
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