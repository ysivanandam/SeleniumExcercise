package com.siva.excercise.testscript;

import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.siva.excercise.exception.InvalidBrowserTypeException;
import com.siva.excercise.wrapper.WrappedWebDriver;

public class PlaceOrder {

	WrappedWebDriver wrappedDriver = null;
	boolean validToContinue = true;
	Logger logger = Logger.getLogger(com.siva.excercise.testscript.PlaceOrder.class);
	com.siva.excercise.util.INI iniObj = null;
	
	public void initializeWebDriver() {
		iniObj = new com.siva.excercise.util.INI("Config.ini");
		try {
			wrappedDriver = new WrappedWebDriver();
			logger.info("Created WebDriver Instance");
		} catch (IOException e) {
			validToContinue = false;
			logger.fatal(e.getMessage() + e.getStackTrace());
			logger.info("validToContinue = FALSE. No more steps will be executed");	
			e.printStackTrace();
		} catch (InvalidBrowserTypeException e) {
			validToContinue = false;
			logger.fatal(e.getMessage() + e.getStackTrace());	
			logger.info("validToContinue = FALSE. No more steps will be executed");	
		}
	}
	
	public void loadURL(String urlToLoad) {
		if(validToContinue) 			
			wrappedDriver.get(urlToLoad);
	}
	
	public void exitDriver() {
		if (validToContinue) {
			wrappedDriver.close();
			logger.info("Closed WebDriver Instance");
			wrappedDriver.quit();
			logger.info("Quit WebDriver Instance");
		}
	}
	
	public static void main(String[] args) {
		BasicConfigurator.configure();
		PlaceOrder obj = new PlaceOrder();
		obj.initializeWebDriver();
		obj.loadURL("http://www.google.com");
		obj.exitDriver();
	}

}
