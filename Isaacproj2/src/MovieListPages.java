import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.swing.plaf.synth.SynthSeparatorUI;

import java.sql.*;
import HelperClasses.*;
import java.util.*;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MovieListPages
 */
@WebServlet("/MovieListPages")
public class MovieListPages extends HttpServlet {
	//private static final long serialVersionUID = 1L;
       

    public MovieListPages() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try{
			System.out.println("Arrives at MovieListPages");
			int page = 1;
			int moviesPerPage = 10;
			if (request.getParameter("page")!= null){
				page = Integer.parseInt(request.getParameter("page"));
			}
			
			List<Movie> movieList = (List)request.getAttribute("movieList");
			request.setAttribute("page", page);
			request.setAttribute("movieList",movieList);
			request.getRequestDispatcher("pages.jsp").forward(request, response);
			
			//response.getWriter().append("Served at: ").append(request.getContextPath());
		} catch (Exception e){
			e.printStackTrace();
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
