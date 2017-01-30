<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "HelperClasses.*, java.util.*"  %>
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
	List<String> movie_list = (List<String>)request.getSession().getAttribute("movie_list");%>
	<p><%=id %>
	<p><%=fn%> <%=ln %></p>
	<p>DoB = <%=dob%></p>
	<p>photo URL = <%=photo_url%> </p>
</body>
</html>