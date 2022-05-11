package accesstopostgre;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgreSQLConnection {

	public PostgreSQLConnection() {
		try {
			// connection au base de donnee postgresql
			System.out.println("Connection a la base de donnee...");
			System.out.println(MicrosoftAccessConnection.ACCDB);
			String url = "jdbc:postgresql://localhost/" + MicrosoftAccessConnection.ACCDB.toLowerCase();
			Connection conn = DriverManager.getConnection(url, EmptyPostgresDB.username, EmptyPostgresDB.mdp);
			Statement st = conn.createStatement();
			// execution des requetes
			st.executeUpdate(MicrosoftAccessConnection.createTableQuery);
			System.out.println(MicrosoftAccessConnection.createTableQuery);

		} catch (SQLException e) {
			System.out.println("connexion postgre échoué");
			e.printStackTrace();

		}
	}

}
