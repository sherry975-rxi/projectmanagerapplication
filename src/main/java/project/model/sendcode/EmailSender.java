package project.model.sendcode;

import project.model.EmailMessage;
import project.model.SendEmail;


import javax.mail.MessagingException;
import java.io.IOException;



public class EmailSender implements MessageSender{

    private SendEmail sendEmail;
    private EmailMessage emailMessage;


    @Override
    public void codeSender (String codeSender, String receipientPhoneNum, String email, String msg) throws IOException, MessagingException {

        emailMessage.setEmailAddress(email);
        emailMessage.setBody(msg);
        emailMessage.setSubject("Verification Code");
        sendEmail.sendMail(emailMessage);
    }
}
