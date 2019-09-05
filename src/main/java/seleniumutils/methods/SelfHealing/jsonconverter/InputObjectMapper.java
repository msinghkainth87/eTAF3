package seleniumutils.methods.SelfHealing.jsonconverter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import seleniumutils.methods.SelfHealing.inputdto.InputPOJO;

import java.io.IOException;

public class InputObjectMapper {
	private static final Logger logger = LogManager.getLogger(InputObjectMapper.class);
	String jsonPayLoad;
	public InputPOJO inputPayLoad;
	
	public InputObjectMapper(String jsonPayload){
		this.jsonPayLoad = jsonPayload;
		createInputObject(this.jsonPayLoad);
		
	}
	public void createInputObject(String jsonPayload) {
		this.inputPayLoad= new InputPOJO();
		ObjectMapper inputMapper = new ObjectMapper();
		
		try {
			this.inputPayLoad = inputMapper.readValue(jsonPayload, InputPOJO.class);
			
			logger.info("added the payload to the inputobject mapper class");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("Error while adding the inputobjectmapper to inputpojo "+ e.getLocalizedMessage());
			e.printStackTrace();
			
		}
		

	}
}
