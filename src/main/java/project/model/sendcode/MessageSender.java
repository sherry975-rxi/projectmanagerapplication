package project.model.sendcode;


import javax.mail.MessagingException;
import java.io.IOException;

public interface MessageSender {

    void codeSender (String codeSender, String receipientPhoneNum, String email, String msg) throws IOException, MessagingException;
}
