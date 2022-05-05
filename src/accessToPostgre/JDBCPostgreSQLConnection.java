package accessToPostgre;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCPostgreSQLConnection {

	public JDBCPostgreSQLConnection() {
		try {
			// connection a postgresql
			System.out.println("Connecting to database...");
			String a = JDBCMicrosoftAccessConnection.ACCDB;
			System.out.println("oooooooo");
			System.out.println(JDBCMicrosoftAccessConnection.ACCDB);
			String url = "jdbc:postgresql://localhost/" + JDBCMicrosoftAccessConnection.ACCDB.toLowerCase();
			Connection conn = DriverManager.getConnection(url, "postgres", "root");
			Statement st = conn.createStatement();
			// execution des requetes
			st.executeUpdate(JDBCMicrosoftAccessConnection.createTableQuery);
			System.out.println(JDBCMicrosoftAccessConnection.createTableQuery);

		} catch (SQLException e) {
			System.out.println("connection failed");
			e.printStackTrace();

		}
	}

}
