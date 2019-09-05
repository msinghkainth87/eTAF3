package seleniumutils.applicationlayer.policycenter;

import cucumber.api.java.en.Given;
import org.openqa.selenium.WebDriver;
import seleniumutils.reusablestepdefinitions.ReusableStepDefinitions;

import java.util.HashMap;

//import info.seleniumcucumber.applicationstepdefinitions.PredefinedStepDefinitions;

public class PolicyCenterPageStepDefinitions {
	protected WebDriver driver;
	protected HashMap<String,HashMap<String,HashMap<String, String>>> pageObjects;
	static ReusableStepDefinitions predef= new ReusableStepDefinitions();

	public String getXLSheetNameFromClass(){
		return this.getClass().getSimpleName().replaceAll("PageStepDefinitions","").toLowerCase();
	}
	public PolicyCenterPageStepDefinitions() {
		//this.driver = DriverUtil.getDefaultDriver();
		//this.elementObjects= ApachePOIExcel.readXL2Hash("PageObjects.xlsx");
	}

	//Navigation Steps
	@Given("I navigate to Policy Center as (.*) user")
	public void navigate_to(String data) throws Throwable
	{
		String link= "policycenter.login";
		predef.navigate_toURL(link);
		predef.iFillDataFromYamlOntoThePage(data, "policycenter");
	}


}