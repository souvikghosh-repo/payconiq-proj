package org.payconiq.test.libraries;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.payconiq.test.constants.testConstants;

public class ApiConnect 
{
	static testConstants constant = new testConstants();
	
	/**
	 * Function returns the token in secific string format as to be used for cookie value
	 * @author SOUVIK
	 * @return String token usable
	 */
	public static String getToken()
	{
		String token = "";
		try
		{
			String tokenEndpoint = constant.REQ_EP_token;
			String urlParmeters = constant.REQ_PARAM_Token;
			
			URL urlObj = new URL(tokenEndpoint);
			
			
			URLConnection connection = urlObj.openConnection();
			((HttpURLConnection) connection).setRequestMethod(constant.REQ_METHOD_POST);
			
			//Setting Header
			connection.setRequestProperty("Content-Type", "application/json");
			
			//Connecting and enabling response capture
			connection.setDoOutput(true);
			
			
			//adding body toHttpURLConnection request
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(urlParmeters);
			wr.flush();
			wr.close();
			
			//getting responseCode
			int responseCode = ((HttpURLConnection) connection).getResponseCode();
			
			
			BufferedReader br;
			
			//checking if response code positive
			if((responseCode >= 200) && (responseCode<=299))
			{
				br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			}
			else
			{
				br = new BufferedReader(new InputStreamReader(( (HttpURLConnection)connection).getErrorStream()));
			}
			
			String response;
			StringBuffer rsp = new StringBuffer();
			
			//getting response written in string
			while ((response = br.readLine())!= null)
			{
				rsp.append(response);
			}
			br.close();
			
			token = "token="+jsonHandler.getFieldValue(rsp.toString(), "token");   //((rsp.toString().split(":")[1]).split("\"")[1]);
			
		}
		catch(Exception ex)
		{
			token = "Error";
		}
		return token;
		
	}
	
	
	/**
	 * Function to connect an API with parameters as below and receive the response based on response code
	 * @author SOUVIK
	 * @param method
	 * @param endPoint
	 * @param rqstBody if needed, else blank
	 * @return response
	 */
	public static String getResponse(String method, String endPoint, String rqstBody )
	{
		String response = "";
		try
		{
			URL urlObj = new URL(endPoint);
		
			URLConnection connection = urlObj.openConnection();
			
			//Setting Common Header
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Accept", "application/json");
			
			//Setting cookie based on method		
			if((method.equalsIgnoreCase("PUT")) || (method.equalsIgnoreCase("DELETE")))
			{
				connection.setRequestProperty("Cookie", getToken());
				((HttpURLConnection) connection).setRequestMethod(method.toUpperCase());
			}
			else if((method.equalsIgnoreCase("PATCH")))
			{
				connection.setRequestProperty("Cookie", getToken());
				
				//Modifying the accepted method for PATCH
				modifyConnectionMethod();
				((HttpURLConnection) connection).setRequestMethod(method.toUpperCase());		
			}
			else
			{
				((HttpURLConnection) connection).setRequestMethod(method.toUpperCase());
			}
			
			
			//Connecting and enabling response capture
			connection.setDoOutput(true);
			
			
			//Setting rqst body if available
			if(rqstBody!="")
			{
				//adding body toHttpURLConnection request
				DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
				wr.writeBytes(rqstBody);
				wr.flush();
				wr.close();
			}
			
			//getting responseCode
			int responseCode = ((HttpURLConnection) connection).getResponseCode();
			constant.responsecode = responseCode;   //Storing response code for global use
			
			
			BufferedReader br;
			
			//checking if response code positive
			if((responseCode >= 200) && (responseCode<=299))
			{
				br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			}
			else
			{
				br = new BufferedReader(new InputStreamReader(( (HttpURLConnection)connection).getErrorStream()));
			}
			
			
			StringBuffer rsp = new StringBuffer();
			
			//getting response written in string
			while ((response = br.readLine())!= null)
			{
				rsp.append(response);
			}
			br.close();
			
			response = rsp.toString();
			
		}
		catch(Exception ex)
		{
			response = "Error";
		}
		return response;
		
	}
	
	
	/**
	 * As realized HTTPURLConection does not allow "PATCH" method, hence updated the method list to get it accepted.
	 * This is specifically needed for applying PATCH method on API
	 */
	public static void modifyConnectionMethod()
	{
		try {
	         Field methodsField = HttpURLConnection.class.getDeclaredField("methods");
	         methodsField.setAccessible(true);
	         // get the methods field modifiers
	         Field modifiersField = Field.class.getDeclaredField("modifiers");
	         // bypass the "private" modifier 
	         modifiersField.setAccessible(true);

	         // remove the "final" modifier
	         modifiersField.setInt(methodsField, methodsField.getModifiers() & ~Modifier.FINAL);

	         /* valid HTTP methods */
	         String[] methods = {
	                    "GET", "POST", "HEAD", "OPTIONS", "PUT", "DELETE", "TRACE", "PATCH"
	         };
	         // set the new methods - including patch
	         methodsField.set(null, methods);

	     } catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException e) {
	      e.printStackTrace();
	     }
	}

}
