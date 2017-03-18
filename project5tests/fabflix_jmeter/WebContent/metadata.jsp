<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "HelperClasses.*, java.util.*, java.math.*, java.sql.*"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Meta Data List</title>
</head>
<style>
        	body {background-color : #ADD8E6}
        	body {font-family:Arial}
</style>
<body>
	<center><a href="dashboard.html">Return to DashBoard</a></center> <br>
	<%
	Connection connection = Database.openConnection();
	
	//Movies
	Statement select = connection.createStatement();
	ResultSet result = select.executeQuery("Select * from movies");
	ResultSetMetaData metadata = result.getMetaData();
	%>
	<b>Movies</b>
	<table border = "1">
		<tr>
			<td>Column</td>
			<td>Column Type</td>
		</tr>
		<%for (int i = 1; i<= metadata.getColumnCount(); i++){%>
		<tr>
			<td><%=metadata.getColumnName(i)%></td>
			<td><%=metadata.getColumnTypeName(i) %></td>
		</tr>
		<%}%>
		
	</table> <br>
	<%
	//stars
	select = connection.createStatement();
	result = select.executeQuery("Select * from stars");
	metadata = result.getMetaData();
	%>
	<b>Stars</b>
	<table border = "1">
		<tr>
			<td>Column</td>
			<td>Column Type</td>
		</tr>
		<%for (int i = 1; i<= metadata.getColumnCount(); i++){%>
		<tr>
			<td><%=metadata.getColumnName(i)%></td>
			<td><%=metadata.getColumnTypeName(i) %></td>
		</tr>
		<%}%>
		
	</table> <br>
	<%
	//StarsinMovies
	select = connection.createStatement();
	result = select.executeQuery("Select * from stars_in_movies");
	metadata = result.getMetaData();
	%>
	<b>Stars in Movies</b>
	<table border = "1">
		<tr>
			<td>Column</td>
			<td>Column Type</td>
		</tr>
		<%for (int i = 1; i<= metadata.getColumnCount(); i++){%>
		<tr>
			<td><%=metadata.getColumnName(i)%></td>
			<td><%=metadata.getColumnTypeName(i) %></td>
		</tr>
		<%}%>
		
	</table> <br>
	<%
	//Genres
	select = connection.createStatement();
	result = select.executeQuery("Select * from genres");
	metadata = result.getMetaData();
	%>
	<b>Genres</b>
	<table border = "1">
		<tr>
			<td>Column</td>
			<td>Column Type</td>
		</tr>
		<%for (int i = 1; i<= metadata.getColumnCount(); i++){%>
		<tr>
			<td><%=metadata.getColumnName(i)%></td>
			<td><%=metadata.getColumnTypeName(i) %></td>
		</tr>
		<%}%>
		
	</table> <br>
	<%
	//Genres in Movies
	select = connection.createStatement();
	result = select.executeQuery("Select * from genres_in_movies");
	metadata = result.getMetaData();
	%>
	<b>Genres in Movies</b>
	<table border = "1">
		<tr>
			<td>Column</td>
			<td>Column Type</td>
		</tr>
		<%for (int i = 1; i<= metadata.getColumnCount(); i++){%>
		<tr>
			<td><%=metadata.getColumnName(i)%></td>
			<td><%=metadata.getColumnTypeName(i) %></td>
		</tr>
		<%}%>
		
	</table> <br>
	<%
	//Creditcards
	select = connection.createStatement();
	result = select.executeQuery("Select * from creditcards");
	metadata = result.getMetaData();
	%>
	<b>Credit Cards</b>
	<table border = "1">
		<tr>
			<td>Column</td>
			<td>Column Type</td>
		</tr>
		<%for (int i = 1; i<= metadata.getColumnCount(); i++){%>
		<tr>
			<td><%=metadata.getColumnName(i)%></td>
			<td><%=metadata.getColumnTypeName(i) %></td>
		</tr>
		<%}%>
		
	</table> <br>
	<%
	//StarsinMovies
	select = connection.createStatement();
	result = select.executeQuery("Select * from customers");
	metadata = result.getMetaData();
	%>
	<b>Customers</b>
	<table border = "1">
		<tr>
			<td>Column</td>
			<td>Column Type</td>
		</tr>
		<%for (int i = 1; i<= metadata.getColumnCount(); i++){%>
		<tr>
			<td><%=metadata.getColumnName(i)%></td>
			<td><%=metadata.getColumnTypeName(i) %></td>
		</tr>
		<%}%>
		
	</table> <br>
	<%
	//StarsinMovies
	select = connection.createStatement();
	result = select.executeQuery("Select * from sales");
	metadata = result.getMetaData();
	%>
	<b>sales</b>
	<table border = "1">
		<tr>
			<td>Column</td>
			<td>Column Type</td>
		</tr>
		<%for (int i = 1; i<= metadata.getColumnCount(); i++){%>
		<tr>
			<td><%=metadata.getColumnName(i)%></td>
			<td><%=metadata.getColumnTypeName(i) %></td>
		</tr>
		<%}%>
		
	</table> <br>
</body>
</html>