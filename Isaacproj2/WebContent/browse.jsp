<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Browse</title>
	</head>
	<body>
		<h1>By Title or Genre?</h1>
		<form action="BrowseByTitle" method="post">
			Title:<input type="text" name="title"/>
			<input type="submit" name="by title"/>
		</form>
		<form action="BrowseByGenre" method="post">
			Genre:<input type="text" name="genre"/>
			<input type="submit" name="by genre"/>
		</form>
	</body>
</html>