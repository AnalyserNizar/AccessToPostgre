
package accessToPostgre;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;

public class Application {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		LookAndFeel style = new LookAndFeel();
		
		JFileChooser chooser = FileChooser.createFilePicker();
		
		JDBCMicrosoftAccessConnection connect = new JDBCMicrosoftAccessConnection(chooser);
		
		JDBCPostgreSQLConnection  connect2 = new JDBCPostgreSQLConnection();

		
		System.out.println("end of program");

	}
}
