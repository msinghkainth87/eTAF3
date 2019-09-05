package seleniumutils.reusablestepdefinitions;

import com.fasterxml.jackson.core.type.TypeReference;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import env.DriverUtil;
import seleniumutils.methods.*;
import seleniumutils.methods.helpers.*;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.*;

import static seleniumutils.methods.GlobalProperties.APPLICATION;
import static seleniumutils.methods.GlobalProperties.APPLICATION_DATA_PATH;
import static seleniumutils.methods.GlobalProperties.TESTRESOURCESPATH;

public class ReusableStepDefinitions implements BaseTest {
	protected WebDriver driver = DriverUtil.getDefaultDriver();
	HashMap<String,ElementObject> pageObjects = PageObjectGenerator.elementObjects;
	//HashMap<String,HashMap<String,HashMap<String, String>>> data= ApachePOIExcel.getXlData();
	//Navigation Steps

	//Step to navigate to specified URL
	@Then("^I navigate to \"([^\"]*)\"$")
	public void navigate_to(String url)
	{
		navigationObj.navigateTo(url);
	}

	//Step to navigate to URL from Test Data sheet
	@Then("^I navigate to ([^\"]*) page$")
	public void navigate_toURL(String link)
	{
		ElementObject pObj= PageObjectGenerator.getElementObject(link);
		String url= pObj.getAccessName();
		navigationObj.navigateTo(url);
	}

	//Step to navigate forward
	@Then("^I navigate forward")
	public void navigate_forward()
	{
		navigationObj.navigate("forward");
	}

	//Step to navigate backward
	@Then("^I navigate back")
	public void navigate_back()
	{
		navigationObj.navigate("back");
	}


	// steps to refresh page
	@Then("^I refresh page$")
	public void refresh_page()
	{
		driver.navigate().refresh();
	}

	// Switch between windows

	//Switch to new window
	@Then("^I switch to new window$")
	public void switch_to_new_window()
	{
		navigationObj.switchToNewWindow();
	}

	//Switch to old window
	@Then("^I switch to previous window$")
	public void switch_to_old_window()
	{
		navigationObj.switchToOldWindow();
	}

	//Switch to new window by window title
	@Then("^I switch to window with title (.+)$")
	public void switch_to_window_by_title(String title) throws Exception
	{
		ElementObject pObj= PageObjectGenerator.getElementObject(title);
		String windowTitle= pObj.getAccessName().toLowerCase();
		navigationObj.switchToWindowByTitle(windowTitle);
	}

	//Close new window
	@Then("^I close new window$")
	public void close_new_window()
	{
		navigationObj.closeNewWindow();
	}

	// Switch between frame

	// Step to switch to frame by web element
	@Then("^I switch to frame (.*)$")
	public void switch_frame_by_element(String name)
	{
		ElementObject pObj= PageObjectGenerator.getElementObject(name);
		navigationObj.switchFrame(pObj.getAccessType(),pObj.getAccessName());
	}

	// step to switch to main content
	@Then("^I switch to main content$")
	public void switch_to_default_content()
	{
		navigationObj.switchToDefaultContent();
	}

	// To interact with browser

	// step to resize browser
	@Then("^I resize browser window size to width (\\d+) and height (\\d+)$")
	public void resize_browser(int width, int heigth)
	{
		navigationObj.resizeBrowser(width, heigth);
	}

	// step to maximize browser
	@Then("^I maximize browser window$")
	public void maximize_browser()
	{
		navigationObj.maximizeBrowser();
	}

	//Step to close the browser
	@Then("^I close browser$")
	public void close_browser()
	{
		navigationObj.closeDriver();
	}

	// zoom in/out page

	// steps to zoom in page
	@Then("^I zoom in page$")
	public void zoom_in()
	{
		navigationObj.zoomInOut("ADD");
	}

	// steps to zoom out page
	@Then("^I zoom out page$")
	public void zoom_out()
	{
		navigationObj.zoomInOut("SUBTRACT");
	}

	// zoom out webpage till necessary element displays

	// steps to zoom out till element displays
	@Then("^I zoom out page till I see element (.+)$")
	public void zoom_till_element_display(String element) throws Exception
	{
		ElementObject pObj= PageObjectGenerator.getElementObject(element);
		miscmethodObj.validateLocator(pObj.getAccessType());
		navigationObj.zoomInOutTillElementDisplay(pObj.getAccessType(),"substract", pObj.getAccessName());
	}

	// reset webpage view use

	@Then("^I reset page view$")
	public void reset_page_zoom()
	{
		navigationObj.zoomInOut("reset");
	}

	// scroll webpage

	@Then("^I scroll to (top|end) of page$")
	public void scroll_page(String to) throws Exception
	{
		navigationObj.scrollPage(to);
	}


	// scroll webpage to specific element

	@Then("^I scroll to element (.+)$")
	public void scroll_to_element(String element) throws Exception
	{
		ElementObject pObj= PageObjectGenerator.getElementObject(element);
		miscmethodObj.validateLocator(pObj.getAccessType());
		navigationObj.scrollToElement(pObj.getAccessType(), pObj.getAccessName());
	}

	// hover over element

	// Note: Doesn't work on Windows firefox
	@Then("^I hover over element (.+)$")
	public void hover_over_element(String element) throws Exception
	{
		ElementObject pObj= PageObjectGenerator.getElementObject(element);
		miscmethodObj.validateLocator(pObj.getAccessType());
		navigationObj.hoverOverElement(pObj.getAccessType(), pObj.getAccessName());
	}

	//Assertion steps

	/** page title checking
	 * @param present :
	 * @param title :
	 */
	@Then("^I should\\s*((?:not)?)\\s+see page title as \"(.+)\"$")
	public void check_title(String present,String title) throws TestCaseFailed
	{
		//System.out.println("Present :" + present.isEmpty());
		assertionObj.checkTitle(title,present.isEmpty());
	}

	// step to check element partial text
	@Then("^I should\\s*((?:not)?)\\s+see page title having partial text as \"(.*?)\"$")
	public void check_partial_text(String present, String partialTextTitle) throws TestCaseFailed
	{
		//System.out.println("Present :" + present.isEmpty());
		assertionObj.checkPartialTitle(partialTextTitle, present.isEmpty());
	}

	// step to check element text
	@Then("^element (.+) should\\s*((?:not)?)\\s+have text as \"(.*?)\"$")
	public void check_element_text(String element,String present,String value) throws Exception
	{
		ElementObject pObj= PageObjectGenerator.getElementObject(element);
		miscmethodObj.validateLocator(pObj.getAccessType());
		assertionObj.checkElementText(pObj.getAccessType(), value, pObj.getAccessName(),present.isEmpty());
	}

	//step to check element partial text
	@Then("^element (.+) should\\s*((?:not)?)\\s+have partial text as \"(.*?)\"$")
	public void check_element_partial_text(String element,String present,String value) throws Exception
	{
		ElementObject pObj= PageObjectGenerator.getElementObject(element);
		miscmethodObj.validateLocator(pObj.getAccessType());
		assertionObj.checkElementPartialText(pObj.getAccessType(), value, pObj.getAccessName(), present.isEmpty());
	}

	// step to check attribute value
	@Then("^element (.+) should\\s*((?:not)?)\\s+have attribute \"(.*?)\" with value \"(.*?)\"$")
	public void check_element_attribute(String element,String present,String attrb,String value) throws Exception
	{
		ElementObject pObj= PageObjectGenerator.getElementObject(element);
		miscmethodObj.validateLocator(pObj.getAccessType());
		assertionObj.checkElementAttribute(pObj.getAccessType(), attrb, value, pObj.getAccessName(), present.isEmpty());
	}

	// step to check element enabled or not
	@Then("^element (.+) should\\s*((?:not)?)\\s+be (enabled|disabled)$")
	public void check_element_enable(String element,String present,String state) throws Exception
	{
		ElementObject pObj= PageObjectGenerator.getElementObject(element);
		miscmethodObj.validateLocator(pObj.getAccessType());
		boolean flag = state.equals("enabled");
		if(!present.isEmpty())
		{
			flag = !flag;
		}
		assertionObj.checkElementEnable(pObj.getAccessType(), pObj.getAccessName(), flag);
	}

	//step to check element present or not
	@Then("^element (.+) should\\s*((?:not)?)\\s+be present$")
	public void check_element_presence(String element,String present) throws Exception
	{
		ElementObject pObj= PageObjectGenerator.getElementObject(element);
		miscmethodObj.validateLocator(pObj.getAccessType());
		assertionObj.checkElementPresence(pObj.getAccessType(), pObj.getAccessName(), present.isEmpty());
	}

	//step to assert checkbox is checked or unchecked
	@Then("^checkbox (.+) should be (checked|unchecked)$")
	public void is_checkbox_checked(String element,String state) throws Exception
	{
		ElementObject pObj= PageObjectGenerator.getElementObject(element);
		miscmethodObj.validateLocator(pObj.getAccessType());
		boolean flag = state.equals("checked");
		assertionObj.isCheckboxChecked(pObj.getAccessType(), pObj.getAccessName(), flag);
	}

	//steps to assert radio button checked or unchecked
	@Then("^radio button (.+) should be (selected|unselected)$")
	public void is_radio_button_selected(String element,String state) throws Exception
	{
		ElementObject pObj= PageObjectGenerator.getElementObject(element);
		miscmethodObj.validateLocator(pObj.getAccessType());
		boolean flag = state.equals("selected");
		assertionObj.isRadioButtonSelected(pObj.getAccessType(), pObj.getAccessName(), flag);
	}

	//steps to assert option by text from radio button group selected/unselected - Option by Text or Option by Value
	@Then("^option \"(.*?)\" by (.+) from radio button group (.+) should be (selected|unselected)$")
	public void is_option_from_radio_button_group_selected(String option,String attrb,String element,String state) throws Exception
	{
		ElementObject pObj= PageObjectGenerator.getElementObject(element);
		miscmethodObj.validateLocator(pObj.getAccessType());
		boolean flag = state.equals("selected");
		assertionObj.isOptionFromRadioButtonGroupSelected(pObj.getAccessType(),attrb,option,pObj.getAccessName(),flag);
	}
	
	//step to assert javascript pop-up alert text
	@Then("^I should see alert text as \"(.*?)\"$")
	public void check_alert_text(String actualValue) throws TestCaseFailed
	{
		assertionObj.checkAlertText(actualValue);
	}

	// step to select dropdown list - Option by Text or Option by Value
	@Then("^option \"(.*?)\" by (.+) from dropdown (.+) should be (selected|unselected)$")
	public void is_option_from_dropdown_selected(String option,String by,String element,String state) throws Exception
	{
		ElementObject pObj= PageObjectGenerator.getElementObject(element);
		miscmethodObj.validateLocator(pObj.getAccessType());
		boolean flag = state.equals("selected");
		assertionObj.isOptionFromDropdownSelected(pObj.getAccessType(),by,option,pObj.getAccessName(),flag);
	}

	//Input steps

	// enter text into input field steps
	@Then("^I enter \"([^\"]*)\" into input field (.+)$")
	public void enter_text(String text, String element) throws Exception
	{
		ElementObject pObj= PageObjectGenerator.getElementObject(element);
		miscmethodObj.validateLocator(pObj.getAccessType());//type means text or select or any html element type
		inputObj.enterText(pObj.getAccessType(), text, pObj.getAccessName());
	}

	@Then("^I enter ([^\"]*) into input field (.+)$")
	public void enter_text_from_input_data(String lookup, String element) throws Exception
	{
		ElementObject dObj=PageObjectGenerator.getElementObject(lookup);
		String text= dObj.getAccessName();
		ElementObject pObj= PageObjectGenerator.getElementObject(element);
		miscmethodObj.validateLocator(pObj.getAccessType());
		inputObj.enterText(pObj.getAccessType(), text, pObj.getAccessName());
	}


	// press keyboard keys into input field steps
	@Then("^I press \"([^\"]*)\" key into input field (.+)$")
	public void enter_key(String key, String element) throws Exception
	{
		ElementObject pObj= PageObjectGenerator.getElementObject(element);
		Keys k = HelperUtils.identifyKey(key);
		miscmethodObj.validateLocator(pObj.getAccessType());
		inputObj.enterKeys(pObj.getAccessType(), k, pObj.getAccessName());
	}

	// clear input field steps
	@Then("^I clear input field (.+)$")
	public void clear_text(String element) throws Exception
	{
		ElementObject pObj= PageObjectGenerator.getElementObject(element);
		miscmethodObj.validateLocator(pObj.getAccessType());
		inputObj.clearText(pObj.getAccessType(), pObj.getAccessName());
	}

	// select option by text/value from dropdown
	@Then("^I select \"(.*?)\" option by (index|value|text) from dropdown (.+)$")
	public void select_option_from_dropdown(String option,String optionBy,String element) throws Exception
	{
		ElementObject pObj= PageObjectGenerator.getElementObject(element);
		miscmethodObj.validateLocator(pObj.getAccessType());
		miscmethodObj.validateOptionBy(optionBy);
		inputObj.selectOptionFromDropdown(pObj.getAccessType(),optionBy, option, pObj.getAccessName());
	}

	// deselect option by text/value from multiselect
	@Then("^I deselect \"(.*?)\" option by (index|value|text) from multiselect dropdown (.+)$")
	public void deselect_option_from_multiselect_dropdown(String option,String optionBy, String element) throws Exception
	{
		ElementObject pObj= PageObjectGenerator.getElementObject(element);
		miscmethodObj.validateLocator(pObj.getAccessType());
		optionBy=optionBy.equalsIgnoreCase("index")?"selectByIndex":optionBy;
		miscmethodObj.validateOptionBy(optionBy);
		inputObj.deselectOptionFromDropdown(pObj.getAccessType(), optionBy, option, pObj.getAccessName());
	}

	// step to select option from mutliselect dropdown list
	/*@Then("^I select all options from multiselect dropdown (.+)$")
	public void select_all_option_from_multiselect_dropdown(String element) throws Exception
	{
	miscmethod.validateLocator(type);
	//inputObj.
	//select_all_option_from_multiselect_dropdown(pObj.getAccessType(), access_name)
	}*/

	// step to unselect option from mutliselect dropdown list
	@Then("^I deselect all options from multiselect dropdown (.+)$")
	public void unselect_all_option_from_multiselect_dropdown(String element) throws Exception
	{
		ElementObject pObj= PageObjectGenerator.getElementObject(element);
		miscmethodObj.validateLocator(pObj.getAccessType());
		inputObj.unselectAllOptionFromMultiselectDropdown(pObj.getAccessType(), pObj.getAccessName());
	}

	//check checkbox steps
	@Then("^I check the checkbox (.+)$")
	public void check_checkbox(String element) throws Exception
	{
		ElementObject pObj= PageObjectGenerator.getElementObject(element);
		miscmethodObj.validateLocator(pObj.getAccessType());
		inputObj.checkCheckbox(pObj.getAccessType(), pObj.getAccessName());
	}

	//uncheck checkbox steps
	@Then("^I uncheck the checkbox (.+)$")
	public void uncheck_checkbox(String element) throws Exception
	{
		ElementObject pObj= PageObjectGenerator.getElementObject(element);
		miscmethodObj.validateLocator(pObj.getAccessType());
		inputObj.uncheckCheckbox(pObj.getAccessType(), pObj.getAccessName());
	}

	//steps to toggle checkbox
	@Then("^I toggle checkbox (.+)$")
	public void toggle_checkbox(String element) throws Exception
	{
		ElementObject pObj= PageObjectGenerator.getElementObject(element);
		miscmethodObj.validateLocator(pObj.getAccessType());
		inputObj.toggleCheckbox(pObj.getAccessType(), pObj.getAccessName());
	}

	// step to select radio button
	@Then("^I select radio button (.+)$")
	public void select_radio_button(String element) throws Exception
	{
		ElementObject pObj= PageObjectGenerator.getElementObject(element);
		miscmethodObj.validateLocator(pObj.getAccessType());
		inputObj.selectRadioButton(pObj.getAccessType(), pObj.getAccessName());
	}

	// steps to select option by text from radio button group
	@Then("^I select \"(.*?)\" option by (value|text) from radio button group (.+)$")
	public void select_option_from_radio_btn_group(String option,String by, String element) throws Exception
	{
		ElementObject pObj= PageObjectGenerator.getElementObject(element);
		miscmethodObj.validateLocator(pObj.getAccessType());
		//miscmethodObj.validateOptionBy(optionBy);
		inputObj.selectOptionFromRadioButtonGroup(pObj.getAccessType(), option, by, pObj.getAccessName());
	}

	//Click element Steps

	// click on web element
	@Then("^I click on element (.+)$")
	public void click(String element) throws Exception
	{
		DriverUtil.waitForJSandJQueryToLoad();
		DriverUtil.waitForAjaxCall();
		ElementObject pObj= PageObjectGenerator.getElementObject(element);
		miscmethodObj.validateLocator(pObj.getAccessType());
		clickObj.click(pObj.getAccessType(), pObj.getAccessName());

	}

	//Forcefully click on element
	@Then("^I forcefully click on element (.+)$")
	public void click_forcefully(String element) throws Exception
	{
		ElementObject pObj= PageObjectGenerator.getElementObject(element);
		miscmethodObj.validateLocator(pObj.getAccessType());
		clickObj.clickForcefully(pObj.getAccessType(),pObj.getAccessName());
		DriverUtil.waitForAjaxCall();
	}

	// double click on web element
	@Then("^I double click on element (.+)$")
	public void double_click(String element) throws Exception
	{
		ElementObject pObj= PageObjectGenerator.getElementObject(element);
		miscmethodObj.validateLocator(pObj.getAccessType());
		clickObj.doubleClick(pObj.getAccessType(), pObj.getAccessName());
		DriverUtil.waitForAjaxCall();
	}

	//Progress methods

	// explicit wait for specific period of time
	@Then("^I wait for (\\d+) sec$")
	public void wait(String time) throws NumberFormatException, InterruptedException
	{
		progressObj.wait(time);
	}

	@Then("^the element (.+) is displayed$")
	public void ele_display(String element) throws Exception
	{
		ElementObject pObj= PageObjectGenerator.getElementObject(element);
		miscmethodObj.validateLocator(pObj.getAccessType());
		progressObj.waitForElementToDisplay(pObj.getAccessType(), pObj.getAccessName(), "10");
	}

	@Then("^the element (.+) is enabled$")
	public void ele_enable(String element) throws Exception
	{
		ElementObject pObj= PageObjectGenerator.getElementObject(element);
		miscmethodObj.validateLocator(pObj.getAccessType());
		progressObj.waitForElementToClick(pObj.getAccessType(), pObj.getAccessName(), "10");
	}

	//explicit wait for specific element to display for specific period of time
	@Then("^I wait (\\d+) seconds for element (.+) to display$")
	public void wait_for_ele_to_display(String duration, String element) throws Exception
	{
		ElementObject pObj= PageObjectGenerator.getElementObject(element);
		miscmethodObj.validateLocator(pObj.getAccessType());
		progressObj.waitForElementToDisplay(pObj.getAccessType(), pObj.getAccessName(), duration);
	}

	// explicit wait for specific element to enable for specific period of time
	@Then("^I wait (\\d+) seconds for element (.+) to be enabled$")
	public void wait_for_ele_to_click(String duration, String element) throws Exception
	{
		ElementObject pObj= PageObjectGenerator.getElementObject(element);
		miscmethodObj.validateLocator(pObj.getAccessType());
		progressObj.waitForElementToClick(pObj.getAccessType(), pObj.getAccessName(), duration);
	}

	//JavaScript handling steps

	//Step to handle java script
	@Then("^I accept alert$")
	public void handle_alert()
	{
		javascriptObj.handleAlert("accept");
	}

	//Steps to dismiss java script
	@Then("^I dismiss alert$")
	public void dismiss_alert()
	{
		javascriptObj.handleAlert("dismiss");
	}

	//Screen shot methods

	@Then("^I take screenshot$")
	public void take_screenshot() throws IOException
	{
		screenshotObj.takeScreenShot();
	}

	//Configuration steps

	// step to print configuration
	@Then("^I print configuration$")
	public void print_config()
	{
		configObj.printDesktopConfiguration();
	}

	@After(order=0)
	public final void tearDown() {
		//DriverUtil.closeDriver();
	}


	@Then("^get me the current page$")
	public void getCurrentPage() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		ElementObject pObj = PageObjectGenerator.getElementObject("general.page_title");
		miscmethodObj.validateLocator(pObj.getAccessType());
		String pagename = assertionObj.getElementText(pObj.getAccessType(), pObj.getAccessName());
	}

	@Then("^I wait for ajax call to be completed$")
	public void iWaitForAjaxCallToBeCompleted() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		DriverUtil.waitForAjaxCall();
	}

	@Then("^I run HTMLCS accessibility test on the current page$")
	public void iValidateHTMLCS() throws Throwable {
		// Write code here th\wat turns the phrase above into concrete actions
		AccessibilityHelper accessibilityHelper = new AccessibilityHelper();
		accessibilityHelper.runCodeSniffer();
	//	accessibilityHelper.testAccessibility();
	}
	@Then("^I run accessibility tests using (.*) tools on the page$")
	public void iValidateAccessibility(String tools) throws Throwable {
		// Write code here that turns the phrase above into concrete actions

		tools=tools.toLowerCase();
		List<String> toolSet= Arrays.asList(tools.split(","));
		AccessibilityHelper accessibilityHelper = new AccessibilityHelper();
		String pageName=assertionObj.getPageTitle();
		if(toolSet.contains("htmlcs"))
			accessibilityHelper.runCodeSniffer(pageName);
		if(toolSet.contains("axe"))
			accessibilityHelper.testAccessibility(pageName);
	}

	@Given("^I am on (.+) page$")
	public void iAmOnPage(String pagename) throws Throwable{
		iLandOnPage(pagename);
	}
	@Then("^I land on (.+) page$")
	public void iLandOnPage(String pagename) throws Throwable {
		// To use this step, add the unique page identifier to each page object sheet with the element name as page_title
		//TODO: Currently the framework is too fast for GWCC. Hence added a manual delay
		DriverUtil.waitForAjaxCall();
		DriverUtil.waitForJSandJQueryToLoad();
		pagename=pagename.toLowerCase();
		ElementObject pObj= PageObjectGenerator.getElementObject(pagename+".page_title_expected");
		//System.out.println("before land on "+pagename+ " check time: "+ Instant.now());
		check_element_text(pagename+".page_title","",pObj.getAccessName());
		//System.out.println("after land on "+pagename+ "check time: "+ Instant.now());
//		PageObjectCreator.createPageObject(pagename);
	}


	@Given("I upload (.+) file to (.+)")
	public void iUploadIntroToTestingJpgFileToCapsFile_upload(String fileName, String element) throws Exception {
		ElementObject pObj= PageObjectGenerator.getElementObject(element);
		miscmethodObj.validateLocator(pObj.getAccessType());//type means text or select or any html element type
		String filePath = System.getProperty("user.dir") + "\\" + fileName;
		inputObj.enterText(pObj.getAccessType(), filePath, pObj.getAccessName());

	}

	@When("^I fill (.+) data from (.+) yaml onto the page$")
	public void iFillDataFromYamlOntoThePage(String inputDataKey, String pageName ) throws Throwable {
		Map<String,Object> tMap= YAMLReader.getMasterDataMap();
		String key=pageName+"."+inputDataKey;
		Map<String,Object> newMap= TestDataHandler.traverseToMap(key.toUpperCase(),tMap);
		TestDataHandler.injectInputData(newMap,pageName.toUpperCase());
	}


	@Then("^I get the text from element ([^ ]+) and write it to ([^ ]+) file under (.+) data")
	public void iGetElementTextAndWriteToFile(String element, String fileName,String dataKey) throws Throwable {
		ElementObject pObj= PageObjectGenerator.getElementObject(element);
		miscmethodObj.validateLocator(pObj.getAccessType());
		String output = assertionObj.getElementText(pObj.getAccessType(), pObj.getAccessName());
		HashMap<String,Object> jsonOutput = new HashMap<String,Object>();
		/*jsonOutput.put(element,output);
		JSONObject jsonObject = new JSONObject(jsonOutput);
		JSONHelper.JSONWrite(jsonObject.toString(),GlobalProperties.APPLICATION_DATA_PATH,"output.json");*/
		JSONHelper.appendToJSON(jsonOutput, APPLICATION_DATA_PATH, "output.json", new TypeReference<HashMap<String,HashMap<String,String>>>(){}, output);
		YAMLHelper.appendToYAML(jsonOutput, TESTRESOURCESPATH+"\\claimcenter\\data\\inputdata\\fnolsearchorcreatepolicy.yaml", output, dataKey);
	}

	@And("^I switch to ([^\\s]+) application$")
	public void iSwitchApplicationAtRuntime(String application) throws Throwable {
		GlobalProperties.APPLICATION=application;
		GlobalProperties.APPLICATION_DATA_PATH=TESTRESOURCESPATH+"\\"+APPLICATION+"\\data\\";
		GlobalProperties.APPLICATION_PAGE_OBJECTS_PATH = APPLICATION_DATA_PATH + "\\PageObjects.xlsx";
		GlobalProperties.PAGEIDPATH= APPLICATION_DATA_PATH+"\\"+"pageidentifiers.properties";
		/*String argv = String.format("--tags %s --plugin pretty --plugin html:target/cucumberHtmlReport.html --plugin json:target/cucumberJSONReport.json --plugin com.cucumber.listener.ExtentCucumberFormatter:target/report.html --snippets UNDERSCORE --glue seleniumutils.reusablestepdefinitions --glue seleniumutils.applicationlayer --glue seleniumutils.frameworklayer --glue methods src/test/resources",tag);
		RuntimeOptions runtimeOptions = new RuntimeOptions(Arrays.asList(argv.split(" ")));*/
	}
}