package com.siva.excercise.pageobject;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.siva.excercise.automation.model.PageObject;
import com.siva.excercise.variables.ObjectNotation;
import com.siva.excercise.wrapper.WrappedWebDriver;

public class ProductPage extends PageObject {

	Logger logger = Logger.getLogger(com.siva.excercise.pageobject.ProductPage.class);
	String pageTitle = " | ONLINE STORE";
	
	public ProductPage(WrappedWebDriver driver) {
		super(driver);
		element(ObjectNotation.BUTTON_PREFIX + "AddToCart", By.xpath("//input[@type='submit' and @name='Buy' and @value='Add To Cart']"));
		element(ObjectNotation.SPAN_PREFIX + "ProductPrice", By.xpath("//span[contains(@class,'currentprice') and contains(@class,'pricedisplay') and contains(@class,'product_price_')]"));		
	}
	
	public boolean clickAddToCart() {
		boolean methodStatus = true;
		List<WebElement> buttonList = element(ObjectNotation.BUTTON_PREFIX + "AddToCart");
		if(buttonList.size() > 1) {
			logger.error("Found more than 1 for \"Add To Cart\" button");
			methodStatus = false;
		}else if(buttonList.size() == 0) {
			logger.error("\"Add To Cart\" button - Not Found");
			methodStatus = false;
		}else {
			buttonList.get(0).click();
			logger.info("Clicked \"Add To Cart\" button");
		}
		return methodStatus;
	}
	
	public boolean validateOrderDetails() {
		boolean methodStatus = true;
		
		return methodStatus;
	}
	
	public String getProductPrice() {
		String productPrice = "";
		List<WebElement> priceSpan = element(ObjectNotation.SPAN_PREFIX + "ProductPrice");
		if(priceSpan.size() == 0) {
			logger.error("No Price Found for the product");
		}else if(priceSpan.size() > 1) {
			logger.error("Found more than 1 object");
		}else {
			productPrice = priceSpan.get(0).getText();
			logger.info("Product Price: " + productPrice);
		}
		return productPrice;
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
