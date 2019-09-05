package seleniumutils.methods.SelfHealing.comparison;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.select.Elements;
import seleniumutils.methods.SelfHealing.inputdto.InputPOJO;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.stream.IntStream;


/*
 * GetWeightedResult class is to calculate the compare and calculate the weighted of each scraped Element against
 * historical data
 * 
 * Developer: Vik
 */
public class GetWeightedResult {
	
	private static final Logger logger = LogManager.getLogger(GetWeightedResult.class);	
	
	private InputPOJO inputPojo;
	
	public GetWeightedResult(InputPOJO inputPojo) {
		this.inputPojo = inputPojo;
	}


	 /*   This method calculates the weightage of each element in the element array.
	  *   and returns the weighted Map with index added to it
	  *   
	  *   Developer: Vik
	  */
			
	public HashMap<Integer, HashMap<String, String>> getWeightedResultForAllScrapedElements(Elements scrapedElements) throws Exception {
		HashMap<Integer, HashMap<String, String>> comparedElements = new HashMap<>();
		HashMap<String, String> elementsPropAndValueAfterComparison = new HashMap<>();
		logger.info("Initialized the getWeightedResultForAllScrapedElements method");
		try {
			IntStream.range(0, scrapedElements.size())
				.forEach(idx -> {
					CompareProperties compareProperties = new CompareProperties(scrapedElements.get(idx),this.inputPojo);
//					System.out.println(scrapedElements.get(idx) + " this is element and list of its properties");
					elementsPropAndValueAfterComparison.putAll(compareProperties.comparePropertiesForEachAttr());
					HashMap<String, Double> weightedMap = new HashMap<>();
					weightedMap = compareProperties.calculateWeightage();
					logger.info(idx + " is the index and weighted Map for that index is "+weightedMap);
					elementsPropAndValueAfterComparison.put("average",String.valueOf(compareProperties.getAverageForEachElement(weightedMap).get("average")));
					comparedElements.put(idx, elementsPropAndValueAfterComparison);
				});
			
		}catch(Exception e) {
			logger.error("getWeightedResultForAllScrapedElements method error "+e.getLocalizedMessage());
			throw new RuntimeException(e.getLocalizedMessage());
		}
		return comparedElements;
	}
	
	 /*   This method gets the index of the hash element which has the highest average
	  *   returns the index key. 
	  *   
	  *   input : weighted Array from the previous method
	  *   
	  *   Developer: Vik
	  */
	public int getRequiredArrayOfElement(LinkedHashMap<Integer,HashMap<String,String>> comparedElements) throws Exception {
		
		logger.info("Initialized the getRequiredArrayOfElement method");
		int requiredKey = -1 ;
		try {
			Iterator<Integer> a = comparedElements.keySet().iterator();
			Double max = 0.0;
			
			while (a.hasNext()) {
				int mapIndex = a.next().intValue();
				Double average = Double.valueOf(comparedElements.get(mapIndex).get("average"));
				if (max < average) {
					max = average;

					requiredKey = mapIndex;
					logger.info(requiredKey + " is the requiredKey for the map");
					//System.out.println(max + " " + requiredKey);
				} else {
					//System.out.println(max + " " + requiredKey + " less than average");
					logger.info(max + " is the max value " + requiredKey + " less than average");
//					throw new RuntimeException("max is less than zero");
				}
			}
			if (requiredKey < 0) {
				logger.error(requiredKey + " less than zero ?");
				throw new RuntimeException("required Key not found in the map ");
				
			} else {
				//System.out.println(comparedElements.get(requiredKey));
				logger.info(requiredKey + " is the required key");
			}
		}catch(Exception e) {
			logger.error("getRequiredArrayOfElement method error "+e.getLocalizedMessage());
			throw new RuntimeException(e.getMessage());
		}
		
		
		return requiredKey;
	}


}
