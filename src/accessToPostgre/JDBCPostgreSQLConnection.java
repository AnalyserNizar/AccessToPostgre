package accessToPostgre;

import java.awt.desktop.ScreenSleepEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;



public class JDBCPostgreSQLConnection {
	{
		
	

		try {	
			// connection a postgresql
			System.out.println("Connecting to database...");
			String a = JDBCMicrosoftAccessConnection.ACCDB;
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/"+ a,"postgres","pfe");
			Statement st = conn.createStatement();
			// execution des requetes
			st.executeUpdate(JDBCMicrosoftAccessConnection.createTableQuery);
			System.out.println(JDBCMicrosoftAccessConnection.createTableQuery);
			
		} catch (SQLException e) {
			System.out.println("connection failed");
			e.printStackTrace();
			// TODO: handle exception
		} 

	}
}

