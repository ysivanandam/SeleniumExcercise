package com.siva.excercise.testscript.ju.test;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.json.JSONException;
import org.json.JSONObject;

import com.siva.excercise.util.INI;
import com.siva.excercise.util.MyJSONUtil;
import com.siva.excercise.variables.GlobalValues;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JSONTest {
	String stationId = "-1";
	static Logger logger = Logger.getLogger(com.siva.excercise.testscript.ju.test.JSONTest.class);
	String apiKey = "lt0Hz37cIXU7pQh3abUdVnqj1s6OPS1ulDNf9lxh";
	
	@BeforeClass
	public static void initializeEnvironment() {
		GlobalValues.iniObj = new com.siva.excercise.util.INI("Config.ini");
	}
	
	@Before
	public void beforeEachTest() {
		
	}
	
	@Test
	public void doTest_01_searchForStation() {	
		String url = "https://developer.nrel.gov/api/alt-fuel-stations/v1/nearest.json?api_key=" + apiKey + "&location=78704&fuel_type=ELEC&radius=100&limit=100";
		try {
			JSONObject json = MyJSONUtil.returnJSonData(url);
			JSONObject jsonSearchedObject = MyJSONUtil.searchAndReturnJsonObjectFromArray(json, "fuel_stations", "station_name", "HYATT AUSTIN");
			stationId = jsonSearchedObject.getString("id");
			INI.storeProperty("JSON", "station_id", stationId);
			INI.storeIniFile();
			logger.info("Station ID for HYATT AUSTIN: " + stationId);
		} catch (MalformedURLException e) {
			logger.fatal(e.getMessage());
		} catch (IOException e) {
			logger.fatal(e.getMessage());
		} catch (JSONException e) {
			logger.fatal(e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Test
	public void doTest_02_getStationDetails() {
		stationId = INI.getProperty("JSON", "station_id");
		if(!stationId.equalsIgnoreCase(INI.VAR_CONFIGERRORMESSAGE)) {
			String url = "https://developer.nrel.gov/api/alt-fuel-stations/v1/" + stationId + ".json?api_key=" + apiKey;
			try {
				JSONObject json = MyJSONUtil.returnJSonData(url);
				
				JSONObject jObj = json.getJSONObject("alt_fuel_station");
				logger.info("Street: " + jObj.getString("street_address"));
				logger.info("City: " + jObj.getString("city"));
				logger.info("State: " + jObj.getString("state"));
				logger.info("Zip: " + jObj.getString("zip"));
				
			} catch (MalformedURLException e) {
				logger.fatal(e.getMessage());
			} catch (IOException e) {
				logger.fatal(e.getMessage());
			} catch (JSONException e) {
				logger.fatal(e.getMessage());
			}
		}else {
			logger.info("Not Executed");
		}
	}
	
	@After
	public void afterEachTest() {		
		
	}
	
	@AfterClass
	public static void cleanEnvironment() {
		
	}
}
