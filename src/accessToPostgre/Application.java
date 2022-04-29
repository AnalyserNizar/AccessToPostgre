package accessToPostgre;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.File;
import java.io.FileFilter;

//import accesstopostgre.MyFrame;
//perspective 

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

		try {
			int i = 0;
			Connection cd = DriverManager.getConnection(DBurl);
			System.out.println("Connection reussie a la base de donnee");
			Statement s = cd.createStatement();
			ResultSet t = s.executeQuery("SELECT COUNT(*) AS COUNT FROM UNI");
			t.next();
			ResultSet r = s.executeQuery("SELECT * FROM UNI");
			ResultSetMetaData l = r.getMetaData();
			int p = l.getColumnCount();
			int taille = t.getInt("COUNT");
			int[] ID = new int[taille];
			String[] nom = new String[taille];
			int[] nbr = new int[taille];
			while (r.next()) {
				ID[i] = r.getInt("ID");
				nom[i] = r.getString("nomuni");
				nbr[i] = r.getInt("nbretudiants");
				i++;
			}
			// for( i =0 ; i<taille ; i++)
			// System.out.println("ID = "+ ID[i] +",nom = " + nom[i] + ", nbr = " + nbr[i]);

			System.out.println(l.getTableName(i));

			for (int j = 1; j < p + 1; j++) {
				System.out.println(l.getColumnName(j));
			}

			r.close();
			s.close();
			cd.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		scan.close();

	}
}
