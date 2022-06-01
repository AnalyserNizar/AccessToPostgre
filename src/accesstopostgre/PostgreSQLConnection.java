package accesstopostgre;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class PostgreSQLConnection {
	private String[] options = { "Convertir une autre Base de donnée", "Fermer" };

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
			int x = JOptionPane.showOptionDialog(null, "Voulez vous convertir une autre table?",
					"La base de donnée a été crée", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
					options, options[0]);
			if (x == 1 || x == -1) {
				System.exit(0);
			}
		} catch (SQLException e) {
			System.out.println("connexion postgre échoué");
			e.printStackTrace();

		}
	}

}
