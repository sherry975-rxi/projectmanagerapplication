package project.model.sendcode;

import project.model.SendSMS;

import java.io.IOException;


public class SMSSender implements MessageSender{


    private SendSMS sendSMS;


    @Override
    public void codeSender (String receipientPhoneNum, String email, String msg) throws IOException{

        sendSMS = new SendSMS();
        sendSMS.sendMessage(msg, receipientPhoneNum);
    }
}
