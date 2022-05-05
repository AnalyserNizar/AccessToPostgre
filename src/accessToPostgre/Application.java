
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
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class Application {

	public static void main(String[] args) throws InterruptedException, SQLException {

		LookAndFeel style = new LookAndFeel();

		JFileChooser chooser = FileChooser.createFilePicker();

		String[] url = FileChooser.dBurlString.split(Pattern.quote(File.separator));
		String url2 = url[url.length - 1].toString();
		JDBCMicrosoftAccessConnection.ACCDB = url2.split("[.]")[0];
		System.out.println(JDBCMicrosoftAccessConnection.ACCDB);
		System.out.println("Connecting to database...");
		Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/", "postgres", "root");
		Statement st = conn.createStatement();

		try {
			st.executeUpdate("CREATE DATABASE " + JDBCMicrosoftAccessConnection.ACCDB.toLowerCase() + ";");
		} finally {
			st.close();
			conn.close();
		}

		JDBCMicrosoftAccessConnection connect = new JDBCMicrosoftAccessConnection(chooser);

		JDBCPostgreSQLConnection connect2 = new JDBCPostgreSQLConnection();

		System.out.println("end of program");

	}
}
