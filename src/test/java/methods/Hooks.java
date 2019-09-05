package methods;

//import com.cucumber.listener.Reporter;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.AfterStep;
import cucumber.api.java.Before;
import env.DriverUtil;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import seleniumutils.methods.ApachePOIExcel;
import seleniumutils.methods.GlobalProperties;
import seleniumutils.methods.ScreenShotMethods;
import seleniumutils.methods.TestCaseFailed;
import seleniumutils.methods.YAMLReader;
import seleniumutils.methods.helpers.PageObjectGenerator;
//import seleniumutils.methods.helpers.ReportingUtils;


import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static seleniumutils.methods.GlobalProperties.APPLICATION_DATA_PATH;
import static seleniumutils.methods.JavascriptHandlingMethods.driver;

public class Hooks {

/*	@Before
	public void before(Scenario scenario){
		ReportingUtils.startReport(scenario.getName(),"");
	}*/
	@Before
	public void pageObjectInitialize()throws Exception{
//		System.out.println("Before hook");
//		if(GlobalProperties.VIDEORESULTS) {
//			try {
//				VideoRecord.startRecording();
//				System.out.println("Started video recording");
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		GlobalProperties globalProperties = new GlobalProperties();
        File dir = new File( APPLICATION_DATA_PATH);
		File[] files = dir.listFiles(new FilenameFilter()
		{
			public boolean accept(File dir, String name)
			{
				return name.startsWith("PageObjects") && name.endsWith(".xlsx");
			}
		});



        int index = files.length==1?0:-1;
        if(index<0)
			throw new IndexOutOfBoundsException("More than 1 file named PageObjects or none at all. Please inspect");
		PageObjectGenerator.elementObjects = ApachePOIExcel.readXL2FlatHash(files[index].getAbsolutePath());
		if(GlobalProperties.getConfigProperties().get("input_data_type").equalsIgnoreCase("YAML"))
			YAMLReader.readAllYAMLData();
		//TestDataHandler.testData=ApachePOIExcel.readXL2FlatHash("InputTestData.xlsx");
		//ApachePOIExcel.setXlData("PageObjects.xlsx");
	}


	@AfterStep("@stepwise_screenshots")
	public void afterStep(Scenario scenario) throws Throwable{
		scenario.embed(((TakesScreenshot)DriverUtil.getDefaultDriver()).getScreenshotAs(OutputType.BYTES),"image/png");
	}

	@After(order=1)//executes before Order=0
	public void afterScenario(Scenario scenario) throws Throwable{
		System.out.println("After hook");
//		Reporter.addScreenCaptureFromPath(ScreenShotMethods.captureScreenShot(OutputType.FILE));
		scenario.embed(((TakesScreenshot)DriverUtil.getDefaultDriver()).getScreenshotAs(OutputType.BYTES),"image/png");
		//ReportingUtils.endReport();
	}


}
