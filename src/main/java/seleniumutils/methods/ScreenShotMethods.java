package seleniumutils.methods;

import env.DriverUtil;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ScreenShotMethods implements BaseTest {
	private String SCREENSHOTPATH="";
	protected WebDriver driver = DriverUtil.getDefaultDriver();

	/** Method to take screen shot and save in ./Screenshots folder*/

	public String takeScreenShot() throws IOException
	{

		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar cal = Calendar.getInstance();
		System.out.println(dateFormat.format(cal.getTime()));
		
		String scrFilepath = scrFile.getAbsolutePath();
		System.out.println("scrFilepath: " +scrFilepath);
		
		File currentDirFile = new File("Screenshots");
		String path = currentDirFile.getAbsolutePath();
		System.out.println("path: " +path+"+++");
		SCREENSHOTPATH=path+"\\screenshot"+dateFormat.format(cal.getTime())+".png";
		System.out.println("****\n"+SCREENSHOTPATH);
		
		FileUtils.copyFile(scrFile, new File(SCREENSHOTPATH));
		return SCREENSHOTPATH;
		/*DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		System.out.println(dateFormat.format(cal.getTime()));*/
	}

	public static String captureScreenShot(OutputType type) throws IOException
	{
		if(type==OutputType.BYTES){
			byte[] scrFile = ((TakesScreenshot)DriverUtil.getDefaultDriver()).getScreenshotAs(OutputType.BYTES);
			String s = new String(scrFile);
			return s;
		}
		File scrFile = ((TakesScreenshot)DriverUtil.getDefaultDriver()).getScreenshotAs(OutputType.FILE);

		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar cal = Calendar.getInstance();
		//System.out.println(dateFormat.format(cal.getTime()));

		String scrFilepath = scrFile.getAbsolutePath();
		//System.out.println("scrFilepath: " +scrFilepath);

		File currentDirFile = new File("Screenshots");
		String path = currentDirFile.getAbsolutePath();
		//System.out.println("path: " +path+"+++");
		 String SCREENSHOTPATH=path+"\\screenshot"+dateFormat.format(cal.getTime())+".png";
		//System.out.println("****\n"+SCREENSHOTPATH);

		FileUtils.copyFile(scrFile, new File(SCREENSHOTPATH));
		return SCREENSHOTPATH;
	}

}
