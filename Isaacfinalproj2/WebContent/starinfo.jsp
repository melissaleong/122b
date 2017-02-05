<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "HelperClasses.*, java.util.*"  %>
<%@ page import = "java.sql.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Star Info</title>
</head>
<body>
	<% String fn = request.getParameter("fn");
	String ln = request.getParameter("ln");
	String id = request.getParameter("id");
	String photo_url = request.getParameter("photo_url");
	String dob = request.getParameter("dob");
	
	String actor_name = fn + " " + ln;%>
	<p><%=id %>
	<p><%=fn%> <%=ln %></p>
	<p>DoB = <%=dob%></p>
	<p><a href = "<%=photo_url%>"> photos of actor </a></p>
	
	<% Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb?useSSL=false", "isinger", "pi3zza");
	Star star = new Star();
	Statement statement = connection.createStatement();
	String query = "SELECT DISTINCT * FROM movies m LEFT OUTER JOIN stars_in_movies s ON movie_id=m.id  WHERE star_id=" + id + "";
	ResultSet result = statement.executeQuery(query); %>
		
	<h1>MOVIES WITH STAR</h1>
	<% while(result.next()) { %>
 		<p> <a href="movieinfo.jsp?id=<%=result.getString("id")%>&title=<%= result.getString("title")%>"><%= result.getString("title") %> </a></p>
	<% } %>
	
	<h2>Back to MovieList</h2>
	<p> <a href="movielist.jsp"></a></p>
</body>
</html>