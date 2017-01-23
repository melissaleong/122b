import java.util.Scanner;
import java.sql.*;
import java.text.*;
import java.util.Date;

public class insertNewStar {
	insertNewStar(){};
	
	public void run(Connection connection) throws Exception{
		try{
			Statement select = connection.createStatement();
			
			Scanner in= new Scanner(System.in);
			System.out.println("Please enter First name: ");
			String first_name = in.nextLine();
			System.out.println("Please enter Last name: ");
			String last_name = in.nextLine();
			
			//optional inputs for user
			System.out.println("Please enter date of birth (MM/DD/YYYY) (optional-> press enter to skip): ");
			String inputDateOfBirth = in.nextLine();
			
			java.sql.Date dateOfBirth = null; //used for conversion from string to sql.date
			if (!inputDateOfBirth.equals("")){
				dateOfBirth = convertStringtoDate(inputDateOfBirth);
			}
			System.out.println("Please enter Photo URL(optional-> press enter to skip: ");
			String photoURL = in.nextLine();
			
			if (last_name.equals("")){
				last_name = first_name;
				first_name = " ";
			}
			
			if (inputDateOfBirth.equals("") && photoURL.equals("")){
				String queryString2 = "INSERT INTO stars(first_name, last_name) VALUES(?, ?)";
		//		String queryString2 = "INSERT INTO stars(first_name, last_name) VALUES('" + first_name +"', '" + last_name +"')";
				PreparedStatement prepState = connection.prepareStatement(queryString2);
				
				prepState.setString(1, first_name);
				prepState.setString(2, last_name);
				prepState.execute();
			}
			else if (inputDateOfBirth.equals("")){
				String queryString2 = "INSERT INTO stars(first_name, last_name, photo_url) VALUES(?, ?, ?)";
				PreparedStatement prepState = connection.prepareStatement(queryString2);
				
				prepState.setString(1, first_name);
				prepState.setString(2, last_name);
				prepState.setString(3, photoURL);
				prepState.execute();
			}
			else if (photoURL.equals("")){
				String queryString2 = "INSERT INTO stars(first_name, last_name, dob) VALUES(?, ?, ?)";
		//		String queryString2 = "INSERT INTO stars(first_name, last_name) VALUES('" + first_name +"', '" + last_name +"')";
				PreparedStatement prepState = connection.prepareStatement(queryString2);
				
				prepState.setString(1, first_name);
				prepState.setString(2, last_name);
				prepState.setDate(3, dateOfBirth);
				prepState.execute();
			}
			else{
				String queryString2 = "INSERT INTO stars(first_name, last_name, dob, photo_url) VALUES(?, ?, ?, ?)";
				PreparedStatement prepState = connection.prepareStatement(queryString2);
				
				prepState.setString(1, first_name);
				prepState.setString(2, last_name);
				prepState.setDate(3, dateOfBirth);
				prepState.setString(4, photoURL);
				prepState.execute();
			}
			
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public java.sql.Date convertStringtoDate(String dateString){
		try{
			java.util.Date returnDate = new SimpleDateFormat("MM/dd/yyyy").parse(dateString);
			java.sql.Date sqlDate = new java.sql.Date(returnDate.getTime());
			return sqlDate;
		}catch (ParseException e){
			System.out.println("Date is not entered correctly");
			e.printStackTrace();
		}
		return null;
	}
}
