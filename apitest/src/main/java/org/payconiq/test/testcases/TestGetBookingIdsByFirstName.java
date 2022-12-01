package org.payconiq.test.testcases;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.payconiq.test.constants.testConstants;
import org.payconiq.test.libraries.ApiConnect;
import org.payconiq.test.libraries.jsonHandler;

public class TestGetBookingIdsByFirstName 
{
	static String bookingId="";
	public String result = "";
	testConstants constant = new testConstants();
	
	static String paramFname = "";
	
	

	
	/**
	 * As part of prerequisite, creating a booking to ensure we get proper response while using the getBookings with 
	 * predefined parameter value
	 * Storing the Parameter for getBooking use,
	 * Storing the created bookingID for validation
	 */
	@Before
	public void prerequisite()
	{
		String method = constant.REQ_METHOD_POST;
		String endPoint = constant.REQ_EP_createBookings;
		String rqstBody = constant.REQ_PARAM_CREATEBOOKING;
		String createBookingResp = ApiConnect.getResponse(method,endPoint ,rqstBody);
		
		//getting the firstname value from the created booking response
		if(jsonHandler.isJson(createBookingResp))
		{
			paramFname = jsonHandler.getFieldValue(createBookingResp, "firstname");
			bookingId = jsonHandler.getFieldValue(createBookingResp, "bookingid");
		}
	}
	
	/**
	 * In test, connecting getBookingId API with parameter as captured in prerequisite. It should return the same bookingId
	 */
	@Test
	public void test() 
	{
		String method = constant.REQ_METHOD_GET;
		String endPoint = constant.REQ_EP_getBookingIds_with_param;
		String rqstBody = "";
		
		//replacing parameter value in endPoint
		endPoint = endPoint.replaceAll("\\{fnm\\}", paramFname);
		
		
		String getResponse = ApiConnect.getResponse(method, endPoint, rqstBody);
		System.out.println("getBookingIds-with parameter API response code:: "+testConstants.responsecode);
		
		//Validation
		String returnedBookingId = jsonHandler.getFieldValue(getResponse, "bookingid");
		if(bookingId.equalsIgnoreCase(returnedBookingId))
		{
			System.out.println("VALIDATION - Booking id pulled by getBokingIds-with Parameter API: "+returnedBookingId
					+" and its matched with the creted booking id as ::"+bookingId);
			result = "PASS";
		}
		else
		{
			System.out.println("VALIDATION - Booking id pulled by getBokingIds-with Parameter API: "+returnedBookingId
					+" and its matched with the creted booking id as ::"+bookingId);
			result = "FAIL";
		}
		System.out.println("@@@TEST RESULT FOR getBookingId - with parameter API:: "+result);		

	}
	
	
	/**
	 * deleting the created booking id after the test done for no duplication on regression run
	 */
	@After
	public void afterTest()
	{
		String method = constant.REQ_METHOD_DELETE;
		String endPoint = constant.REQ_EP_deleteBookings;
		String rqstBody = "";
		
		//replacing parameter value in endPoint
		endPoint = endPoint.replaceAll("\\{id\\}", bookingId);
		
		String deleteResp = ApiConnect.getResponse(method, endPoint, rqstBody);
		
		 if(jsonHandler.isJson(deleteResp))
         {
             String field = jsonHandler.getFieldValue(deleteResp, "");
         }
         else if(deleteResp.contains("Created"))
         {
             System.out.println("Booking id: "+bookingId+" deleted successfully after the test");
         }
         else
         {
             System.out.println("DELETE Api not working for booking id: "+bookingId);
         }
	}

}
