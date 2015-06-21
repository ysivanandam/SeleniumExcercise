import java.io.File;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class Sample {

	public static void openBrowser() {
		try {
//			File browserDriver = new File(new File(".").getCanonicalFile() + File.separator + "browserdrivers" + File.separator + "IEDriverServer.exe");
			File browserDriver = new File(new File(".").getCanonicalFile() + File.separator + "browserdrivers" + File.separator + "chromedriver.exe");
		
			System.out.println(browserDriver.getAbsolutePath());
//			System.setProperty("webdriver.ie.driver", browserDriver.getAbsolutePath());
			System.setProperty("webdriver.chrome.driver", browserDriver.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
//		WebDriver driver = new InternetExplorerDriver();
		WebDriver driver = new ChromeDriver();
		driver.get("http://www.google.com");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.close();
		driver.quit();
		System.out.println("End");
	}
	
	public static void main(String[] args) {
		openBrowser();

	}

}
