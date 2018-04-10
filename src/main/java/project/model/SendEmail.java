package project.model;

import org.springframework.web.bind.annotation.RequestBody;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;


//@RestController
public class SendEmail {

    //@Value("{gmail.usernmae}")
    private String username = "joao.mscr.leite@gmail.com";
    //@Value("{gmail.password}")
    private String passToUse = "252646816-Cinco";


    //@RequestMapping(value="/send", method = RequestMethod.POST)

    public String sendEmail(@RequestBody EmailMessage emailMessage) throws AddressException, MessagingException {

        sendmail(emailMessage);
        return "Email sent successfully";

    }

    private void sendmail(EmailMessage emailmessage) throws AddressException, MessagingException {


        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");


        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, passToUse);
                    }
                });


        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(username, false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("rita_silverio@hotmail.com"));
        msg.setSubject("teste");
        msg.setContent("Funciona?", "text/html");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(emailmessage.getBody(), "text/html");


        //NEXT PART IS TO SEND ATTACHMENTS
	/*


	Multipart multipart = new MimeMultipart();
	multipart.addBodyPart(messageBodyPart);
	MimeBodyPart attachPart = new MimeBodyPart();

	attachPart.attachFile("C:\\talk2amareswaran-downloads\\mysql2.png");

	multipart.addBodyPart(attachPart);
	msg.setContent(multipart);

	*/
        // sends the e-mail
        Transport.send(msg);





    }
}
