package seleniumutils.methods;

import java.util.Map;

public class GlobalProperties {
    //TODO - replace static global properties with instantiated properties available across test run through Test Context Manager
    public static Map<String,String> pageIdentifier;
    public static String FILE_SEPARATOR= System.getProperty("file.separator");
    public static Map<String,String> configProperties;
    public static final String CONFIGPATH= "src/main/resources/cucumber.properties";
    public static final String EXTENT_CONFIG_PATH= "src/main/resources/extent-config.xml";
    public static final String SOURCEPATH=System.getProperty("user.dir")+FILE_SEPARATOR+"src"+FILE_SEPARATOR;
    public static final String TESTRESOURCESPATH=System.getProperty("user.dir")+FILE_SEPARATOR+"src"+FILE_SEPARATOR+"test"+FILE_SEPARATOR+"resources";

    public static String APPLICATION=getConfigProperties().get("application").toLowerCase();

    public static String getApplicationDataPath() {
        return APPLICATION_DATA_PATH;
    }

    public static String APPLICATION_DATA_PATH=TESTRESOURCESPATH+FILE_SEPARATOR+APPLICATION+FILE_SEPARATOR+"data"+FILE_SEPARATOR;
    public static final String ACCESSIBILITY_REPORTS_PATH = APPLICATION_DATA_PATH + "accessibility"+FILE_SEPARATOR;
    public static String APPLICATION_PAGE_OBJECTS_PATH = APPLICATION_DATA_PATH +"PageObjects.xlsx";
    public static String PAGEIDPATH= APPLICATION_DATA_PATH+FILE_SEPARATOR+"pageidentifiers.properties";
    public static String HISTORICAL_PROPERTIES_PATH = APPLICATION_DATA_PATH + FILE_SEPARATOR+"HistoricalProperties.xlsx";
    public static final String MAINRESOURCESPATH=System.getProperty("user.dir")+FILE_SEPARATOR+"src"+FILE_SEPARATOR+"main"+FILE_SEPARATOR+"resources";
    public static final String TARGETPATH=System.getProperty("user.dir")+FILE_SEPARATOR+"target"+FILE_SEPARATOR;
    public static final String EXTENTPATH=System.getProperty("user.dir")+FILE_SEPARATOR+"target"+FILE_SEPARATOR+"ExtentReport";
    public static final String REPORT_TYPE=getConfigProperties().get("report_type");
    public static String BROWSER= getConfigProperties().get("browser");
    public static String HEADLESS= getConfigProperties().get("headless");
    public static String TAKES_SCREENSHOT= getConfigProperties().get("takes_screenshot");
    public static String CHROME_BROWSERVERSION= getConfigProperties().get("chrome_browserversion");
    public static Boolean CHROMEUSEAUTOMATIONEXTENSION= getConfigProperties().get("chromeuseAutomationExtension").equals("true");
    public static String PLATFORM = getConfigProperties().get("platform");
    public static String PLATFORM_VERSION = getConfigProperties().get("platform_version");
    public static String BROWSERSTACK_DEBUG= getConfigProperties().get("chrome_browserstack_debug");
    public static String CHROME_BUILD= getConfigProperties().get("chrome_build");
    public static Boolean ACCESSIBILITY= getConfigProperties().get("accessibility").equals("true");
    public static String INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS= getConfigProperties().get("introduce_flakiness_by_ignoring_security_domains");
    public static Boolean IGNOREPROTECTEDMODESETTINGS = getConfigProperties().get("ignoreProtectedModeSettings").equals("true");
    public static Boolean IGNOREZOOMSETTING = getConfigProperties().get("ignoreZoomSetting").equals("true");
    public static String INITIALBROWSERURL = getConfigProperties().get("initialBrowserUrl");
    public static Boolean ENABLEPERSISTENTHOVER = getConfigProperties().get("enablePersistentHover").equals("true");
    public static Boolean ENABLEELEMENTCACHECLEANUP = getConfigProperties().get("enableElementCacheCleanup").equals("true");
    public static Boolean REQUIREWINDOWFOCUS = getConfigProperties().get("requireWindowFocus").equals("true");
    public static int BROWSERATTACHTIMEOUT = Integer.parseInt(getConfigProperties().get("browserAttachTimeout"));
    public static Boolean IE_FORCECREATEPROCESSAPI = getConfigProperties().get("ie_forceCreateProcessApi").equals("true");
    public static String IE_BROWSERCOMMANDLINESWITCHES = getConfigProperties().get("ie_browserCommandLineSwitches");
    public static Boolean IE_USEPERPROCESSPROXY = getConfigProperties().get("ie_usePerProcessProxy").equals("true");
    public static Boolean IE_ENSURECLEANSESSION = getConfigProperties().get("ie_ensureCleanSession").equals("true");
    public static String LOGFILE = getConfigProperties().get("logFile");
    public static String LOGLEVEL = getConfigProperties().get("logLevel");
    public static String HOST = getConfigProperties().get("host");
    public static String EXTRACTPATH = getConfigProperties().get("extractPath");
    public static Boolean SILENT = getConfigProperties().get("silent").equals("true");
    public static Boolean IE_SETPROXYBYSERVER = getConfigProperties().get("ie_setProxyByServer").equals("true");
    public static String PHANTOMJS_ELEMENT_SCROLL_BEHAVIOR = getConfigProperties().get("phantomjs_element_scroll_behavior");
    public static String PHANTOMJS_TAKES_SCREENSHOT = getConfigProperties().get("phantomjs_takes_screenshot");
    public static String PHANTOMJS_ENABLE_PROFILING_CAPABILITY = getConfigProperties().get("phantomjs_enable_profiling_capability");
    public static String PHANTOMJS_HAS_NATIVE_EVENTS = getConfigProperties().get("phantomjs_has_native_events");
    public static String PHANTOMJS_PAGE_SETTINGS_USERAGENT = getConfigProperties().get("phantomjs_page_settings_useragent");
    public static String PHANTOMJS_WEB_SECURITY = getConfigProperties().get("phantomjs_web-security");
    public static String PHANTOMJS_IGNORE_SSL_ERRORS = getConfigProperties().get("phantomjs_ignore-SSL-ERRORS");
    public static String PHANTOMJS_LOGLEVEL = getConfigProperties().get("phantomjs_loglevel");
    public static String ACCESSIBILITY_REPORT_TYPE = getConfigProperties().get("accessibility_report_type");
    public static String UPDATE_HISTORICAL_PROPERTIES = getConfigProperties().get("Update_Historical_Properties");
    public static Boolean DEPLOY_SELF_HEALING = getConfigProperties().get("Deploy_Self-Healing").equalsIgnoreCase("true");
    public static Boolean VIDEORESULTS= getConfigProperties().get("record_test_video").equals("true");
    public static int IMPLICIT_WAIT_DURATION_SEC=Integer.parseInt(getConfigProperties().get("implicit_wait_duration_sec"));
    public static int PAGE_LOAD_WAIT_DURATION_SEC = Integer.parseInt(getConfigProperties().get("page_load_wait_duration_sec"));
    public static int DEFAULT_WAIT = Integer.parseInt(getConfigProperties().get("default_wait_duration_sec"));

    private static String currentpage;

    //Auto-instantiation of global properties
    public GlobalProperties() {
        getConfigProperties();
        //getPageIdentifier();
    }


    public static String getSOURCEPATH() {
        return SOURCEPATH;
    }
    public static String getTARGETPATH() {
        return TARGETPATH;
    }
    //Alternate method to get config properties
    public static Map<String, String> getConfigProperties() {
        if (configProperties==null||configProperties.size()==0) {
            PropertyReader propertyReader = new PropertyReader(CONFIGPATH);
            configProperties = (Map) propertyReader.getProperties();
        }
        return configProperties;
    }

    public static void setConfigProperties(Map<String, String> configProperties) {
        GlobalProperties.configProperties = configProperties;
    }

    //Alternate method to get pageidentifiers properties
//    public static Map<String, String> getPageIdentifier() {
//        if (pageIdentifier==null||pageIdentifier.size()==0) {
//            PropertyReader propertyReader = new PropertyReader(PAGEIDPATH);
//            pageIdentifier = (Map) propertyReader.getProperties();
//        }
//        return pageIdentifier;
//    }

//    public static void setPageIdentifier(Map<String, String> pageIdentifier) {
//        GlobalProperties.pageIdentifier = pageIdentifier;
//    }
}
