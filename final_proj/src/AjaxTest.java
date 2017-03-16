

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import HelperClasses.Database;
import HelperClasses.Movie;
import HelperClasses.MovieList;

/**
 * Servlet implementation class AjaxTest
 */
@WebServlet("/AjaxTest")
public class AjaxTest extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		try {
			Connection connection = Database.openConnection();
			String text = request.getParameter("text");
			Statement state = connection.createStatement();
			String query = "SELECT DISTINCT title FROM movies WHERE MATCH(title) AGAINST('";
			String[] pieces = text.split(" ");

			query += "+" + pieces[0] + "*";
			for (int i = 1; i < pieces.length; ++i) {
				query += " +" + pieces[i] + "*";
			}
			query += "' IN BOOLEAN MODE) LIMIT 10";
			
//			System.out.println(query);
			ResultSet rs = state.executeQuery(query);
			
			PrintWriter out = response.getWriter();
			
			while(rs.next()) {
				out.println(rs.getString(1) + ";");
				System.out.println(rs.getString(1));
			}


			
		} catch (Exception e) {
			System.out.println("NO PARAMS");
//			e.printStackTrace();
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);

	}

}
