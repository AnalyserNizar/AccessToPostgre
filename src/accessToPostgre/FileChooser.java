package accessToPostgre;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooser {
	public static String dBurlString = "";
	
	 public String getUrl() {
	     return FileChooser.dBurlString;
	  }

	public static JFileChooser createFilePicker() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("."));
		chooser.setDialogTitle("choosertitle");
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.addChoosableFileFilter(new FileNameExtensionFilter("Microsoft Access", "accdb"));
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			dBurlString = "jdbc:ucanaccess://" + chooser.getSelectedFile();
			System.out.println(dBurlString);
		} else {
			System.out.println("No Selection");
		}
		return chooser;
	}

	 
}
