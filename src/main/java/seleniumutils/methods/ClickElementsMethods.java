package seleniumutils.methods;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static seleniumutils.methods.JavascriptHandlingMethods.highlightElementInControl;


public class ClickElementsMethods extends GetElementUsingBy implements BaseTest
{
	//GetElementUsingBy eleType= new GetElementUsingBy();
	private WebElement element=null;
	
	/** Method to click on an element
	@param accessType : String : Locator type (id, name, class, xpath, css)
	@param accessName : String : Locator value
	*/
	public void click(String accessType, String accessName)
	{
		element = wait.until(ExpectedConditions.elementToBeClickable(getElementByAttributes(accessType, accessName)));
		try {
			if(element.isEnabled()) {
				JavascriptHandlingMethods.scrollIntoView(element);
				JavascriptHandlingMethods.highlightElementInControl(element,"GreenYellow");
				Actions actions = new Actions(driver);
				actions.moveToElement(element).click().perform();
			}
		} catch (StaleElementReferenceException e) {
			click(accessType, accessName);
		}
	}
	
	/** Method to forcefully click on an element
	@param accessType : String : Locator type (id, name, class, xpath, css)
	@param accessName : String : Locator value
	*/
	public void clickForcefully(String accessType, String accessName)
	{
		element = wait.until(ExpectedConditions.elementToBeClickable(getElementByAttributes(accessType, accessName)));
		try {
			if(element.isEnabled()) {
				JavascriptExecutor executor = (JavascriptExecutor)driver;
				executor.executeScript("arguments[0].click();",element);
			}
		} catch (StaleElementReferenceException e) {
			click(accessType, accessName);
		}
	}
	
	/** Method to Double click on an element
	@param accessType : String : Locator type (id, name, class, xpath, css)
	@param accessValue : String : Locator value
	*/
	public void doubleClick(String accessType, String accessValue)
	{
		element = wait.until(ExpectedConditions.presenceOfElementLocated(getElementByAttributes(accessType, accessValue)));

		Actions action = new Actions(driver);
		action.moveToElement(element).doubleClick().perform();
	}
}