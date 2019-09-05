package methods;

//import com.cucumber.listener.Reporter;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import seleniumutils.methods.TestCaseFailed;
import seleniumutils.methods.helpers.VideoRecord;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static seleniumutils.methods.GlobalProperties.*;


//comment @RunWith and uncomment above import and the RunCukesTestNG class if you are running with TestNG
@CucumberOptions(tags={"@uat"},
        plugin = {"pretty","html:target/cucumberHtmlReport.html","json:target/[CUCABLE:RUNNER].json"},
        features = {"target/parallel/features/[CUCABLE:FEATURE].feature"},
        glue = {"seleniumutils.reusablestepdefinitions", "seleniumutils.applicationlayer","seleniumutils.frameworklayer","methods"}
)
@RunWith(Cucumber.class)
//Junit
public class CucableJavaTemplate {

  /*@BeforeClass
    public static void setup() {
        if(VIDEORESULTS) {
            try {
                VideoRecord.startRecording();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
            r.addShutdownHook(new MyNewThread());

        }
    }

    static void generateReport(String reportOutputPath) {
        if(REPORT_TYPE.equalsIgnoreCase("extent")) {
//            Reporter.loadXMLConfig(EXTENT_CONFIG_PATH);
//            Reporter.setSystemInfo("User Name", System.getProperty("user.name"));
//            Reporter.setSystemInfo("Time Zone", System.getProperty("user.timezone"));
//            Reporter.setSystemInfo("Machine", PLATFORM + " "+ PLATFORM_VERSION);
//            Reporter.setSystemInfo("Browser", BROWSER);
        }
        Collection<File> jsonFiles = FileUtils.listFiles(new File(reportOutputPath), new String[]{"json"}, true);
        jsonFiles.remove(new File("target/cucumber-html-reports/aXe.json"));
        jsonFiles.remove(new File("target/cucumber-html-reports/htmlcs.json"));
        jsonFiles.remove(new File("target/cucumber-html-reports/testAccessibility.json"));
        jsonFiles.remove(new File("target/test-classes/policycenter/data/output.json"));
        List jsonPaths = new ArrayList(jsonFiles.size());
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

class MyNewThread extends Thread{
    public void run(){
        FunctionalCukesTest.generateReport("target");
    }*/
}

/*TestNG
@Test
public class RunCukesTestNG extends AbstractTestNGCucumberTests{

}*/

