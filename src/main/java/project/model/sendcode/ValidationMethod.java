package project.model.sendcode;


import javax.mail.MessagingException;
import java.io.IOException;

public interface ValidationMethod {

    String performValidationMethod(String receipientPhoneNum, String email, String question, String msg) throws IOException, MessagingException;

    boolean checkRightAnswer(String userInput, String rightAnswer);
}
