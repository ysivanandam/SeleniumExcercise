package com.siva.excercise.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyJSONUtil {
	public static JSONObject returnJSonData(String urlToGet) throws MalformedURLException, IOException {
		InputStream in = new URL(urlToGet).openStream();
		try {
			JSONObject json = new JSONObject(IOUtils.toString( in ));
			return json;		      
		} catch (JSONException e) {			
			e.printStackTrace();
			return null;
		} finally {
			IOUtils.closeQuietly(in);
		}
	}
	
	public static JSONObject searchAndReturnJsonObjectFromArray(JSONObject jsonObj, String jsonArrayNameToSearch, String arrayKeyToSearch, String keyValueToSearch) {
		JSONObject json = null;
		try {
			JSONArray jsonArray = (JSONArray) jsonObj.get(jsonArrayNameToSearch);
			int arraySize = jsonArray.length();
			for(int arrInd=0; arrInd<arraySize; arrInd++) {
				JSONObject objectInArray = jsonArray.getJSONObject(arrInd);
				if(objectInArray.getString(arrayKeyToSearch).equalsIgnoreCase(keyValueToSearch)) {
					json = objectInArray;
					break;
				}
			}
		} catch (JSONException e) {
			json = null;
			e.printStackTrace();
		}
		return json;
	}	
	
}
