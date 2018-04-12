package project.model.sendcode;

import project.model.SendSMS;


public class SMSSender implements ValidationMethod {


    private SendSMS sendSMS;


    @Override
    public String performValidationMethod(String receipientPhoneNum, String email, String question, String msg) {

        sendSMS = new SendSMS();
        sendSMS.sendMessage(msg, receipientPhoneNum);
        return "SMS sent! Please input the code sent to you:";
    }

    @Override
    public boolean checkRightAnswer(String userInput, String rightAnswer) {
        return userInput.equals(rightAnswer);
    }
}