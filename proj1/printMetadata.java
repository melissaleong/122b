import java.sql.*;

public class printMetadata {
	
	printMetadata(){};
	
	public void run(Connection connection) throws Exception{
		
		System.out.println("Metadata of database Moviedb: ");
		System.out.println("=============================");
		printMovieData(connection);
		printStarsData(connection);
		printStarsinMoviesData(connection);
		printGenresData(connection);
		printGenresinMoviesData(connection);
		printCreditcardsData(connection);
		printCustomersData(connection);
		printSalesData(connection);
		
	}
	
	public void printMovieData(Connection connection) throws Exception{
		Statement select = connection.createStatement();
		ResultSet result = select.executeQuery("Select * from movies");
		System.out.println("Results of the Movie Query: ");
		ResultSetMetaData metadata = result.getMetaData();
		System.out.println("There are " + metadata.getColumnCount() + " columns");
		
		for (int i = 1; i <= metadata.getColumnCount(); i++){
			System.out.println("Type of column "+ i + " is " + metadata.getColumnTypeName(i));
		}
		System.out.println("-------------------------------------------------");
		System.out.println();
	}
	
	public void printStarsData(Connection connection)throws Exception{
		Statement select = connection.createStatement();
		ResultSet result = select.executeQuery("Select * from stars");
		System.out.println("Results of the Star Query: ");
		ResultSetMetaData metadata = result.getMetaData();
		System.out.println("There are " + metadata.getColumnCount() + " columns");
		
		for (int i = 1; i <= metadata.getColumnCount(); i++){
			System.out.println("Type of column "+ i + " is " + metadata.getColumnTypeName(i));
		}
		System.out.println("-------------------------------------------------");
		System.out.println();
	}
	
	public void printStarsinMoviesData(Connection connection)throws Exception{
		Statement select = connection.createStatement();
		ResultSet result = select.executeQuery("Select * from stars_in_movies");
		System.out.println("Results of the stars_in_movies Query: ");
		ResultSetMetaData metadata = result.getMetaData();
		System.out.println("There are " + metadata.getColumnCount() + " columns");
		
		for (int i = 1; i <= metadata.getColumnCount(); i++){
			System.out.println("Type of column "+ i + " is " + metadata.getColumnTypeName(i));
		}
		System.out.println("-------------------------------------------------");
		System.out.println();
	}
	
	public void printGenresData(Connection connection)throws Exception{
		Statement select = connection.createStatement();
		ResultSet result = select.executeQuery("Select * from genres");
		System.out.println("Results of the genre Query: ");
		ResultSetMetaData metadata = result.getMetaData();
		System.out.println("There are " + metadata.getColumnCount() + " columns");
		
		for (int i = 1; i <= metadata.getColumnCount(); i++){
			System.out.println("Type of column "+ i + " is " + metadata.getColumnTypeName(i));
		}
		System.out.println("-------------------------------------------------");
		System.out.println();
	}
	
	public void printGenresinMoviesData(Connection connection)throws Exception{
		Statement select = connection.createStatement();
		ResultSet result = select.executeQuery("Select * from genres_in_movies");
		System.out.println("Results of the genres_in_movies Query: ");
		ResultSetMetaData metadata = result.getMetaData();
		System.out.println("There are " + metadata.getColumnCount() + " columns");
		
		for (int i = 1; i <= metadata.getColumnCount(); i++){
			System.out.println("Type of column "+ i + " is " + metadata.getColumnTypeName(i));
		}
		System.out.println("-------------------------------------------------");
		System.out.println();
	}
	
	public void printCreditcardsData(Connection connection)throws Exception{
		Statement select = connection.createStatement();
		ResultSet result = select.executeQuery("Select * from creditcards");
		System.out.println("Results of the creditcards Query: ");
		ResultSetMetaData metadata = result.getMetaData();
		System.out.println("There are " + metadata.getColumnCount() + " columns");
		
		for (int i = 1; i <= metadata.getColumnCount(); i++){
			System.out.println("Type of column "+ i + " is " + metadata.getColumnTypeName(i));
		}
		System.out.println("-------------------------------------------------");
		System.out.println();
	}
	
	public void printCustomersData(Connection connection)throws Exception{
		Statement select = connection.createStatement();
		ResultSet result = select.executeQuery("Select * from customers");
		System.out.println("Results of the customers Query: ");
		ResultSetMetaData metadata = result.getMetaData();
		System.out.println("There are " + metadata.getColumnCount() + " columns");
		
		for (int i = 1; i <= metadata.getColumnCount(); i++){
			System.out.println("Type of column "+ i + " is " + metadata.getColumnTypeName(i));
		}
		System.out.println("-------------------------------------------------");
		System.out.println();
	}
	
	public void printSalesData(Connection connection)throws Exception{
		Statement select = connection.createStatement();
		ResultSet result = select.executeQuery("Select * from sales");
		System.out.println("Results of the sales Query: ");
		ResultSetMetaData metadata = result.getMetaData();
		System.out.println("There are " + metadata.getColumnCount() + " columns");
		
		for (int i = 1; i <= metadata.getColumnCount(); i++){
			System.out.println("Type of column "+ i + " is " + metadata.getColumnTypeName(i));
		}
		System.out.println("-------------------------------------------------");
		System.out.println();
	}
}