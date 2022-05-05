package accessToPostgre;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class JDBCMicrosoftAccessConnection {
	Scanner scan = new Scanner(System.in);
	public String columnName = null;
	public static String createDbQuery =null;
	public static String createTableQuery = "";
	public int columnCount = 0;
	public int taille = 0;
	public static String ACCDB=null;
	public FileChooser chooser;
	
	public JDBCMicrosoftAccessConnection(JFileChooser chooser) throws InterruptedException {

		this.columnName = "";

		// TODO Auto-generated constructor stub
		

		try {
			String[] url = FileChooser.dBurlString.split(Pattern.quote(File.separator));
			String url2 = url[url.length-1].toString();
		    ACCDB = url2.split("[.]")[0];
			System.out.println(ACCDB);
			System.out.println("Connecting to database...");
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/","postgres","pfe");
			Statement st = conn.createStatement();
			st.executeUpdate("CREATE DATABASE "+ACCDB);
			ArrayList<String> primary = new ArrayList<String>();
			ArrayList<String> fore = new ArrayList<String>();
			int i = 0;
			Connection cd = DriverManager.getConnection(FileChooser.dBurlString);
			System.out.println("Connection reussie a la base de donnee");
			Statement s = cd.createStatement();
			DatabaseMetaData metaData = cd.getMetaData();
			ResultSet rs = metaData.getTables(null, null, "%", null);

			// System.out.println(createDbQuery);
			

			while (rs.next()) {
				createTableQuery += "CREATE TABLE " + rs.getString("TABLE_NAME") + "(\r\n";
				ResultSet t = s.executeQuery("SELECT COUNT(*) AS COUNT FROM " + rs.getString("TABLE_NAME"));
				t.next();
				ResultSet r = s.executeQuery("SELECT * FROM " + rs.getString("TABLE_NAME"));
				ResultSetMetaData l = r.getMetaData();
				this.columnCount = l.getColumnCount();
				this.taille = t.getInt("COUNT");
				ResultSet rs1 = metaData.getTables(null, null, rs.getString("TABLE_NAME"), new String[] { "TABLE" });
				ResultSet rs2 = metaData.getExportedKeys(null, null, rs.getString("TABLE_NAME"));
				rs1 = metaData.getPrimaryKeys(null, null, rs.getString("TABLE_NAME"));
				rs2=metaData.getExportedKeys(null, null, rs.getString("TABLE_NAME"));
				while (rs1.next()) {
					primary.add(rs1.getString(4));

				}
				while (rs2.next()) {
					fore.add(rs2.getString(4));
				}
				for (int j = 1; j < columnCount + 1; j++) {

					columnName = l.getColumnName(j);
					createTableQuery = createTableQuery + "    " + columnName + " ";
					switch (l.getColumnType(j)) {
					case 4:
						createTableQuery = createTableQuery + "INT NOT NULL,\r\n";
						break;
					case 12:
						createTableQuery = createTableQuery + "VARCHAR ( 50 ) NOT NULL,\r\n";
						break;
					case 16:
						createTableQuery = createTableQuery + "BOOLEAN NOT NULL,\r\n";
						break;
					case -5:
						createTableQuery = createTableQuery + "BIGINT NOT NULL,\r\n";
						break;
					default:
						// code block
					}
				}

				this.createTableQuery = createTableQuery + "    PRIMARY KEY (" + primary.get(i) + ")\r\n";
				//this.createTableQuery = createTableQuery + "    SECONDARY KEY (" + fore.get(i) + ")";
				this.createTableQuery = createTableQuery + "\n);" + "\n";
				i++;
			}
			
        System.out.println(createTableQuery);
			s.close();
			cd.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		// -----------------------
		scan.close();
		TimeUnit.SECONDS.sleep(10);  
	}

}
