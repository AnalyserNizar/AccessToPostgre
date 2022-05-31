package accesstopostgre;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class PostgreSQLConnection {

	public PostgreSQLConnection() {
		try {
			// connection au base de donnee postgresql
			String url = "jdbc:postgresql://localhost/" + MicrosoftAccessConnection.ACCDB.toLowerCase();
			Connection conn = DriverManager.getConnection(url, InterfaceGraphique.username, InterfaceGraphique.pwd);
			Statement st = conn.createStatement();
			// execution des requetes
			st.executeUpdate(MicrosoftAccessConnection.createTableQuery);
			st.executeUpdate(MicrosoftAccessConnection.insertInto);
			st.executeUpdate(MicrosoftAccessConnection.addconstraints);
		} catch (SQLException e) {
			System.out.println("connexion postgre échoué");
			e.printStackTrace();

		}
	}

}
