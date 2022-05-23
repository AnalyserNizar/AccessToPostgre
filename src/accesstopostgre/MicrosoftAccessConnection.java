package accesstopostgre;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Iterator;

public class MicrosoftAccessConnection {
	Scanner scan = new Scanner(System.in);
	public String columnName = null;
	public static String createDbQuery = "";
	public static String createTableQuery = "";
	public static String insertInto = "";
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

			Connection con = DriverManager.getConnection(FileChooser.dBurlString);
			System.out.println("Connection reussie a la base de donnee");
			Statement stat = con.createStatement();
			DatabaseMetaData metaData = con.getMetaData();
			ResultSet R_table = metaData.getTables(null, null, "%", null);

			// la creation des requetes sql a partir du BD access

			while (R_table.next()) {

				createTableQuery += "CREATE TABLE " + R_table.getString("TABLE_NAME") + "(\r\n";

				ResultSet R_listcolumns = stat.executeQuery("SELECT * FROM " + R_table.getString("TABLE_NAME"));
				ResultSetMetaData listcolumns_meta = R_listcolumns.getMetaData();
				columnCount = listcolumns_meta.getColumnCount();

				ResultSet R_PK = metaData.getPrimaryKeys(null, null, R_table.getString("TABLE_NAME"));
				ResultSet R_FK = metaData.getImportedKeys(null, null, R_table.getString("TABLE_NAME"));
				
				boolean bool = true;
				int cmp = 0;
				while (R_listcolumns.next()) {
					if (bool) {
						insertInto += "INSERT INTO " + R_table.getString("TABLE_NAME");
						for (int j = 1; j < columnCount + 1; j++) {
							if (j == 1) {
								insertInto += "(";
							}

							insertInto += listcolumns_meta.getColumnName(j);
							if (j < columnCount) {
								insertInto += ",";
							}
						}
						insertInto += ")\r\n" + "VALUES ";
						bool = false;
					}
					// ligne par ligne
					insertInto += "(";
					cmp++;
					int j = 1;
					for (j = 1; j < columnCount + 1; j++) {// collumn par collumn
						switch (listcolumns_meta.getColumnType(j)) {
						case 4:
							insertInto += R_listcolumns.getInt(j);
							if (j < columnCount) {
								insertInto += ",";
							}
							break;
						case 3:
							insertInto += R_listcolumns.getBigDecimal(j);
							if (j < columnCount) {
								insertInto += ",";
							}
							break;
						case 2:
						insertInto += R_listcolumns.getBigDecimal(j);
						if (j < columnCount) {
							insertInto += ",";
						}
						break;
						case 91:
							insertInto += R_listcolumns.getDate(j);
							if (j < columnCount) {
								insertInto += ",";
							}
							break;
						case 93:
							Timestamp date = R_listcolumns.getTimestamp(j); 
							if(!R_listcolumns.wasNull()) {
							insertInto += "'" + date ;
							insertInto = insertInto.substring(0,insertInto.length() -2);
							insertInto += "'";
							}else {
								insertInto += date ;
							}
							if (j < columnCount) {
								insertInto += ",";
							}
							
							break;
							case 2004:
							insertInto += "'" + R_listcolumns.getBlob(j) + "'";
							if (j < columnCount) {
								insertInto += ",";
							}
							break;
						case 16:
							insertInto += R_listcolumns.getBoolean(j);
							if (j < columnCount) {
								insertInto += ",";
							}
							break;
						default:
							insertInto += "'" + R_listcolumns.getString(j) + "'";
							if (j < columnCount) {
								insertInto += ",";
							}
						}
					}

					insertInto += "),\n";

				}
				if (cmp > 0) {
					insertInto = insertInto.substring(0, insertInto.length() - 1);
				}

				insertInto += ";\n";
				bool = true;

				for (int j = 1; j < columnCount + 1; j++) {
					int nullable = listcolumns_meta.isNullable(j);

					columnName = listcolumns_meta.getColumnName(j);
					createTableQuery = createTableQuery + "    " + columnName + " ";

					switch (listcolumns_meta.getColumnType(j)) {
					case 4:
						if (nullable == ResultSetMetaData.columnNoNulls) {
							createTableQuery = createTableQuery + "INT NOT NULL,\r\n";

						} else {

							createTableQuery = createTableQuery + "INT,\r\n";
						}
						break;
					case 12:
						if (listcolumns_meta.getColumnDisplaySize(j) > 255) {
							if (nullable == ResultSetMetaData.columnNoNulls) {
								createTableQuery = createTableQuery + "TEXT NOT NULL,\r\n";
							} else {
								createTableQuery = createTableQuery + "TEXT,\r\n";
							}
						} else if (nullable == ResultSetMetaData.columnNoNulls) {
							createTableQuery = createTableQuery + "VARCHAR (" + listcolumns_meta.getColumnDisplaySize(j)
									+ ") NOT NULL,\r\n";
						} else {
							createTableQuery = createTableQuery + "VARCHAR (" + listcolumns_meta.getColumnDisplaySize(j) + "),\r\n";

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
						if (listcolumns_meta.isCurrency(j)) {
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
					case 2014:
						if (nullable == ResultSetMetaData.columnNoNulls) {
							createTableQuery = createTableQuery + "TIMESTAMPTZ NOT NULL,\r\n";
						} else {
							createTableQuery = createTableQuery + "TIMESTAMPTZ,\r\n";
						}
						break;
					case 2004:
						if (nullable == ResultSetMetaData.columnNoNulls) {
							createTableQuery = createTableQuery + "BYTEA NOT NULL,\r\n";
						} else {
							createTableQuery = createTableQuery + "BYTEA,\r\n";
						}
						break;
					case 92:
						if (nullable == ResultSetMetaData.columnNoNulls) {
							createTableQuery = createTableQuery + "TIME NOT NULL,\r\n";
						} else {
							createTableQuery = createTableQuery + "TIME,\r\n";
						}
						break;
					default:
					}

				}
				// cle primaire

				int i = 0;
				while (R_PK.next()) {
					if (i < 1) {
						createTableQuery = createTableQuery + "    PRIMARY KEY(";
					}
					if (i >= 1) {
						createTableQuery = createTableQuery + ",";
					}
					if (i >= 0) {
						createTableQuery = createTableQuery + R_PK.getString("COLUMN_NAME");
					}
					i++;
				}

				if (i >= 1) {

					createTableQuery = createTableQuery + ")";
				} else {
					createTableQuery = createTableQuery.substring(0, createTableQuery.length() - 3);
				}
				// cle etrangere
				while (R_FK.next()) {
					addconstraints = addconstraints + "\nALTER TABLE " + R_table.getString("TABLE_NAME");
					addconstraints = addconstraints + "\nADD CONSTRAINT fk_" + R_FK.getString("PKTABLE_NAME")
							+ " FOREIGN KEY(" + R_FK.getString("FKCOLUMN_NAME") + ") REFERENCES "
							+ R_FK.getString("PKTABLE_NAME") + "(" + R_FK.getString("PKCOLUMN_NAME") + ");";
					if (R_FK.getShort("UPDATE_RULE") == 0 || R_FK.getShort("UPDATE_RULE") == 0) {

						addconstraints = addconstraints.substring(0, addconstraints.length() - 1);
						addconstraints = addconstraints + "\nON DELETE CASCADE\nON UPDATE CASCADE;";
					}
				}

				createTableQuery = createTableQuery + "\n);\n";
				i++;

			}
			
			stat.close();
			con.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		// -----------------------
		scan.close();

	}

}