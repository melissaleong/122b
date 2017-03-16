<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "HelperClasses.*, java.util.*, javax.naming.InitialContext, javax.naming.Context, javax.sql.DataSource"  %>
<%@ page import = "java.sql.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Star Info</title>
<style>
        	body {background-color : #ADD8E6}
        	body {font-family:Arial}
        	h3 {text-align:right}
</style>
</head>
<body>
	<h3><a href="shoppingCart.jsp">Shopping Cart</a></h3>

	<% String fn = request.getParameter("fn");
	String ln = request.getParameter("ln");
	String id = request.getParameter("id");
	String photo_url = request.getParameter("photo_url");
	String dob = request.getParameter("dob");
	String actor_name = fn + " " + ln;%>
	<h2><font size="5">STAR INFO</font></h2>
	<p>Star ID: <%=id %></p>
	<p>Star Name: <%=fn%> <%=ln %></p>
	<p>Date of Birth: <%=dob%></p>
	<p><a href = "<%=photo_url%>"> photos of actor </a></p>
	
	<% /* Connection connection = DriverManager.getConnection("jdbc:mysql:///moviedb?autoReconnect=true&useSSL=false", "root", "root1234"); */
	/* Connection connection = Database.openConnection(); */
	Context initCtx = new InitialContext();
	Context envCtx = (Context) initCtx.lookup("java:comp/env");
	DataSource ds = (DataSource) envCtx.lookup("jdbc/moviedb");
	
	Connection connection = ds.getConnection();
	Star star = new Star();
	String query = "SELECT DISTINCT * FROM movies m LEFT OUTER JOIN stars_in_movies s ON movie_id=m.id  WHERE star_id=?";
	
	PreparedStatement statement = connection.prepareStatement(query);
	statement.setString(1, id);
	ResultSet result = statement.executeQuery(); %>
		
	<h1><font size="5">MOVIES WITH STAR</font></h1>
	<% while(result.next()) { %>
 		<p> <a href="movieinfo.jsp?id=<%=result.getString("id")%>&title=<%= result.getString("title")%>"><%= result.getString("title") %> </a></p>
	<% } %>
	<br>
	<br>
	<br>
	<p><a href="mainpage.html">Back to Search/Browse</a></p>
	<p> <a href="movielist.jsp"></a></p>
</body>
</html>