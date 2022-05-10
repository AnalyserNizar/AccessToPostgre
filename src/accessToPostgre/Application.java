
package accessToPostgre;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import javax.swing.JFileChooser;
import java.io.File;
import java.util.regex.Pattern;

public class Application {
	static Scanner scan = new Scanner(System.in); 
	public static String username;
	public static String mdp;

	public static void main(String[] args) throws InterruptedException, SQLException {

		new LookAndFeel();

		JFileChooser chooser = FileChooser.createFilePicker();
		String[] url = FileChooser.dBurlString.split(Pattern.quote(File.separator));
		String url2 = url[url.length - 1].toString();
		JDBCMicrosoftAccessConnection.ACCDB = url2.split("[.]")[0];
		System.out.println(JDBCMicrosoftAccessConnection.ACCDB);
		System.out.println("entrer votre username postgre");
		username = scan.next();
		System.out.println("entrer votre mot de passe postgre");
		mdp = scan.next();
		System.out.println("Connection a la base de donnee");
		Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/", "postgres", mdp);
		Statement st = conn.createStatement();

		/*try {
			st.executeUpdate("CREATE DATABASE " + JDBCMicrosoftAccessConnection.ACCDB.toLowerCase() + ";");
		} finally {
			st.close();
			conn.close();
		}*/

		new JDBCMicrosoftAccessConnection(chooser);

		new JDBCPostgreSQLConnection();

		System.out.println("fin du programme");

	}
}
