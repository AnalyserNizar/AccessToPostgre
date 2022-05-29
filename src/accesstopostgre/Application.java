
package accesstopostgre;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import javax.swing.JFileChooser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Pattern;

public class Application {
	public static void fin() {
		System.out.println("fin du programme");
	}

	public static void main(String[] args) throws InterruptedException {

		// modifier le theme
		
		new LookAndFeel();
		FileChooser.createFilePicker();
		try {
			new EmptyPostgresDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			new MicrosoftAccessConnection();
		} catch (InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			new PostgreSQLConnection();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fin();

	}

}
