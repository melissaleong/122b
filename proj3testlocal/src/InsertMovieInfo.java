
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.swing.plaf.synth.SynthSeparatorUI;

import java.sql.*;
import java.text.*;

import HelperClasses.*;
import java.util.*;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

@WebServlet("/InsertMovie")
public class InsertMovieInfo extends HttpServlet {



	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		HttpSession mySession = request.getSession();
		String opName = "Insert Movie Info";
		try{
			Connection connection = Database.openConnection();

			
			String title = request.getParameter("title");
			String year = request.getParameter("year");
			String director = request.getParameter("director");
			String banner_url = request.getParameter("banner_url");
			String trailer_url = request.getParameter("trailer_url");
			String firstname = request.getParameter("firstname");
			String lastname = request.getParameter("lastname");
			String genre = request.getParameter("genre");
			
			if (year.equals("")){
				year="0";
			}
			
			if ((firstname =="" && lastname!="") || (firstname!="" && lastname=="")){
				System.out.println("Need both firstname and lastname to update Star. Please try again");
				request.setAttribute("opName", opName);
				request.getRequestDispatcher("Fail.jsp").forward(request, response);
			}
			
			else if (!movieExists(connection, title)){
				System.out.println("movie does not exist, update not successful");
				request.setAttribute("opName", opName);
				request.getRequestDispatcher("Fail.jsp").forward(request, response);
			}
			else{
				System.out.println("movie does exist, updating...");

				insertData(connection, title, year, director, banner_url, trailer_url, firstname, lastname, genre);
				response.sendRedirect("operationconf.html");
			}
			
			
		}catch (Exception e){
			System.out.println("Something went wrong...");
			e.printStackTrace();
		}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	public void insertData (Connection connection, String title, String year, String director, String banner_url, String trailer_url,
			String firstname, String lastname, String genre){
		String queryString;
		try{
			queryString = "CALL add_movie (?,?,?,?,?,?,?,?);";
			PreparedStatement prepState = connection.prepareStatement(queryString);
			prepState.setString(1, title);
			prepState.setInt(2, Integer.parseInt(year));
			prepState.setString(3, director);
			prepState.setString(4, banner_url);
			prepState.setString(5, trailer_url);
			prepState.setString(6, firstname);
			prepState.setString(7, lastname);
			prepState.setString(8, genre);
			prepState.execute();
	

		}catch(Exception e){
			System.out.println("One of the Parameters are empty");
			e.printStackTrace();
		}
	}
	
	private boolean movieExists(Connection conn,String title){
		int exists = 0;
		try{
			Statement select = conn.createStatement();
			String queryString = "SELECT COUNT(*) FROM movies WHERE title = '" + title + "'";
			ResultSet result = select.executeQuery(queryString);
			//System.out.println("Here");
			if (result.next()){
				exists = result.getInt(1);
			}
			if (exists ==0){
				return false;
			}
			else {
				return true;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}

}
