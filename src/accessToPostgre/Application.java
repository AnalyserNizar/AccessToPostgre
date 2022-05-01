package accessToPostgre;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Scanner;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.File;
import java.io.FileFilter;

public class Application {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		// CHANGING LOOK AND FEEL
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		// ----------------------------------------
		// Filtre pour les fichier .accdb
		JFileChooser chooser = new JFileChooser();
		String DBurl = null;
		System.out.println("Choose your db file");
		chooser.setCurrentDirectory(new File("."));
		chooser.setDialogTitle("choosertitle");
		chooser.setAcceptAllFileFilterUsed(false);
		// FileTypeFilter dbfilter = new FileTypeFilter(".accdb", "Access Database");
		// chooser.addChoosableFileFilter(dbfilter);
		chooser.addChoosableFileFilter(new FileNameExtensionFilter("Microsoft Access", "accdb"));
		// ^----simplification du code

		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			DBurl = "jdbc:ucanaccess://" + chooser.getSelectedFile();
			System.out.println(DBurl);
		} else {
			System.out.println("No Selection");
		}
		String columnName = null, createDbQuery = "CREATE DATABASE IF NOT EXISTS DB1;";
		String createTableQuery;

		// Connection avec la base de donnée access
		try {
			ArrayList<String> primary = new ArrayList<String>();
			int i = 0;
			Connection cd = DriverManager.getConnection(DBurl);
			System.out.println("Connection reussie a la base de donnee");
			Statement s = cd.createStatement();
			DatabaseMetaData metaData = cd.getMetaData();
			ResultSet rs = metaData.getTables(null, null, "%", null);
 
			
            
			System.out.println(createDbQuery);
			i=0;
			
			
			
			while(rs.next()) {	
				createTableQuery = "CREATE TABLE "+rs.getString("TABLE_NAME")  +"(\r\n";
				ResultSet t = s.executeQuery("SELECT COUNT(*) AS COUNT FROM "+rs.getString("TABLE_NAME"));
				t.next();
				ResultSet r = s.executeQuery("SELECT * FROM "+rs.getString("TABLE_NAME"));
				ResultSetMetaData l = r.getMetaData();
				int columnCount = l.getColumnCount();
				int taille = t.getInt("COUNT");
				ResultSet rs1= metaData.getTables(null, null,rs.getString("TABLE_NAME"), new String[]{"TABLE"});
				rs1=metaData.getPrimaryKeys(null, null,rs.getString("TABLE_NAME"));
				while(rs1.next()) {
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
				
			createTableQuery = createTableQuery +"    PRIMARY KEY (" + primary.get(i) + ")";
			createTableQuery = createTableQuery + "\n);";
			System.out.println(createTableQuery);
			i++;
			}
			s.close();
			cd.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		// -----------------------
		scan.close();

	}
}