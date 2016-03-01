import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import mx4j.log.Log;
import utility.Constants;
import org.apache.log4j.Logger;


public class Keyword_Impl {
	
	public static String currPrjLoc = System.getProperty("user.dir");
	public static WebDriver wd = null;
	public static Properties prop = null;
	public static String ORFilePath = currPrjLoc + "\\xPathRepositories\\xPath.Repositories";
	Logger log = Logger.getLogger("Logger");
	
	public static String launchBrowser(String object, String browserName ) throws IOException {
		
		if (prop == null) {
			prop = new Properties();
			FileInputStream fis = new FileInputStream(ORFilePath);
			prop.load(fis);			
		}
		
		try {
			if (browserName.equalsIgnoreCase("firefox"))
				wd = new FirefoxDriver();
			else if (browserName.equalsIgnoreCase("ie")) {
				System.setProperty("webdriver.ie.driver", Constants.ieDriverPath);
				wd = new InternetExplorerDriver();
			}else if (browserName.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver", Constants.chromeDriverPath);
				wd = new ChromeDriver();
			}else if (browserName.equalsIgnoreCase("htmlunit"))
				wd = new HtmlUnitDriver();		
			else
				return "Fail - Invalid Browser: " + browserName;
			
		}catch(Exception e) {
			return "Fail - Invalid Browser: " + browserName;
		}
		return "Pass";
	}
	
	public static String navigateURL(String object, String url ) {
		try {
			wd.get(url);
			wd.manage().window().maximize();
			wd.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		}catch(Exception e) {
			return "Fail - Invalid URL: " + url;
		}
		return "Pass";
	}
	
	public static String closeBrowser(String object, String browserName) {
		try {
			wd.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			wd.close();
			wd.quit();
		}catch(Exception e) {
			return "Fail - to close the browser: " ;
		}
		return "Pass";
	}
	
	public static String click(String object, String data ) {
		try {
			wd.findElement(By.xpath(prop.getProperty(object))).click();
		}catch(Exception e) {
			return "Fail - Invalid click Action on element: " + object;
		}
		return "Pass";
	}
	
	public static String click_button(String object, String data ) {
		try {
			
				WebElement el1 = wd.findElement(By.cssSelector(prop.getProperty(object)));
				Actions builder = new Actions(wd);
			    //builder.moveToElement( el1 ).perform();
			    builder.sendKeys(Keys.ENTER);
			    builder.doubleClick(el1).build().perform();
			    //builder.sendKeys(Keys.ENTER);
			
		}catch(Exception e) {
			return "Fail - Invalid click Action on element: " + object;
		}
		return "Pass";
	}
	
	public String validate_results(String object, String data ) {
		try {
			wd.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			String val = wd.findElement(By.xpath(prop.getProperty(object))).getAttribute("innerHTML");
			Assert.assertTrue("The results returned is not Same as Expected i.e."+val+"", val.contains(data));
			log.debug("The expected value is "+val+"");
		}catch(Exception e) {
			return "Fail - Invalid Action on element: " + object;
		}
		return "Pass";
	}
	
	public static String selectRadio(String object, String data ) {
		try {
			wd.findElement(By.xpath(prop.getProperty(object))).click();
		}catch(Exception e) {
			return "Fail - Invalid select Readio Action on element: " + object;
		}
		return "Pass";
	}
	
	public static String type(String object, String data ) {
		try {
			wd.findElement(By.xpath(prop.getProperty(object))).sendKeys(data);
		}catch(Exception e) {
			return "Fail - Invalid type Action on element: " + object + "  --  " + data;
		}
		return "Pass";
	}
	
	public static String selectList(String object, String data ) {
		try {
			wd.findElement(By.xpath(prop.getProperty(object))).sendKeys(data);
		}catch(Exception e) {
			return "Fail - Invalid Select List Action on element: " + object + "  --  " + data;
		}
		return "Pass";
	}
	
	public static String check(String object, String data ) {
		try {
			wd.findElement(By.xpath(prop.getProperty(object))).click();
		}catch(Exception e) {
			return "Fail - Invalid Check Action on element: " + object;
		}
		return "Pass";
	}
	
	
	
		
	
}
