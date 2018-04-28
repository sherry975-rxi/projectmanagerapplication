package project.restControllers.exceptions;

import org.junit.Test;
import project.restcontroller.exceptions.StandardError;

import static org.junit.Assert.*;

public class StandardErrorTest {

    /**
     * GIVEN a StandardError and a status equal to 10
     * WHEN we call the method getStatus
     * THEN we must receive the error status and it must be equal to the status defined
     */
    @Test
    public void getStatus() {

        StandardError error = new StandardError(10, "Error", new Long(10));
        error.setStatus(11);

        Integer status = 11;

        assertTrue(status.equals(error.getStatus()));
    }

    /**
     * GIVEN a StandardError and a message equal to "Error
     * WHEN we call the method get Message
     * THEN we must receive the error message and it must be equal to the message defined
     */
    @Test
    public void getMsg() {

        StandardError error = new StandardError(10, "Error", new Long(10));
        error.setMsg("ERROR!");

        String message = "ERROR!";

        assertEquals(message, error.getMsg());
    }

    /**
     * GIVEN a StandardError and a timeStamp equal to 10
     * WHEN we call the method getTimeStamp
     * THEN we must receive the timeStamp and it must be equal to the TimeStamp defined
     */
    @Test
    public void getTimeStamp() {

        StandardError error = new StandardError(10, "Error", new Long(10));
        error.setTimeStamp(new Long(20));

        Long timeStamp = new Long(20);

        assertTrue(timeStamp.equals(error.getTimeStamp()));
    }
}