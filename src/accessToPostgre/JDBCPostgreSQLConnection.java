package accessToPostgre;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCPostgreSQLConnection {
	{
		
	

		try {	
			// connection a postgresql
			System.out.println("Connecting to database...");
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/db1","postgres","pfe");
			Statement st = conn.createStatement();
			// execution des requetes
			//st.execute(JDBCMicrosoftAccessConnection.createDbQuery);
			//conn.commit();
			st.executeUpdate(JDBCMicrosoftAccessConnection.createTableQuery);
			System.out.println(JDBCMicrosoftAccessConnection.createTableQuery);
		} catch (SQLException e) {
			System.out.println("connection failed");
			e.printStackTrace();
			// TODO: handle exception
		}

	}
}

