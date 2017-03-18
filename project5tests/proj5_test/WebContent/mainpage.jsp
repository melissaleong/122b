<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "HelperClasses.*, java.util.*, java.math.*, java.sql.*"  %>

<!DOCTYPE html>
<html>
	<head>
	    <meta charset="utf-8">
	    <link href="https://fonts.googleapis.com/css?family=Raleway" rel="stylesheet">
	    <title>Main Page</title>
	     <script src='https://www.google.com/recaptcha/api.js'></script>
	    <link rel="stylesheet" type="text/css" href="css/bootstrap.css">

 	    <!-- Optional Bootstrap theme -->
	    <link rel="stylesheet" href="css/bootstrap-theme.css">
		<style>
			#clear-box {
			  padding: 20px;
			  background: rgba(255,255,255,0.5);
			  width: 640px;
			  height: 360px;
			  margin: auto;
			  border-radius: 10px;
			}
/*         	body {font-family:Raleway}
        	body {text-align : center} */

		</style>
		<script language ="javascript">
			function ajaxFunction(value) {
				var ajaxRequest;
				
				try {
					ajaxRequest = new XMLHttpRequest();
				} catch (e) {
					alert("fail!");
					return false;
				}
				document.getElementById("datalist").innerHTML = "";
				var dataList = document.getElementById('datalist');
				/* clearChildren(dataList); */
				ajaxRequest.onreadystatechange = function() {
	/* 				alert("rdystate: " + ajaxRequest.readyState + " status: " + ajaxRequest.status); */
	 				if(ajaxRequest.readyState == 4) {
						var data = ajaxRequest.responseText;
	
						var str_array = data.split(";");
						for(var i = 0; i < str_array.length; i++) {
							var option = document.createElement('option');
							option.value = str_array[i];
							dataList.appendChild(option);
						}
						
					}
				}
				
				ajaxRequest.open("GET", "AjaxTest?text=" + value,true);
				ajaxRequest.send();
			}
			
			function clearChildren(parent_id) {
				var i;
				for(i = parent_id.options.length - 1; i >= 0; i--) {
					parent_id.remove(i);
				}
			}
		
	</script>
	</head>
	<body>
 		<nav class="navbar navbar-default container-fullwidth">
			<div class="navbar-inner navbar-right">
				<div class="navbar-header">
					<a class="brand" href="#">FabFlix 20</a>
				</div>
				<ul class="nav navbar-nav pull-left">
					<li class="active"><a href="#">Home/Browse</a></li>
					<li><a href="search.html">Advanced Search</a></li>
				</ul>
				<form class="navbar-form pull-left" method = "get" action ="Search" name = "searchbar">
					<input type="text" name="textsearch" id="ajax" list="datalist" placeholder="Movie Title" class="form-control" onkeyup="ajaxFunction(this.value);">
					<datalist id="datalist">
					</datalist>
 					<input type="submit" value="Search" class="btn btn-secondary">
 				</form>
				<ul class="nav navbar-nav pull-right">
					<li><a href="shoppingCart.jsp">Shopping Cart</a>
				</ul>
			</div>
		</nav>
		<br>
		<br>
		<br>
		<br>
		<br>
		

		
		<div id="clear-box">
			<h1>Welcome to FabFlix 20!</h1>
			<br>
			<font size="4">Begin by browsing our movie selection by title <b>OR</b> by genre</font>
			<%String[] start = {"A", "B", "C", "D", "E", "F" , "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};%>
			<br>
			<br>
			<br>
			<p><u>By Title:</u></p>
			<p><a href="BrowseByTitle?title=<%=start[0]%>"><%=start[0]%></a>
			<a href="BrowseByTitle?title=<%=start[1]%>"><%=start[1]%></a>
			<a href="BrowseByTitle?title=<%=start[2]%>"><%=start[2]%></a>
			<a href="BrowseByTitle?title=<%=start[3]%>"><%=start[3]%></a>
			<a href="BrowseByTitle?title=<%=start[4]%>"><%=start[4]%></a>
			<a href="BrowseByTitle?title=<%=start[5]%>"><%=start[5]%></a>
			<a href="BrowseByTitle?title=<%=start[6]%>"><%=start[6]%></a>
			<a href="BrowseByTitle?title=<%=start[7]%>"><%=start[7]%></a>
			<a href="BrowseByTitle?title=<%=start[8]%>"><%=start[8]%></a>
			<a href="BrowseByTitle?title=<%=start[9]%>"><%=start[9]%></a>
			<a href="BrowseByTitle?title=<%=start[10]%>"><%=start[10]%></a>
			<a href="BrowseByTitle?title=<%=start[11]%>"><%=start[11]%></a>
			<a href="BrowseByTitle?title=<%=start[12]%>"><%=start[12]%></a>
			<a href="BrowseByTitle?title=<%=start[13]%>"><%=start[13]%></a>
			<a href="BrowseByTitle?title=<%=start[14]%>"><%=start[14]%></a>
			<a href="BrowseByTitle?title=<%=start[15]%>"><%=start[15]%></a>
			<a href="BrowseByTitle?title=<%=start[16]%>"><%=start[16]%></a>
			<a href="BrowseByTitle?title=<%=start[17]%>"><%=start[17]%></a>
			<a href="BrowseByTitle?title=<%=start[18]%>"><%=start[18]%></a>
			<a href="BrowseByTitle?title=<%=start[19]%>"><%=start[19]%></a>
			<a href="BrowseByTitle?title=<%=start[20]%>"><%=start[20]%></a>
			<a href="BrowseByTitle?title=<%=start[21]%>"><%=start[21]%></a>
			<a href="BrowseByTitle?title=<%=start[22]%>"><%=start[22]%></a>
			<a href="BrowseByTitle?title=<%=start[23]%>"><%=start[23]%></a>
			<a href="BrowseByTitle?title=<%=start[24]%>"><%=start[24]%></a>
			<a href="BrowseByTitle?title=<%=start[25]%>"><%=start[25]%></a>
			<a href="BrowseByTitle?title=<%=start[26]%>"><%=start[26]%></a>
			<a href="BrowseByTitle?title=<%=start[27]%>"><%=start[27]%></a>
			<a href="BrowseByTitle?title=<%=start[28]%>"><%=start[28]%></a>
			<a href="BrowseByTitle?title=<%=start[29]%>"><%=start[29]%></a>
			<a href="BrowseByTitle?title=<%=start[30]%>"><%=start[30]%></a>
			<a href="BrowseByTitle?title=<%=start[31]%>"><%=start[31]%></a>
			<a href="BrowseByTitle?title=<%=start[32]%>"><%=start[32]%></a>
			<a href="BrowseByTitle?title=<%=start[33]%>"><%=start[33]%></a>
			<a href="BrowseByTitle?title=<%=start[34]%>"><%=start[34]%></a>
			<a href="BrowseByTitle?title=<%=start[35]%>"><%=start[35]%></a>
			</p>
			<br>
			<br>
			<p><u>By Genre:</u></p>
			<form action="BrowseByGenre">
					<select name="genre">		
	 				<% Connection connection = Database.openConnection();
	 				String query = "SELECT name FROM genres ORDER BY name";
					Statement statement = connection.createStatement();
					ResultSet result = statement.executeQuery(query);
					while(result.next()){%>
						<option value= <%=result.getString("name") %>> <%=result.getString("name") %> </option>
					<%} %>
				</select><br>
				<input type="submit" name="genre" class="btn btn-primary">
			</form>
		</div>
		



		
<!--  		<h2><a href="shoppingCart.jsp">Shopping Cart</a></h2>
		<h1>Choose whether to "Browse" or "Search"</h1>
			<p style = font-size:20px><b>Browse</b></p>
 		<form method="post" action="MainPage">
			<input type="submit" name="Browse"/>
		</form>
			<p style = font-size:20px><b>Search</b></p>
		<form method="post" action="MainPage">
			<input type="submit" name="Search"/>
		</form>  -->
	</body> 
</html>