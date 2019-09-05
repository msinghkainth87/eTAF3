package seleniumutils.methods.SelfHealing.self_healing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import seleniumutils.methods.SelfHealing.comparison.GetWeightedResult;
import seleniumutils.methods.SelfHealing.jsonconverter.InputObjectMapper;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Method invocation for DB connection through factory pattern.
 *
 */
public class SelfHealing {	
	
	private static final Logger logger = LogManager.getLogger(SelfHealing.class);	
	private LinkedHashMap<Integer, HashMap<String, String>> comparedElements;	
	private WebDriver driver;
	
	
	public SelfHealing(WebDriver driver) {
		this.driver = driver;
	}
	
	/*
	 * Main method which will be exposed out to the Framework for integration
	 * 
	 * Developer: Vik
	 * 
	 */
	// Add the logs later ---> Very important!
	public String shMain(String jsonPayload) throws Exception {
		String returnJson = "";
		try {
			logger.info("entered self healing flow ");			
			int requiredKey = -1;
			comparedElements = new LinkedHashMap<>();
			ExtractData extractDataFromHtml = new ExtractData();
			InputObjectMapper iOM = new InputObjectMapper(jsonPayload);
			iOM.inputPayLoad.setHtmlDom(driver.getPageSource());
			Elements finalScrapedElements = extractDataFromHtml.scrapDom(iOM.inputPayLoad);
			
			GetWeightedResult weightedResultObject = new GetWeightedResult(iOM.inputPayLoad);
			// System.out.println(comparedElements.get(1).get("average"));
			comparedElements.putAll(weightedResultObject.getWeightedResultForAllScrapedElements(finalScrapedElements));
			requiredKey = weightedResultObject.getRequiredArrayOfElement(comparedElements);

			ObjectMapper hashMapper = new ObjectMapper();

			try {
				returnJson = hashMapper.writeValueAsString(comparedElements.get(requiredKey));
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				throw new Exception(e.getLocalizedMessage() + " error processing json requsts");
			}
			JSONObject json = new JSONObject(returnJson);
			logger.info("final average of the info "+ json.getFloat("average"));			
			
		}catch(Exception e) {
			logger.warn(e.getLocalizedMessage() + " error inside shMain");
			
			throw new RuntimeException(e.getLocalizedMessage());
		}
		return returnJson;
		
	}

}
