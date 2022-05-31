
package accesstopostgre;

import java.sql.SQLException;

import com.formdev.flatlaf.FlatDarculaLaf;

public class Application {

	public static void main(String[] args) throws InterruptedException, SQLException {
		FlatDarculaLaf.setup();
		new LookAndFeel();
		new InterfaceGraphique();

	}

}
