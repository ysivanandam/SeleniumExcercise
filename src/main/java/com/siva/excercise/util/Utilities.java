package com.siva.excercise.util;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.siva.excercise.wrapper.WrappedWebDriver;

public class Utilities {
	static Logger logger = Logger.getLogger(com.siva.excercise.util.Utilities.class);
	
	public static String takeScreenShot(WrappedWebDriver driver) {
		String screenShotLocation = "";
		File scrFile = ((TakesScreenshot)driver.getDriver()).getScreenshotAs(OutputType.FILE);
		DateFormat dateFormat = new SimpleDateFormat("YYYYMMddHHmmssSSS");
		Date date = new Date();
		String currentDateFormat = dateFormat.format(date);
		try {
			if(!new File("screenshots").exists()) {
				new File("screenshots").mkdirs();
			}
			screenShotLocation = new File(".").getCanonicalPath() + File.separator + "screenshots" + File.separator + "screenshot_" + currentDateFormat + ".png";
			FileUtils.copyFile(scrFile, new File( screenShotLocation ));
		} catch (IOException e) {
			logger.fatal(e.getMessage());
		}
		
		return " | Location: " + screenShotLocation;
	}
	
	public static void scrollToObject(WebDriver driver, WebElement element) {
		Actions actions = new Actions(driver);
		actions.moveToElement(element);
		actions.perform();
		logger.info("Scrolled to object location: " + element.getLocation());
	}	
	
}
