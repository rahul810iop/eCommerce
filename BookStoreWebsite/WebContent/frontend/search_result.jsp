<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Results for ${keyword} - Online Books Store</title>
<link rel="icon" type="image/jpg" href="images/book.jpg">
<link rel="stylesheet" href="css/style.css" >
</head>
<body>
    <jsp:directive.include file="header.jsp" />

     <div align="center">
           <c:if test="${fn:length(result) == 0}">
			<center><h2>No Results found for "${keyword}"</h2></center>
		</c:if>
		<c:if test="${fn:length(result) > 0}">
				
           
           <div align="left" style="width: 80%; margin: 0 auto;">

              <center><h2>${fn:length(result)} Results found for "${keyword}":</h2></center>
			  <c:forEach items="${result}" var="book">
		

               <div>
                  <div style="display: inline-block; margin: 20px width: 10%">
                     <div align="left">
                        <a href="view_book?id=${book.bookId}">
                             <img src="data:image/jpg;base64,${book.base64Image}" width="128" height="164"/>
                        </a>
                     </div>
                  </div>
                  <div style="display: inline-block; margin: 20px; vertical-align: top; width: 60%" align="left">
                     <div>
                         <h2><a href="view_book?id=${book.bookId}"><b>${book.title}</b></a></h2>
                     </div>   
                     <div>
                     	 <jsp:directive.include file="book_rating.jsp" />
                     </div>
                     <div>
                         <i>by ${book.author}</i>
                     </div>
                     <div>
						 <p>${fn:substring(book.description, 0, 100)}...</p>
				     </div>
                  </div>
                  <div style="display: inline-block; margin: 20px ; vertical-align: top;">
                      <b>&#8377; ${book.price}</b>
                      <h3><a href="add_to_cart?book_id=${book.bookId}">Add To Cart</a></h3>
                  </div>
             </div>
          </c:forEach>
     </div>
           
           </c:if>
     </div>
     
     <jsp:directive.include file="footer.jsp" />
     </body>
</html>