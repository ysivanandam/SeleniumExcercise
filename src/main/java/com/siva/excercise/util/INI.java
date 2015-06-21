package com.siva.excercise.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import org.ini4j.Ini;
import org.ini4j.InvalidIniFormatException;

public class INI {

	public static final String VAR_CONFIGERRORMESSAGE = "##ERROR_WHEN_GETTING_PROPERTY_VALUE##";
	
	static Ini ini = null;
	File iniFile = null;
	
	public INI(String filePath) {			
		iniFile = new File(filePath);
		if(!iniFile.exists()) {
			try {				
				iniFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			ini = new Ini();
			ini.load(new FileReader(iniFile));
		} catch (InvalidIniFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createSection(String sectionName) {
		if(!ini.containsKey(sectionName)) {
			ini.add(sectionName);
		}
	}
	
	public void storeProperty(String sectionName, String keyName, String keyValue) {
		createSection(sectionName);
		Ini.Section section = ini.get(sectionName);
		section.put(keyName, keyValue);
	}
	
	public void storeIniFile() {
		
		try {
			File tmpFile = new File(iniFile.getCanonicalPath());
			FileOutputStream newFile = new FileOutputStream(tmpFile);
			ini.store(newFile);
			newFile.flush();
			newFile.close();			
		}  catch (IOException ioe) {
			ioe.printStackTrace();
		}			
	}
	
	public static String getProperty(String sectionName, String keyName) {
		String returnString = VAR_CONFIGERRORMESSAGE;
		if(!ini.containsKey(sectionName)) {
			System.err.println("CONFIG FILE: There is no such section (" + sectionName + ")");
		}else {
			Ini.Section section = ini.get(sectionName);
			if(!section.containsKey(keyName)) {
				System.err.println("CONFIG FILE: section (" + sectionName + ") does not have parameter (" + keyName + ")");
			}else {
				returnString = section.get(keyName).toString();
			}
		}
		return returnString;
	}
	
}
