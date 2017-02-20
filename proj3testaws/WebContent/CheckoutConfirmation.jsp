<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import = "HelperClasses.*, java.util.*"  %>
     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
        	body {background-color : #ADD8E6}
        	body {font-family:Arial}
        	h2 {text-align : right}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CheckoutConfirmation</title>
</head>
<Body BGCOLOR = "#F1948A"></Body>
<h2><a href="shoppingCart.jsp">Shopping Cart</a></h2>
<div id="main_content">
<%
	if ((Customer) request.getSession().getAttribute("valid_customer") == null)
	{
		response.sendRedirect("index.html");
	}
%>
<p><a href="mainpage.html">Return to Browse/Search</a></p>
<h3 style="position: absolute; top:150px; left:150px; ">Checkout complete</h3>


</body>
</html>