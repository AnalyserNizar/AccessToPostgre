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


public class JDBCMicrosoftAccessConnection {
	Scanner scan = new Scanner(System.in);
	public String columnName = null;
	public static String createDbQuery = null;
	public static String createTableQuery = "";
	public int columnCount = 0;
	public int taille = 0;
	public static String ACCDB = null;
	public FileChooser chooser;

	public JDBCMicrosoftAccessConnection(JFileChooser chooser) throws InterruptedException {


		// TODO Auto-generated constructor stub

		try {

			ArrayList<String> primary = new ArrayList<String>();
			//ArrayList<String> fore = new ArrayList<String>();
			int i = 0;
			
            //connexion a la base de donnee access
			
			Connection cd = DriverManager.getConnection(FileChooser.dBurlString);
			System.out.println("Connection reussie a la base de donnee");
			Statement s = cd.createStatement();
			DatabaseMetaData metaData = cd.getMetaData();
			ResultSet rs = metaData.getTables(null, null, "%", null);
			
			//la creation des requetes sql a partir du BD access
			
			while (rs.next()) {
				createTableQuery += "CREATE TABLE " + rs.getString("TABLE_NAME") + "(\r\n";
				ResultSet r = s.executeQuery("SELECT * FROM " + rs.getString("TABLE_NAME"));
				ResultSetMetaData l = r.getMetaData();
				this.columnCount = l.getColumnCount();
				
				ResultSet rs1 = metaData.getPrimaryKeys(null, null, rs.getString("TABLE_NAME"));
				//ResultSet rs2 = metaData.getExportedKeys(cd.getCatalog(), null, rs.getString("TABLE_NAME"));
				
				while (rs1.next()) {
					primary.add(rs1.getString("COLUMN_NAME"));
				}
				
				/*while (rs2.next()) {
					fore.add(rs2.getString("FKCOLUMN_NAME"));
				}*/
				
				for (int j = 1; j < columnCount + 1; j++) {

					columnName = l.getColumnName(j);
					createTableQuery = createTableQuery + "    " + columnName + " ";
					switch (l.getColumnType(j)) {
					case 4:
						createTableQuery = createTableQuery + "INT NOT NULL,\r\n";
						break;
					case 12:
						createTableQuery = createTableQuery + "VARCHAR (50) NOT NULL,\r\n";
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

			   createTableQuery = createTableQuery + "    PRIMARY KEY (" + primary.get(i) + ")\r\n";
			
			  // createTableQuery =createTableQuery + " FOREIGN KEY (" + fore.get(i) +")";
			   createTableQuery = createTableQuery + "\n);" + "\n";
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
