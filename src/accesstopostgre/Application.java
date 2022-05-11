
package accesstopostgre;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import javax.swing.JFileChooser;
import java.io.File;
import java.util.regex.Pattern;

public class Application {
	public static void fin() {
		System.out.println("fin du programme");
	}

	public static void main(String[] args) throws InterruptedException, SQLException {

		// modifier le theme
		new LookAndFeel();
		FileChooser.createFilePicker();
		new EmptyPostgresDB();
		new MicrosoftAccessConnection();
		new PostgreSQLConnection();
		fin();

	}

}
