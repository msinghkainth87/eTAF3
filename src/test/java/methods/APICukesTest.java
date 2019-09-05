package methods;
import com.intuit.karate.cucumber.CucumberRunner;
import com.intuit.karate.cucumber.KarateStats;
import cucumber.api.CucumberOptions;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import seleniumutils.methods.GlobalProperties;
import seleniumutils.methods.TestCaseFailed;
import seleniumutils.methods.helpers.VideoRecord;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
//import cucumber.api.testng.AbstractTestNGCucumberTests;

//comment @RunWith and uncomment above import and the RunCukesTestNG class if you are running with TestNG
/**Cucumber Option to run Karate API*/
@CucumberOptions(tags={"@karate"},
		//Built-In report
		//plugin = {"pretty","html:target/cucumberHtmlReport.html","json:target/cucumberJSONReportKarate.json", "com.cucumber.listener.ExtentCucumberFormatter:target/report.html"},
		//cucumber_pro
		//plugin = {"io.cucumber.pro.JsonReporter12:default"},
		//extent_reports uncomment the @afterclass method in junit
		//plugin = {"com.sitture.ExtentFormatter:output/extent-report/index.html", "html:output/html-report"},
		//pretty:target/cucumber-json-report.json
		features = {"src/test/resources"},
		glue = {"com.intuit.karate","seleniumutils.reusablestepdefinitions", "seleniumutils.applicationlayer","seleniumutils.frameworklayer","methods"}
)
//Junit
public class APICukesTest {
	/**This is the API Test runner Junit*/
	@Test
    public void testParallel() {
       String karateOutputPath = "target";
        KarateStats stats = CucumberRunner.parallel(getClass(), 2, karateOutputPath);
    }
	/*
	 * Uncomment only if you want to run Functional and API tests on the same run (Also see @Afterclass annotation for more)
	@CucumberOptions(tags={"@chrome"},
			plugin = {"pretty","html:target/cucumberHtmlReport.html","json:target/cucumberJSONReportUI.json", "com.cucumber.listener.ExtentCucumberFormatter:target/report.html"},
			features = {"src/test/resources"},
			glue = {"seleniumutils.reusablestepdefinitions", "seleniumutils.applicationlayer","seleniumutils.frameworklayer","methods"}
	)
	@RunWith(Cucumber.class)
	public static class SubTestWithRunner {

	}*/
	@BeforeClass
	public static void setup() {
		System.out.println("Before Class annotation");
		if(GlobalProperties.VIDEORESULTS) {
			try {
				VideoRecord.startRecording();
				System.out.println("Started video recording");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	@AfterClass
	public static void beforeScenario() throws Exception {
		System.out.println("After Class annotation");
		/* Uncomment only if you want to run API and Functional tests together
			JUnitCore.runClasses(SubTestWithRunner.class);
		 */
		generateReport("target");
		try {
			if (GlobalProperties.VIDEORESULTS)
				VideoRecord.stopRecording();
		}catch(Exception e){
			throw new TestCaseFailed("Video recorder crashed. Please fix");
		}
	}

	private static void generateReport(String reportOutputPath) {
		Collection<File> jsonFiles = FileUtils.listFiles(new File(reportOutputPath), new String[]{"json"}, true);
		jsonFiles.remove(new File("target/cucumber-html-reports/aXe.json"));
		jsonFiles.remove(new File("target/cucumber-html-reports/htmlcs.json"));
		jsonFiles.remove(new File("target/cucumber-html-reports/testAccessibility.json"));
		List jsonPaths = new ArrayList(jsonFiles.size());
		for (File file : jsonFiles) {
			jsonPaths.add(file.getAbsolutePath());
		}
		Configuration config = new Configuration(new File("target"), "CucumberBDD");
		//config.addClassifications("Environment", System.getProperty("karate.env"));
		config.addClassifications("Platform", GlobalProperties.PLATFORM);
		config.addClassifications("Platform_version", GlobalProperties.PLATFORM_VERSION);
		config.addClassifications("Browser", GlobalProperties.BROWSER);
		ReportBuilder reportBuilder = new ReportBuilder(jsonPaths, config);
		reportBuilder.generateReports();
	}

}

/*TestNG
@Test
public class RunCukesTestNG extends AbstractTestNGCucumberTests{

}*/

