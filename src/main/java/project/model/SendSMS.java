package project.model;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.hateoas.ResourceSupport;

public class SendSMS extends ResourceSupport {




    public void sendMessage(String messageToSend){

        /*
         Settings taken from twilio.com/user/account

             RegisteredEmail:   isepswitch3@gmail.com
             Password:          Switch_Isep2018

             Check Account_SID and Auth_Token in website's account

         */

        String accountSid = "AC4ffe069234a3ce253b84b7f7ed678407";
        String authToken = "7b30788fd931e158fe3846ec7e3866a0";



        Twilio.init(accountSid, authToken);


        /*
        This is the host number provided by Twilio's Service
         */
        String numberProvidedByTwilio = "+17044577646";

        /*
            Test number. Must be a registered number on Twilio's account.
            In this case, the registered number on the Twilio account is +351937429087 number (João Leite's number)
         */


        //HARDCODING NUMBER SO TWILIO DOESNT CRASH APPLICATION
        //When possible, remove this variable and change all its calls to call numberToSend instead
        String hardcodedNumberToSend = "+351911790134";

        Message.creator(new PhoneNumber(hardcodedNumberToSend),
                new PhoneNumber(numberProvidedByTwilio),
                messageToSend).create();


    }



}
