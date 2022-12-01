package org.payconiq.test.testcases;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.payconiq.test.constants.testConstants;
import org.payconiq.test.libraries.ApiConnect;
import org.payconiq.test.libraries.jsonHandler;

public class TestDeleteBooking {

	static String bookingId="";
	public String result = "";
	testConstants constant = new testConstants();
	
	/**
	 * As part of prerequisite, creating a booking to ensure we get proper response while using the DeleteBooking
	 * Storing the Parameter for deleteBooking use,
	 * Storing the created bookingID for validation
	 */
	@Before
	public void prerequisite()
	{
		String method = constant.REQ_METHOD_POST;
		String endPoint = constant.REQ_EP_createBookings;
		String rqstBody = constant.REQ_PARAM_CREATEBOOKING;
		String createBookingResp = ApiConnect.getResponse(method,endPoint ,rqstBody);
		
		//storing values from cratebooking response which will be updated in partialUpdate API
		if(jsonHandler.isJson(createBookingResp))
		{
			bookingId = jsonHandler.getFieldValue(createBookingResp, "bookingid");
		}
	}
	
	
	
	/**
	 * Test is to validate the created booking is deleted properly
	 */
	@Test
	public void test() 
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
             System.out.println("@@VALIDATION - Booking id: "+bookingId+" deleted successfully after the test");
             result = "PASS";
         }
         else
         {
             System.out.println("DELETE Api not working for booking id: "+bookingId);
             result = "FAIL";
         }
		 
		 System.out.println("@@@TEST RESULT FOR DeleteBooking API:: "+result);
	}

}
