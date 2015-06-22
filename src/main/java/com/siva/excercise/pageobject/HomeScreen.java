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
	String pageTitle = "ONLINE STORE | Toolsqa Dummy Test site";
	
	public HomeScreen(WrappedWebDriver driver) {
		super(driver);	
		element(ObjectNotation.LINK_PREFIX + "Home", By.linkText("Home"));
		element(ObjectNotation.TEXT_PREFIX + "Search", By.xpath("//input[@type='text' and @name='s' and @value='Search Products']"));
		element(ObjectNotation.LINK_PREFIX + "ManageAccount", By.xpath("//a[@title='My Account' and contains(@href,'products-page') and contains(@href,'your-account')]"));
		element(ObjectNotation.LINK_PREFIX + "YourDetails", By.linkText("Your Details"));
		element(ObjectNotation.LINK_PREFIX + "Logout", By.linkText("Log out"));
	}
	
	public boolean clickHomeMenuLink() {
		boolean methodStatus = true;
		List<WebElement> objList = element(ObjectNotation.LINK_PREFIX + "Home");
		if(objList != null) {
			if(objList.size() == 1) {
				objList.get(0).click();
				logger.info("Clicked on \"Home\" Menu Link");
				methodStatus = validatePageTitle();
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
				try {
					WebDriverWait wait = new WebDriverWait(driver.getDriver(), 10);
					wait.until(ExpectedConditions.elementToBeClickable(elements.get((ObjectNotation.TEXT_PREFIX + "Search").toLowerCase())));
				}catch(Exception e) {
					logger.fatal(e.getMessage());
				}
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
//		driver.get("http://store.demoqa.com/products-page/your-account/");
		element(ObjectNotation.LINK_PREFIX + "ManageAccount").get(0).click();
		List<WebElement> logoutObjs = element(ObjectNotation.LINK_PREFIX + "Logout");
		if(logoutObjs.size() > 0) {
			element(ObjectNotation.LINK_PREFIX + "Logout").get(0).click();
			logger.info("Logged off successfully");
		}else{
			logger.info("No Logout link found");
		}
	}
	
	public boolean validatePageTitle() {
		boolean returnStatus = true;
		String currentPageTitle = driver.getTitle();
		returnStatus = currentPageTitle.equalsIgnoreCase(pageTitle);
		if(returnStatus) {
			logger.info("Valid Page with PageTitle: " + pageTitle);
		}else {
			logger.error("Expected PageTitle: " + pageTitle + " | Actual PageTitle: " + currentPageTitle);
			returnStatus = false;
		}
		return returnStatus;
	}
	
	public boolean gotoManageAccountPage() {
		boolean returnStatus = true;
		try {
			Thread.sleep(2000);
		}catch(Exception e) {
			logger.fatal(e.getMessage());
		}
		List<WebElement> linkObjs = element(ObjectNotation.LINK_PREFIX + "ManageAccount");
		if(linkObjs.size() == 0) {
			logger.error("ManageAccount link not found");
		}else {
			linkObjs.get(0).click();
		}
		return returnStatus;
	}
	
	public boolean gotoYourDetails() {
		boolean returnStatus = true;
		List<WebElement> linkObjs = element(ObjectNotation.LINK_PREFIX + "YourDetails");
		if(linkObjs.size() == 0) {
			logger.error("ManageAccount link not found");
		}else {
			linkObjs.get(0).click();
		}
		return returnStatus;
	}

}
