import java.sql.*;
import java.util.Scanner;

public class deleteCustomer {
	
	deleteCustomer(){};
	
	public void run(Connection connection) throws Exception{
		Statement select = connection.createStatement();
		Scanner in= new Scanner(System.in);
		System.out.println("Please enter the id of the customer that you want to delete: ");
		int customerID = in.nextInt();
		
		  Statement update = connection.createStatement();
		  String queryString4 = "delete from customers where id = " + customerID;
		  int retID = update.executeUpdate(queryString4);
		  System.out.println("retID = " + retID);
	}
}