<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "HelperClasses.*, java.util.*"  %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
 	<table style="width:100%" border="1">
		<h1>Found ${movieListSize} result(s). </h1>
		
		<%
		int size = (Integer)request.getAttribute("movieListSize");
		List<Movie> movieList = (List)request.getAttribute("movieList");
		for(int i = 0; i < size; ++i) {%>
		
		<tr>
			<td><%= movieList.get(i).id %></td>
			<td><%= movieList.get(i).title %></td>
			<td><%= movieList.get(i).year %></td>
			<td><%= movieList.get(i).director %></td>
			<td> <b>GENRES</b>
			<% int genre_size = movieList.get(i).genresInMovie.size();
				for(int j = 0; j < genre_size; ++j) { %>
					<p> <%= movieList.get(i).genresInMovie.get(j) %> </p>
					<%}%>
			</td>
			
			<td> <b>STARS</b>
			<% int star_size = movieList.get(i).starsInMovie.size(); 
				for(int j = 0; j < star_size; ++j) {%>
				<p> <%= movieList.get(i).starsInMovie.get(j).fn %> <%= movieList.get(i).starsInMovie.get(j).ln %></p>
				<%}%>
			</td>
		</tr>
		<%}%>
	</table>
</body>
</html>