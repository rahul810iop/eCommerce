<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
    <%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>My Orders - Bookstall</title>
<link rel="icon" type="image/jpg" href="images/book.jpg">
<link rel="stylesheet" href="css/style.css">
<script type="text/javascript" src="../js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
</head>
<body>
     <jsp:directive.include file="header.jsp" />
     
     <div align="center">
          <h2 class="pageheading">My Orders History</h2>
     </div>
     
	     <c:if test="${fn:length(listOrders) == 0}">
	     	 <div align="center">
	     		<h2>You have not placed any orders yet.</h2>
	     	</div>
	     </c:if>
     
     <c:if test="${fn:length(listOrders) > 0}">
     <div align="center">
         <table border="1" cellpadding="5">
            <tr>
                <th>Index</th>
                <th>Order ID</th>        
                <th>Quantity</th>
                <th>Total Amount</th>
                <th>Order Date</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>
            <c:forEach var="order" items="${listOrders}" varStatus="status">
            <tr>
                 <td>${status.index + 1}</td>  
                 <td>${order.orderId}</td>   
                 <td>${order.bookCopies}</td>
                 <td>&#8377; <fmt:formatNumber value="${order.total}" /></td>
                 <td>${order.orderDate}</td>
                 <td>${order.status}</td>
                 <td>
                 	<a href="show_order_detail?id=${order.orderId}">View Details</a>
                 </td>
            </tr>
            </c:forEach>
         </table>
     </div>
    </c:if>
    
     <jsp:directive.include file="footer.jsp" />

</body>
</html>