package accesstopostgre;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgreSQLConnection {

	public PostgreSQLConnection() throws FileNotFoundException {
		try {
			// connection au base de donnee postgresql
			System.out.println(MicrosoftAccessConnection.ACCDB);
			String url = "jdbc:postgresql://localhost/"+ MicrosoftAccessConnection.ACCDB.toLowerCase();
			Connection conn = DriverManager.getConnection(url, EmptyPostgresDB.username, EmptyPostgresDB.mdp);
			Statement st = conn.createStatement();
			// execution des requetes
			System.out.println(MicrosoftAccessConnection.createTableQuery);
			System.out.println(MicrosoftAccessConnection.addconstraints);
			System.out.println(MicrosoftAccessConnection.insertInto);
			st.executeUpdate(MicrosoftAccessConnection.createTableQuery);
			st.executeUpdate(MicrosoftAccessConnection.insertInto);
			st.executeUpdate(MicrosoftAccessConnection.addconstraints);
			
		} catch (SQLException e) {
			System.out.println("connexion postgre échoué");
			e.printStackTrace();

		}
	}

}
