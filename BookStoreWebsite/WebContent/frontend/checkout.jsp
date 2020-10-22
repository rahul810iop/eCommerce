<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
	<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
   <meta charset="ISO-8859-1">
   <title>Checkout - Online Book Store</title>
   <link rel="icon" type="image/jpg" href="images/book.jpg">
   <link rel="stylesheet" href="css/style.css">
   <script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
   <script type="text/javascript" src="js/jquery.validate.min.js"></script>
</head>
<body>
	
	<jsp:directive.include file="header.jsp" />

    <div align="center">

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
      			<h2>Review Your Order &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="view_cart">Edit Order</a></h2>
      				<table border="1">
      					<tr>
      						<th>No</th>
      						<th colspan="2">Book</th>
      						<th>Author</th>
      						<th>Price</th>
      						<th>Quantity</th>
      						<th>Subtotal</th>
      					</tr>	
      						<c:forEach items="${cart.items}" var="item" varStatus="status">
      							<tr>
      								<td>${status.index + 1}</td>
      								<td>
      									<img src="data:image/jpg;base64,${item.key.base64Image}" width="84" height="110"/>
      								</td>
      								<td><b>${item.key.title}</b></td>
      								<td>${item.key.author}</td>
      								<td>&#8377;<fmt:formatNumber value="${item.key.price}" /></td>
      								<td>
      									<input type="text" name="quantity${status.index + 1}" value="${item.value}" size="5" readonly="readonly"/>
      								</td>
      								<td>&#8377;<fmt:formatNumber value="${item.value * item.key.price}"/></td>
      							</tr>
      						</c:forEach>
      						
      						<tr>
      							<td></td>
      							<td></td>
      							<td><b>${cart.totalQuantity} book(s)</b></td>
      							<td colspan="2" align="right"><i><b>Total:</b></i></td>
      							<td colspan="2"><b>&#8377;<fmt:formatNumber value="${cart.totalAmount}" /></b></td>
      						</tr>
      				</table>
      			<h2>Your Shipping Information</h2>
      			<form id="orderForm" action="place_order" method="post">
      				<table>
      					<tr>
      						<td>Recipient Name:</td>
      						<td><input type="text" name="recipientName" value="${loggedCustomer.fullname}" /></td>
      					</tr>
      					<tr>
      						<td>Recipient Phone:</td>
      						<td><input type="text" name="recipientPhone" value="${loggedCustomer.phone}" /></td>
      					</tr>
      					<tr>
      						<td>Locality:</td>
      						<td><input type="text" name="address" value="${loggedCustomer.address}" /></td>
      					</tr>
      					<tr>
      						<td>City:</td>
      						<td><input type="text" name="city" value="${loggedCustomer.city}" /></td>
      					</tr>
      					<tr>
      						<td>Zipcode:</td>
      						<td><input type="text" name="zipcode" value="${loggedCustomer.zipcode}" /></td>
      					</tr>
      					<tr>
      						<td>Country:</td>
      						<td><input type="text" name="country" value="${loggedCustomer.country}" /></td>
      					</tr>
      				</table>
      				<div>
      					<h2>Payment</h2>
      					Choose your Payment method:
      					&nbsp;&nbsp;&nbsp;
      					<select name="paymentMethod">
      						<option value="Cash on Delivery">Cash on Delivery</option>
      						<option value="Credit Card">Credit Card</option>
      						<option value="Debit Card">Debit Card</option>
      						<option value="UPI">UPI</option>
      						<option value="Wallets">Wallets</option>
      					</select>
      				</div>
      				<div>
      				<table class="normal">
      					<tr><td>&nbsp;</td></tr>
      					<tr>
      						<td><button type="submit">Place Order</button>
      						<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
      						<td><a href="${pageContext.request.contextPath}/" >Continue Shopping</a></td>
      					</tr>
      				</table>
      			</div>
      			</form>
      		</div>	
      </c:if>
      
    </div>
    
    <jsp:directive.include file="footer.jsp" />
    
<script type="text/javascript">

$(document).ready(function() {
	$("#orderForm").validate({
		rules: {
			recipientName: "required",
			recipientPhone: "required",
			address: "required",
			city: "required",
			zipcode: "required",
			country: "required"
		},
		messages: {
			recipientName: "Please provide recipient name",
			recipientPhone: "Please provide recipient phone",
			address: "Please provide delivery address",
			city: "Please enter city",
			zipcode: "Please enter zipcode",
			country: "Please enter Country"
		}
	})
});
</script>
</body>
</html>