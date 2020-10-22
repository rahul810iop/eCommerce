<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Bookstall - Online Books Store</title>
<link rel="icon" type="image/jpg" href="images/book.jpg">
<link rel="stylesheet" href="css/style.css" >
</head>
<body>
    <jsp:directive.include file="header.jsp" />

     <div align="center">
           <br></br>
           
           <div align="center" style="width: 80%; margin: 0 auto;">
               <h2 class="pageheading">New Books</h2>
              <c:forEach items="${listNewBooks}" var="book">
					<div style="display: inline-block;margin: 10px">
                 <div>
                     <a href="view_book?id=${book.bookId}">
                       <img src="data:image/jpg;base64,${book.base64Image}" width="128" height="164"/>
                     </a>
                 </div>
               <a href="view_book?id=${book.bookId}">
                  <div><b>${book.title}</b></div>
               </a>   
                  <div>
                  		<jsp:directive.include file="book_rating.jsp" />	
                  </div>
                  
                  <div>
                  		<i>by ${book.author}</i>
                  </div>
                  <div><b>&#8377; ${book.price}</b></div>
             </div>
					
          	  </c:forEach>
     </div>
           
        <div class="next-row">   
           <h2 class="pageheading">Best-Selling Books</h2>
           <c:forEach items="${listBestSellingBooks}" var="book">
              <div style="display: inline-block;margin: 10px">
                 <div>
                     <a href="view_book?id=${book.bookId}">
                       <img src="data:image/jpg;base64,${book.base64Image}" width="128" height="164"/>
                     </a>
                 </div>
               <a href="view_book?id=${book.bookId}">
                  <div><b>${book.title}</b></div>
               </a>   
                  <div>
                  		<jsp:directive.include file="book_rating.jsp" />	
                  </div>
                  
                  <div>
                  		<i>by ${book.author}</i>
                  </div>
                  <div><b>&#8377; ${book.price}</b></div>
             </div>
              
          </c:forEach>
        </div>
        
        <div class="next-row">   
           <h2>Most-favorite Books</h2>
           
           <br></br>
        </div>   
     </div>
     
     <jsp:directive.include file="footer.jsp" />
     </body>
</html>