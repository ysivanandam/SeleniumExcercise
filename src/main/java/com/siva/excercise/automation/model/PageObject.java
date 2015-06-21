package com.siva.excercise.automation.model;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.siva.excercise.wrapper.WrappedWebDriver;

public class PageObject {

	protected WrappedWebDriver driver;
	protected HashMap<String, By> elements = new HashMap<String, By>();
	
	protected PageObject(WrappedWebDriver driver) {
		this.driver = driver;
	}
	
	protected void element(String name, By property){
		elements.put(name.toLowerCase(), property);		
	}
	
	protected List<WebElement> element(String name){
		if(elements.containsKey(name.toLowerCase())){			
			return driver.findElements(elements.get(name.toLowerCase()));
		}else {
			return null;
		}
	}

}
