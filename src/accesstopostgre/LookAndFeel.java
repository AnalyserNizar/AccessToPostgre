package accesstopostgre;

import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarculaLaf;

public class LookAndFeel {
	public LookAndFeel() {

		try {
			FlatDarculaLaf.setup();
			UIManager.setLookAndFeel(new FlatDarculaLaf());

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}