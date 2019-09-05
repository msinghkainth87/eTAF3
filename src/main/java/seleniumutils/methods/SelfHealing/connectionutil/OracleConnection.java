package seleniumutils.methods.SelfHealing.connectionutil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class OracleConnection implements Connect {

	private Connection connection;

	@Override
	public Connection getConnection() {
		// TODO Auto-generated method stub
		return this.connection;
	}

	@Override
	public void setConnection(String url, String userName, String password) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		Class.forName("oracle.jdbc.driver.OracleDriver");
		try {
			this.connection = DriverManager.getConnection(url, userName, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public synchronized void createConnection() {
		try {
			Properties prop = new Properties();
			prop.load(MySqlConnection.class.getClassLoader().getResourceAsStream("Application.properties"));
			setConnection(prop.getProperty("ConnectionString"), prop.getProperty("UserName"), prop.getProperty("password"));
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
