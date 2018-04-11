package project.model.sendcode;

import org.springframework.stereotype.Service;
import project.model.EmailMessage;
import project.model.SendEmail;


import javax.mail.MessagingException;
import java.io.IOException;


public class EmailSender implements MessageSender{

    private SendEmail sendEmail;
    private EmailMessage emailMessage;


    @Override
    public void codeSender (String receipientPhoneNum, String email, String msg) throws IOException, MessagingException {

        sendEmail = new SendEmail();
        emailMessage = new EmailMessage();

        emailMessage.setEmailAddress(email);
        emailMessage.setBody(msg);
        emailMessage.setSubject("Verification Code");
        sendEmail.sendMail(emailMessage);
    }
}
