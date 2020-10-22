<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
   <meta charset="ISO-8859-1">
   <title>Bookstall Administration</title>
   <link rel="icon" type="image/jpg" href="../images/book.jpg">
   <link rel="stylesheet" href="../css/style.css">
</head>
<body>
     <jsp:directive.include file="header.jsp" />
     
     <div align="center">
          <h2>Administrative Dashboard</h2>
     </div>
     <div align="center">
     <hr width="60%"/>
         <h2 class="pageheading">Quick Actions:</h2> 
          <b>
          <a href="new_book">New Books</a>&nbsp;&nbsp;&nbsp;
          <a href="user_form.jsp">New User</a>&nbsp;&nbsp;&nbsp;
          <a href="category_form.jsp">New Category</a>&nbsp;&nbsp;&nbsp;
          <a href="customer_form.jsp">New Customer</a>
          </b>
     </div>
     
     <div align="center">
          <hr width="60%"/>
          <h2 class="pageheading">Recent Sales</h2>
          <table border="1">
          		<tr>
	          		<th>Order ID</th>
	          		<th>Ordered by</th>
	          		<th>Book Copies</th>
	          		<th>Total</th>
	          		<th>Payment Method</th>
	          		<th>Status</th>
	          		<th>Order Date</th>
          		</tr>
          		<c:forEach var="order" items="${listMostRecentSales}" varStatus="status">
            <tr>  
                 <td><a href="view_order?id=${order.orderId}">${order.orderId}</a></td>   
                 <td>${order.customer.fullname}</td>   
                 <td>${order.bookCopies}</td>
                 <td>&#8377; <fmt:formatNumber value="${order.total}" /></td>
                 <td>${order.paymentMethod}</td>
                 <td>${order.status}</td>
                 <td>${order.orderDate}</td>
            </tr>
            </c:forEach>
          </table>
     </div>
     
     <div align="center">
          <hr width="60%"/>
          <h2 class="pageheading">Recent Reviews</h2>
          <table border="1">
          	<tr>
          		<th>Book</th>
          		<th>Rating</th>
          		<th>Headline</th>
          		<th>Customer</th>
          		<th>Review On</th>
          	</tr>
          	<c:forEach var="review" items="${listMostRecentReviews}" varStatus="status">
            <tr>  
                 <td>${review.book.title}</td>   
                 <td>${review.rating}</td>   
                 <td><a href="edit_review?id=${review.reviewId}">${review.headline}</a></td>
                 <td>${review.customer.fullname}</td>
                 <td>${review.reviewTime}</td>
            </tr>
            </c:forEach>
          </table>
     </div>
     
     <div align="center">
          <hr width="60%"/>
          <h2 class="pageheading">Statistics</h2>
     </div>
     
     <jsp:directive.include file="footer.jsp" />
</body>
</html>