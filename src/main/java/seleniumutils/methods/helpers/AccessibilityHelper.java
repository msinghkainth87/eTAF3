package seleniumutils.methods.helpers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.opendevl.JFlat;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import seleniumutils.methods.JavascriptHandlingMethods;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static seleniumutils.methods.GlobalProperties.ACCESSIBILITY_REPORTS_PATH;
import static seleniumutils.methods.GlobalProperties.ACCESSIBILITY_REPORT_TYPE;

public class AccessibilityHelper {

    private WebDriver driver;
    private JavascriptExecutor js;
    private static final URL scriptUrl = AccessibilityHelper.class.getResource("/accessibility/axe.min.js");
    private ArrayList<LinkedHashMap<String,String>> messageList;
    private HashMap<String,ArrayList<LinkedHashMap<String,String>>> htmlcsResult;
    public AccessibilityHelper() throws IOException {
        this.driver = JavascriptHandlingMethods.driver;
        js = JavascriptHandlingMethods.js;
        htmlcsResult= new HashMap<>();
    }

    public void runCodeSniffer(String...pageName) throws IOException {
        messageList=new ArrayList<>();
        LinkedHashMap<String,String> message;
        String jquery_content = Jsoup.connect("http://squizlabs.github.io/HTML_CodeSniffer/build/HTMLCS.js").ignoreContentType(true).execute().body();
        js.executeScript(jquery_content);
        js.executeScript("window.HTMLCS_RUNNER.run('WCAG2AA');");
        LogEntries logs = driver.manage().logs().get("browser");
        String[] msg;

        for (LogEntry entry : logs) {
            if(!entry.getMessage().contains("console-api "))
                continue;
            String sentinel=entry.getMessage().replaceFirst("console-api ([\\d:])+ ","");
             if(sentinel.equalsIgnoreCase("\"done\"")){
                 break;
             }
             message = new LinkedHashMap<>();
             String text=entry.getMessage().replaceFirst("^\"(.*)\"$","$1");
             msg=text.split("\\|");
             message.put("Type",msg[0].replaceFirst("^(.*)\\[HTMLCS\\] ",""));
             message.put("Code",msg[1]);
             message.put("Node",msg[2]);
             message.put("elementID",msg[3]);
             message.put("message",msg[4]);
             message.put("HTML",HelperUtils.removeUTFCharacters(msg[5]).toString().replace("\\", ""));
             if(pageName!=null&&pageName.length>0)
                 message.put("PageName",pageName[0]);
             messageList.add(message);
        }

        //convert to json

        ObjectMapper objectMapper = new ObjectMapper();
        DateTimeHelper dateTimeHelper = new DateTimeHelper();
        String timeStamp=dateTimeHelper.getTimeStamp();
        htmlcsResult=objectMapper.readValue(JSONHelper.JSONRead(ACCESSIBILITY_REPORTS_PATH,"HTMLCS.json").toString(),new TypeReference<HashMap<String,ArrayList<LinkedHashMap<String,String>>>>(){});
        htmlcsResult.put(timeStamp,messageList);

        String jsonString = objectMapper.writeValueAsString(htmlcsResult);
        JSONHelper.JSONWrite(jsonString,ACCESSIBILITY_REPORTS_PATH,"HTMLCS.json");
    }

    public String testAccessibility(String pageName) {
        String result="";
        JSONObject responseJSON=null;
        switch("")  //pass configuration from accessibility-config.properties
        {
            case "SKIP_FRAMES":
                responseJSON = new AXE.Builder(driver, scriptUrl)
                        .skipFrames()
                        .analyze();
                break;
            case "OPTIONS":
                responseJSON = new AXE.Builder(driver, scriptUrl)
                        .options("{ reporter: 'v2' }")
                        .analyze();
                break;
            case "INCLUDES_EXCLUDES":
                responseJSON = new AXE.Builder(driver, scriptUrl)
                        .include("div") //example
                        .exclude("h1")  //example
                        .analyze();
                break;
            case "SPECIFIC_ELEMENT":
                responseJSON = new AXE.Builder(driver, scriptUrl).analyze();
                break;
            default:
                responseJSON = new AXE.Builder(driver, scriptUrl).analyze();
        }

        /*   ObjectMapper mapper = new ObjectMapper();
         *   AxePOJO axe=null;
         *   try {
         *       axe = mapper.readValue(responseJSON.toString(), AxePOJO.class);
         *   } catch (IOException e) {
         *      e.printStackTrace();
         *   }
         */

        //JSONArray violations = responseJSON.getJSONArray("violations");
        DateTimeHelper dateTimeHelper = new DateTimeHelper();
        String timeStamp = dateTimeHelper.getTimeStamp();
        if(pageName!=null && pageName.length()>0) {
            responseJSON.put("PageName", pageName);
        }
        org.json.simple.JSONObject pageResponse = JSONHelper.JSONRead(ACCESSIBILITY_REPORTS_PATH, "Axe.json");
        pageResponse.put(timeStamp, responseJSON);
        String jsonObject = pageResponse.toString();
        JSONHelper.JSONWrite(jsonObject, ACCESSIBILITY_REPORTS_PATH, "Axe.json");

        if(!ACCESSIBILITY_REPORT_TYPE.equalsIgnoreCase("JSON")){
            JFlat flatMe = new JFlat(jsonObject);

            //directly write the JSON document to CSV but with delimiter
            try {
                flatMe.json2Sheet().write2csv(ACCESSIBILITY_REPORTS_PATH+"Axe.csv",'|');
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        //AXE.writeResults(GlobalProperties.getTARGETPATH()+"cucumber-html-reports\\testAccessibility", responseJSON);
        //result=AXE.report(violations);
        //JSONHelper.JSONWrite(result, GlobalProperties.getTARGETPATH()+"cucumber-html-reports\\","violations.json");

        return result;
    }
}
