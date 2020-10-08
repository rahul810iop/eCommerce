<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>	
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>${book.title}- Online Books Store</title>
<link rel="icon" type="image/jpg" href="images/book.jpg"><link rel="icon" type="image/jpg" href="images/book.jpg">    
<link rel="stylesheet" href="css/style.css">
<script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
</head>
<body>
	<jsp:directive.include file="header.jsp" />

	<div align="center">
		<table align="center" style="width:80%", margin="0">
			<tr>
				<td colspan="3" align="left">
					<p id="book-title"><h2>${book.title}</h2></p>
					 by <span id="author">${book.author}</span>
				</td>
			</tr>
			<tr>
				<td rowspan="2">
					<img class="book-large" src="data:image/jpg;base64,${book.base64Image}" width="240" height="300"/>
				</td>
				<td valign="top" align="left">
					<jsp:directive.include file="book_rating.jsp" />
					<a href="#reviews"> ${fn:length(book.reviews)} Reviews</a>
				</td>
				<td valign="top" rowspan="2" width="20%">
					<h2>&#8377; ${book.price}</h2>
					<br/><br/>
					<button id="buttonAddToCart">Add to Cart</button>
				</td>
			</tr>
			<tr>
				<td valign="top" style="text-align: justify">
					${book.description}
				</td>
			</tr>
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td><h2><a id="reviews">Customer Reviews</a></h2></td>
				<td colspan="2" align="center">
				    <button>Write a Customer Review</button>
				</td>
			</tr>
			<tr>
				<td colspan="3">
					<table class="normal">
						<c:forEach items="${book.reviews}" var="review">
							<tr>
								<td>
							       <c:forTokens items="${review.stars}" delims="," var="star">
									     <c:if test="${star eq 'on'}">
									          <img src="images/rating_on.png"/>
									     </c:if>
									     <c:if test="${star eq 'off'}">
									           <img src="images/rating_off.png"/>
									     </c:if>
									</c:forTokens>
									- <b>${review.headline}</b>
								</td>
							</tr>
							<tr>
								<td>
								    by ${review.customer.fullname} on ${review.reviewTime}
								</td>
							</tr>
							<tr>
								<td><i>${review.comment}</i></td>
							</tr>
							<tr>
								<td>&nbsp;</td>
							</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
		</table>


	</div>

	<jsp:directive.include file="footer.jsp" />
	
</body>
</html>