package project.model;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SendSmsTest {



    @Test
    public void sendSmsTest(){


        //GIVEN
        /*
            The account credentials to send a TEST SMS
         */
        String accountSid = "AC309860890fe87733a1a320dd545a593b";
        String authToken = "33c052727999e08a93ef17eec3c4c69b";
        Twilio.init(accountSid, authToken);

        String messageToSend = "Teste";
        String numberProvidedByTwilio = "+15005550006";
        String hardcodedNumberToSend = "+351937429087";


        //THEN
        /*
            Returns a QUEUED.status, as its a trial account
         */

        assertEquals(Message.Status.QUEUED, Message
                .creator(new PhoneNumber("+15005550006"), new PhoneNumber("+15005550006"), "Teste")
                .create().getStatus());



    }
}
