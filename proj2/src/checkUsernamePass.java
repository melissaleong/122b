import java.sql.*;

public class checkUsernamePass {
	public static boolean checkUser(String email, String password) {

		boolean answer = false;
		try {
			System.out.printf("%s %s", email, password);
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb", "root", "root");
			Statement select = connection.createStatement();
			String query = "SELECT * FROM customers WHERE email='" + email + "' AND password='" + password + "'";
			ResultSet check = select.executeQuery(query);
			
			answer = check.next();
			System.out.println(answer);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return answer;
	}

}
