package seleniumutils.applicationlayer.claimcenter.claimcenterstepdefinitions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import seleniumutils.methods.ApachePOIExcel;
import seleniumutils.methods.YAMLReader;
import seleniumutils.methods.helpers.DataUtils;
import org.openqa.selenium.WebDriver;
import seleniumutils.methods.helpers.TestDataHandler;
import seleniumutils.methods.pageobjectmethods.PageObjectCreator;
import seleniumutils.methods.pageobjectmethods.PageObjectInterface;
import seleniumutils.reusablestepdefinitions.ReusableStepDefinitions;

import java.util.HashMap;
import java.util.Map;

//import info.seleniumcucumber.applicationstepdefinitions.PredefinedStepDefinitions;

public class LoginPageStepDefinitions {
	protected WebDriver driver;
	protected HashMap<String,HashMap<String,HashMap<String, String>>> pageObjects;
	static ReusableStepDefinitions predef= new ReusableStepDefinitions();

	public String getXLSheetNameFromClass(){
		return this.getClass().getSimpleName().replaceAll("StepDefinitions","").toLowerCase();
	}
	public LoginPageStepDefinitions() {
		//this.driver = DriverUtil.getDefaultDriver();
		//this.elementObjects= ApachePOIExcel.readXL2Hash("PageObjects.xlsx");
	}

	//Navigation Steps
	@Given("I navigate to ([^ ]+)")
	public void navigate_to(String link)
	{
		link= "login."+link.toLowerCase();
		predef.navigate_toURL(link);
	}


}