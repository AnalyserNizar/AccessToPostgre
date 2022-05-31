
package accesstopostgre;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.io.File;
import java.util.regex.Pattern;

public class Application {

	public static void main(String[] args) throws InterruptedException, SQLException {
		new LookAndFeel();
		InterfaceGraphique frame = new InterfaceGraphique();
	}

}
