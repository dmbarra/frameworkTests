package frameworkTests.utils;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class DriverManager {
	private static WebDriver driver = null;
	private static String browser = null;

	/**
	 * Create the object driver.
	 */
	public static WebDriver startDriver(String browser, String url, int timeout){
		if ( browser.equals("IE") ){
			DesiredCapabilities ieCapabilities = null;
			ieCapabilities = DesiredCapabilities.internetExplorer();
			ieCapabilities.setCapability(CapabilityType.VERSION, "8");
			ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			ieCapabilities.setCapability("nativeEvents", false);
			ieCapabilities.setCapability("enablePersistentHover", true);
			ieCapabilities.setCapability("nativeEvents", true);
			ieCapabilities.setCapability("ignoreProtectedModeSettings", true);
			ieCapabilities.setCapability("elementScrollBehavior", true);
			driver = new InternetExplorerDriver(ieCapabilities);
		}
		else if ( browser.equals("HEAD_LESS") ){
			DesiredCapabilities capabilities = DesiredCapabilities.htmlUnit();
			capabilities.setBrowserName("IE");
			capabilities.setVersion("INTERNET_EXPLORER_8");
			capabilities.setPlatform(Platform.ANY);
			capabilities.setJavascriptEnabled(true); 
			driver = new HtmlUnitDriver(capabilities);	
				
		}
		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
		driver.get(url);
		driver.manage().deleteAllCookies();
		return driver;
	}

	/**
	 * Close driver
	 */
	public static void stopDriver()
	{
		driver.close();
		driver.quit();
	}

	/**
	 * Get Driver
	 */
	public static WebDriver getDriverInstance()
	{
		return driver;
	}

	/**
	 * Getter method for the currect browser being used for testing
	 * @return the browser string being used for testing
	 */
	public static String getBroswer()
	{
		return browser;
	}
}
