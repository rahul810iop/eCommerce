<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
   <meta charset="ISO-8859-1">
   <title>Forgot Password</title>
   <link rel="icon" type="image/jpg" href="images/book.jpg">
   <link rel="stylesheet" href="css/style.css">
   <script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
   <script type="text/javascript" src="js/jquery.validate.min.js"></script>
</head>
<body>
	
	<jsp:directive.include file="header.jsp" />

    <div align="center">
        
        <h2 class="pageheading">Forgot Password</h2>

       <c:if test="${message != null }">
         <div align="center">
             <h4 class="message">${message}</h4>
         </div>
      </c:if>
      
        <form id="forgotForm" action="send_password" method="post">
           <table>
               <tr>
                   <td>Email:</td>
                   <td><input type="text" name="email" id="email" size="20"></td>
               </tr>
                <tr>
				<td colspan="2" align="center">
					
					<button type="submit">Send me a password</button>
				</td>
			</tr>
           </table>
        </form>
        <br>
    </div>
    
    <jsp:directive.include file="footer.jsp" />
    
<script type="text/javascript">

$(document).ready(function() {
	$("#forgotForm").validate({
		rules: {
			email: {
				required: true,
				email: true
			},
		},
		
		messages: {
			email: {
				required: "Please enter email",
				email: "Please enter an valid email address"
			},
		}
	});
});
</script>
</body>
</html>