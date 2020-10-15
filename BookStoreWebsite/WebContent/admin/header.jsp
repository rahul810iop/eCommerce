<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<div align="center">
  <div>
      <a href="${pageContext.request.contextPath}/admin/">
           <img src="../images/logo_admin_final.png" width="800" height="200"/>     
      </a>
  </div>
  <br>
  <div>
         Welcome, <c:out value="${sessionScope.useremail}" /> | <a href="logout">Logout</a>
         <br><br>
  </div>
  <div id="headermenu">
     <div class="menu_item">
         <a href="list_users">
            <img src="../images/users.png" width="50" height="50" /><br/>Users
         </a>
      </div>
      
     <div class="menu_item">
         <a href="list_category">
            <img src="../images/category.png" width="50" height="50" /><br/>Categories
         </a>
     </div>
     
     <div>
         <a href="list_books">
            <img src="../images/books.png" width="50" height="50" /><br/>Books
         </a>
     </div>
     
     <div>
         <a href="list_customer">
            <img src="../images/customers.png" width="50" height="50" /><br/>Customers
         </a>
     </div>

     <div>
         <a href="list_review">
            <img src="../images/reviews_2.png" width="50" height="50" /><br/>Reviews
         </a>
     </div>
     
     <div>
         <a href="list_order">
            <img src="../images/orders.png" width="50" height="50" /><br/>Orders
         </a>
     </div>  
          
  </div>  