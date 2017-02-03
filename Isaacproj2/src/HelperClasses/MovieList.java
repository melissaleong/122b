package HelperClasses;

import java.util.*;
import java.sql.*;
import HelperClasses.*;
import java.io.*;


public class MovieList {
	public static List<Movie> movieList;
	public static int numOfMovies;
	
	public MovieList(){};
	
	public MovieList(List<Movie> movieList){
		 this.movieList = movieList;
		 numOfMovies = movieList.size();
	}
	
	public List<Movie> getMovieList(){
		return movieList;
	}
}
