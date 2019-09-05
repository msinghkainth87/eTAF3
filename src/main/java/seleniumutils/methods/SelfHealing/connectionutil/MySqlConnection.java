package seleniumutils.methods.SelfHealing.connectionutil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySqlConnection implements Connect{

	private Connection connection = null;

	public MySqlConnection() {
		if(this.connection==null) {
			createConnection();
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
	
	@Override
	public void setConnection(String url,String userName, String password) throws ClassNotFoundException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			this.connection = DriverManager.getConnection(url,userName,password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	@Override
	public Connection getConnection() {
		// TODO Auto-generated method stub
		
		return this.connection;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return new RuntimeException("Clone not Supported");
	}

	
	public Object readResolve(){
		return getConnection();
	}
	

}
