package project.model;

import org.junit.Test;

import static org.junit.Assert.*;


public class EmailMessageTest {

    private EmailMessage emailMessage = new EmailMessage();


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

    @Test
    public void testEquals() {
        //GIVEN
        emailMessage.setBody("body");
        emailMessage.setEmailAddress("address");
        emailMessage.setSubject("subject");

        //WHEN
        boolean areEqual = emailMessage.equals(emailMessage);

        //THEN
        assertTrue(areEqual);

        //GIVEN
        Report report = new Report();

        //WHEN
        areEqual = emailMessage.equals(report);

        //THEN
        assertFalse(areEqual);

        //GIVEN
        EmailMessage emailMessageB = new EmailMessage();
        emailMessageB.setBody("body");
        emailMessageB.setEmailAddress("address");
        emailMessageB.setSubject("subject");

        //WHEN
        areEqual = emailMessage.equals(emailMessageB);

        //THEN
        assertTrue(areEqual);

        //GIVEN
        emailMessageB.setBody("body2");

        //WHEN
        areEqual = emailMessage.equals(emailMessageB);

        //THEN
        assertFalse(areEqual);

        //GIVEN
        emailMessageB.setBody("body");
        emailMessageB.setSubject("subject2");

        //WHEN
        areEqual = emailMessage.equals(emailMessageB);

        //THEN
        assertFalse(areEqual);

        //GIVEN
        emailMessageB.setSubject("subject");
        emailMessageB.setEmailAddress("address2");

        //WHEN
        areEqual = emailMessage.equals(emailMessageB);

        //THEN
        assertFalse(areEqual);
    }

}
