package seleniumutils.frameworklayer.frameworkstepdefinitions;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import seleniumutils.reusablestepdefinitions.ReusableStepDefinitions;

import java.util.HashMap;

import static java.lang.Integer.parseInt;

//import info.seleniumcucumber.applicationstepdefinitions.PredefinedStepDefinitions;

public class GWOOTBReusableStepDefinitions {
	protected WebDriver driver;
	protected HashMap<String,HashMap<String,HashMap<String, String>>> pageObjects;
	static ReusableStepDefinitions predef= new ReusableStepDefinitions();

	public String getXLSheetNameFromClass(){
		return this.getClass().getSimpleName().replaceAll("StepDefinitions","").toLowerCase();
	}
	public GWOOTBReusableStepDefinitions() {
		//this.driver = DriverUtil.getDefaultDriver();
		//this.elementObjects= ApachePOIExcel.readXL2Hash("PageObjects.xlsx");
	}

	//Navigation Steps


	// select option by text/value from dropdown
	@Then("^I gwselect option \"(.*?)\" by (index) from dropdown (.+)$")
	public static void gw_select_option_from_dropdown(String option,String optionBy,String element) throws Exception
	{
		int optionint;
		if(optionBy.equals("index")) {
			optionint = Integer.parseInt(option);
			predef.click(element);
			for (int i = 0; i < optionint; i++)
				predef.enter_key("ARROW_DOWN", element);
			predef.enter_key("ENTER", element);
		}
	}


	@Given("^I fill \"([^\"]*)\" into input field (.+)")
	public void iSearchForInTheInformationBar(String text, String element) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		predef.enter_text(text,element);
		predef.enter_key("TAB", element);
		predef.enter_key("TAB", element);
	}
	@Given("^I create a claim using ([^\\s]+) data$")
	public void iCreateAClaimUsingData(String inputDataKey) throws Throwable {
		predef.enter_text("NewClaim","general.goto_page");
		predef.enter_key("ENTER", "general.goto_page");
		predef.ele_enable("fnolsearchorcreatepolicy.enter_loss_date");

		predef.iFillDataFromYamlOntoThePage(inputDataKey, "fnolsearchorcreatepolicy" );
		predef.click("fnolsearchorcreatepolicy.next");
		predef.ele_enable("fnolselectvehicleinvolved.involved_vehicle_0");

		predef.iFillDataFromYamlOntoThePage(inputDataKey, "fnolselectvehicleinvolved" );
		predef.ele_enable("fnolbasicinformation.relation_to_insured");

		predef.iFillDataFromYamlOntoThePage(inputDataKey, "fnolbasicinformation" );
		predef.ele_enable("fnollossdetails.loss_location");

		predef.iFillDataFromYamlOntoThePage(inputDataKey, "fnollossdetails" );
		predef.ele_enable("fnolvehicledetails.loss_party_insured");

		predef.iFillDataFromYamlOntoThePage(inputDataKey, "fnolvehicledetails" );
		predef.ele_enable("fnollossdetails.loss_location");

		predef.click("fnollossdetails.next");
		predef.ele_enable("fnolservices.appraisal_checkbox");

		predef.click("fnolservices.next");
		predef.ele_enable("fnolassignment.claim_assign");

		predef.click("fnolassignment.finish");
		predef.ele_enable("fnolclaimsaved.view_created_claim");

		predef.ele_display("fnolclaimsaved.view_created_claim");
	}

	@When("^I search for \"([^\"]*)\" in the (.*) text box$")
	public void iSearchForInTheSearchBar(String searchString,String searchBar) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		predef.enter_text(searchString,searchBar);
		predef.enter_key("ENTER", searchBar);
	}

    @Given("^I create a policy using ([^\\s]+) data$")
    public void iCreateAPolicyUsingHappy_pathData(String inputDataKey) throws Throwable {
		this.iSearchForInTheSearchBar("NewSubmission","mysummary.search_bar");
		predef.ele_enable("newsubmissions.producer_organization");
		predef.iLandOnPage("newsubmissions");
		predef.iFillDataFromYamlOntoThePage(inputDataKey, "newsubmissions" );
		predef.ele_enable("offerings.quote");
		predef.iLandOnPage("offerings");
		predef.iFillDataFromYamlOntoThePage(inputDataKey, "Offerings" );
		predef.ele_enable("qualification.quote");
		predef.iLandOnPage("qualification");
		predef.iFillDataFromYamlOntoThePage(inputDataKey, "qualification" );
		predef.ele_enable("policyinfo.quote");
		predef.iLandOnPage("policyinfo");
		predef.iFillDataFromYamlOntoThePage(inputDataKey, "policyinfo" );
		predef.ele_enable("policylinedetails.quote");
		predef.iLandOnPage("policylinedetails");
		predef.iFillDataFromYamlOntoThePage(inputDataKey, "policylinedetails" );
		predef.ele_enable("locations.quote");
		predef.iLandOnPage("locations");
		predef.iFillDataFromYamlOntoThePage(inputDataKey, "locations" );
		predef.ele_enable("vehicles.quote");
		predef.iLandOnPage("vehicles");
		predef.iFillDataFromYamlOntoThePage(inputDataKey, "vehicles" );
		predef.ele_enable("vehicleinfo.vehicle_vin");
		predef.iLandOnPage("vehicleinfo");
		predef.iFillDataFromYamlOntoThePage(inputDataKey, "vehicleinfo" );
		predef.ele_enable("vehicles.quote");
		predef.iLandOnPage("vehicles");
		predef.click("vehicles.next");
		predef.ele_enable("stateinfo.quote");
		predef.iLandOnPage("stateinfo");
		predef.click("stateinfo.next");
		predef.ele_enable("drivers.quote");
		predef.iLandOnPage("drivers");
		predef.iFillDataFromYamlOntoThePage(inputDataKey, "drivers" );
		predef.ele_enable("driverinfo.driver_lastname");
		predef.iLandOnPage("driverinfo");
		predef.iFillDataFromYamlOntoThePage(inputDataKey, "driverinfo" );
		predef.ele_enable("drivers.quote");
		predef.iLandOnPage("drivers");
		predef.click("drivers.next");
		predef.ele_enable("coveredvehicles.quote");
		predef.iLandOnPage("coveredvehicles");
		predef.iFillDataFromYamlOntoThePage(inputDataKey, "coveredvehicles" );
		predef.ele_enable("modifiers.quote");
		predef.iLandOnPage("modifiers");
		predef.iFillDataFromYamlOntoThePage(inputDataKey, "modifiers" );
		predef.ele_enable("riskanalysis.quote");
		predef.iLandOnPage("riskanalysis");
		predef.iFillDataFromYamlOntoThePage(inputDataKey, "riskanalysis" );
		predef.ele_enable("policyreview.quote");
		predef.iLandOnPage("policyreview");
		predef.click("submissionwizard.line_setup");
		predef.click("submissionwizard.policy_review");
		predef.click("policyreview.quote");
		predef.ele_enable("quote.print_quote");
		predef.iLandOnPage("quote");
		predef.iFillDataFromYamlOntoThePage(inputDataKey, "quote" );
		predef.ele_enable("forms.bind_options");
		predef.iLandOnPage("forms");
		predef.iFillDataFromYamlOntoThePage(inputDataKey, "forms" );
		predef.ele_enable("payment.bind_options");
		predef.iLandOnPage("payment");
		predef.iFillDataFromYamlOntoThePage(inputDataKey, "payment" );
		predef.wait("2");
		predef.handle_alert();
		predef.ele_enable("submissionbound.view_policy");
		predef.iLandOnPage("submissionbound");
		predef.iGetElementTextAndWriteToFile("submissionbound.policy_number", "output", "commercial_auto");

    }
}