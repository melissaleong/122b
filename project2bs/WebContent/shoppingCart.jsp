<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import = "HelperClasses.*, java.util.*"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Shopping Cart</title>
<style> 

    body {background-color : #ADD8E6}
    body {font-family:Arial}
	h4 {font-size: 200%;}	
	td {border: 1px solid black}
	h1 {text-align: center}
</style>
</head>
<body>
	<h1><b>Shopping Cart</b></h1>
	<table width="100%">
		<tr>
			<td><b>Movie</b></td>
			<td><b>Quantity</b></td>
		</tr>
		
		<% 
		cartSession cart_session= (cartSession)request.getSession().getAttribute("cart_session");
		ArrayList<Cart> cartItems= cart_session.returnitems();
		int size= cartItems.size();
		for(int i=0 ; i<size;++i){%>
		<tr>
			
			<td><%=cartItems.get(i).getMovie().getTitle()%></td>
			<td><%=cartItems.get(i).getQuantity()%></td>
			<td>
 				<form method="POST" action="CartPages">
 					<input type="text" name="quantity" />
					<input type="hidden" name="id" value=<%=cartItems.get(i).getMovie().getId()%>/>
 					<button name="request" value="update_item_quantity" type="submit" >Update Quantity</button>
 				</form>
				
 			</td>
 		</tr>
				<%}%>
				<tr>
				<td><a href="customerinfo.html"><button>Checkout</button></a></td>
				<td>
					<form method="POST" action="CartPages">
						<button name="request" value="remove_all_items" type="submit">Clear Cart</button>
					</form>
				</td>
			</tr>
		</table>
		<a href="mainpage.html">Return to Browse/Search</a>
</body>
</html>