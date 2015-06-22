package com.siva.excercise.pageobject;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.siva.excercise.automation.model.PageObject;
import com.siva.excercise.wrapper.WrappedWebDriver;

public class SearchResult extends PageObject {

	Logger logger = Logger.getLogger(com.siva.excercise.pageobject.SearchResult.class);
	String pageTitle = "Search Results";
	
	public SearchResult(WrappedWebDriver driver) {
		super(driver);
	}
	
	public boolean clickOnProduct(String productLinkText) {		
		boolean methodStatus = true;
		List<WebElement> productList = driver.findElements(By.linkText(productLinkText));
		if(productList.size() == 0) {
			methodStatus = false;
			logger.error("Invalid Page or No Such Product - " + productLinkText);
		}else if(productList.size() > 1) {			
				logger.error("Found more than 1 for " + productLinkText);
				methodStatus = false;			
		}else {
			productList.get(0).click();
			logger.info("Clicked the product - " + productLinkText);
		}
		return methodStatus;
	}
	
	public boolean validatePageTitle() {
		boolean returnStatus = true;
		String currentPageTitle = driver.getTitle();
		returnStatus = currentPageTitle.contains(pageTitle);
		if(returnStatus) {
			logger.info("Valid Page with PageTitle: " + pageTitle);
		}else {
			logger.error("Expected PageTitle: " + pageTitle + " | Actual PageTitle: " + currentPageTitle);
			returnStatus = false;
		}
		return returnStatus;
	}
	

}
