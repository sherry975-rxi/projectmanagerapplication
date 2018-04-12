package project.model;


import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;


public class SendEmail {


    private String senderEmail = "isepswitch3@gmail.com";
    private String senderXPMS = "Switch_Isep2018";

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


        //In case one wants to send an attachment
	/*
	Multipart multipart = new MimeMultipart();
	multipart.addBodyPart(messageBodyPart);
	MimeBodyPart attachPart = new MimeBodyPart();

	attachPart.attachFile("C:\\talk2amareswaran-downloads\\mysql2.png");

	multipart.addBodyPart(attachPart);
	msg.setContent(multipart);
	*/
        // Shifts the e-mail to recipient
        Transport.send(msg);
    }
}
