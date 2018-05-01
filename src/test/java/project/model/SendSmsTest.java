package project.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import static org.junit.Assert.*;


public class SendSmsTest {


    SendSMS sendSMS;
    SendSMS sendSMSComparison;
    Project otherObject;

    @Before
    public void setup(){

        sendSMS = new SendSMS();
        sendSMSComparison = new SendSMS();
        otherObject = new Project();
    }

    @After
    public void tearDown(){
        sendSMS = null;
    }




    @Test
    public void sendSmsTest(){



        //GIVEN
        /*
         * The account credentials to send a TEST SMS
         */

        String accountSid = "AC309860890fe87733a1a320dd545a593b";
        String authToken = "33c052727999e08a93ef17eec3c4c69b";
        sendSMS.setAccountSid(accountSid);
        sendSMS.setAuthToken(authToken);


        sendSMS.setNumberProvidedByTwilio("+15005550006");

        //THEN
        /*
         * Returns a QUEUED.status, as its a trial account
         */

        assertEquals(Message.Status.QUEUED, sendSMS.sendMessage("ola", "teste", true));



    }


    @Test
    public void sendSmsEqualsTest(){

        //GIVEN two objects that belong to the class sendSMS,
        // THEN the equals method will return TRUE

        assertTrue(sendSMS.equals(sendSMS));
        assertTrue(sendSMS.equals(sendSMSComparison));

        //GIVEN two objects of different classes
        // THEN the equals method will return FALSE
        assertFalse(sendSMS.equals(otherObject));

    }



}
