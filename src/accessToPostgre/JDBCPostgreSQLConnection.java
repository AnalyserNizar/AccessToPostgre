package accessToPostgre;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class JDBCPostgreSQLConnection {

	public JDBCPostgreSQLConnection() {
		try {
			// connection a postgresql
			System.out.println("Connecting to database...");
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mydb", "postgres", "root");
			Statement st = conn.createStatement();
			// execution des requetes
			st.executeUpdate(JDBCMicrosoftAccessConnection.createDbQuery);
			st.executeUpdate(JDBCMicrosoftAccessConnection.createTableQuery);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
