<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "HelperClasses.*, java.util.*, java.math.*, java.sql.*"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Browse</title>
	</head>
	<style> 
        	body {background-color : #ADD8E6}
	       			text-align: center;}
	       	h1 {text-align : center;}
	       	body {font-family:Arial}
	       	h2 {text-align : right}
	       	
	       	
	</style>
	<body>
		<h2><a href="shoppingCart.jsp">Shopping Cart</a></h2>
	
		<h1>By Title or Genre?</h1>
		<form action="BrowseByTitle" method="post">
			Title:<input type="text" name="title"/>
			<input type="submit" name="by title"/>
		</form>
		
<!--  		<form action="BrowseByGenre" method="post">
			Genre:<input type="text" name="genre"/>
			<input type="submit" name="by genre"/>
		</form>  -->
		
		<form action="BrowseByGenre">
			Genre:<select name="genre">
<%-- 				<%Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb?useSSL=false", "root", "root1234");
 --%>			
 				<% Connection connection = Database.openConnection();
 				String query = "SELECT name FROM genres ORDER BY name";
				Statement statement = connection.createStatement();
				ResultSet result = statement.executeQuery(query);
				while(result.next()){%>
					<option value= <%=result.getString("name") %>> <%=result.getString("name") %> </option>
				<%} %>
			</select>
			<input type="submit" name="genre">
		</form>
	</body>
</html>