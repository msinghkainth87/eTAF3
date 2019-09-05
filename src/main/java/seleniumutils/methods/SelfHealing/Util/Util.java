package seleniumutils.methods.SelfHealing.Util;

import java.io.IOException;
import java.util.Properties;

public class Util {
	
	
	public String getPropertyValue(String propKey) throws IOException {
		Properties prop = new Properties();
		
		prop.load(Util.class.getClassLoader().getResourceAsStream("weightage.properties"));
		return prop.getProperty(propKey);
	}
	
	public Properties readPropertiesFile(String fileName) throws IOException{
		Properties prop = new Properties();
		prop.load(Util.class.getClassLoader().getResourceAsStream("weightage.properties"));
		return prop;
		
	}
}
