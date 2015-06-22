package com.siva.excercise.testscript.ju.test;

import java.io.IOException;

import java.util.LinkedHashMap;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.siva.excercise.exception.InvalidBrowserTypeException;
import com.siva.excercise.pageobject.CheckOutConfirmationPage;
import com.siva.excercise.pageobject.CheckOutPage;
import com.siva.excercise.pageobject.FancyNotificationDialog;
import com.siva.excercise.pageobject.HomeScreen;
import com.siva.excercise.pageobject.OrderConfirmationPage;
import com.siva.excercise.pageobject.ProductPage;
import com.siva.excercise.pageobject.SearchResult;
import com.siva.excercise.pageobject.TransactionResultPage;
import com.siva.excercise.testscript.VerifyAndSubmitOrder;
import com.siva.excercise.wrapper.WrappedWebDriver;
import com.siva.excercise.util.Utilities;
import com.siva.excercise.variables.GlobalValues;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlaceOrder {
	static Logger logger = Logger.getLogger(com.siva.excercise.testscript.ju.test.PlaceOrder.class);
	static WrappedWebDriver wrappedDriver = null;
	
	private static boolean validToContinue = true;
	
	@BeforeClass
	public static void initializeEnvironment() {
		logger.info("Before Class");
		GlobalValues.iniObj = new com.siva.excercise.util.INI("Config.ini");
		try {
			wrappedDriver = new WrappedWebDriver();
			wrappedDriver.manage().window().maximize();
			logger.info("WebDriver initiated successfully");
		} catch (IOException e) {
			validToContinue  = false;
			logger.fatal(e.getMessage() + e.getStackTrace());
			logger.info("validToContinue = FALSE. No more steps will be executed");	
		} catch (InvalidBrowserTypeException e) {
			validToContinue = false;
			logger.fatal(e.getMessage() + e.getStackTrace());	
			logger.info("validToContinue = FALSE. No more steps will be executed");	
		}
	}
	
	@Before
	public void beforeEachTest() {
		if(validToContinue) {
			wrappedDriver.get("http://store.demoqa.com");		
		}
	}
	
	@Test
	public void doTest_01_searchProduct() {	
		if(validToContinue) {
			logger.info("Search Product");
			VerifyAndSubmitOrder verifyAndSubmitOrder = null;
			verifyAndSubmitOrder = new VerifyAndSubmitOrder(wrappedDriver);
//			verifyAndSubmitOrder.searchOrderAndReview("iPhone 4s 16GB");
			verifyAndSubmitOrder.searchOrderAndReview("4s");
			
			SearchResult searchResultPage = new SearchResult(wrappedDriver);
			validToContinue = searchResultPage.clickOnProduct("Apple iPhone 4S 16GB SIM-Free – Black");
			if(validToContinue) {
				ProductPage productPage = new ProductPage(wrappedDriver);
				String productPrice = productPage.getProductPrice();
				if(!productPrice.trim().equalsIgnoreCase("")) {
					validToContinue = productPage.clickAddToCart();
					if(validToContinue) {
						FancyNotificationDialog fancyDialog = new FancyNotificationDialog(wrappedDriver);
						validToContinue = fancyDialog.goToCheckOutPage("Apple iPhone 4S 16GB SIM-Free – Black");
						
						if(validToContinue) {
							CheckOutPage checkOutPage = new CheckOutPage(wrappedDriver);
							if(checkOutPage.validateCheckOutDetails()) {
								if(checkOutPage.continueToPlaceOrder()) {
									CheckOutConfirmationPage checkOutConfirmationPage = new CheckOutConfirmationPage(wrappedDriver);
									if(checkOutConfirmationPage.enterUserName("abcdefg"))
										if(checkOutConfirmationPage.enterUserPassword("cP6i8qfxMFx8"))
											if(checkOutConfirmationPage.clickLoginButton()) {
												OrderConfirmationPage orderConfirmPage = new OrderConfirmationPage(wrappedDriver);
												if(orderConfirmPage.waitForFormToLoad())
													if(orderConfirmPage.waitForBillingFormToLoad()) {
														validToContinue = orderConfirmPage.reviewOrderSummary();
														logger.info("Screenshot Captured " + Utilities.takeScreenShot(wrappedDriver));
														if(validToContinue) {
															orderConfirmPage.submitOrder();
															logger.info("Final Transaction Summary Page: " + Utilities.takeScreenShot(wrappedDriver));
															
															TransactionResultPage transactionResultPage = new TransactionResultPage(wrappedDriver);
															transactionResultPage.getTableHeaders();
															LinkedHashMap<String, String> expectedTransLog = new LinkedHashMap<String, String>();
															expectedTransLog.put("Name", "Apple iPhone 4S 16GB SIM-Free – Black");
															expectedTransLog.put("Price", "$270.00");
															expectedTransLog.put("Quantity", "1");
															expectedTransLog.put("Item Total", "$270.00");
															validToContinue = transactionResultPage.verifyTransactionSummary(expectedTransLog);
															if(validToContinue) {
																logger.info("Verification Summary: " + Utilities.returnHashMapAsString(expectedTransLog));
															}else {
																logger.error("Verification Summary: " + Utilities.returnHashMapAsString(expectedTransLog));
															}
															
														}
													}
											}
								}
							}
							
							
						}
					}
				}else {
					logger.error("No Price set for the product");
				}
			}			
		}
	}
	
//	@Test
//	public void doTest_02_verifySearchResult() {	
//		if(validToContinue) {
//			logger.info("Verify Search Result");
//		}
//	}
	
	@After
	public void afterEachTest() {		
		logger.info("After");
		HomeScreen homeScreen = new HomeScreen(wrappedDriver);
		homeScreen.logoutFromApp();
	}
	
	@AfterClass
	public static void cleanEnvironment() {
		logger.info("After Class");
		wrappedDriver.close();
		wrappedDriver.quit();
	}

}
