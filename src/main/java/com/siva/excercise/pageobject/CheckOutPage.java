package com.siva.excercise.pageobject;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.siva.excercise.automation.model.PageObject;
import com.siva.excercise.variables.ObjectNotation;
import com.siva.excercise.wrapper.WrappedWebDriver;

public class CheckOutPage extends PageObject {
	Logger logger = Logger.getLogger(com.siva.excercise.pageobject.CheckOutPage.class);
	String pageTitle = "Checkout";
	public CheckOutPage(WrappedWebDriver driver) {
		super(driver);
		element(ObjectNotation.DIV_PREFIX + "CheckOutContainer", By.id("checkout_page_container"));
		element(ObjectNotation.DIV_PREFIX + "EntryContent", By.xpath("//div[@class='entry-content']"));		
	}
	
	private boolean waitForPageToLoad() {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		try {			
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='checkout_page_container']/div[@class='slide1' and contains(@style, 'display: block')]")));
		}catch(Exception e) {
			logger.info("No Items to Checkout");
			return false;
		}
		return true;
	}
	
	private boolean validateIfCheckOutContainerExists(List<WebElement> checkOutContainer) {
		boolean methodStatus = true;
		if(checkOutContainer.size() == 0) {
			logger.error("Invalid CheckOut Page");
			methodStatus = false;
		}else if(checkOutContainer.size() > 1) {
			logger.error("Found multiple instances of the CheckOutContainer");
			methodStatus = false;
		}
		boolean pageLoaded = true;
		if(!waitForPageToLoad()) {
			try {
				Thread.sleep(3000);
				pageLoaded = waitForPageToLoad();
			}catch(InterruptedException ie) {
				logger.fatal("Exception occured while Thread.sleep");
				pageLoaded = false;
			}
		}
		methodStatus = methodStatus && pageLoaded;
		return methodStatus;
	}
	
	public boolean validateCheckOutDetails() {
		boolean methodStatus = true;
		
		List<WebElement> checkOutContainer = element(ObjectNotation.DIV_PREFIX + "CheckOutContainer");
		double totalCheckOutAmount;
		totalCheckOutAmount = 0.00;
		if(validateIfCheckOutContainerExists(checkOutContainer)) {	
			List<WebElement> checkOutTRs = checkOutContainer.get(0).findElements(By.xpath("./descendant::table[@class='checkout_cart']/descendant::tr[@class!='header']"));
			for(WebElement checkOutTR : checkOutTRs) {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				WebElement productName = checkOutTR.findElement(By.xpath("./descendant::td[contains(@class,'wpsc_product_name')]/a"));				
				WebElement productQuantity = checkOutTR.findElement(By.xpath("./descendant::td[contains(@class,'wpsc_product_quantity')]/descendant::input[@type='text' and @name='quantity']"));
				WebElement productPrice = checkOutTR.findElement(By.xpath("./descendant::td[contains(@class,'wpsc_product_quantity')]/following-sibling::td[1]/span"));
				WebElement productTotalPrice = checkOutTR.findElement(By.xpath("./descendant::td[contains(@class,'wpsc_product_price')]/span/span"));
				
				String logMessage = "Product Name: " + productName.getText() + " | " + "Product Quantity: " + productQuantity.getAttribute("value") + " | " + "Product Price: " + productPrice.getText() + " | " + "Product Total Price: " + productTotalPrice.getText();
				double productPriceValue = Double.parseDouble(productPrice.getText().replaceAll("\\$", "").replaceAll(",", ""));
				int productQuantityValue = Integer.parseInt(productQuantity.getAttribute("value"));
				double productTotalPriceValue = Double.parseDouble(productTotalPrice.getText().replaceAll("\\$", "").replaceAll(",", ""));
				
				totalCheckOutAmount += (productPriceValue * productQuantityValue);
				
				if((productPriceValue * productQuantityValue) == productTotalPriceValue) {
					logger.info("Valid Result: " + logMessage);
				}else {
					logger.error("Invalid Result: " + logMessage);
				}
				
			}
			String logMessage = "Expected Total: " + totalCheckOutAmount + " | Actual Total: " + getYourTotal();
			if(totalCheckOutAmount == Double.parseDouble(getYourTotal().replaceAll("\\$", "").replaceAll(",", ""))) {
				logger.info(logMessage);	
			}else {
				logger.error(logMessage);
			}
		}else {
			methodStatus = false;
		}
		return methodStatus;
	}
	
	public boolean continueToPlaceOrder() {
		boolean methodStatus = true;
		List<WebElement> checkOutContainer = element(ObjectNotation.DIV_PREFIX + "CheckOutContainer");
		if(validateIfCheckOutContainerExists(checkOutContainer)) {	
			List<WebElement> continueToPlaceOrderButtons = checkOutContainer.get(0).findElements(By.xpath("./descendant::table[@class='checkout_cart']/following-sibling::a[@class='step2']"));
			if(continueToPlaceOrderButtons.size() == 0) {
				logger.error("Continue button not found");
			}else if(continueToPlaceOrderButtons.size() > 0) {
				continueToPlaceOrderButtons.get(0).click();
				logger.info("Clicked Continue button");
			}
		}
		return methodStatus;
	}
	
	public boolean removeAllProducts() {
		boolean methodStatus = true;
		List<WebElement> checkOutContainer = element(ObjectNotation.DIV_PREFIX + "CheckOutContainer");
		if(validateIfCheckOutContainerExists(checkOutContainer)) {	
			List<WebElement> checkOutTRs = checkOutContainer.get(0).findElements(By.xpath("./descendant::table[@class='checkout_cart']/descendant::tr[@class!='header']"));
			int totalRows = checkOutTRs.size();
			for(int rowInd=totalRows-1; rowInd>=0; rowInd--) {
				WebElement checkOutTR = checkOutTRs.get(rowInd);
				List<WebElement> productRemoveButtons = checkOutTR.findElements(By.xpath("./descendant::td[contains(@class,'wpsc_product_remove')]/descendant::input[@type='submit' and @value='Remove']"));
				if(productRemoveButtons.size() > 0) {
					productRemoveButtons.get(0).click();
					validatePageTitle();
				}
			}		
			
		}
		return methodStatus;
	}
	
	public int getTotalItemsToCheckOut() {
		int totalItems = 0;
		List<WebElement> checkOutContainer = element(ObjectNotation.DIV_PREFIX + "CheckOutContainer");
		if(validateIfCheckOutContainerExists(checkOutContainer)) {	
			List<WebElement> checkOutTRs = checkOutContainer.get(0).findElements(By.xpath("./descendant::table[@class='checkout_cart']/descendant::tr[@class!='header']"));
			totalItems = checkOutTRs.size();			
		}
		return totalItems;
	}
	
	public String getYourTotal() {
		String returnTotal = "";
		List<WebElement> checkOutContainer = element(ObjectNotation.DIV_PREFIX + "CheckOutContainer");
		if(validateIfCheckOutContainerExists(checkOutContainer)) {		
			List<WebElement> yourPrice = checkOutContainer.get(0).findElements(By.xpath("./descendant::span[@class='yourtotal']/span[@class='pricedisplay']"));
			if(yourPrice.size() == 0) {
				logger.error("Price Not Found for the product in CheckOut Page");
			}else if(yourPrice.size() > 1) {
				logger.error("Found multiple instances of \"YourTotal\"");
			}else {
				returnTotal = yourPrice.get(0).getText();
			}
		}
		return returnTotal;
	}
	
	public String getMessage() {
		String returnMessage = "";
		List<WebElement> msgObjs = element(ObjectNotation.DIV_PREFIX + "EntryContent");
		if(msgObjs.size() > 0) {
			returnMessage = msgObjs.get(0).getText();
		}else {
			logger.error("Message not found");
		}
		return returnMessage;
	}

	public boolean validatePageTitle() {
		boolean returnStatus = true;
		waitForPageToLoad();
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
