

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.swing.plaf.synth.SynthSeparatorUI;

import java.sql.*;
import HelperClasses.*;
import java.util.*;
import javax.naming.InitialContext;
import javax.naming.Context;
import javax.sql.DataSource;
/**
 * Servlet implementation class Pages
 */
@WebServlet("/Pages")
public class Pages extends HttpServlet {
	//private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MovieList allMovies = new MovieList();
		List<Movie> movieList;
		
		
		int page = 1;
		if (request.getParameter("page")!= null){
			page = Integer.parseInt(request.getParameter("page"));
		}
		System.out.println("Transfer Successful");
		
		String fieldName=request.getParameter("fn");
		String sortType=request.getParameter("sort");
//		System.out.println(fieldName);
//		System.out.println(sortType);
		if (fieldName!=null && sortType !=null){
			fieldName=request.getParameter("fn");
			sortType=request.getParameter("sort");
			allMovies.setFieldName(fieldName);
			allMovies.setSortType(sortType);
			movieList = getMovieList(allMovies.getSearchQuery(),fieldName, sortType);
			allMovies.setMovieList(movieList);
		} else{
			movieList = allMovies.getMovieList();
		}
		
//		for (Movie m : movieList){
//			System.out.println(m.title);
//		}
		

		request.setAttribute("page", page);
		request.setAttribute("movieList", movieList);
		request.setAttribute("movieListSize", movieList.size());
		request.getRequestDispatcher("pages.jsp").forward(request, response);

		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	public List<Movie> getMovieList(String searchQuery,String fieldName,String sortType) {
		List<Movie> movieList = new ArrayList<Movie>();
		
		try {
//			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb?useSSL=false", "root", "root1234");
//			Connection connection = Database.openConnection();
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource) envCtx.lookup("jdbc/moviedb");
			
			Connection connection = ds.getConnection();

			Statement statement = connection.createStatement();
			String query = searchQuery;
			query = createSortQuery(query, fieldName, sortType);
			
			ResultSet result = statement.executeQuery(query);
			movieList = BrowseByTitle.returnMovieList(result, connection);
			
			result.close();
			statement.close();
			connection.close();
			return movieList;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return movieList;
		
	}
	
	private String createSortQuery(String searchQuery, String fieldName, String sortType){
		String result = searchQuery + " ORDER BY " + fieldName + " " + sortType;
		
		return result;
	}

}
