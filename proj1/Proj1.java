
import java.util.Scanner;
import java.sql.*;

public class Proj1 {
	public static void main(String args[]) throws Exception{
		try{
			while (true){
				try{
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					System.out.println("==Welcome to the JDBC interface==");
					Scanner input = new Scanner(System.in);
					System.out.println("Please enter the database you want to connect to: \n personal: localhost:3306/moviedb");
					String databaseName = input.nextLine();
					System.out.println("Please enter username: ");
					String username = input.nextLine();
					System.out.println("Please enter password: ");
					String password = input.nextLine();
					Connection connection = DriverManager.getConnection("jdbc:mysql://" + databaseName,username, password);

					while(true){
						System.out.println("Please enter number of the MySql command that you want to run: ");
						System.out.println("List of Commands: \n (1)printmoviesfeaturingstar \n (2)insertnewstar \n (3)insertcustomer \n (4)deletecustomer \n (5)printmetadata \n (6)customcommand \n (7)exit ");
						Integer command = input.nextInt();
						
						if (command == 1){
							printMoviesFeaturingStar Query1 = new printMoviesFeaturingStar();
							Query1.run(connection);
						}else if (command == 2){
							insertNewStar Query2 = new insertNewStar();
							Query2.run(connection);
						}else if (command == 3){
							insertCustomer Query3 = new insertCustomer();
							Query3.run(connection);
						}else if (command == 4){					
							deleteCustomer Query4 = new deleteCustomer();
							Query4.run(connection);
						}else if (command == 5){
							printMetadata Query5 = new printMetadata();
							Query5.run(connection);
						}else if (command == 6){
							customQuery Query6 = new customQuery();
							Query6.run(connection);
						}
						else if(command == 7){
							System.out.println("exiting main menu....");
							break;
						}
	
					}
					connection.close();
					Scanner exit = new Scanner(System.in);
					System.out.println("Would you like to continue? (yes/no)");
					String inputString = exit.nextLine();
					exit.close();
					if (inputString.equalsIgnoreCase("no")){
						System.out.println("Exiting....");
						break;
					}
				}catch(Exception e){
					System.out.println("Incorrect inputs. Please try again...");
					continue;
				}
			}
		}catch (Exception e){	
			e.printStackTrace();
		}		
	}
}
