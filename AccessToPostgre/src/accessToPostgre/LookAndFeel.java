package accessToPostgre;

import javax.swing.UIManager;

public class LookAndFeel {
	public LookAndFeel() {
		// TODO Auto-generated constructor stub
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}
