import java.util.*;
import java.sql.*;
import java.io.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;

public class castParser extends DefaultHandler{
	
	
	HashMap<String, Integer> all_actors = new HashMap<String, Integer>();
	HashMap<String, Integer> all_movies = new HashMap<String ,Integer>();
	
	
	ArrayList<Pair<Integer, Integer>> add_to_db = new ArrayList<Pair<Integer, Integer>>();
	
	Movie tempMovie = new Movie();
	
	public boolean inDirFilms = false;
	public boolean inFilmC = false;
	public boolean inM = false;
	
	public boolean isIs = false;
	public boolean isT = false;
	public boolean isA = false;
	
	String director;
	String actor;
	String title;
	
	public void runExample() {
		fillAllActors();
		fillAllMovies();
		parseDocument();
	}
	
	public void fillAllActors() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb?useSSL=false","root", "root");
			Statement s = conn.createStatement();
			String query = "SELECT first_name, last_name, id FROM stars";
			ResultSet result = s.executeQuery(query);
			
			while(result.next()) {
				String fn = result.getString(1);
				String ln = result.getString(2);
				int id = result.getInt(3);
				String name = fn + " " + ln;
				all_actors.put(name, id);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void fillAllMovies() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb?useSSL=false","root", "root");
			Statement s = conn.createStatement();
			String query = "SELECT title, id FROM movies";
			ResultSet result = s.executeQuery(query);
			
			while(result.next()) {
				String t = result.getString(1);
				int id = result.getInt(2);
				all_movies.put(t, id);
			}
		} catch(Exception e) {
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
			sp.parse("casts124.xml", this);
			
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch (IOException ie) {
			ie.printStackTrace();
		}
	}
	
	
	
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equalsIgnoreCase("dirfilms")) {
			inDirFilms = true;
		} else if (qName.equalsIgnoreCase("is")) {
			isIs = true;
		} else if (qName.equalsIgnoreCase("filmc")) {
			inFilmC = true;
		} else if (qName.equalsIgnoreCase("m")) {
			inM = true;
		} else if (qName.equalsIgnoreCase("t")) {
			isT = true;
		} else if (qName.equalsIgnoreCase("a")) {
			isA = true;
		}
	}
	
	
	
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (isIs) {
			director = new String(ch, start, length);
			isIs = false;
		} else if (isT) {
			title = new String(ch,start,length);
			isT = false;
		} else if (isA) {
			actor = new String(ch,start,length);
			isA = false;
		}
	}
	
	
	
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equalsIgnoreCase("dirfilms")) {
			inDirFilms = false;
		} else if (qName.equalsIgnoreCase("filmc")) {
			inFilmC = false;
			
		// important part. Need to add stuff into id_list
		} else if (qName.equalsIgnoreCase("m")) {
			if (all_movies.containsKey(title) && all_actors.containsKey(actor)){
				Pair<Integer, Integer> pair = Pair.createPair(all_movies.get(title), all_actors.get(actor));
				System.out.println(pair.getElement0() + " " + pair.getElement1());
				add_to_db.add(pair);
			} 
			inM = false;

//			String[] sn_pieces = actor.trim().split(" ");
//			String fn = null;
//			String ln = null;
//			if (sn_pieces.length > 2) {
//				fn = ActorParser.javaStringLiteral(sn_pieces[0]);
//				ln = "";
//				for (int i = 1; i < sn_pieces.length; ++i)
//					ln += sn_pieces[i].trim() + " ";
//				ln = ActorParser.javaStringLiteral(ln.trim());
//			} else if (sn_pieces.length == 2) {
//				fn = ActorParser.javaStringLiteral(sn_pieces[0]);
//				ln = ActorParser.javaStringLiteral(sn_pieces[1]);
//			}
//			
//			try {
//				Class.forName("com.mysql.jdbc.Driver");
//				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb?useSSL=false","root", "root");
////				System.out.println("connected to database");
//				
//				Statement actor_state = conn.createStatement();
//				Statement movie_state = conn.createStatement();
//				title = ActorParser.javaStringLiteral(title);
//				String actor_query = "SELECT id FROM stars WHERE first_name='" + fn + "' AND last_name ='" + ln + "'";
//				String movie_query = "SELECT id FROM movies WHERE title='" + title + "' AND director='" + director + "'";
//				System.out.println(actor_query);
//				
//				ResultSet check_actor = actor_state.executeQuery(actor_query);
//				ResultSet check_movie = movie_state.executeQuery(movie_query);
//				
//				if (check_actor.next() && check_movie.next()) {
//					Pair<Integer,Integer> id_pair = Pair.createPair(check_actor.getInt(1), check_movie.getInt(1));
//					id_list.add(id_pair);
//				}
//				conn.close();
//				
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
			
		}
	}
	
	
	
	public static void main(String[] args){
		System.out.println("parsing casts124.xml . . .");
		castParser cp = new castParser();
		cp.runExample();
		try {
			PrintWriter clear = new PrintWriter("s_in_m_info.csv", "UTF-8");
			clear.print("");
			clear.close();
			
			PrintWriter writer = new PrintWriter("s_in_m_info.csv", "UTF-8");
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb?useSSL=false","root", "root");
			System.out.println("connected to database");
			
			for(int i = 0; i < cp.add_to_db.size()-1; ++i) {
				Statement s = conn.createStatement();
				String checkpair = "SELECT * FROM stars_in_movies WHERE star_id=" + cp.add_to_db.get(i).getElement1() + " AND movie_id=" + cp.add_to_db.get(i).getElement0();
				ResultSet r = s.executeQuery(checkpair);
				if(!r.next())
					writer.append(cp.add_to_db.get(i).getElement1() + "," + cp.add_to_db.get(i).getElement0() + ",\n");
			}
			
			Statement s = conn.createStatement();
			String checkpair = "SELECT * FROM stars_in_movies WHERE star_id=" + cp.add_to_db.get(cp.add_to_db.size()-1).getElement1() + " AND movie_id=" + cp.add_to_db.get(cp.add_to_db.size()-1).getElement0();
			ResultSet r = s.executeQuery(checkpair);
			if(!r.next())
				writer.append(cp.add_to_db.get(cp.add_to_db.size()-1).getElement1() + "," + cp.add_to_db.get(cp.add_to_db.size()-1).getElement0());
			writer.close();

			
			String data_query = "LOAD DATA LOCAL INFILE \'s_in_m_info.csv\' INTO TABLE stars_in_movies FIELDS TERMINATED BY \',\' (star_id, movie_id)";
			Statement load_stuff = conn.createStatement();
			load_stuff.executeUpdate(data_query);
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("finished parsing");
	}
}
