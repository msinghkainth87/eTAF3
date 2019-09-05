package seleniumutils.methods.SelfHealing.connectionutil;
import java.sql.Connection;

public interface Connect {
	public Connection getConnection();
	public void setConnection(String url, String userName, String password) throws ClassNotFoundException;
	public void createConnection();
}
