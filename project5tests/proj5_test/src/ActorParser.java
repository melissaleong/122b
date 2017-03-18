import HelperClasses.*;
import java.io.IOException;
import java.io.PrintWriter;
//import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.sql.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class ActorParser {

	//No generics
	ArrayList<Star> myActors;
	Document dom;


	public ActorParser(){
		//create a list to hold the employee objects
		myActors = new ArrayList<Star>();
	}

	public void runExample() {
		
		//parse the xml file and get the dom object
		parseXmlFile();
		
		//get each employee element and create a Employee object
		parseDocument();
		
		System.out.println("Parsing actors63.xml . . .");
		//create an instance
//		ActorParser dpe = new ActorParser();
//		//call run example
//		dpe.runExample();
		Connection conn = null;
		try {
			// clear the csv file before writing to it
			PrintWriter clear = new PrintWriter("actor_info.csv", "UTF-8");
			clear.print("");
			clear.close();
			
			// begin writing out to the csv file
			PrintWriter writer = new PrintWriter("actor_info.csv", "UTF-8");
			//set up connection
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb?useSSL=false","root", "root1234");
			System.out.println("connected to database");
			int actors_size = myActors.size();
			
			// go through the actor list and begin adding actors that are not already in the database
			for(int i = 0; i < actors_size-1; ++i) {
				Statement statement = conn.createStatement();
				
				String first_name = (myActors.get(i).fn);
				if (!first_name.contains("\\")) {
					first_name = javaStringLiteral(first_name);
				}
				String last_name = javaStringLiteral(myActors.get(i).ln);
				if (!last_name.contains("\\")) {
					last_name = javaStringLiteral(last_name);
				}
				String query = "SELECT * FROM stars WHERE first_name='" + first_name + "' AND last_name='" + last_name + "'";
				ResultSet check = statement.executeQuery(query);
				if (check.next()) {
					continue;
				} else {
					if(!first_name.equals(""))
						writer.append(first_name + ",," + last_name + ",,\n");
					else
						writer.append("NULL,," + last_name + ",,\n");
				}
				
				
				
			}
			
			Statement statement = conn.createStatement();
			String first_name = javaStringLiteral(myActors.get(actors_size-1).fn);
			String last_name = javaStringLiteral(myActors.get(actors_size-1).ln);
			String query = "SELECT * FROM stars WHERE first_name='" + first_name + "' AND last_name='" + last_name + "'";
			ResultSet check = statement.executeQuery(query);
			if (!check.next())
			{ 
				if(!first_name.equals(""))
					writer.append(first_name + ",," + last_name);
				else
					writer.append("NULL," + last_name);
			}
			writer.close();
			
			String data_query = "LOAD DATA LOCAL INFILE \'actor_info.csv\' INTO TABLE stars FIELDS TERMINATED BY \',,\' (first_name, last_name) SET ID=NULL";
			Statement load_stuff = conn.createStatement();
			load_stuff.executeUpdate(data_query);
			
			conn.close();
			System.out.println("Finished parsing actors63.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Iterate through the list and print the data
//		printData();
		
	}
	
	public static String javaStringLiteral(String str)
	{
	    StringBuilder sb = new StringBuilder();
	    for (int i=0; i<str.length(); i++)
	    {
	        char c = str.charAt(i);
	        if (c == '\n')
	        {
	            sb.append("\\n");
	        }
	        else if (c == '\r')
	        {
	            sb.append("\\r");
	        }
	        else if (c == '"')
	        {
	            sb.append("\\\"");
	        }
	        else if (c == '\'')
	        {
	        	sb.append("\\\'");
	        }
	        else if (c == '\\')
	        {
	            sb.append("\\\\");
	        }
	        else if (c < 0x20)
	        {
	            sb.append(String.format("\\%03o", (int)c));
	        }
	        else if (c >= 0x80)
	        {
	            sb.append(String.format("\\u%04x", (int)c));
	        }
	        else
	        {               
	            sb.append(c);
	        }
	    }
	    return sb.toString();
	}
	private void parseXmlFile(){
		//get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		try {
			
			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			//parse using builder to get DOM representation of the XML file
			dom = db.parse("actors63.xml");
			

		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}

	
	private void parseDocument(){
		//get the root elememt
		Element docEle = dom.getDocumentElement();
		
		//get a nodelist of <employee> elements
		NodeList nl = docEle.getElementsByTagName("actor");
		if(nl != null && nl.getLength() > 0) {
			for(int i = 0 ; i < nl.getLength();i++) {
				
				//get the employee element
				Element el = (Element)nl.item(i);
				
				//get the Employee object
				Star e = getActor(el);
				
				//add it to list
				if (!(e.fn == null && e.ln == null))
					myActors.add(e);
			}
		}
	}


	/**
	 * @param actorEl
	 * @return
	 */
	private Star getActor(Element actorEl) {

		String fn = null;
		String ln = null;
		
		NodeList stagename = actorEl.getElementsByTagName("stagename");

		Node n = stagename.item(0);
		Node sn_child = n.getFirstChild();
		
		String[] sn_pieces = sn_child.getTextContent().trim().split(" ");
		
		if (sn_pieces.length > 2) {
			fn = sn_pieces[0];
			ln = "";
			for (int i = 1; i < sn_pieces.length; ++i)
				ln += sn_pieces[i].trim() + " ";
			ln = ln.trim();
		} else if (sn_pieces.length == 2) {
			fn = sn_pieces[0];
			ln = sn_pieces[1];
		}


		Star actor = new Star();
		actor.fn = fn;
		actor.ln = ln;



		
		return actor;
	}

	
	/**
	 * Iterate through the list and print the 
	 * content to console
	 */
	private void printData(){
		
		System.out.println("No of Actors '" + myActors.size() + "'.");
		
		Iterator it = myActors.iterator();
		while(it.hasNext()) {
			System.out.println(it.next().toString());
		}
	}

	
//	public static void main(String[] args){
////		System.out.println("Parsing actors63.xml . . .");
//		//create an instance
//		ActorParser dpe = new ActorParser();
//		//call run example
//		dpe.runExample();
////		Connection conn = null;
////		try {
////			// clear the csv file before writing to it
////			PrintWriter clear = new PrintWriter("actor_info.csv", "UTF-8");
////			clear.print("");
////			clear.close();
////			
////			// begin writing out to the csv file
////			PrintWriter writer = new PrintWriter("actor_info.csv", "UTF-8");
////			//set up connection
////			Class.forName("com.mysql.jdbc.Driver");
////			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb?useSSL=false","root", "root1234");
////			System.out.println("connected to database");
////			int actors_size = dpe.myActors.size();
////			
////			// go through the actor list and begin adding actors that are not already in the database
////			for(int i = 0; i < actors_size-1; ++i) {
////				Statement statement = conn.createStatement();
////				
////				String first_name = (dpe.myActors.get(i).getFN());
////				if (!first_name.contains("\\")) {
////					first_name = javaStringLiteral(first_name);
////				}
////				String last_name = javaStringLiteral(dpe.myActors.get(i).getLN());
////				if (!last_name.contains("\\")) {
////					last_name = javaStringLiteral(last_name);
////				}
////				String query = "SELECT * FROM stars WHERE first_name='" + first_name + "' AND last_name='" + last_name + "'";
////				ResultSet check = statement.executeQuery(query);
////				if (check.next()) {
////					continue;
////				} else {
////					if(!first_name.equals(""))
////						writer.append(first_name + ",," + last_name + ",,\n");
////					else
////						writer.append("NULL,," + last_name + ",,\n");
////				}
////				
////				
////				
////			}
////			
////			Statement statement = conn.createStatement();
////			String first_name = javaStringLiteral(dpe.myActors.get(actors_size-1).getFN());
////			String last_name = javaStringLiteral(dpe.myActors.get(actors_size-1).getLN());
////			String query = "SELECT * FROM stars WHERE first_name='" + first_name + "' AND last_name='" + last_name + "'";
////			ResultSet check = statement.executeQuery(query);
////			if (!check.next())
////			{ 
////				if(!first_name.equals(""))
////					writer.append(first_name + ",," + last_name);
////				else
////					writer.append("NULL," + last_name);
////			}
////			writer.close();
////			
////			String data_query = "LOAD DATA LOCAL INFILE \'actor_info.csv\' INTO TABLE stars FIELDS TERMINATED BY \',,\' (first_name, last_name) SET ID=NULL";
////			Statement load_stuff = conn.createStatement();
////			load_stuff.executeUpdate(data_query);
////			
////			conn.close();
////			System.out.println("Finished parsing actors63.xml");
////		} catch (Exception e) {
////			e.printStackTrace();
////		}
//		
//	}

}