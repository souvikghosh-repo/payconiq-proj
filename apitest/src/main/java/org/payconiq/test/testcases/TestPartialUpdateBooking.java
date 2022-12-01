package org.payconiq.test.testcases;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.payconiq.test.constants.testConstants;
import org.payconiq.test.libraries.ApiConnect;
import org.payconiq.test.libraries.jsonHandler;

public class TestPartialUpdateBooking
{
	
	static String bookingId="";
	public String result = "";
	testConstants constant = new testConstants();
	
	static String initialFname = "";
	static String initialSurname = "";
	static String initialPrice = "";
	
	
	/**
	 * As part of prerequisite, creating a booking to ensure we get proper response while using the Partial Update with 
	 * predefined parameter value
	 * Storing the Parameter for partial update use,
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
			initialFname = jsonHandler.getFieldValue(createBookingResp, "firstname");
			initialSurname = jsonHandler.getFieldValue(createBookingResp, "lastname");
			initialPrice = jsonHandler.getFieldValue(createBookingResp, "totalprice");
			bookingId = jsonHandler.getFieldValue(createBookingResp, "bookingid");
		}
	}
	
	
	/**
	 * Test is to validate Partial Update API working perfectly
	 */
	@Test
	public void test() 
	{
		String method = constant.REQ_METHOD_PATCH;
		String endPoint = constant.REQ_EP_partialUpdateBookings;
		String rqstBody = constant.REQ_PARAM_PARTIALUPDATEBOOKING;
		
		//replacing parameter value in endPoint
		endPoint = endPoint.replaceAll("\\{id\\}", bookingId);
		
		
		String getUpdateResponse = ApiConnect.getResponse(method, endPoint, rqstBody);
		System.out.println("partialUpdate API response code:: "+testConstants.responsecode);
		
		//Validation
		String updatedFname = jsonHandler.getFieldValue(getUpdateResponse, "firstname");
		String updatedSurname = jsonHandler.getFieldValue(getUpdateResponse, "lastname");	
		String updatedPrice = jsonHandler.getFieldValue(getUpdateResponse, "totalprice");
		if(!(initialFname.equalsIgnoreCase(updatedFname)) && !(initialSurname.equalsIgnoreCase(updatedSurname)) 
				&& !(initialPrice.equalsIgnoreCase(updatedPrice)))
		{
			System.out.println("VALIDATION - Partial Update API: executed. Initial FistName: "+initialFname
					+" and updated value as ::"+updatedFname);

			System.out.println("VALIDATION - Partial Update API: executed. Initial LastName: "+initialSurname
					+" and updated value as ::"+updatedSurname);

			System.out.println("VALIDATION - Partial Update API: executed. Initial Price: "+initialPrice
					+" and updated value as ::"+updatedPrice);
			result = "PASS";
		}
		
		else
		{
			System.out.println("VALIDATION - PartialUpdate not working as initial and updated values found same.");
			System.out.println("Response code is : "+testConstants.responsecode+". If the response code is OK "
					+ "then please check the PartilUpdate API request body if it has same value as initial value" );
			result = "FAIL";
		}
		System.out.println("@@@TEST RESULT FOR PartialUpdateBooking API:: "+result);
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
