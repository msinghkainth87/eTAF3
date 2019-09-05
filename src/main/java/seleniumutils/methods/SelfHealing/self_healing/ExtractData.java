package seleniumutils.methods.SelfHealing.self_healing;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import seleniumutils.methods.SelfHealing.generics.GetProperty;
import seleniumutils.methods.SelfHealing.inputdto.InputPOJO;

import java.lang.reflect.Field;

public class ExtractData {

	/*
	 * method to extract the relevant elements from original HTML. Developer:Vik
	 * 
	 */
	private static final Logger logger = LogManager.getLogger(ExtractData.class);
	Document htmlDocument;
	Elements finalScrapedElements;
	GetProperty gp;
	private WebDriver driver;
	public ExtractData() {
		
	}
	
	public ExtractData(WebDriver driver) {
		this.driver= driver;
	}
	
	public Elements scrapDom(InputPOJO inputPayLoad) {
		try {
			logger.info("start of scrapDom method ... ");	
			String htmlAsString = inputPayLoad.getHtmlDom();
//			String constructedHtml = constructHtml(htmlAsString);
			// System.out.println(constructedHtml);
			this.finalScrapedElements = null;
			extractContentfromHtml(htmlAsString, inputPayLoad);
		}catch(Exception e) {
			throw new RuntimeException(e.getLocalizedMessage());
		}
		
		return this.finalScrapedElements;
	}

	/*
	 * Method to convert the base64 encoded html back to original HTML String
	 * Developer : Vik
	 * 
	 */
/*	private String constructHtml(String htmlContent) throws Exception {
		String constructedHtml = "";
		try {
			byte[] htmlBytes = Base64.getDecoder().decode(htmlContent);
			constructedHtml = new String(htmlBytes);
			logger.info("constructed Html is successful");
		} catch (Exception e) {
			logger.error("constructed Html is unsuccessful " +e.getLocalizedMessage());
			throw new Exception();
		}
		return constructedHtml;

	}*/

	private void extractContentfromHtml(String constructedHtml, InputPOJO inputPayLoad) {

		try {
			this.htmlDocument = Jsoup.parse(constructedHtml);
			this.gp = new GetProperty(inputPayLoad);
			Field[] fields = inputPayLoad.getClass().getDeclaredFields();

			// Method[] methods = inputPayLoad.getClass().getDeclaredMethods();

			extractDocumentByProperty(fields);

		} catch (Exception e) {
			throw new RuntimeException(e.getLocalizedMessage());
		}
		
		

	}

	public void extractDocumentByProperty(Field[] fields) {
		
		for (Field field : fields) {

			String[] propValue = this.gp.getPropValue(field.getName());
			try {
				if (!field.getName().equals("id") && !field.getName().equals("htmlDom") && propValue[0] != ""
						&& propValue[0] != null) {
					addElementsToList(propValue[1], propValue[0]);
				}
				

			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e.getLocalizedMessage());
			}
		}
	}



	private void addElementsToList(String propertyName, String propertyValue) {
		Elements tempElements;
		
		try {
			if (propertyName.equals("tagName")) {
				
			} 
			else {
				try {
					tempElements = this.htmlDocument.getElementsByAttributeValueContaining(propertyName, propertyValue);
					if (tempElements == null)
						this.htmlDocument.getElementsByAttributeValueContaining(propertyName,
								propertyValue.substring(0, propertyValue.length() / 2));
					if (tempElements == null)
						this.htmlDocument.getElementsByAttributeValueContaining(propertyName,
								propertyValue.substring(propertyValue.length() / 2, propertyValue.length()));

				} catch (Exception e) {
					throw new Exception(e.getLocalizedMessage());
				}
				if(this.finalScrapedElements!=null) {
					this.finalScrapedElements.addAll(tempElements);
				}else {
					this.finalScrapedElements = tempElements;
				}
				

			}
			
			logger.info(this.finalScrapedElements + " is the list of final scraped elements");
		}catch(Exception e) {
			throw new RuntimeException(e.getLocalizedMessage());
		}
	}
			
}
