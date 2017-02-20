package HelperClasses;

import java.util.*;
import java.sql.*;
import HelperClasses.*;
import java.io.*;


public class MovieList {
	public static List<Movie> movieList;
	public static int numOfMovies;
	public static String searchQuery;//stores query for later use in order to run sorting within pages
	public static String fieldName;
	public static String sortType;
	
	public MovieList(){};
	
	public MovieList(List<Movie> movieList, String searchQuery){
		 this.movieList = movieList;
		 numOfMovies = movieList.size();
		 this.searchQuery= searchQuery;
	}
	
	public List<Movie> getMovieList(){
		return movieList;
	}
	
	public String getSearchQuery(){
		return searchQuery;
	}
	
	public void setFieldName(String fieldName){
		this.fieldName=fieldName;
	}
	
	public void setSortType(String sortType){
		this.sortType=sortType;
	}
	
	public void setMovieList(List<Movie> movieList){
		this.movieList=movieList;
	}
	
	public String getFieldName(){return fieldName;}
	
	public String getSortType(){return sortType;}
	
}
