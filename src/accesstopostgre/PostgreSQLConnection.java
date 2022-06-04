package accesstopostgre;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import net.ucanaccess.complex.Attachment;
import javax.swing.JOptionPane;

public class PostgreSQLConnection {
	private String[] options = { "Convertir une autre Base de donnée", "Fermer" };
	private String insertInto = "";

	public PostgreSQLConnection() throws IOException {
		try {
			Connection con = DriverManager.getConnection(FileChooser.dBurlString);
			// connection au base de donnee postgresql
			String url = "jdbc:postgresql://localhost/" + MicrosoftAccessConnection.ACCDB ;
			Connection conn = DriverManager.getConnection(url, InterfaceGraphique.username, InterfaceGraphique.pwd);
			Statement st = conn.createStatement();
			// execution des requetes
			st.executeUpdate(MicrosoftAccessConnection.createTableQuery);
			DatabaseMetaData metaData = con.getMetaData();
			ResultSet R_table = metaData.getTables(null, null, "%", null);

			while (R_table.next()) {
				insertInto = "";
				Statement stat = con.createStatement();
				ResultSet R_listcolumns = stat.executeQuery("SELECT * FROM " + R_table.getString("TABLE_NAME"));
				ResultSetMetaData listcolumns_meta = R_listcolumns.getMetaData();
				int columnCount = listcolumns_meta.getColumnCount();

				boolean bool = true;

				while (R_listcolumns.next()) {
					if (bool) {
						insertInto += "\nINSERT INTO " + R_table.getString("TABLE_NAME");
						for (int j = 1; j < columnCount + 1; j++) {
							if (j == 1) {
								insertInto += " VALUES (";

							}

							if (j < columnCount + 1) {
								insertInto += "?,";
							}
						}
						insertInto = insertInto.substring(0, insertInto.length() - 1);
						insertInto += ")";

						bool = false;
					}

					System.out.println(insertInto);
					PreparedStatement ps = conn.prepareStatement(insertInto);

					// ligne par ligne

					for (int j = 1; j < columnCount + 1; j++) {// collumn par collumn
						switch (listcolumns_meta.getColumnType(j)) {
						case 4:
							ps.setInt(j, R_listcolumns.getInt(j));
							System.out.println(R_listcolumns.getInt(j));
							break;
						case 3:
							ps.setBigDecimal(j, R_listcolumns.getBigDecimal(j));

							break;
						case -5:
							ps.setInt(j, R_listcolumns.getInt(j));
							break;
						case 2:

							ps.setBigDecimal(j, R_listcolumns.getBigDecimal(j));

							break;
						case 91:
							ps.setDate(j, R_listcolumns.getDate(j));

							break;
						case 93:
							ps.setTimestamp(j, R_listcolumns.getTimestamp(j));
							break;
						case 2004:
							if (R_listcolumns.getBlob(j) != null) {
								Blob clob = R_listcolumns.getBlob(j);
								byte[] byteArr = clob.getBytes(1, (int) clob.length());

								FileOutputStream fileOutputStream = new FileOutputStream(".\\files\\savedImage.jpg");
								fileOutputStream.write(byteArr);
								File file = new File(".\\files\\savedImage.jpg");
								FileInputStream fis = new FileInputStream(file);
								ps.setBinaryStream(j, fis, (int) file.length());
							}

							break;
						case 16:
							ps.setBoolean(j, R_listcolumns.getBoolean(j));
							break;
						case 1111:

							Attachment[] atts = (Attachment[]) R_listcolumns.getObject(j);
							ps.setObject(j, null);
							for (Attachment att : atts) {
								System.out.println(att.getName());
								org.apache.commons.io.FileUtils
										.writeByteArrayToFile(new File(".\\files\\" + att.getName()), att.getData());
								File file = new File(".\\files\\" + att.getName());
								FileInputStream fis = new FileInputStream(file);
								ps.setBinaryStream(j, fis, (int) file.length());
							}

							break;

						default:
							ps.setString(j, R_listcolumns.getString(j));
							System.out.println(R_listcolumns.getString(j));

						}
					}
					ps.executeUpdate();
				}

				bool = true;

			}

			st.executeUpdate(MicrosoftAccessConnection.addconstraints);

			int x = JOptionPane.showOptionDialog(null, "Voulez vous convertir une autre table?",
					"La base de donnée a été crée", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
					options, options[0]);
			if (x == 1 || x == -1) {
				System.exit(0);
			}
		} catch (SQLException e) {
			System.out.println("connexion postgre échoué");
			e.printStackTrace();

		}
	}

}