package org.payconiq.test.libraries;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class jsonHandler 
{
	
	/**
	 * Function returns field value from json based on the field name. Considering if the field is repeated in nested,
	 * then the first matched value would be returned. Not handled for repeated fields as no scenario given
	 * @param apiResponse String
	 * @param fieldName String
	 * @return
	 */
	public static String getFieldValue(String apiResponse, String fieldName)
    {
		String value = "";
		boolean isArray = apiResponse.startsWith("[");
		
		if(!isArray)
		{
			JSONObject objJson = new JSONObject(apiResponse);
		       

			boolean exists = objJson.has(fieldName);

			Iterator < ? > keys;
			String nextKeys;
			if (!exists)
			{
	           keys = objJson.keys();
	           while (keys.hasNext())
	           {
	             nextKeys = (String) keys.next();
	             try
	             {
	                if (objJson.get(nextKeys) instanceof JSONObject)
	                {
	                  if (exists == false)
	                  {
	                	  value = getFieldValue((objJson.getJSONObject(nextKeys)).toString(), fieldName);
	                	  if(value!="")
	                	  {
	                		  break;
	                	  }
	                  }
	                }
	                else if (objJson.get(nextKeys) instanceof JSONArray)
	                {
	                  JSONArray jsonarray = objJson.getJSONArray(nextKeys);

	                  for (int i = 0; i < jsonarray.length(); i++)
	                  {
	                     String jsonarrayString = jsonarray.get(i).toString();
	                     JSONObject innerJson = new JSONObject(jsonarrayString);
	                     if (exists == false)
	                     {
	                    	 value = getFieldValue(innerJson.toString(), fieldName);
	                    	 if(value!="")
		                   	 {
		                   		 break;
		                   	 }
	                     }
	                  }
	                }
	             }
	             catch (Exception e)
	             {}
	           }
			}
			else
			{
				value =  (objJson.get(fieldName)).toString();
			}
		}
		else
		{
			JSONArray jsonarray = new JSONArray(apiResponse);

            for (int i = 0; i < jsonarray.length(); i++)
            {
               String jsonarrayString = jsonarray.get(i).toString();
               
               value = getFieldValue(jsonarrayString, fieldName);
               if(value!="")
               {
         		  break;
               }            
            }
		}
		
		return value;
    }
	
	
	
	/**
	 * Function to validate if the response string is a json structure
	 * @author SOUVIK
	 * @param resp
	 * @return boolean
	 */
	public static boolean isJson(String resp)
	{
		try
		{
			new JSONObject(resp);
		}
		catch (JSONException ex)
		{
			try
			{
				new JSONArray(resp);
			}
			catch (JSONException ex1)
			{
				return false;
			}
		}
		return true;
	}
}
