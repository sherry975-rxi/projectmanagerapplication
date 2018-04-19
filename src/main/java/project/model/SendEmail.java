package project.model;


import org.springframework.hateoas.ResourceSupport;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;


public class SendEmail extends ResourceSupport {


    private static String senderEmail = "isepswitch3@gmail.com";
    private static String senderXPMS = "Switch_Isep2018";

    /**
     * This method sends an email to the recipient with the intended message
     * @param emailmessage email address,email subject and email content to send to recipient
     * @throws AddressException
     * @throws MessagingException
     */
    public void sendMail(EmailMessage emailmessage) throws MessagingException {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");


        //authenticates sender's email and password
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(senderEmail, senderXPMS);
                    }
                });

        //Email sender
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(senderEmail, false));

        //recipient email address
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailmessage.getEmailAddress()));
        //Email subject
        msg.setSubject(emailmessage.getSubject());
        //Email context
        msg.setContent(emailmessage.getBody(), "text/html");
        //Email date
        msg.setSentDate(new Date());

        
        Transport.send(msg);
    }
}
