package com.siva.excercise.wrapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.siva.excercise.exception.InvalidBrowserTypeException;
import com.siva.excercise.util.INI;

public class WrappedWebDriver implements WebDriver{

	WebDriver driver = null;
	
	public WrappedWebDriver() throws IOException, InvalidBrowserTypeException {
		String webBrowser = INI.getProperty("browser", "browser");
		if(webBrowser.equalsIgnoreCase("ie")) {
			File browserDriver = new File(new File(".").getCanonicalFile() + File.separator + "browserdrivers" + File.separator + "IEDriverServer.exe");
			System.setProperty("webdriver.ie.driver", browserDriver.getAbsolutePath());
			DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer(); 
			ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			driver = new InternetExplorerDriver(ieCapabilities);
		}else if(webBrowser.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		}else if(webBrowser.equalsIgnoreCase("chrome")) {
			File browserDriver = new File(new File(".").getCanonicalFile() + File.separator + "browserdrivers" + File.separator + "chromedriver.exe");
			System.setProperty("webdriver.chrome.driver", browserDriver.getAbsolutePath());
			driver = new ChromeDriver();
		}else {
			throw new InvalidBrowserTypeException("Invalid browser: " + webBrowser + ". Please specify a valid browser (id|firefox|chrome)");
		}
	}
	
	public WebDriver getDriver() {
		return this.driver;
	}
	
	public void close() {
		driver.close();
	}
	
	public boolean isInitialized() {
		try {
		if(driver == null) {
			return false;
		}
		else {
			return true;
		}
		}catch(Exception e) {
			return false;
		}
	}

	public WebElement findElement(By objLocator) {
		return driver.findElement(objLocator);
	}

	public List<WebElement> findElements(By objLocator) {
		return driver.findElements(objLocator);
	}

	public void get(String url) {
		driver.get(url);
	}

	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	public String getPageSource() {
		return driver.getPageSource();
	}

	public String getTitle() {
		return driver.getTitle();
	}

	public String getWindowHandle() {
		return driver.getWindowHandle();
	}

	public Set<String> getWindowHandles() {
		return driver.getWindowHandles();
	}

	public Options manage() {
		return driver.manage();
	}

	public Navigation navigate() {
		return driver.navigate();
	}

	public void quit() {
		driver.quit();
	}

	public TargetLocator switchTo() {
		return driver.switchTo();
	}

}
