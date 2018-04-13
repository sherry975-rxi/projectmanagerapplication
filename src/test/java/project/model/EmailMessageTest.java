package project.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class EmailMessageTest {

    EmailMessage emailMessage = new EmailMessage();


    @Test
    public void setGetEmailAddress(){

        //GIVEN
        String emailAddress = "myEmail@gmail.com";

        //WHEN
        emailMessage.setEmailAddress(emailAddress);

        //THEN
        assertEquals(emailAddress, emailMessage.getEmailAddress());
    }

    @Test
    public void setGetEmailSubject(){

        //GIVEN
        String subject = "subject";

        //WHEN
        emailMessage.setSubject(subject);

        //THEN
        assertEquals(subject, emailMessage.getSubject());
    }

    @Test
    public void setGetEmailBody(){

        //GIVEN
        String body = "body";

        //WHEN
        emailMessage.setBody(body);

        //THEN
        assertEquals(body, emailMessage.getBody());
    }



}
