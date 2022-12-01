package org.payconiq.test.testcases;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

//import java.net.HttpURLConnection;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.logging.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import org.apache.commons.io.FileUtils;

import org.junit.Test;
import org.junit.runner.Runner;

public class unitAPItest 
{


       @Test

       public void test() throws ParseException

       {

            

 

             //System.out.println("Testing 1 - Send Http GET request");

             //sendGet();

            

             //Pre-req creating the booking

             String bookingResp = sendPost("POST", "https://restful-booker.herokuapp.com/booking", "{\"firstname\":\"David\",\"lastname\":\"Brown\",\"totalprice\":1207,\"depositpaid\":true,\"bookingdates\":{\"checkin\":\"2022-12-01\",\"checkout\":\"2022-12-07\"},\"additionalneeds\":\"Breakfast\"}");

             String bookingId = getFieldValue(bookingResp, "bookingid");

            

             //Deleting booking with id

             String deleteResp = sendPost("DELETE", "https://restful-booker.herokuapp.com/booking/"+bookingId, "");

            

             if(isJson(deleteResp))

             {

                    String field = getFieldValue(deleteResp, "");

             }

             else if(deleteResp.contains("Created"))

             {

                    System.out.println("DELETE Api worked well for booking id: "+bookingId);

             }

             else

             {

                    System.out.println("DELETE Api not working for booking id: "+bookingId);

             }

            

            

             //String bookingId = (bookingResp.split(",")[0]).split(":")[1];

 

       }

      

      

      

      

       // HTTP GET request

             public void sendGet()

             {

                   

                    //String token = getToken();

                    try

                    {

                         

                          String url = "https://restful-booker.herokuapp.com/booking";

                         

                          URL obj = new URL(url);

                          //HostNameVerifier();

                          verifier();

                          //HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

                          URLConnection con = obj.openConnection();

                         

                          // optional default is GET

                          //con.setRequestMethod("GET");

 

                          //add request header

                           //con.setRequestProperty("Authorization", "Bearer "+token);

                          //con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

                          con.setRequestProperty("Content-Type", "application/json");

                         

                         

                          int responseCode = ((HttpURLConnection) con).getResponseCode();

                         

                          System.out.println("Response Code : " + responseCode);

 

                          BufferedReader in;

                         

                          if(200<=((HttpURLConnection) con).getResponseCode() && ((HttpURLConnection) con).getResponseCode() <=299)

                          {

                                 in = new BufferedReader(new InputStreamReader(con.getInputStream()));

                          }

                          else

                          {

                                 in = new BufferedReader(new InputStreamReader(((HttpURLConnection) con).getErrorStream()));

                          }

                          String inputLine;

                          StringBuffer response = new StringBuffer();

 

                          while ((inputLine = in.readLine()) != null) {

                                 response.append(inputLine);

                          }

                          in.close();

 

                          //print result

                           //System.out.println(response.toString());

                          //int totalInqCount = StringUtils.countMatches(response.toString(),"bookingid");

                           //System.out.println(totalInqCount);

                         

                         

                         

                         

                    }

                    catch(Exception ex)

                    {

                           System.out.println(ex.getMessage());

                          System.out.println("Error in Get method");

                    }

 

                   

 

             }

            

             // HTTP Get Token (Modified as per IDM structure)

             public String getToken()

             {

                    String token = "";

                    try

                    {

                          String url = "https://restful-booker.herokuapp.com/auth";

                          String urlParameters ="{\"username\":\"admin\",\"password\":\"password123\"}";

                          URL obj = new URL(url);

                         

                          verifier();

                          URLConnection con = obj.openConnection();

 

                          //add reuqest header

                          ((HttpURLConnection) con).setRequestMethod("POST");

                          //con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

                          con.setRequestProperty("Content-Type", "application/json");

                         

                         

                          //byte[] postdata = urlParameters.getBytes(StandardCharsets.UTF_8);

                          //int postDataLength = postdata.length;

                         

                          // Send post request

                          con.setDoOutput(true);

                          //con.setRequestProperty("charset", "utf-8");

                          //con.setRequestProperty("Content-Length", Integer.toString(postDataLength));

                          DataOutputStream wr = new DataOutputStream(con.getOutputStream());

                          wr.writeBytes(urlParameters);

                          wr.flush();

                          wr.close();

 

                          int responseCode = ((HttpURLConnection) con).getResponseCode();

                          System.out.println("\nSending 'POST' request to URL : " + url);

                          System.out.println("Post parameters : " + urlParameters);

                          System.out.println("Response Code : " + responseCode);

 

                         

                          BufferedReader in;

                         

                          if(200<=((HttpURLConnection) con).getResponseCode() && ((HttpURLConnection) con).getResponseCode() <=299)

                          {

                                 in = new BufferedReader(new InputStreamReader(con.getInputStream()));

                          }

                          else

                          {

                                 in = new BufferedReader(new InputStreamReader(((HttpURLConnection) con).getErrorStream()));

                          }

                          String inputLine;

                          StringBuffer response = new StringBuffer();

 

                          while ((inputLine = in.readLine()) != null) {

                                 response.append(inputLine);

                          }

                          in.close();

                         

                          //print result

                           //System.out.println(response.toString());

                          token = "token="+((response.toString().split(":")[1]).split("\"")[1]);

                    }

                    catch(Exception ex)

                    {

                           System.out.println(ex.getMessage());

                          System.out.println("Error in get token method");

                    }

                    return token;

             }

            

            

            

             // HTTP POST request

             public String sendPost(String method, String endPoint, String reqBody)

             {

                    //JSONObject respJson = null;

                    String apiResponse = "";

                    String token = getToken();

                    try

                    {

                          String url = endPoint; //https://restful-booker.herokuapp.com/booking/14018;

                          String urlParameters =reqBody;

                          URL obj = new URL(url);

                          //HostNameVerifier();

                          verifier();

                          //HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

                          URLConnection con = obj.openConnection();

 

                          //add reuqest header

                          ((HttpURLConnection) con).setRequestMethod(method);

                           //con.setRequestProperty("Authorization", "Bearer "+token);

                          //con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

                          con.setRequestProperty("Content-Type", "application/json");

                          con.setRequestProperty("Accept", "application/json");

                   

                         

                          if((method.equalsIgnoreCase("PUT")) || (method.equalsIgnoreCase("PATCH")) || (method.equalsIgnoreCase("DELETE")))

                          {

                                 con.setRequestProperty("Cookie", token);

                                 //String pwd = GenericLibrary.decrypt("YWRtaW46cGFzc3dvcmQxMjM=");

                                 //con.setRequestProperty("Authorisation", "YWRtaW46cGFzc3dvcmQxMjM=");

                          }

                         

                         

                         

                         

                          // Send post request

                          con.setDoOutput(true);

                         

                         

                          if(reqBody!="")

                          {

                                 DataOutputStream wr = new DataOutputStream(con.getOutputStream());

                                 wr.writeBytes(urlParameters);

                                 wr.flush();

                                 wr.close();

                          }

 

                          int responseCode = ((HttpURLConnection) con).getResponseCode();

                          System.out.println("\nSending 'POST' request to URL : " + url);

                          System.out.println("Request Body : " + urlParameters);

                          System.out.println("Response Code : " + responseCode);

 

                         

                          BufferedReader in;

                         

                          if(200<=((HttpURLConnection) con).getResponseCode() && ((HttpURLConnection) con).getResponseCode() <=299)

                          {

                                 in = new BufferedReader(new InputStreamReader(con.getInputStream()));

 

                          }

                          else

                          {

                                 in = new BufferedReader(new InputStreamReader(((HttpURLConnection) con).getErrorStream()));

                          }

                          String inputLine;

                          StringBuffer response = new StringBuffer();

 

                          while ((inputLine = in.readLine()) != null) {

                                 response.append(inputLine);

                          }

                          in.close();

                         

                          //print result

                           //System.out.println(response.toString());

                          apiResponse = response.toString();

                         

                          /*respJson = new JSONObject(apiResponse);

                           System.out.println(respJson.get("bookingid"));*/

                         

                         

                         

                    }

                    catch(Exception ex)

                    {

                           System.out.println(ex.getMessage());

                          System.out.println("Error in POST method");

                    }

                   

                    return apiResponse;

             }

            

            

             //get field value from json object

             public static String getFieldValue(String apiResponse, String fieldName)

             {

                    JSONObject objJson = new JSONObject(apiResponse);

                    String value = "";

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

                                          getFieldValue((objJson.getJSONObject(nextKeys)).toString(), fieldName);

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

                                                getFieldValue(innerJson.toString(), fieldName);

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

                          //parseObject(objJson, key);

                    }

                   

                    return value;

             }

            

            

             //to check if a response string matches json structure

	public boolean isJson(String resp)
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

            

            

//           public static void parseObject(JSONObject json, String key)

//           {

//                  System.out.println(key+" is : "+json.get(key));

//           }

            

            

             public void verifier()

             {

                    try

                    {

                          TrustManager[] trustAllCerts = new TrustManager[]

                          {

                                 new X509TrustManager()

                                 {

                                       public java.security.cert.X509Certificate[] getAcceptedIssuers()

                                       {

                                              return null;

                                       }

                                       public void checkClientTrusted(X509Certificate[] certs, String authType)

                                       {}

                                       public void checkServerTrusted(X509Certificate[] certs, String authType)

                                       {}

                                 }

                          };

                         

                          SSLContext sc = SSLContext.getInstance("SSL");

                          sc.init(null, trustAllCerts, new java.security.SecureRandom());

                    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

                         

                          HostnameVerifier allHostValid = new HostnameVerifier()

                          {

                                 public boolean verify (String hostname, SSLSession session)

                                 {

                                       return true;

                                 }

                          };

                         

                      HttpsURLConnection.setDefaultHostnameVerifier(allHostValid);

                   

                    }

                    catch(Exception ex)

                    {

                           System.out.println(ex.getMessage());

                    }

                   

             }

            

            

            

            

             public static int convertStringMonthToint(String month)

             {

                    int mnth = 0;

                    try

                    {

                          Date dt = new SimpleDateFormat("MMMM", Locale.ENGLISH).parse(month);

                          Calendar cal = Calendar.getInstance();

                          cal.setTime(dt);

                          int convertedmnth = cal.get(Calendar.MONTH);

                          mnth = convertedmnth+1;

                    }

                    catch(Exception ex)

                    {

                          ex.getMessage();

                          mnth = 404;

                    }

                    return mnth;

             }

            

 

}
