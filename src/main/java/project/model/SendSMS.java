package project.model;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SendSMS {




    public void sendMessage(String messageToSend, String numberToSend){

        /*
         Settings taken from twilio.com/user/account

             RegisteredEmail:   isepswitch3@gmail.com
             Password:          Switch_Isep2018

             Check Account_SID and Auth_Token in website's account

         */

        String accountSid = "ACeaf1c8f54ad48d6442a491ffaab817ff";
        String authToken = "72dc7643db2de2eb51e89eca5201d83d";



        Twilio.init(accountSid, authToken);


        /*
        This is the host number provided by Twilio's Service
         */
        String numberProvidedByTwilio = "+12622791709";

        /*
            Test number. Must be a registered number on Twilio's account.
            In this case, the registered number on the Twilio account is +351937429087 number (João Leite's number)
         */
        String numberToSendMessage = numberToSend;

        //HARDCODING NUMBER SO TWILIO DOESNT CRASH APPLICATION
        numberToSendMessage = "+351937429087";

        Message message = Message.creator(new PhoneNumber(numberToSendMessage),
                new PhoneNumber(numberProvidedByTwilio),
                messageToSend).create();


    }



}
