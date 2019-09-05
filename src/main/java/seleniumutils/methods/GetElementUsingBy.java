package seleniumutils.methods;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import env.DriverUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import seleniumutils.methods.SelfHealing.HistoricalPropertiesUtil;
import seleniumutils.methods.SelfHealing.SelfHealingWrapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import static seleniumutils.methods.GlobalProperties.*;

public class GetElementUsingBy
{
	public static String getElementName() {
		return elementName;
	}

	public static void setElementName(String elementName) {
		GetElementUsingBy.elementName = elementName;
	}

	public static String elementName;
	public WebDriver driver;
	protected WebDriverWait wait;
	Map<String, String> map = new HashMap<String, String>();
	Boolean selfHealingSuccess = false;
	public GetElementUsingBy(){
		driver = DriverUtil.getDefaultDriver();
		wait = new WebDriverWait(driver, 30);
	}
	/**Method to select element 'by' type
	 * @param attr : String : 'By' type
	 * @param access_name : String : Locator value
	 * @return By
	 */
	public By getElementByAttributes(String attr, String access_name)
	{
		HistoricalPropertiesUtil HPUtil = new HistoricalPropertiesUtil();
		By by;
		switch(attr.toLowerCase())
		{
			case "id" : by = By.id(access_name); break;
			case "name" : by = By.name(access_name); break;
			case "class" : by = By.className(access_name); break;
			case "xpath" : by = By.xpath(access_name); break;
			case "css" : by = By.cssSelector(access_name); break;
			case "linkText" : by = By.linkText(access_name); break;
			case "partialLinkText" : by = By.partialLinkText(access_name); break;
			case "tagName" : by = By.tagName(access_name); break;
			default : by = null;

		}
		try {
			if(DEPLOY_SELF_HEALING) {
				wait.until(ExpectedConditions.presenceOfElementLocated(by));
			}
		}
		catch (TimeoutException e)
		{
			SelfHealingWrapper shw = new SelfHealingWrapper();
			try {
				String updatedJson = shw.SelfHealingWrapper(elementName);
				selfHealingSuccess = true;
				ObjectMapper mapper = new ObjectMapper();
				map = mapper.readValue(updatedJson, new TypeReference<Map<String, String>>(){});
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			String preferredProperty = attr;

			if(preferredProperty.equals("id") || preferredProperty.equals("xpath")) {
				preferredProperty = "htmlId";
				attr = "id";
			}
			Boolean originalLocatorExists = false;
			for (String property : map.keySet()) {
				if(property.equals(preferredProperty) && map.get(property) != null && !map.get(property).equals("")){
					by = getElementByAttributesAfterSelfHeal(attr, map.get(preferredProperty));
					access_name = map.get(property);
					originalLocatorExists = true;
					break;
				}
			}
			if(!originalLocatorExists) for (String property : map.keySet()) {
				if (property.equals("htmlId") && map.get(property) != null && !map.get(property).equals("")) {
					attr = "id";
					access_name = map.get(property);
					by = getElementByAttributesAfterSelfHeal(attr, map.get(property));
					break;
				} else if (property.equals("name") && map.get(property) != null && !map.get(property).equals("")) {
					attr = "name";
					access_name = map.get(property);
					by = getElementByAttributesAfterSelfHeal(attr, map.get(property));
					break;
				} else if (property.equals("class") && map.get(property) != null && !map.get(property).equals("")) {
					attr = "class";
					access_name = map.get(property);
					by = getElementByAttributesAfterSelfHeal(attr, map.get(property));
					break;
				}
			}
		}
		if(GlobalProperties.getConfigProperties().get("Update_Historical_Properties").equals("true")){
			HPUtil.updateHistoricalProperties(elementName, attr, access_name);
		}

		if(selfHealingSuccess)
			HPUtil.healedElementRepository(elementName, attr, access_name);

		return by;
	}

	protected By getElementByAttributesAfterSelfHeal(String attr, String access_name)
	{
		switch(attr.toLowerCase())
		{
			case "id" : return By.id(access_name);
			case "name" : return By.name(access_name);
			case "class" : return By.className(access_name);
			case "xpath" : return By.xpath(access_name);
			case "css" : return By.cssSelector(access_name);
			case "linkText" : return By.linkText(access_name);
			case "partialLinkText" : return By.partialLinkText(access_name);
			case "tagName" : return By.tagName(access_name);
			default : return null;

		}

	}

	/**
	 * Identifies the type of element passed.
	 *
	 * @param attr        the attr
	 * @param access_name the access name
	 * @return the element tag/type
	 */
	public String getElementType(String attr, String access_name)
	{
		String tagName = null;
		try {
			tagName = driver.findElement(getElementByAttributes(attr, access_name)).getTagName();
		} catch (StaleElementReferenceException e) {
			tagName = driver.findElement(getElementByAttributes(attr, access_name)).getTagName();
		}
		switch(tagName.toLowerCase()){
			case "input":
				String elementType;
				try {
					elementType = driver.findElement(getElementByAttributes(attr, access_name)).getAttribute("type");
				} catch (StaleElementReferenceException e) {
					elementType = driver.findElement(getElementByAttributes(attr, access_name)).getAttribute("type");
				}
				switch(elementType.toLowerCase()){
					case "tel": 			return "textbox";
					case "time":			return "textbox";
					case "url":             return "textbox";
					case "search":          return "textbox";
					case "number":          return "textbox";
					case "email":           return "textbox";
					case "datetime-local":  return "textbox";
					case "date":            return "textbox";
					case "week":            return "textbox";
					case "month":           return "textbox";
					case "password":        return "textbox";
					case "text":			return "textbox";

					case "color":           return "button";
					case "button":          return "button";
					case "checkbox":        return "button";
					case "radio":           return "button";
					case "reset":           return "button";
					case "submit":			return "button";
					default:				return "button";
				}
			default:   return tagName;
		}
 	}
}
