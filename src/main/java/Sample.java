import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Sample {
	
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
	
	public static void main(String[] args) {
		String url = "https://developer.nrel.gov/api/alt-fuel-stations/v1/nearest.json?api_key=lt0Hz37cIXU7pQh3abUdVnqj1s6OPS1ulDNf9lxh&location=78704&fuel_type=ELEC&radius=100&limit=100";
		try {
			JSONObject json = returnJSonData(url);
			JSONArray jsonArray = (JSONArray) json.get("fuel_stations");
			for (int i = 0, size = jsonArray.length(); i < size; i++)
			{
				JSONObject objectInArray = jsonArray.getJSONObject(i);

				// "...and get thier component and thier value."
				String[] elementNames = JSONObject.getNames(objectInArray);
				System.out.printf("%d ELEMENTS IN CURRENT OBJECT:\n", elementNames.length);
				for (String elementName : elementNames)
				{
					String value = objectInArray.getString(elementName);
					System.out.printf("name=%s, value=%s\n", elementName, value);
				}
				System.out.println();
				break;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
