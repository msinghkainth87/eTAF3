package seleniumutils.methods.SelfHealing.connectionutil;

public class ConnectionFactory implements AbstractConnFactory {
	
	
	@Override
	public Connect connect(String connectionType) {
		// TODO Auto-generated method stub
		switch(connectionType) {
			case "MYSQL": 
				return new MySqlConnection();
			
			case "MsSql":
				return new MsSqlConnection();
			
			case "Oracle":
				return new OracleConnection();
				
		}
		return null;
	}
	
}
