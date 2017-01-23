import java.sql.*;
import java.util.Scanner;
import java.util.HashMap;


public class customQuery{
	public customQuery() {}
	
	public void run(Connection connection) throws Exception{
		try{
			while (true){
				Scanner sc = new Scanner(System.in);
				System.out.println("Please enter in a valid SQL query: ");
				String query = sc.nextLine();
				if (query.equalsIgnoreCase("quit")){
					System.out.println("Now Exiting...");
					break;
				}
				
				// this is to get table we are inserting into
				String[] queryPieces = query.split(" ");
		//		System.out.println(queryPieces[2]);
				String[] moreQueryPieces = queryPieces[2].split("\\(");
				
				if (queryPieces[0].equalsIgnoreCase("select")) {
					querySelect(query, connection);
					
				
				} else if (queryPieces[0].equalsIgnoreCase("insert")) {
					queryInsert(query, moreQueryPieces, connection);
		
				} else if (queryPieces[0].equalsIgnoreCase("update")) {
					queryUpdate(query, connection);
				}
				else if (queryPieces[0].equalsIgnoreCase("delete")){
					queryDelete(query, connection);
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		} 
	}
	
	public void querySelect(String query, Connection connection) throws Exception {
		Statement select = connection.createStatement();
		ResultSet result = select.executeQuery(query);
		
		ResultSetMetaData metadata = result.getMetaData();
		
		HashMap<Integer, String> metadataColumns = new HashMap<Integer,String>();
		
		for (int i = 1; i <= metadata.getColumnCount(); ++i)
			metadataColumns.put(i, metadata.getColumnName(i));
		
		while(result.next()) {
			for (int i = 1; i <= metadataColumns.size(); ++i)
				System.out.println(metadataColumns.get(i) + ": " + result.getString(i));
			System.out.println("--------------------------------------");
		}
	}
	
	public void queryInsert(String query, String[] pieces, Connection connection) throws Exception {
		//do all insert statements follow the same format???
		String tableName = pieces[0];		
		System.out.println(tableName);
		Statement select = connection.createStatement();
		

		ResultSet result = select.executeQuery("select count(*) as rowcount from " + tableName);
		result.next();
		int count = result.getInt("rowcount");
		System.out.println(tableName + " has " + count + " rows.");
		
//		
//		ResultSet result = select.executeQuery("select * from " + tableName);
//		ResultSetMetaData metadata = result.getMetaData();
//		int columnCount = metadata.getColumnCount();

		Statement st = connection.createStatement();
		st.executeUpdate(query);
		
//		PreparedStatement prepState = connection.prepareStatement(query);
//		prepState.execute();
		System.out.println("Added row to table");
		
//		ResultSet afterResult = select.executeQuery("select * from " + tableName);
//		ResultSetMetaData metadata1 = afterResult.getMetaData();
//		int columnCountAfter = metadata1.getColumnCount();
		
		result = select.executeQuery("select count(*) as rowcount from " + tableName);
		result.next();
		count = result.getInt("rowcount");
		System.out.println(tableName + " has " + count + " rows.");
	}
	
	public void queryUpdate(String query, Connection connection) throws Exception {
		Statement update = connection.createStatement();
		
		int numUpdated = update.executeUpdate(query);
		
		System.out.print(numUpdated + " rows updated");
//		String whereClause = "";
//		String pattern = "(update) .+ where (.+)";
//		Pattern r = Pattern.compile(pattern);
//		Matcher m = r.matcher(query);
//		if (m.find()) {
//			whereClause = m.group(2);
//		}	
	}
	
	public void queryDelete(String query, Connection connection) throws Exception{
		
		Statement delete = connection.createStatement();
		int retID = delete.executeUpdate(query);
		System.out.println("retID = " + retID);
		if (retID== 1){
			System.out.println("--Deletion successful--");
		} else{
			System.out.println("--Deletion not successful--");
		}
	}
	
		
	
}
