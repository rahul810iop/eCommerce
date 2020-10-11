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
      		<div>
      			<form>
      				<table border="1">
      					<tr>
      						<th>No</th>
      						<th colspan="2">Book</th>
      						<th>Quantity</th>
      						<th>Price</th>
      						<th>Subtotal</th>
      						<th>
      							<a href=""><b>Clear Cart</b></a>
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
      								<td>${item.value}</td>
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
      							<td colspan="2">&#8377;<fmt:formatNumber value="${cart.totalAmount}" /></td>
      						</tr>
      				</table>
      			</form>
      		</div>
      </c:if>
      
    </div>
    
    <jsp:directive.include file="footer.jsp" />
    
<script type="text/javascript">

$(document).ready(function() {
	$("#loginForm").validate({
		rules: {
			email: {
				required: true,
				email: true
			},
			password: "required",
		},
		
		messages: {
			email: {
				required: "Please enter email",
				email: "Please enter an valid email address"
			},
			password: "Please enter password"
		}
	});
});
</script>
</body>
</html>