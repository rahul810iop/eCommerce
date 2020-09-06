<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Internal Server Error</title>
    <link rel="icon" type="image/jpg" href="../images/book.jpg">
</head>
<body>
     <div align="center">
       <div>
           <img src="${pageContext.request.contextPath}/images/logo_3.jpg" width="600" height="150" />   
       </div>
       <div>
           <h2>500 Error</h2>
           <br>
           <h2>Sorry! the server has encountered an error while fulfilling your request.</h2>
           <h3>Please come back again later or contact our administrators.</h3>
       </div>
       <div>
           <a href="javascript:history.go(-1);">Go Back</a>
       </div>
     </div>
</body>
</html>