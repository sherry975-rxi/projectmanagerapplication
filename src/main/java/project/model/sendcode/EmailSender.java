package project.model.sendcode;

import project.model.EmailMessage;
import project.model.SendEmail;

import javax.mail.MessagingException;


public class EmailSender implements ValidationMethod {

    private SendEmail sendEmail;
    private EmailMessage emailMessage;


    @Override
    public String performValidationMethod(String receipientPhoneNum, String email, String question, String msg) throws MessagingException {

        sendEmail = new SendEmail();
        emailMessage = new EmailMessage();

        emailMessage.setEmailAddress(email);
        emailMessage.setBody(msg);
        emailMessage.setSubject("Verification Code");
        sendEmail.sendMail(emailMessage);
        return "E-mail sent! Please input the code sent to you:";
    }

    @Override
    public boolean checkRightAnswer(String userInput, String rightAnswer) {
        return userInput.equals(rightAnswer);
    }
}
