package com.zeffah.usermanagementportal;

import com.zeffah.usermanagementportal.methods.GlobalMethods;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    //test for addNumbers function
    @Test
    public void addNumbers(){
        double actualSum = GlobalMethods.addToNumbers(35.0, 45.0, 90.0);
        double expectedSum = 170;
        assertEquals("Sum of passed values expected to be equal", expectedSum, actualSum, 0);
    }
}