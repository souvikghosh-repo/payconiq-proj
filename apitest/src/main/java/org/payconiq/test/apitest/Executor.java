package org.payconiq.test.apitest;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.payconiq.test.testcases.*;

/**
 * Hello world!
 *
 */
public class Executor 
{
    public static void main( String[] args )
    {
        
        JUnitCore.runClasses(TestGetBookingId.class);
        JUnitCore.runClasses(TestGetBookingIdsByFirstName.class);
        JUnitCore.runClasses(TestPartialUpdateBooking.class);
        JUnitCore.runClasses(TestDeleteBooking.class);
        
        
    }
}
