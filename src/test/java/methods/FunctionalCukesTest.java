package methods;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import seleniumutils.methods.GlobalProperties;
import seleniumutils.methods.SelfHealing.HistoricalPropertiesUtil;
import seleniumutils.methods.TestCaseFailed;
import seleniumutils.methods.helpers.VideoRecord;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static seleniumutils.methods.GlobalProperties.*;

//import com.cucumber.listener.Reporter;
//import cucumber.api.testng.AbstractTestNGCucumberTests;

//import cucumber.api.testng.AbstractTestNGCucumberTests;

//comment @RunWith and uncomment above import and the RunCukesTestNG class if you are running with TestNG
@CucumberOptions(tags={"@FUNCTIONAL"},
        plugin = {"pretty","html:target/cucumberHtmlReport.html","json:target/cucumberJSONReport.json"},
        features = {"src/test/resources"},
        glue = {"seleniumutils.reusablestepdefinitions", "seleniumutils.applicationlayer","seleniumutils.frameworklayer","methods"}
)
@RunWith(Cucumber.class)
//Junit
public class FunctionalCukesTest {
// TODO Get rid of static helper methods through out application - especially for non-read-only methods
    @BeforeClass
    public static void setup() throws IOException {
        System.out.println("Hello");
        if(VIDEORESULTS) {
            try {
                VideoRecord.startRecording();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(UPDATE_HISTORICAL_PROPERTIES.equals("true") || DEPLOY_SELF_HEALING.equals("true"))
            HistoricalPropertiesUtil.buildPropertyList();
    }

    @AfterClass
    public static void beforeScenario() throws Exception {
        try {
            if (VIDEORESULTS)
                VideoRecord.stopRecording();
        }catch(Exception e){
            throw new TestCaseFailed("Video recorder crashed. Please fix");
        }finally {
            Runtime r = Runtime.getRuntime();
            r.addShutdownHook(new MyThread());
            if(GlobalProperties.getConfigProperties().get("Update_Historical_Properties").equals("true"))
                HistoricalPropertiesUtil.flushHistoricalProperties();

            if(HistoricalPropertiesUtil.getHealedElements().size() > 0)
                HistoricalPropertiesUtil.updateObjectRepository();
        }
    }

    static void generateReport(String reportOutputPath) {
        Collection<File> jsonFiles = FileUtils.listFiles(new File(reportOutputPath), new String[]{"json"}, true);
        jsonFiles.remove(new File("target/cucumber-html-reports/aXe.json"));
        jsonFiles.remove(new File("target/cucumber-html-reports/htmlcs.json"));
        jsonFiles.remove(new File("target/cucumber-html-reports/testAccessibility.json"));
        jsonFiles.remove(new File("target/test-classes/policycenter/data/output.json"));
        List<String> jsonPaths = new ArrayList<String>(jsonFiles.size());
        for (File file : jsonFiles) {
            jsonPaths.add(file.getAbsolutePath());
        }
        Configuration config = new Configuration(new File("target"), "CucumberBDD");
        //config.addClassifications("Environment", System.getProperty("karate.env"));
        config.addClassifications("Platform", PLATFORM);
        config.addClassifications("Platform_version", PLATFORM_VERSION);
        config.addClassifications("Browser", BROWSER);
        ReportBuilder reportBuilder = new ReportBuilder(jsonPaths, config);
        reportBuilder.generateReports();
    }

}

class MyThread extends Thread{
    public void run(){
        FunctionalCukesTest.generateReport("target");
    }
}

/*TestNG
@Test
public class RunCukesTestNG extends AbstractTestNGCucumberTests{

}*/

