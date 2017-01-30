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
			<td><%= movieList.get(i).title %></td>
		</tr>
			<%}%>
		
<%-- 		<%
			int size = (Integer)request.getAttribute("movieListSize");
			List<Movie> movieList = (List<Movie>)request.getAttribute("movieList");
			for (int i = 0; i < size; ++i) {
				Movie m = movieList.get(i);
			}
		%> --%>
		
		
		
	</table>
</body>
</html>