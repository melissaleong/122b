

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

/**
 * Servlet implementation class InsertStar
 */
@WebServlet("/InsertStar")
public class InsertStar extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		HttpSession mySession = request.getSession();
		try{
			//System.out.println("First name: " + firstname + " Last name:  " + lastname + " dob: " + dob + " photoURL: " + photo_url);
			Connection connection = Database.openConnection();

			if (request.getParameter("firstname")=="" && request.getParameter("lastname")==""){
				System.out.println("First name or last name required for insert");
				response.sendRedirect("insertstar.html");
			}
			
			String firstname = request.getParameter("firstname");
			String lastname = request.getParameter("lastname");
			String dob = request.getParameter("dob");
			String photo_url = request.getParameter("photo_url");
			
			java.sql.Date dateOfBirth = null;
			
			if (lastname == ""){
				lastname = firstname;
				firstname="";
			}
			if (dob !=null){
				dateOfBirth = convertStringtoDate(dob);
			}
			
			if (dob!=null && photo_url!=null){
				String queryString = "INSERT INTO stars(first_name, last_name, dob, photo_url) VALUES(?, ?, ?, ?)";
				PreparedStatement prepState = connection.prepareStatement(queryString);
				prepState.setString(1, firstname);
				prepState.setString(2, lastname);
				prepState.setDate(3, dateOfBirth);
				prepState.setString(4, photo_url);
				prepState.execute();

			}
			else if (dob!=null){
				String queryString = "INSERT INTO stars(first_name, last_name, photo_url) VALUES(?, ?, ?)";
				PreparedStatement prepState = connection.prepareStatement(queryString);
				
				prepState.setString(1, firstname);
				prepState.setString(2, lastname);
				prepState.setString(3, photo_url);
				prepState.execute();
			} 
			else if (photo_url!=null){
				String queryString = "INSERT INTO stars(first_name, last_name, dob) VALUES(?, ?, ?)";
				PreparedStatement prepState = connection.prepareStatement(queryString);
				
				prepState.setString(1, firstname);
				prepState.setString(2, lastname);
				prepState.setDate(3, dateOfBirth);
				prepState.execute();
			} 
			else{
				String queryString = "INSERT INTO stars(first_name, last_name) VALUES(?, ?)";
				PreparedStatement prepState = connection.prepareStatement(queryString);
				
				prepState.setString(1, firstname);
				prepState.setString(2, lastname);
				prepState.execute();
			}
			
			response.sendRedirect("insertstarconf.html");

			
		} catch(Exception e){
			System.out.println("something went wrong...");
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	public java.sql.Date convertStringtoDate(String dateString){
		try{
			java.util.Date returnDate = new SimpleDateFormat("MM/dd/yyyy").parse(dateString);
			java.sql.Date sqlDate = new java.sql.Date(returnDate.getTime());
			return sqlDate;
		}catch (ParseException e){
			System.out.println("Date is not entered correctly");
			e.printStackTrace();
		}
		return null;
	}

}
