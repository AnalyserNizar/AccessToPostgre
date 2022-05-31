package accesstopostgre;

import javax.swing.UIManager;

public class LookAndFeel {
	public LookAndFeel() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}