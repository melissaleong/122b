<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "HelperClasses.*, java.util.*, javax.naming.InitialContext, javax.naming.Context, javax.sql.DataSource"  %> 
<%@ page import = "java.sql.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>movie info</title>
<style>
        	body {background-color : #ADD8E6}
        	body {font-family:Arial}
        	h1 {text-align : right}
</style>

</head>
<body>
	<h1><a href="shoppingCart.jsp">Shopping Cart</a> </h1>
	<h2> <font size="7"><u><%= request.getParameter("title")%></u></font></h2>
	<h3> <font size="5">STARS IN MOVIE</font></h3>
<%-- 	<% Connection connection = DriverManager.getConnection("jdbc:mysql:///moviedb?autoReconnect=true&useSSL=false", "root", "root1234");
 --%>
 	<% Context initCtx = new InitialContext();
		Context envCtx = (Context) initCtx.lookup("java:comp/env");
		DataSource ds = (DataSource) envCtx.lookup("jdbc/moviedb");
	
		Connection connection = ds.getConnection();
	
 	Star star = new Star();

	String query = "SELECT DISTINCT * FROM stars s LEFT OUTER JOIN stars_in_movies sm ON sm.star_id=s.id WHERE sm.movie_id=?";
	PreparedStatement statement = connection.prepareStatement(query);
	statement.setString(1, request.getParameter("id"));
	ResultSet result = statement.executeQuery();
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
	<h4><font size="5">GENRES</font></h4>
	<% 
	String g_query = "SELECT DISTINCT * FROM genres g LEFT OUTER JOIN genres_in_movies gm ON g.id=gm.genre_id where gm.movie_id =?";
	PreparedStatement statement1 = connection.prepareStatement(g_query);
	statement1.setString(1, request.getParameter("id"));
	ResultSet genres = statement1.executeQuery();
	List<String> genre_list = new ArrayList<String>();
	while(genres.next()){
		genre_list.add(genres.getString("name"));%>
<%-- 		<p><%= genres.getString("name") %></p>
 --%>	<%}%>
	<%for (String g : genre_list) {%>
		<a href="BrowseByGenre?genre=<%=g %>"> <%=g%> </a><br><br>
	<%}%>
	<h5><font size="5">MORE INFO</font></h5>
	
	<% String query2 = "SELECT * FROM movies WHERE id = ?"; 
	String stuff = request.getParameter("id");
	PreparedStatement statement2 = connection.prepareStatement(query2);
	statement2.setString(1, stuff);
	ResultSet rs = statement2.executeQuery(); 
	rs.next();%>
	
	<p> Movie ID: <%= rs.getString("id") %> </p>
	<p> Director: <%= rs.getString("director") %> </p>
<%-- 	<p> <%= rs.getString("banner_url") %> </p>
 --%>	<img src="<%=rs.getString("banner_url") %>">
 	<p> <a href="<%= rs.getString("trailer_url") %>"> TRAILER</a></p>
	
	<br><br>
	<p><a href="mainpage.html">Back to Search/Browse</a></p>
	
</body>
</html>