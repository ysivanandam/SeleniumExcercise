package com.siva.excercise.testscript.ju.test;

import java.io.IOException;

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
import com.siva.excercise.pageobject.HomeScreen;
import com.siva.excercise.pageobject.ManageAccountPage;
import com.siva.excercise.util.INI;
import com.siva.excercise.variables.GlobalValues;
import com.siva.excercise.wrapper.WrappedWebDriver;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UpdateAccount {
	static Logger logger = Logger.getLogger(com.siva.excercise.testscript.ju.test.UpdateAccount.class);
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
		logger.info("Start of Test");
		if(validToContinue) {
			wrappedDriver.get("http://store.demoqa.com");		
		}
	}
	
	@Test
	public void doTest_01_updateAccount() {	
		if(validToContinue) {
			HomeScreen homeScreen = new HomeScreen(wrappedDriver);
			if(homeScreen.validatePageTitle()) {
				homeScreen.gotoManageAccountPage();
				CheckOutConfirmationPage checkOutConfirmationPage = new CheckOutConfirmationPage(wrappedDriver);
				if(checkOutConfirmationPage.enterUserName("abcdefg")) {
					if(checkOutConfirmationPage.enterUserPassword("cP6i8qfxMFx8")) {
						if(checkOutConfirmationPage.clickLoginButton()) {
							logger.info("Logged in successfully");
							if(homeScreen.gotoManageAccountPage())
								if(homeScreen.gotoYourDetails()) {
									logger.info("Inside Account Details Page");
									ManageAccountPage manageAccount = new ManageAccountPage(wrappedDriver);
									String countryName = manageAccount.getSelectedCountry();
									if(countryName.equalsIgnoreCase("USA")) {
										manageAccount.selectCountry("Armenia");
										INI.storeProperty("UPDATE_ACCOUNT", "selected_country", "Armenia");
										INI.storeIniFile();
									}else {
										manageAccount.selectCountry("USA");
										INI.storeProperty("UPDATE_ACCOUNT", "selected_country", "USA");
										INI.storeIniFile();
									}
									
									String firstName = manageAccount.getFirstName();
									if(firstName.equalsIgnoreCase("FN1")) {
										manageAccount.setFirstName("fn");
										INI.storeProperty("UPDATE_ACCOUNT", "first_name", "fn");
										INI.storeIniFile();
									}else {
										manageAccount.setFirstName("fn1");
										INI.storeProperty("UPDATE_ACCOUNT", "first_name", "fn1");
										INI.storeIniFile();
									}
									
									manageAccount.submitForm();
									
								}
						}
					}
				}
			}
		}
	}
	
	@Test
	public void doTest_02_verifyAccount() {
		if(validToContinue) {
			String expCountryName = INI.getProperty("UPDATE_ACCOUNT", "selected_country");
			String expFirstName = INI.getProperty("UPDATE_ACCOUNT", "first_name");
			HomeScreen homeScreen = new HomeScreen(wrappedDriver);
			if(homeScreen.validatePageTitle()) {
				homeScreen.gotoManageAccountPage();
				CheckOutConfirmationPage checkOutConfirmationPage = new CheckOutConfirmationPage(wrappedDriver);
				if(checkOutConfirmationPage.enterUserName("abcdefg")) {
					if(checkOutConfirmationPage.enterUserPassword("cP6i8qfxMFx8")) {
						if(checkOutConfirmationPage.clickLoginButton()) {
							logger.info("Logged in successfully");
							if(homeScreen.gotoManageAccountPage())
								if(homeScreen.gotoYourDetails()) {
									logger.info("Inside Account Details Page");
									ManageAccountPage manageAccount = new ManageAccountPage(wrappedDriver);
									
									String firstName = manageAccount.getFirstName();
									if(firstName.equalsIgnoreCase(expFirstName)) {
										logger.info("FirstName Updated Successfully");
									}else {
										logger.info("FirstName Not Updated");
									}
									
									String countryName = manageAccount.getSelectedCountry();
									if(expCountryName.equalsIgnoreCase(countryName)) {
										logger.info("Country Updated Successfully");
									}else {
										logger.error("Country Not Updated");
									}
									
								}
						}
					}
				}
			}
		}
	}
	
	@After
	public void afterEachTest() {		
		logger.info("After");
		HomeScreen homeScreen = new HomeScreen(wrappedDriver);
		homeScreen.logoutFromApp();
		logger.info("End of Test");
	}
	
	@AfterClass
	public static void cleanEnvironment() {
		logger.info("After Class");
		wrappedDriver.close();
		wrappedDriver.quit();
	}

}
