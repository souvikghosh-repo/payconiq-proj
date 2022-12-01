/**
 * 
 */
package org.payconiq.test.testcases;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.payconiq.test.constants.testConstants;
import org.payconiq.test.libraries.ApiConnect;

/**
 * @author SOUVIK
 *
 */
public class TestGetBookingId {
	
	//static String bookingId="";
	public String result = "";
	testConstants constant = new testConstants();
	
	/**
	 * Test is to validate getBookingIds - All is working perfectly
	 */
	@Test
	public void test() 
	{
		
		String apiMethod = constant.REQ_METHOD_GET;
		String endPoint = constant.REQ_EP_getBookingIds;
		
		String allId = ApiConnect.getResponse(apiMethod, endPoint, "");
		System.out.println("getBookingIds-All API response code:: "+testConstants.responsecode);
		//System.out.println("getBookingIds-All API response :: "+allId);
		
		
		if(allId.contains("bookingid"))
		{
			int totBookingID = StringUtils.countMatches(allId,"bookingid");
			System.out.println("VALIDATION - Total Booking id pulled by getBokingIds: "+totBookingID);
			result = "PASS";
		}
		else
		{
			result = "FAIL";
		}
		System.out.println("@@@TEST RESULT FOR getBookingId - All API:: "+result);
		
		
	}
	

}
