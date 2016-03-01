package utility;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class Library {
	public static WebDriver wd = null;
		
	public static WebDriver launchBrowser(String browserName) {
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
			System.out.println("Invalid Browser: " + browserName);
		
		wd.manage().window().maximize();
		return wd;
	}
	
	public static boolean isElementPresent(String xPathExpr) {
		try {
			wd.findElement(By.xpath(xPathExpr));
		}catch(Exception e) {
			System.out.println("Element does not Exist: " + xPathExpr);
			return false;
		}		
		return true;
	}
	
	public static String getWindowHwndID() {
		Set<String> windows = wd.getWindowHandles();		
		Iterator<String> itr = windows.iterator();
		String windowID = null;
		
		while(itr.hasNext())
			windowID = itr.next();		
		
		return windowID;
	}
}
