package com.siva.excercise.pageobject;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.siva.excercise.automation.model.PageObject;
import com.siva.excercise.wrapper.WrappedWebDriver;
import com.siva.excercise.util.Utilities;
import com.siva.excercise.variables.*;

public class HomeScreen extends PageObject {

	Logger logger = Logger.getLogger(com.siva.excercise.pageobject.HomeScreen.class);
	public HomeScreen(WrappedWebDriver driver) {
		super(driver);	
		element(ObjectNotation.LINK_PREFIX + "Home", By.linkText("Home"));
		element(ObjectNotation.TEXT_PREFIX + "Search", By.xpath("//input[@type='text' and @name='s' and @value='Search Products']"));
		element(ObjectNotation.LINK_PREFIX + "ManageAccount", By.xpath("//a[@title='My Account' and @class='account_icon']"));
		element(ObjectNotation.LINK_PREFIX + "Logout", By.linkText("Log out"));
	}
	
	public boolean clickHomeMenuLink() {
		boolean methodStatus = true;
		List<WebElement> objList = element(ObjectNotation.LINK_PREFIX + "Home");
		if(objList != null) {
			if(objList.size() == 1) {
				objList.get(0).click();
				logger.info("Clicked on \"Home\" Menu Link");
			}else {
				methodStatus = false;
				logger.error("There are more than 1 instance of \"Home\" Menu Link");
			}
		}else {
			methodStatus = false;
			logger.error("\"Home\" Menu Link - Not Found");
		}
		return methodStatus;
	}
	
	public boolean searchProduct(String searchString) {
		boolean methodStatus = true;
		
		List<WebElement> objList = element(ObjectNotation.TEXT_PREFIX + "Search");
		if(objList != null) {
			if(objList.size() == 1) {
				Utilities.scrollToObject(driver.getDriver(), objList.get(0));
//				try {
					WebDriverWait wait = new WebDriverWait(driver.getDriver(), 10);
					wait.until(ExpectedConditions.elementToBeClickable(elements.get(ObjectNotation.TEXT_PREFIX + "Search")));
//				}catch(Exception e) {
//					logger.fatal(e.getMessage());
//				}
				objList.get(0).sendKeys(searchString);
				objList.get(0).submit();
				logger.info("Entered \"" + searchString + "\" in \"Search\" TextBox");
			}else {
				methodStatus = false;
				logger.error("There are more than 1 instance of \"Search\" TextBox");
			}
		}else {
			methodStatus = false;
			logger.error("\"Search\" TextBox - Not Found");
		}
		return methodStatus;
		
	}
	
	public void logoutFromApp() {
//		logger.info("1");
//		List<WebElement> manageAccount = element(ObjectNotation.LINK_PREFIX + "ManageAccount");
//		logger.info("2");		
//		Utilities.scrollToObject(driver.getDriver(), manageAccount.get(0));
//		logger.info("3");
//		WebDriverWait wait = new WebDriverWait(driver, 10);
//		try {
//			wait.until(ExpectedConditions.elementToBeClickable(elements.get(ObjectNotation.LINK_PREFIX + "ManageAccount")));
//		}catch(Exception e) {
//			logger.fatal(e.getMessage());
//			e.printStackTrace();
//		}	
//		
//		logger.info("4");
//		manageAccount.get(0).click();
//		logger.info("5");
		driver.get("http://store.demoqa.com/products-page/your-account/");
//		try {
//			WebDriverWait wait = new WebDriverWait(driver, 10);
//			wait.until(ExpectedConditions.elementToBeClickable(elements.get(ObjectNotation.LINK_PREFIX + "Logout")));
//		}catch(Exception e) {
//			logger.fatal(e.getMessage());
//		}
		List<WebElement> logoutObjs = element(ObjectNotation.LINK_PREFIX + "Logout");
		if(logoutObjs.size() > 0) {
			element(ObjectNotation.LINK_PREFIX + "Logout").get(0).click();
			logger.info("Logged off successfully");
		}else{
			logger.info("No Logout link found");
		}
	}

}
