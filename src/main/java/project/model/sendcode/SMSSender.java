package project.model.sendcode;

import project.model.SendSMS;

import java.io.IOException;

public class SMSSender implements MessageSender{


    private SendSMS sendSMS;


    @Override
    public void codeSender (String codeSender, String receipientPhoneNum, String email, String msg) throws IOException{
        sendSMS.sendMessage(msg, receipientPhoneNum);
    }
}
