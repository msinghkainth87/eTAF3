package env;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.ErrorHandler;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import seleniumutils.methods.GlobalProperties;
import seleniumutils.methods.helpers.VideoRecord;

import java.io.File;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import static seleniumutils.methods.GlobalProperties.*;

/**
 * Created by tom on 24/02/17.
 */

public class DriverUtil {
    protected static WebDriver driver;

    private static String CHROMEDRIVERPROPERTY="webdriver.chrome.driver";
    private static String FIREFOXDRIVERPROPERTY="webdriver.gecko.driver";
    private static String IEDRIVERPROPERTY="webdriver.ie.driver";
    private static String DRIVERPATH=System.getProperty("user.dir")+FILE_SEPARATOR+"webdrivers";

    //private static String CHROME_BROWSERVERSION=GlobalProperties.getConfigProperties().get("chrome_browserversion");

    protected static WebDriverWait wait;
    public static WebDriver getDefaultDriver(){
        if (driver != null) {
            return driver;
        }

        //System.setProperty("webdriver.gecko.driver", "./geckodriver");

        driver = chooseDriver();
        driver.manage().timeouts().setScriptTimeout(DEFAULT_WAIT,
                TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT_DURATION_SEC,TimeUnit.MILLISECONDS);
        driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_WAIT_DURATION_SEC,TimeUnit.SECONDS);
        driver.manage().window().maximize();
        return driver;
    }

    /**
     * By default to web driver will be PhantomJS
     *
     * Override it by passing -DWebDriver=Chrome to the command line arguments
     * @return
     */
    private static WebDriver chooseDriver() {
        String preferredDriver = BROWSER;
        boolean headless = HEADLESS.equals("true");
        DesiredCapabilities capabilities = null;

        switch (preferredDriver.toLowerCase()) {
            case "ch":
            case "chrome":
//                    System.setProperty(CHROMEDRIVERPROPERTY, DRIVERPATH + FILE_SEPARATOR+"chromedriver.exe");
                    capabilities = setChromeDriverCapabilities();
                    final ChromeOptions chromeOptions = new ChromeOptions();
                    if (headless) {
                        chromeOptions.addArguments("--headless");
                    }
                    chromeOptions.setExperimentalOption("useAutomationExtension", CHROMEUSEAUTOMATIONEXTENSION);
                    chromeOptions.addArguments("disable-infobars");
                    capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                    System.out.println("********************* before driver created");
                    ChromeDriverService service = new ChromeDriverService.Builder()
                            .usingDriverExecutable(new File(DRIVERPATH + FILE_SEPARATOR+"chromedriver.exe"))
                            .usingAnyFreePort()
                            .build();

                    //ChromeOptions options = new ChromeOptions();
                    chromeOptions.merge(capabilities);
                    ChromeDriver driver = new ChromeDriver(service, chromeOptions);
                    System.out.println("********************* after driver created");
                    ErrorHandler handler = new ErrorHandler();
                    handler.setIncludeServerErrors(false);
                    driver.setErrorHandler(handler);
                    return driver;
            case "phantomjs":
                DesiredCapabilities caps = new DesiredCapabilities();
                caps.setJavascriptEnabled(true);
                caps.setCapability("takesScreenshot", TAKES_SCREENSHOT);
                PhantomJSDriverService servicePJS = new PhantomJSDriverService.Builder()
                        .usingAnyFreePort()
                        .usingPhantomJSExecutable(new File(DRIVERPATH+FILE_SEPARATOR+"phantomjs"+FILE_SEPARATOR+"bin"+FILE_SEPARATOR+"phantomjs.exe"))
                        .build();
                return new PhantomJSDriver(servicePJS, caps);
			/*
				System.setProperty(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, DRIVERPATH+FILE_SEPARATOR+"phantomjs"+FILE_SEPARATOR+"bin"+FILE_SEPARATOR+"phantomjs.exe");
				//capabilities = DesiredCapabilities.phantomjs();
				capabilities.setJavascriptEnabled(true);
				Capabilities caps = new DesiredCapabilities();
				capabilities.setCapability("takesScreenshot", TAKES_SCREENSHOT);
//				capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,DRIVERPATH+FILE_SEPARATOR+"phantomjs"+FILE_SEPARATOR+"bin"+FILE_SEPARATOR+"phantomjs.exe");
				return new PhantomJSDriver(caps);
				*/
            case "ff":
            case "firefox":
                //ProfilesIni allProfiles = new ProfilesIni();
                //FirefoxProfile profile = allProfiles.getProfile("selenium");
                //driver = new FirefoxDriver(profile);
                System.setProperty(FIREFOXDRIVERPROPERTY, DRIVERPATH+FILE_SEPARATOR+"geckodriver.exe");
                capabilities = DesiredCapabilities.firefox();
                capabilities.setJavascriptEnabled(true);
                capabilities.setCapability("takesScreenshot", GlobalProperties.getConfigProperties().get("takes_screenshot"));
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                //capabilities.s
                if (headless) {
                    firefoxOptions.addArguments("-headless", "-safe-mode");
                }
                capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, firefoxOptions);
                return new FirefoxDriver();
            case "ie" :
            case "internetexplorer":
                System.setProperty(IEDRIVERPROPERTY, DRIVERPATH+FILE_SEPARATOR+"IEDriverServer.exe");
                capabilities = DesiredCapabilities.internetExplorer();
                capabilities.setJavascriptEnabled(true);
                capabilities.setCapability("takesScreenshot", TAKES_SCREENSHOT);
                InternetExplorerDriver IEDriver = new InternetExplorerDriver(setIEDriverCapabilities());
                //this is to reset Zoom level to 100% for IE.

                IEDriver.findElement(By.tagName("html")).sendKeys(Keys.chord(Keys.CONTROL, "0"));
                return IEDriver;

            case "safari":
                return new SafariDriver();

            default:
                //return new PhantomJSDriver(capabilities);
                firefoxOptions = new FirefoxOptions();
                //capabilities.s
                if (headless) {
                    firefoxOptions.addArguments("-headless", "-safe-mode");
                }
                capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, firefoxOptions);
                final FirefoxDriver firefox = new FirefoxDriver();
                return firefox;
        }
    }

    public static DesiredCapabilities setChromeDriverCapabilities(){
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setJavascriptEnabled(true);
        if(ACCESSIBILITY) {
            LoggingPreferences logPrefs = new LoggingPreferences();
            logPrefs.enable(LogType.BROWSER, Level.ALL);
            capabilities.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
        }
        capabilities.setCapability("takesScreenshot", TAKES_SCREENSHOT);
        return capabilities;
    }

    public static InternetExplorerOptions setIEDriverCapabilities(){
        InternetExplorerOptions ieOptions = new InternetExplorerOptions();
        ieOptions.setCapability("requireWindowFocus", true);
        ieOptions.setCapability("INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS", INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS);
        ieOptions.setCapability("ignoreProtectedModeSettings", IGNOREPROTECTEDMODESETTINGS);
        ieOptions.setCapability("ignoreZoomSetting", IGNOREZOOMSETTING);
        ieOptions.setCapability("initialBrowserUrl", INITIALBROWSERURL);
        ieOptions.setCapability("enablePersistentHover", ENABLEPERSISTENTHOVER);
        ieOptions.setCapability("enableElementCacheCleanup", ENABLEELEMENTCACHECLEANUP);
        ieOptions.setCapability("requireWindowFocus", REQUIREWINDOWFOCUS);
        ieOptions.setCapability("browserAttachTimeout", BROWSERATTACHTIMEOUT);
//		ieOptions.setCapability("ie.forceCreateProcessApi",IE_FORCECREATEPROCESSAPI);
//		ieOptions.setCapability("ie.browserCommandLineSwitches",IE_BROWSERCOMMANDLINESWITCHES);
//		ieOptions.setCapability("ie.usePerProcessProxy",IE_USEPERPROCESSPROXY);
        ieOptions.setCapability("ie.ensureCleanSession", IE_ENSURECLEANSESSION);
//		ieOptions.setCapability("logFile",LOGFILE);
//		ieOptions.setCapability("logLevel",LOGLEVEL);
//		ieOptions.setCapability("host",HOST);
//		ieOptions.setCapability("extractPath",EXTRACTPATH);
//		ieOptions.setCapability("silent",SILENT);
//		ieOptions.setCapability("ie.setProxyByServer",IE_SETPROXYBYSERVER);
        return ieOptions;
    }

    public static WebElement waitAndGetElementByCssSelector(WebDriver driver, String selector,
                                                            int seconds) {
        By selection = By.cssSelector(selector);
        return (new WebDriverWait(driver, seconds)).until( // ensure element is visible!
                ExpectedConditions.visibilityOfElementLocated(selection));
    }

    public static void waitForAjaxCall() {
        try {

            List<WebElement> ajaxelement = driver.findElements(By.xpath("//body[contains(@class,'x-masked')]"));
            if (ajaxelement.size() > 0) {
                System.out.println("Ajax call started"+ Instant.now());
                WebDriverWait wait1 = new WebDriverWait(driver, 10, 30);

                wait1.until(
                        ExpectedConditions.presenceOfElementLocated(By.xpath("//body[contains(@class,'x-masked')]")));
                // wait1=new WebDriverWait(driver,4,20);
                // wait1.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//body[(@class='x-body
                // x-gecko x-layout-fit x-border-box x-container x-container-default')]")));
                // //GW8
                wait1.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                        "//body[(@class='x-body x-webkit x-chrome x-border-layout-ct x-border-box x-container x-container-default')]"))); // GW9
                System.out.println("Ajax call ended"+Instant.now());
            } else {
            }
        } catch (Exception e) {
            System.out.println("No ajax call"+Instant.now());
            // Log.info("No AJAX CALL ");//e.printStackTrace();
        }
    }

    public static boolean waitForJSandJQueryToLoad() {

        WebDriverWait wait = new WebDriverWait(driver,PAGE_LOAD_WAIT_DURATION_SEC);
        // wait for jQuery to load
        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {

                    return ((Long)((JavascriptExecutor)driver).executeScript("return jQuery.active") == 0);
                }
                catch (Exception e) {
                    // no jQuery present
                    return true;
                }
                finally {
                    //System.out.println("JQuery call ended"+ Instant.now());
                }
            }
        };

        // wait for Javascript to load
        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {

                try {
                    return ((JavascriptExecutor)driver).executeScript("return document.readyState")
                            .toString().equals("complete");
                } catch (Exception e) {
                    return true;
                } finally {
                    //System.out.println("JS call ended"+ Instant.now());
                }
            }
        };

        return wait.until(jQueryLoad) && wait.until(jsLoad);
    }

    public static void closeDriver() {
        if (driver != null) {
            try {
                //driver.close(); // uncomment this and below line
                //driver.quit(); // fails in current geckodriver! TODO: Fixme
            } catch (NoSuchMethodError nsme) { // in case quit fails
            } catch (NoSuchSessionException nsse) { // in case close fails
            } catch (SessionNotCreatedException snce) {} // in case close fails
                driver = null;
        }
    }
}
