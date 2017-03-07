

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import HelperClasses.Database;

/**
 * Servlet implementation class androidSearch
 */
@WebServlet("/androidSearch")
public class androidSearch extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		HttpSession mySession = request.getSession();
		
		try{
			PrintWriter out  = response.getWriter();
			String movie = request.getParameter("movie");
			String movieList = getMovieList(movie);
			
			out.write(movieList);
			
			out.flush();
			out.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private String getMovieList(String movie){
		String movieList = "";
		//todo use full text search sql statements
		try {

			Connection connection = Database.openConnection();
			Statement select = connection.createStatement();
			String query = "SELECT title FROM movies WHERE title like '%" + movie + "%'";
			ResultSet result = select.executeQuery(query);
			while (result.next()){
				movieList = movieList +result.getString(1) + " \n ";
			}
			return movieList;
		}catch (Exception e){
			e.printStackTrace();
		}
		return movieList;
	}

}
