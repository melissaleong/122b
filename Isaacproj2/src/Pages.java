

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

/**
 * Servlet implementation class Pages
 */
@WebServlet("/Pages")
public class Pages extends HttpServlet {
	//private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MovieList allMovies = new MovieList();
		//List<Movie> movieList = (List<Movie>)request.getAttribute("movieList");
		List<Movie> movieList = allMovies.getMovieList();
		int page = 1;
		if (request.getParameter("page")!= null){
			page = Integer.parseInt(request.getParameter("page"));
		}
		System.out.println("Transfer Successful");
		
		for (Movie m : movieList){
			System.out.println(m.title);
		}
		request.setAttribute("page", page);
		request.setAttribute("movieList", movieList);
		request.setAttribute("movieListSize", movieList.size());
		request.getRequestDispatcher("pages.jsp").forward(request, response);

		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
