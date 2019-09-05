package seleniumutils.methods.SelfHealing.comparison;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.jsoup.nodes.Element;
import seleniumutils.methods.GlobalProperties;
import seleniumutils.methods.SelfHealing.Util.Util;
import seleniumutils.methods.SelfHealing.generics.GetProperty;
import seleniumutils.methods.SelfHealing.inputdto.InputPOJO;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;


public class CompareProperties {

	private Element element;
	private InputPOJO inputPayLoad;
	private HashMap<String, Double> mapDynamicPropAndValue = new HashMap<>();
	private Util util = new Util();
	
	
	

	public CompareProperties(Element element, InputPOJO inputPojo) {
		this.element = element;
		this.inputPayLoad = inputPojo;
		
	}

	/*   This method calculates the weightage of each element in the element array. 
	  *   and returns the weighted Map with index added to it
	  *   
	  *   Developer: Vik
	  */

	public double compareTo(String propertyFromPayLoad, String propertyDynamicallyRetrieved) {
		// TODO Auto-generated method stub
		double similarityScore = 0;
		int distance = 0;
		try {
			//System.out.println(propertyDynamicallyRetrieved + " " + propertyFromPayLoad);
			if(propertyFromPayLoad==null) {
				distance = propertyDynamicallyRetrieved.length();
				propertyFromPayLoad = "";
			}else {
				distance = getLevensteinDistance(propertyFromPayLoad, propertyDynamicallyRetrieved);
			}			
			similarityScore = getSimilarityScore(distance, propertyFromPayLoad.length(),
					propertyDynamicallyRetrieved.length());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return similarityScore;
	}

	/*   This method calculates the levensteinsDistance of two strings
	  *   and returns the difference between characters
	  *   
	  *   Developer: Vik
	  */
	private int getLevensteinDistance(String s1, String s2) {
		int levScore = 0;
		try {
			LevenshteinDistance LD = new LevenshteinDistance();
			levScore = LD.apply(s1, s2);
		} catch (Exception e) {
			throw new RuntimeException(e.getLocalizedMessage());
		}

		// System.out.println(LD.getThreshold());
		return levScore;
	}

	/*   Based on the difference from levenshtein distance, this method calculates the similarity of the two properties	  *   
	  *   Developer: Vik
	  */
	
	private double getSimilarityScore(int distance, int length1, int length2) {
		double percentage = 0;
		try {
			if (distance == 0) {
				percentage = 100;
			} else {
				//System.out.println(length1 + " " + length2);
				percentage = (1 - (double) distance / (double) Math.max(length1, length2));
				percentage = percentage * 100;
			}

		} catch (Exception e) {
			throw new RuntimeException(e.getLocalizedMessage());
		}
		return percentage;
	}

	/*   This method calculates the each property and its attribute. INPUT is payload and each element scraped from the list of elements.
	  *   
	  *   Developer: Vik
	  */
	
	public HashMap<String, String> comparePropertiesForEachAttr() {
		String propValueFromElement = "";
		String propOriginalValue = "";
		HashMap<String,String> propNameAndValueInMap = new HashMap<>();
		Field[] fields = this.inputPayLoad.getClass().getDeclaredFields();
		GetProperty getPropertyFromInputPayLoad = new GetProperty(this.inputPayLoad);
		GetProperty getPropertyFromElement = new GetProperty(this.element);

		for (Field field : fields) {
			String propName = field.getName();
			String[] propValue = getPropertyFromInputPayLoad.getPropValue(propName);
			if (!propName.equals("id") && !field.getName().equals("htmlDom")) {
				propOriginalValue = propValue[0];
				propValueFromElement = getPropertyFromElement.getElementValue(propName);
				propNameAndValueInMap.put(propName, propValueFromElement);
				//System.out.println(propName);
				mapDynamicPropAndValue.put(propName, compareTo(propOriginalValue, propValueFromElement));
			}

		}

		//System.out.println(mapDynamicPropAndValue.size());
		return propNameAndValueInMap;
	}

	
	/*   This method calculates the weightage of each element based on the weightage.properties.
	  *   
	  *   Developer: Vik
	  */
	
	public HashMap<String, Double> calculateWeightage() {
		HashMap<String, Double> weightedResult = new HashMap<String, Double>();
		try {
			for (String key : mapDynamicPropAndValue.keySet()) {
				String weight = util.getPropertyValue(key);

				int weightage = Integer.valueOf(weight);
				double newValue = (mapDynamicPropAndValue.get(key).doubleValue() / 100.0) * weightage;
				weightedResult.put(key, newValue);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return weightedResult;
	}
	
	/*   This method calculates the average for each element
	  *   
	  *   Developer: Vik
	  */
	
	public double calculateAverage(HashMap<String,Double> weightedMap) {
//		 = calculateWeightage();
		double average = 0.0;
		try {
			for(Double i:weightedMap.values()) {
				average = average + i;
			}
			//C:\Users\hb427jn\Documents\eTAF\eTAF\src\test\resources\caps\data\selfhealingdata\weightage.properties
			File file = new File(GlobalProperties.getApplicationDataPath() +"/selfhealingdata/weightage.properties");
			//Properties prop = util.readPropertiesFile("C:\\Users\\hb427jn\\Documents\\eTAF\\eTAF\\src\\test\\resources\\caps\\data\\selfhealingdata\\weightage.properties");
			Properties prop = util.readPropertiesFile("weightage.properties");

			//Properties prop = util.readPropertiesFile("src/test/resources/"+GlobalProperties.getApplicationDataPath() +"/selfhealingdata/weightage.properties");
			double total = 0;
			for(Entry<Object, Object> kk:prop.entrySet()) {
			//	System.out.println(kk.getKey().toString());
				if (!kk.getKey().toString().equalsIgnoreCase("over_all")) {
					
					total +=  Double.valueOf(kk.getValue().toString());
				}
			}
			
		
			average = (average/total) * ((Double.valueOf(prop.getProperty("over_all"))/100.0));
			double un_avg = 0;
			for(double k:mapDynamicPropAndValue.values()){
				double d = k/100.0;
				un_avg = un_avg + d;
			}
			
			double per = (100 - Double.valueOf(prop.getProperty("over_all"))) / 100; 
			double diff_average = (un_avg/mapDynamicPropAndValue.size()) * per;
			
			average = average + diff_average;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return average*100;
	}
	
	public HashMap<String, Double> getAverageForEachElement(HashMap<String,Double> weightedMap) {
		Double doub = calculateAverage(weightedMap);
		mapDynamicPropAndValue.put("average", doub);
		return mapDynamicPropAndValue;
	}

}
