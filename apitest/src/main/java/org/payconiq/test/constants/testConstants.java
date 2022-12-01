package org.payconiq.test.constants;

public class testConstants 
{
	public static int responsecode = 0; //This is for global use as response code not returned from method
	
	public final String REQ_METHOD_GET = "GET";
	public final String REQ_METHOD_POST = "POST";
	public final String REQ_METHOD_PUT = "PUT";
	public final String REQ_METHOD_PATCH = "PATCH";
	public final String REQ_METHOD_DELETE = "DELETE";
	
	
	public final String REQ_EP_token = "https://restful-booker.herokuapp.com/auth";
	public final String REQ_EP_getBookingIds = "https://restful-booker.herokuapp.com/booking";
	public final String REQ_EP_getBookingIds_with_param = "https://restful-booker.herokuapp.com/booking?firstname={fnm}";
	public final String REQ_EP_createBookings = "https://restful-booker.herokuapp.com/booking";
	public final String REQ_EP_deleteBookings = "https://restful-booker.herokuapp.com/booking/{id}";
	public final String REQ_EP_partialUpdateBookings = "https://restful-booker.herokuapp.com/booking/{id}";
	
	public final String REQ_PARAM_Token = "{\"username\":\"admin\",\"password\":\"password123\"}";
	public final String REQ_PARAM_CREATEBOOKING="{\"firstname\":\"MyName\",\"lastname\":\"MySurname\",\"totalprice\":1207,\"depositpaid\":true,\"bookingdates\":{\"checkin\":\"2022-12-15\",\"checkout\":\"2022-12-22\"},\"additionalneeds\":\"Pet Bed\"}";
	public final String REQ_PARAM_PARTIALUPDATEBOOKING="{\"firstname\":\"PAUpdate - MyName\",\"lastname\":\"PAUpdate - MySurname\",\"totalprice\":1987}";
}
