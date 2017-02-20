<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "HelperClasses.*, java.util.*, java.math.*"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Fail Operation</title>
</head>
<style>
	h1 {text-align : center}
</style>
<body>
<%
	String opName = (String)request.getAttribute("opName");
%>
<center><a href="dashboard.html">Return to DashBoard</a></center> <br>

<h1><%=opName%> Failed</h1>
</body>
</html>