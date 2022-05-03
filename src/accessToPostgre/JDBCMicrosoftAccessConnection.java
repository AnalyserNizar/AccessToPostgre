package accessToPostgre;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class JDBCMicrosoftAccessConnection {
	Scanner scan = new Scanner(System.in);
	public String columnName = "";
	public static  String createDbQuery = "CREATE DATABASE DB1;";
	public static String createTableQuery = "";
	public int columnCount=0;
	public int taille=0;
	public FileChooser chooser;

	public JDBCMicrosoftAccessConnection (JFileChooser chooser) {
	
		
		this.columnName = "";

		// TODO Auto-generated constructor stub
		Connection conn = null;

		try {
			ArrayList<String> primary = new ArrayList<String>();
			int i = 0;
			Connection cd = DriverManager.getConnection(FileChooser.dBurlString);
			System.out.println("Connection reussie a la base de donnee");
			Statement s = cd.createStatement();
			DatabaseMetaData metaData = cd.getMetaData();
			ResultSet rs = metaData.getTables(null, null, "%", null);

			// System.out.println(createDbQuery);
			i = 0;

			while (rs.next()) {
				createTableQuery += "CREATE TABLE " + rs.getString("TABLE_NAME") + "(\r\n";
				ResultSet t = s.executeQuery("SELECT COUNT(*) AS COUNT FROM " + rs.getString("TABLE_NAME"));
				t.next();
				ResultSet r = s.executeQuery("SELECT * FROM " + rs.getString("TABLE_NAME"));
				ResultSetMetaData l = r.getMetaData();
				this.columnCount = l.getColumnCount();
				this.taille = t.getInt("COUNT");
				ResultSet rs1 = metaData.getTables(null, null, rs.getString("TABLE_NAME"), new String[] { "TABLE" });
				rs1 = metaData.getPrimaryKeys(null, null, rs.getString("TABLE_NAME"));
				while (rs1.next()) {
					primary.add(rs1.getString(4));
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

				this.createTableQuery = createTableQuery + "    PRIMARY KEY (" + primary.get(i) + ")";
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
	}

}
