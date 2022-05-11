package accesstopostgre;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.regex.Pattern;

public class EmptyPostgresDB {

	public static String username;
	public static String mdp;

	public EmptyPostgresDB() throws SQLException {
		Scanner scan = new Scanner(System.in);
		// pour recuperer le nom du fichier access
		String[] url = FileChooser.dBurlString.split(Pattern.quote(File.separator));
		String url2 = url[url.length - 1].toString();
		MicrosoftAccessConnection.ACCDB = url2.split("[.]")[0];
		// pour la connection a postgresql
		System.out.println("entrer votre username postgre");
		username = scan.next();
		System.out.println("entrer votre mot de passe postgre");
		mdp = scan.next();
		System.out.println("Connection a la base de donnee");
		Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/", username, mdp);
		Statement st = conn.createStatement();

		// pour la creation de la base de donnee postgre
		try {
			st.executeUpdate("DROP DATABASE IF EXISTS " + MicrosoftAccessConnection.ACCDB.toLowerCase() + ";");
			st.executeUpdate("CREATE DATABASE " + MicrosoftAccessConnection.ACCDB.toLowerCase() + ";");
		} finally {
			st.close();
			conn.close();
		}

		scan.close();
	}

}
