package accesstopostgre;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Iterator;

public class MicrosoftAccessConnection {
	Scanner scan = new Scanner(System.in);
	public String columnName = null;
	public static String createDbQuery = "";
	public static String createTableQuery = "";
	public static String addconstraints = "";
	public int columnCount = 0;
	public int taille = 0;
	public static String ACCDB = null;
	public FileChooser chooser;

	public static String getFKeyData(String tableName, int i) throws SQLException {
		Connection con;
		String url = "jdbc:ucanaccess://C:\\Users\\hp\\Desktop\\AccessToPostgre\\Database1.accdb";
		con = DriverManager.getConnection(url);

		DatabaseMetaData dm = con.getMetaData();
		ResultSet rs = dm.getImportedKeys(null, null, tableName);
		String fkTableData = null;
		while (rs.next()) {
			fkTableData = rs.getString(i);
		}
		return fkTableData;
	}

	public MicrosoftAccessConnection() throws InterruptedException {

		try {

			// connexion a la base de donnee access

			Connection cd = DriverManager.getConnection(FileChooser.dBurlString);
			System.out.println("Connection reussie a la base de donnee");
			Statement s = cd.createStatement();
			DatabaseMetaData metaData = cd.getMetaData();
			ResultSet rs = metaData.getTables(null, null, "%", null);

			// la creation des requetes sql a partir du BD access

			while (rs.next()) {
				createTableQuery += "CREATE TABLE " + rs.getString("TABLE_NAME") + "(\r\n";
				ResultSet r = s.executeQuery("SELECT * FROM " + rs.getString("TABLE_NAME"));
				ResultSetMetaData l = r.getMetaData();
				this.columnCount = l.getColumnCount();

				ResultSet rs1 = metaData.getPrimaryKeys(null, null, rs.getString("TABLE_NAME"));
				ResultSet rs2 = metaData.getImportedKeys(null, null, rs.getString("TABLE_NAME"));

				ResultSetMetaData metadata = r.getMetaData();

				for (int j = 1; j < columnCount + 1; j++) {
					int nullable = metadata.isNullable(j);

					columnName = l.getColumnName(j);
					createTableQuery = createTableQuery + "    " + columnName + " ";
					switch (l.getColumnType(j)) {
					case 4:
						if (nullable == ResultSetMetaData.columnNoNulls) {
							createTableQuery = createTableQuery + "INT NOT NULL,\r\n";

						} else {

							createTableQuery = createTableQuery + "INT,\r\n";
						}
						break;
					case 12:
						if (l.getColumnDisplaySize(j) > 255) {
							if (nullable == ResultSetMetaData.columnNoNulls) {
								createTableQuery = createTableQuery + "TEXT NOT NULL,\r\n";
							} else {
								createTableQuery = createTableQuery + "TEXT,\r\n";
							}
						} else if (nullable == ResultSetMetaData.columnNoNulls) {
							createTableQuery = createTableQuery + "VARCHAR (" + l.getColumnDisplaySize(j)
									+ ") NOT NULL,\r\n";
						} else {
							createTableQuery = createTableQuery + "VARCHAR (" + l.getColumnDisplaySize(j) + "),\r\n";

						}

						break;
					case 16:
						if (nullable == ResultSetMetaData.columnNoNulls) {
							createTableQuery = createTableQuery + "BOOLEAN NOT NULL,\r\n";
						} else {
							createTableQuery = createTableQuery + "BOOLEAN,\r\n";
						}
						break;
					case -5:

						if (nullable == ResultSetMetaData.columnNoNulls) {
							createTableQuery = createTableQuery + "BIGINT NOT NULL,\r\n";
						} else {
							createTableQuery = createTableQuery + "BIGINT,\r\n";
						}
						break;

					case 3:
						if (l.isCurrency(j)) {
							if (nullable == ResultSetMetaData.columnNoNulls) {
								createTableQuery = createTableQuery + "MONEY NOT NULL,\r\n";
							} else {
								createTableQuery = createTableQuery + "MONEY,\r\n";
							}
						} else if (nullable == ResultSetMetaData.columnNoNulls) {
							createTableQuery = createTableQuery + "DECIMAL NOT NULL,\r\n";
						} else {
							createTableQuery = createTableQuery + "DECIMAL,\r\n";
						}
						break;
					case 5:
						if (nullable == ResultSetMetaData.columnNoNulls) {
							createTableQuery = createTableQuery + "SMALLINT NOT NULL,\r\n";
						} else {
							createTableQuery = createTableQuery + "SMALLINT,\r\n";
						}
						break;
					case 91:
						if (nullable == ResultSetMetaData.columnNoNulls) {
							createTableQuery = createTableQuery + "DATE NOT NULL,\r\n";
						} else {
							createTableQuery = createTableQuery + "DATE,\r\n";
						}
						break;
					case 93:
						if (nullable == ResultSetMetaData.columnNoNulls) {
							createTableQuery = createTableQuery + "TIMESTAMP NOT NULL,\r\n";
						} else {
							createTableQuery = createTableQuery + "TIMESTAMP,\r\n";
						}
						break;
					case 2004:
						if (nullable == ResultSetMetaData.columnNoNulls) {
							createTableQuery = createTableQuery + "BYTEA NOT NULL,\r\n";
						} else {
							createTableQuery = createTableQuery + "BYTEA,\r\n";
						}
						break;
					default:
					}
				}
				// cle primaire

				int i = 0;
				while (rs1.next()) {
					if (i < 1) {
						createTableQuery = createTableQuery + "    PRIMARY KEY(";
					}
					if (i >= 1) {
						createTableQuery = createTableQuery + ",";
					}
					if (i >= 0) {
						createTableQuery = createTableQuery + rs1.getString("COLUMN_NAME");
					}
					i++;
				}

				if (i >= 1) {

					createTableQuery = createTableQuery + ")";
				}
				else {
					createTableQuery = createTableQuery.substring(0, createTableQuery.length()-3);
				}
				// cle etrangere
				while (rs2.next()) {
					addconstraints = addconstraints + "\nALTER TABLE " + rs.getString("TABLE_NAME");
					addconstraints = addconstraints + "\nADD CONSTRAINT fk_" + rs2.getString("PKTABLE_NAME")
							+ " FOREIGN KEY(" + rs2.getString("FKCOLUMN_NAME") + ") REFERENCES "
							+ rs2.getString("PKTABLE_NAME") + "(" + rs2.getString("PKCOLUMN_NAME") + ");";
					if (rs2.getShort("UPDATE_RULE") == 0 || rs2.getShort("UPDATE_RULE") == 0) {

						addconstraints = addconstraints.substring(0, addconstraints.length() - 1);
						addconstraints = addconstraints + "\nON DELETE CASCADE\nON UPDATE CASCADE;";
					}
				}

				createTableQuery = createTableQuery + "\n);\n";
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