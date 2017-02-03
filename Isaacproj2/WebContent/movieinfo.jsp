<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "HelperClasses.*, java.util.*"  %>
<%@ page import = "java.sql.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>movie info</title>
</head>
<body>
	<h1> <%= request.getParameter("title")%></h1>
	<h2> STARS IN MOVIE</h2>
	<% Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb?useSSL=false", "root", "root");
	Star star = new Star();
	Statement statement = connection.createStatement();
	String query = "SELECT DISTINCT * FROM stars s LEFT OUTER JOIN stars_in_movies sm ON sm.star_id=s.id where sm.movie_id = " + request.getParameter("id");
	ResultSet result = statement.executeQuery(query);
	while (result.next()){
		Statement inner = connection.createStatement();
		String query1 = "SELECT * FROM stars WHERE id=" + result.getString("star_id");
		ResultSet star_info = inner.executeQuery(query1);
		star_info.next();
		String id = star_info.getString("id");
		String first_name = star_info.getString("first_name");
		String last_name = star_info.getString("last_name");
		String dob = star_info.getString("dob");
		String photo_url = star_info.getString("photo_url"); %>
		
	<p> <a href="starinfo.jsp?fn=<%=first_name%>&ln=<%=last_name%>&id=<%=id%>&photo_url=<%=photo_url%>&dob=<%=dob%>"><%= first_name %> <%= last_name %> </a></p>	
	<%}%>
	<h4>GENRES</h4>
	<% String g_query = "SELECT DISTINCT * FROM genres g LEFT OUTER JOIN genres_in_movies gm ON g.id=gm.genre_id where gm.movie_id = " + request.getParameter("id");
	ResultSet genres = statement.executeQuery(g_query);
	while(genres.next()){%>
		<p><%= genres.getString("name") %></p>
	<%}%>
	<h3>MORE INFO</h3>
	
	<% String query2 = "SELECT * FROM movies WHERE id = " + request.getParameter("id"); 
	ResultSet rs = statement.executeQuery(query2); 
	rs.next();%>
	
	<p> <%= rs.getString("id") %> </p>
	<p> <%= rs.getString("director") %> </p>
	<p> <%= rs.getString("banner_url") %> </p>
	<p> <%= rs.getString("trailer_url") %> </p>
	
	
</body>
</html>