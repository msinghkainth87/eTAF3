package seleniumutils.applicationlayer.amazon.amazonstepdefinitions;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import org.openqa.selenium.WebDriver;
import seleniumutils.reusablestepdefinitions.ReusableStepDefinitions;

import java.util.HashMap;

//import info.seleniumcucumber.applicationstepdefinitions.PredefinedStepDefinitions;

public class CheckOutPageStepDefinitions {
	protected WebDriver driver;
	protected HashMap<String,HashMap<String,HashMap<String, String>>> pageObjects;
	static ReusableStepDefinitions predef= new ReusableStepDefinitions();

	public String getXLSheetNameFromClass(){
		return this.getClass().getSimpleName().replaceAll("StepDefinitions","").toLowerCase();
	}
	public CheckOutPageStepDefinitions() {
		//this.driver = DriverUtil.getDefaultDriver();
		//this.elementObjects= ApachePOIExcel.readXL2Hash("PageObjects.xlsx");
	}

	//Navigation Steps

	@Given("^I navigate directly to (.*) page for ([^\"]*) data$")
	public void iNavigateDirectlyToProceed_to_checkoutPageFor(String pageName,String inputDataKey) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		predef.navigate_toURL("amazon.home");
		predef.iFillDataFromYamlOntoThePage(inputDataKey, "amazon"); //Fill home page
		predef.check_title("","Amazon.com: samsung galaxy s10 plus case"); //landed in search page
		predef.iFillDataFromYamlOntoThePage(inputDataKey, "searchpage"); //fill search page
		predef.check_partial_text("","Samsung Galaxy S10 Plus"); //land in product page
		predef.iFillDataFromYamlOntoThePage(inputDataKey, "productpage"); //fill product page
		predef.check_title("","Amazon.com Shopping Cart"); //landed in order cart page
		predef.iFillDataFromYamlOntoThePage(inputDataKey, "ordercartpage"); //fill order cart page
		predef.check_title("","Amazon Sign In");
		predef.iFillDataFromYamlOntoThePage(inputDataKey, "loginpage"); //fill login page
		predef.check_title("","Select a shipping address"); //land in check out page

	}

	@And("^I sign out of amazon$")
	public void iSignOutOfAmazon() throws Throwable {
		predef.navigate_toURL("amazon.home");
		predef.hover_over_element("checkoutpage.account_list");
		predef.click("checkoutpage.sign_out");
		predef.navigate_toURL("amazon.home");
	}
}