import java.sql.*;
import java.util.Scanner;
import java.math.BigInteger;

public class insertCustomer {
	insertCustomer(){};
	
	public void run(Connection connection) throws Exception{	
		Statement select = connection.createStatement();
		
		Scanner in= new Scanner(System.in);
//		System.out.println("Please enter a new id: ");
//		String id = in.nextLine();
		
		System.out.println("Please enter First name: ");
		String first_name = in.nextLine();
//		System.out.println("first" + first_name);
		System.out.println("Please enter Last name: ");
		String last_name = in.nextLine();
//		System.out.println(last_name);
		
		//check to see name is only one word
		if (last_name.equals("")){
			last_name = first_name;
			first_name = "";
		}
		
		System.out.println("Please enter your address: ");
		String address = in.nextLine();
		System.out.println("Please enter your email: ");
		String email = in.nextLine();
		System.out.println("Pleaes enter your password: ");
		String password = in.nextLine();
		
		System.out.println("");
		
		String checkCustomerCC = "SELECT * FROM creditcards WHERE first_name = '"+ first_name + "' AND last_name = '" + last_name + "'";
		
		ResultSet check = select.executeQuery(checkCustomerCC);
	
		
		//checks to see if customer is in credit card database
		if (!check.next()){
			System.out.println("User is not in credit card data base. Did not add");
		}else{
			System.out.println("here");
			String creditCardInfo = check.getString(1);
			System.out.println(creditCardInfo);
			
			String insertCustomerQuery = "INSERT INTO customers(first_name, last_name, cc_id, address, email, password) VALUES(?, ?, ?, ?, ?, ?)";
			
			PreparedStatement prepState = connection.prepareStatement(insertCustomerQuery);
			
			
			prepState.setString(1, first_name);
			prepState.setString(2, last_name);
			prepState.setString(3, creditCardInfo);
			prepState.setString(4, address);
			prepState.setString(5, email);
			prepState.setString(6, password);
			
			prepState.execute();
		}
	}
}
