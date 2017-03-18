import HelperClasses.*;
import java.util.*;
import java.sql.*;
import java.io.*;

// import java.util.ArrayList;
// import java.util.HashSet;
// import java.util.Iterator;
// import java.util.List;


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;

public class movieParser extends DefaultHandler{

	private static final Map<String, String> genreMap;
	static
	{
		genreMap = new HashMap<String, String>();
		genreMap.put("susp", "Suspense");
		genreMap.put("bio", "Biography");
		genreMap.put("advt", "Adventure");
		genreMap.put("cart", "Cartoon");
		genreMap.put("docu", "Documentary");
		genreMap.put("hist", "History");
		genreMap.put("dram", "Drama");
		genreMap.put("anti-dram", "Anti-Drama");
		genreMap.put("musical", "Musical");
		genreMap.put("scfi", "Sci-Fi");
		genreMap.put("epic", "Epic");
		genreMap.put("kinky", "Kinky");
		genreMap.put("comd", "Comedy");
		genreMap.put("actn", "Action");
		genreMap.put("myst", "Mystery");
		genreMap.put("muscl", "Musical");
		genreMap.put("biop", "Biography");
		genreMap.put("natu", "Nature");
		genreMap.put("draam", "Drama");
		genreMap.put("hor", "Horror");
		genreMap.put("porn", "Pornography");
		genreMap.put("scif", "Sci-Fi");
		genreMap.put("art", "Art");
		genreMap.put("romtadvt", "Romantic Adventure");
		genreMap.put("romt.", "Romantic");
		genreMap.put("weird", "Weird");
		genreMap.put("comdx", "Comedy");
		genreMap.put("sati", "Satire");
		genreMap.put("psyc", "Psychological");
		genreMap.put("romt", "Romantic");
		genreMap.put("act", "Action");
		genreMap.put("west", "Western");
		genreMap.put("noir", "Noir");
		genreMap.put("faml", "Family");
		genreMap.put("horr", "Horror");
		genreMap.put("dramn", "Drama");
		genreMap.put("psych", "Psychological");
		genreMap.put("dramd", "Drama");
		genreMap.put("west1", "Western");
		genreMap.put("sxfi", "Sci-Fi");
		genreMap.put("sports", "Sports");
		genreMap.put("muusc", "Musical");
		genreMap.put("fant", "Fantasy");
		genreMap.put("dram>", "Drama");
		genreMap.put("crim", "Crime");
	}

	public List<Movie> myMovies = new ArrayList();

		

	public Movie tempMovie = new Movie();
	public String director;
	
	public HashMap<String, List<String>> movie_genre = new HashMap<String, List<String>>();
	
	public boolean inDirectorFilms = false;
	public boolean inFilmList = false;
	public boolean inFilm = false;
	public boolean inCats = false;
	public boolean inDirector = false;

	public boolean isDirname = false;
	// fucking awful data...
	public boolean isDirn = false;
	public boolean isTitle = false;
	public boolean isYear = false;
	public boolean isCat = false;

	
	
	public movieParser(){
	}
	
	public void runExample() {
		System.out.println("Begin parsing mains243.xml . . .");
		parseDocument();
		Connection conn = null;
		
		Map<String, ArrayList<String>> movieMap = new HashMap<String, ArrayList<String>>();

		// first I go through all the genres and put in the ones that aren't currently in the database
		// I used a static final map because there were a lot of new genre entries, and they were all abbreviated; not all of them are included since some of them were crap
		Iterator it = genreMap.entrySet().iterator();
		try {
			PrintWriter clear = new PrintWriter("movie_info.csv", "UTF-8");
			clear.print("");
			clear.close();
			
			PrintWriter writer = new PrintWriter("movie_info.csv", "UTF-8");
			
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb?useSSL=false","root", "root1234");
			System.out.println("connected to database");

			 while(it.hasNext()) {
			 	Statement statement = conn.createStatement();
			 	Map.Entry pair = (Map.Entry)it.next();
			 	String checkQuery = "SELECT name FROM genres WHERE name = '" + pair.getValue() + "'";
			 	ResultSet check = statement.executeQuery(checkQuery);
			 	if (check.next()) {
			 		continue;
			 	} else {
			 		String query = "INSERT INTO genres (name) VALUES ('" + pair.getValue() + "')";
			 		statement.executeUpdate(query);
			 	}
			 	statement.close();
 		 	}
			
			 // next, I put all the movies currently in the database (along with their directors) into a hash map.
			 // this makes it easy to access the director to see if the movie already exists. If it doesn't we add it into the database
			 Statement statement = conn.createStatement();
			 String hashQuery = "SELECT director, title FROM movies";
			 ResultSet movies = statement.executeQuery(hashQuery);
			 while (movies.next()) {
//				System.out.println(movies.getString(1) + "    " + movies.getString(2));
				if (movieMap.containsKey(movies.getString(1))) {
					movieMap.get(movies.getString(1)).add(movies.getString(2));
				} else {
					ArrayList<String> stuff = new ArrayList<String>();
					stuff.add(movies.getString(2));
					movieMap.put(movies.getString(1), stuff);
				}
			 }
			  
			 
			 // here I'm checking to see if the movies in the movie list are in the database already.
			 // if they aren't, I add them to a csv file called movie_info.csv, which i will load all together into the database
			 int myMoviesSize = myMovies.size();
			 for (int i = 0; i < myMoviesSize-1; ++i) {
				 String dir = myMovies.get(i).director;
				 String t = myMovies.get(i).title;
				 t = t.replace("\\", "");
				 int y = myMovies.get(i).year;
				 if (movieMap.containsKey(dir) && movieMap.get(dir).contains(t)) {
					 continue;
				 } else {
					 writer.append(t + ",," + y + ",," + dir + ",,\n");
				 }
				 
			 }
			 
			 String dir = myMovies.get(myMoviesSize-1).director;
			 String t = myMovies.get(myMoviesSize-1).title;
			 int y = myMovies.get(myMoviesSize-1).year;
			 if (movieMap.containsKey(dir) && movieMap.get(dir).contains(t)) {
			 } else {
				 writer.append(t + ",," + y + ",," + dir);
			 }
		writer.close();
		String data_query = "LOAD DATA LOCAL INFILE \'movie_info.csv\' INTO TABLE movies FIELDS TERMINATED BY \',,\' (title, year, director) SET ID=NULL";
		Statement load_stuff = conn.createStatement();
		load_stuff.executeUpdate(data_query);
		
		
		// now I need to link the genres and the movies.
		// I will do this by creating a hash map containing all the genre name and genre id's.
		// I will also create a hash map containing the new 
		
		//key= title, value = id
		HashMap<String,Integer> new_movies = new HashMap<String,Integer>();
		for (int i = 0; i < myMovies.size(); ++i) {
			Statement state = conn.createStatement();
			String query = "SELECT id FROM movies WHERE title='" + ActorParser.javaStringLiteral(myMovies.get(i).title) + "'";
//			System.out.println(query);
			ResultSet result = state.executeQuery(query);
			if (result.next()) {
				new_movies.put(myMovies.get(i).title, result.getInt(1));
			} else {
				continue;
			}
		}
		
		
		
		HashMap<String,Integer> genre_id = new HashMap<String, Integer>();
		Statement state = conn.createStatement();
		String query = "SELECT * FROM genres";
		ResultSet result = state.executeQuery(query);
		while(result.next()) {
//			System.out.println(result.getString(2) + " " + result.getInt(1));
			genre_id.put(result.getString(2), result.getInt(1));
		}
		
		
		
		ArrayList<Pair<Integer,Integer>> mid_gid = new ArrayList<Pair<Integer,Integer>>();
		for (int i = 0; i < myMovies.size(); ++i) {
			if (movie_genre.containsKey(myMovies.get(i).title)) {
//				System.out.println(spe.movie_genre.get(spe.myMovies.get(i).title));
				List<String> genres = movie_genre.get(myMovies.get(i).title);
//				System.out.println(genres);
				if (genres.size() == 0) {
					continue;
				} else {
					for (int j = 0; j < genres.size(); ++j) {
//						System.out.println(genres.size());
						if (genres.get(j) == null) {
							continue;
						} else {
							Pair<Integer,Integer> pair = Pair.createPair(new_movies.get(myMovies.get(i).title), genre_id.get(genres.get(j)));
							mid_gid.add(pair);
						}
					}
				}
			} else {
				continue;
			}
		}
		
		try{
			PrintWriter clear1 = new PrintWriter("mid_gid.csv", "UTF-8");
			clear.print("");
			clear.close();
			
			PrintWriter w = new PrintWriter("mid_gid.csv", "UTF-8");
			for (int i = 0; i < mid_gid.size() - 1; ++i) {
				Statement state1 = conn.createStatement();
				String query1 = "SELECT * FROM genres_in_movies WHERE movie_id=" + mid_gid.get(i).getElement0() + " AND genre_id =" + mid_gid.get(i).getElement1();
				ResultSet r1 = state1.executeQuery(query1);

				if (!r1.next())
					if (mid_gid.get(i).getElement0() != null)
						w.append(mid_gid.get(i).getElement1() + "," + mid_gid.get(i).getElement0() + ",\n");
				state1.close();
			}
			
			Statement last = conn.createStatement();
			String lastq = "SELECT * FROM genres_in_movies WHERE movie_id=" + mid_gid.get(mid_gid.size()-1).getElement0() + " AND genre_id =" + mid_gid.get(mid_gid.size()-1).getElement1();
			ResultSet r = last.executeQuery(lastq);
			if (!r.next())
				w.append(mid_gid.get(mid_gid.size()-1).getElement1() + "," + mid_gid.get(mid_gid.size()-1).getElement0());
			w.close();
			
			String data = "LOAD DATA LOCAL INFILE \'mid_gid.csv\' INTO TABLE genres_in_movies FIELDS TERMINATED BY \',\' (genre_id, movie_id)";
			Statement loading = conn.createStatement();
			loading.executeUpdate(data);
			loading.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		
		conn.close();
		
		
		
		
		System.out.println("Finished parsing mains243.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void parseDocument() {
		
		//get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
		
			//get a new instance of parser
			SAXParser sp = spf.newSAXParser();
			
			//parse the file and also register this class for call backs
			sp.parse("mains243.xml", this);
			
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch (IOException ie) {
			ie.printStackTrace();
		}
	}

	
	//Event Handlers
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		if (qName.equalsIgnoreCase("directorfilms")) {
			inDirectorFilms = true;
		} else if (qName.equalsIgnoreCase("director")) {
			inDirector = true;
		} else if (qName.equalsIgnoreCase("dirn") && inDirector) {
			isDirn = true;
		} else if (qName.equalsIgnoreCase("dirname")) {
			isDirname = true;
		} else if (qName.equalsIgnoreCase("films")) {
			inFilmList = true;
		} else if (qName.equalsIgnoreCase("film")) {
			inFilm = true;
		} else if (qName.equalsIgnoreCase("t")) {
			isTitle = true;
		} else if (qName.equalsIgnoreCase("year")) {
			isYear = true;
		} else if (qName.equalsIgnoreCase("cats")) {
			inCats = true;
		} else if (qName.equalsIgnoreCase("cat")) {
			isCat = true;
		}
	

	}
	
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (isDirname) {
			String name = new String(ch, start, length);
			director = name;
			tempMovie.setDirector(director);
			isDirname = false;
		}
		if (isDirn) {
			String name = new String(ch, start, length);
			director = name;
			isDirn = false;
		}
		if (isTitle && inDirectorFilms) {
			String name = new String(ch, start, length);
			tempMovie.setDirector(director);
			tempMovie.setTitle(name);
			isTitle = false;
		}
		if (isYear) {
			String name = new String(ch, start, length);
			int year; 
			try {
				year = Integer.parseInt(name);
			} catch (Exception e) {
				year = 1900;
			}
			tempMovie.setYear(year);
			isYear = false;
		}
		if (isCat) {
			String name = new String(ch, start, length);
			String[] pieces = name.trim().toLowerCase().split(" ");

			for (String s : pieces) {
				tempMovie.genresInMovie.add(genreMap.get(s));
			}
			isCat = false;
		}
		
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equalsIgnoreCase("directorfilms")) {
			inDirectorFilms = false;
		} else if (qName.equalsIgnoreCase("director")) {
			inDirector = false;
		} else if (qName.equalsIgnoreCase("films")) {
			inFilmList = false;
		} else if (qName.equalsIgnoreCase("cats")) {
			inCats = false;
			// want to reset the genre list for the specific movie once we get out of the categories tag
		} else if (qName.equalsIgnoreCase("film")) {


			myMovies.add(tempMovie);
			movie_genre.put(tempMovie.title, tempMovie.genresInMovie);
			
			tempMovie = new Movie();
			
			
		}

		
	}

	
//	public static void main(String[] args){
//		System.out.println("Parsing mains243.xml . . .");
//		movieParser spe = new movieParser();
//		spe.runExample();
////		Connection conn = null;
////		
////		Map<String, ArrayList<String>> movieMap = new HashMap<String, ArrayList<String>>();
////
////		// first I go through all the genres and put in the ones that aren't currently in the database
////		// I used a static final map because there were a lot of new genre entries, and they were all abbreviated; not all of them are included since some of them were crap
////		Iterator it = spe.genreMap.entrySet().iterator();
////		try {
////			PrintWriter clear = new PrintWriter("movie_info.csv", "UTF-8");
////			clear.print("");
////			clear.close();
////			
////			PrintWriter writer = new PrintWriter("movie_info.csv", "UTF-8");
////			
////			
////			Class.forName("com.mysql.jdbc.Driver");
////			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb?useSSL=false","root", "root");
////			System.out.println("connected to database");
////
////			 while(it.hasNext()) {
////			 	Statement statement = conn.createStatement();
////			 	Map.Entry pair = (Map.Entry)it.next();
////			 	String checkQuery = "SELECT name FROM genres WHERE name = '" + pair.getValue() + "'";
////			 	ResultSet check = statement.executeQuery(checkQuery);
////			 	if (check.next()) {
////			 		continue;
////			 	} else {
////			 		String query = "INSERT INTO genres (name) VALUES ('" + pair.getValue() + "')";
////			 		statement.executeUpdate(query);
////			 	}
////			 	statement.close();
//// 		 	}
////			
////			 // next, I put all the movies currently in the database (along with their directors) into a hash map.
////			 // this makes it easy to access the director to see if the movie already exists. If it doesn't we add it into the database
////			 Statement statement = conn.createStatement();
////			 String hashQuery = "SELECT director, title FROM movies";
////			 ResultSet movies = statement.executeQuery(hashQuery);
////			 while (movies.next()) {
//////				System.out.println(movies.getString(1) + "    " + movies.getString(2));
////				if (movieMap.containsKey(movies.getString(1))) {
////					movieMap.get(movies.getString(1)).add(movies.getString(2));
////				} else {
////					ArrayList<String> stuff = new ArrayList<String>();
////					stuff.add(movies.getString(2));
////					movieMap.put(movies.getString(1), stuff);
////				}
////			 }
////			  
////			 
////			 // here I'm checking to see if the movies in the movie list are in the database already.
////			 // if they aren't, I add them to a csv file called movie_info.csv, which i will load all together into the database
////			 int myMoviesSize = spe.myMovies.size();
////			 for (int i = 0; i < myMoviesSize-1; ++i) {
////				 String dir = spe.myMovies.get(i).director;
////				 String t = spe.myMovies.get(i).title;
////				 t = t.replace("\\", "");
////				 int y = spe.myMovies.get(i).year;
////				 if (movieMap.containsKey(dir) && movieMap.get(dir).contains(t)) {
////					 continue;
////				 } else {
////					 writer.append(t + ",," + y + ",," + dir + ",,\n");
////				 }
////				 
////			 }
////			 System.out.println("done");
////			 
////			 String dir = spe.myMovies.get(myMoviesSize-1).director;
////			 String t = spe.myMovies.get(myMoviesSize-1).title;
////			 int y = spe.myMovies.get(myMoviesSize-1).year;
////			 if (movieMap.containsKey(dir) && movieMap.get(dir).contains(t)) {
////			 } else {
////				 writer.append(t + ",," + y + ",," + dir);
////			 }
////		writer.close();
////		String data_query = "LOAD DATA LOCAL INFILE \'movie_info.csv\' INTO TABLE movies FIELDS TERMINATED BY \',,\' (title, year, director) SET ID=NULL";
////		Statement load_stuff = conn.createStatement();
////		load_stuff.executeUpdate(data_query);
////		
////		
////		// now I need to link the genres and the movies.
////		// I will do this by creating a hash map containing all the genre name and genre id's.
////		// I will also create a hash map containing the new 
////		
////		//key= title, value = id
////		HashMap<String,Integer> new_movies = new HashMap<String,Integer>();
////		for (int i = 0; i < spe.myMovies.size(); ++i) {
////			Statement state = conn.createStatement();
////			String query = "SELECT id FROM movies WHERE title='" + ActorParser.javaStringLiteral(spe.myMovies.get(i).title) + "'";
//////			System.out.println(query);
////			ResultSet result = state.executeQuery(query);
////			if (result.next()) {
////				new_movies.put(spe.myMovies.get(i).title, result.getInt(1));
////			} else {
////				continue;
////			}
////		}
////		
////		
////		
////		HashMap<String,Integer> genre_id = new HashMap<String, Integer>();
////		Statement state = conn.createStatement();
////		String query = "SELECT * FROM genres";
////		ResultSet result = state.executeQuery(query);
////		while(result.next()) {
//////			System.out.println(result.getString(2) + " " + result.getInt(1));
////			genre_id.put(result.getString(2), result.getInt(1));
////		}
////		
////		
////		
////		ArrayList<Pair<Integer,Integer>> mid_gid = new ArrayList<Pair<Integer,Integer>>();
////		for (int i = 0; i < spe.myMovies.size(); ++i) {
////			if (spe.movie_genre.containsKey(spe.myMovies.get(i).title)) {
//////				System.out.println(spe.movie_genre.get(spe.myMovies.get(i).title));
////				List<String> genres = spe.movie_genre.get(spe.myMovies.get(i).title);
//////				System.out.println(genres);
////				if (genres.size() == 0) {
////					continue;
////				} else {
////					for (int j = 0; j < genres.size(); ++j) {
//////						System.out.println(genres.size());
////						if (genres.get(j) == null) {
////							continue;
////						} else {
////							Pair<Integer,Integer> pair = Pair.createPair(new_movies.get(spe.myMovies.get(i).title), genre_id.get(genres.get(j)));
////							mid_gid.add(pair);
////						}
////					}
////				}
////			} else {
////				continue;
////			}
////		}
////		
////		try{
////			PrintWriter clear1 = new PrintWriter("mid_gid.csv", "UTF-8");
////			clear.print("");
////			clear.close();
////			
////			PrintWriter w = new PrintWriter("mid_gid.csv", "UTF-8");
////			for (int i = 0; i < mid_gid.size() - 1; ++i) {
////				Statement state1 = conn.createStatement();
////				String query1 = "SELECT * FROM genres_in_movies WHERE movie_id=" + mid_gid.get(i).getElement0() + " AND genre_id =" + mid_gid.get(i).getElement1();
////				ResultSet r1 = state1.executeQuery(query1);
////
////				if (!r1.next())
////					if (mid_gid.get(i).getElement0() != null)
////						w.append(mid_gid.get(i).getElement1() + "," + mid_gid.get(i).getElement0() + ",\n");
////				state1.close();
////			}
////			
////			Statement last = conn.createStatement();
////			String lastq = "SELECT * FROM genres_in_movies WHERE movie_id=" + mid_gid.get(mid_gid.size()-1).getElement0() + " AND genre_id =" + mid_gid.get(mid_gid.size()-1).getElement1();
////			ResultSet r = last.executeQuery(lastq);
////			if (!r.next())
////				w.append(mid_gid.get(mid_gid.size()-1).getElement1() + "," + mid_gid.get(mid_gid.size()-1).getElement0());
////			w.close();
////			
////			String data = "LOAD DATA LOCAL INFILE \'mid_gid.csv\' INTO TABLE genres_in_movies FIELDS TERMINATED BY \',\' (genre_id, movie_id)";
////			Statement loading = conn.createStatement();
////			loading.executeUpdate(data);
////			loading.close();
////			
////			
////		} catch (Exception e) {
////			e.printStackTrace();
////		}
////		
////
////		
////		conn.close();
////		
////		
////		
////		
////		System.out.println("Finished parsing mains243.xml");
////		} catch (Exception e) {
////			e.printStackTrace();
////		}
//		
//	}
	
}



