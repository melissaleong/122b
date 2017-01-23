
import java.util.Scanner;
import java.sql.*;

public class printMoviesFeaturingStar {
	
	public void run(Connection connection)throws Exception{
		Statement select = connection.createStatement();
		
		Scanner in = new Scanner(System.in);
		
		System.out.println("Search by Name or Search by ID? ");
		String searchType = in.nextLine();
		if (searchType.equalsIgnoreCase("name")){
			System.out.println("Search by firstname, lastname, or both? ");
			String nameSearch = in.nextLine();
			if (nameSearch.equalsIgnoreCase("both")){
				System.out.println("Please Enter first name: ");
				String first_name = in.nextLine();
				System.out.println("Please Enter last name: ");
				String last_name = in.nextLine();
				
				String queryString1 = "SELECT DISTINCT movies.id, movies.title, movies.year, movies.director FROM (movies CROSS JOIN stars) CROSS JOIN stars_in_movies WHERE (stars_in_movies.star_id = stars.id AND stars_in_movies.movie_id =movies.id "
						+ "AND first_name = '" + first_name +"' AND last_name = '"+ last_name +"')";
				ResultSet result = select.executeQuery(queryString1);
				while (result.next()){
					System.out.println("Id = " + result.getInt(1) + "| Title = " + result.getString(2));
					System.out.println("Year = " + result.getString(3) + "| Director = " + result.getString(4));
					System.out.println("-----------------------------------");
				}
			}else if (nameSearch.equalsIgnoreCase("firstname")){
				System.out.println("Please Enter first name: ");
				String first_name = in.nextLine();
				
				String queryString1 = "SELECT DISTINCT movies.id, movies.title, movies.year, movies.director FROM (movies CROSS JOIN stars) CROSS JOIN stars_in_movies WHERE (stars_in_movies.star_id = stars.id AND stars_in_movies.movie_id =movies.id "
						+ "AND first_name = '" + first_name +"')";
				ResultSet result = select.executeQuery(queryString1);
				while (result.next()){
					System.out.println("Id = " + result.getInt(1) + "| Title = " + result.getString(2));
					System.out.println("Year = " + result.getString(3) + "| Director = " + result.getString(4));
					System.out.println("-----------------------------------");
				}
			}else if (nameSearch.equalsIgnoreCase("lastname")){
				System.out.println("Please Enter last name: ");
				String last_name = in.nextLine();
				
				String queryString1 = "SELECT DISTINCT movies.id, movies.title, movies.year, movies.director FROM (movies CROSS JOIN stars) CROSS JOIN stars_in_movies WHERE (stars_in_movies.star_id = stars.id AND stars_in_movies.movie_id =movies.id "
						+ "AND last_name = '" + last_name +"')";
				ResultSet result = select.executeQuery(queryString1);
				while (result.next()){
					System.out.println("Id = " + result.getInt(1) + "| Title = " + result.getString(2));
					System.out.println("Year = " + result.getString(3) + "| Director = " + result.getString(4));
					System.out.println("-----------------------------------");
				}
			} else{
				System.out.println("Incorrect input. Exiting...");
			}
			
		}else if (searchType.equalsIgnoreCase("id")){
			System.out.println("Please enter star ID: ");
			Integer starID = in.nextInt();
			String queryString1 = "SELECT DISTINCT movies.id, movies.title, movies.year, movies.director FROM (movies CROSS JOIN stars) CROSS JOIN stars_in_movies WHERE (stars_in_movies.star_id = stars.id AND stars_in_movies.movie_id =movies.id "
					+ "AND stars.id = " + starID + ")";
			ResultSet result = select.executeQuery(queryString1);
			
			while (result.next()){
				System.out.println("Id = " + result.getInt(1) + "| Title = " + result.getString(2));
				System.out.println("Year = " + result.getString(3) + "| Director = " + result.getString(4));
				System.out.println("-----------------------------------");
			}
		} else{
			System.out.println("Incorrect input. Exiting...");
		}
	}
	
	printMoviesFeaturingStar(){};
};
