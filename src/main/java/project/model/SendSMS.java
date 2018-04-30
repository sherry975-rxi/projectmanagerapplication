package project.model;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.hateoas.ResourceSupport;

public class SendSMS extends ResourceSupport {

   private String accountSid;
   private String authToken;
   private String hardCodeNumberToSend;
   private String numberProvidedByTwilio;

    public void setAccountSid(String accountSid){
        this.accountSid = accountSid;
    }

    public void setAuthToken(String authToken){
        this.authToken = authToken;
    }

    public void setHardCodeNumberToSend(String numberToSend){
        this.hardCodeNumberToSend = null;

    }

    public void setNumberProvidedByTwilio(String numberByTwilio){
        this.numberProvidedByTwilio = numberByTwilio;

    }



    public Message.Status sendMessage(String messageToSend, String numberToSend){

        /*
         Settings taken from twilio.com/user/account

             RegisteredEmail:   isepswitch3@gmail.com
             Password:          Switch_Isep2018

             Check Account_SID and Auth_Token in website's account

         */
        if(accountSid == null){
            accountSid = "AC4ffe069234a3ce253b84b7f7ed678407";
        }

        if(authToken == null){
            authToken = "7b30788fd931e158fe3846ec7e3866a0";

        }



        Twilio.init(accountSid, authToken);


        /*
        This is the host number provided by Twilio's Service
         */
        if(numberProvidedByTwilio == null){
            numberProvidedByTwilio = "+17044577646";
        }

        /*
            Test number. Must be a registered number on Twilio's account.
            In this case, the registered number on the Twilio account is +351937429087 number (João Leite's number)
         */


        //HARDCODING NUMBER SO TWILIO DOESNT CRASH APPLICATION
        //When possible, remove this variable and change all its calls to call numberToSend instead
        if(hardCodeNumberToSend == null){
            hardCodeNumberToSend = "+351911790134";
        }

        return Message.creator(new PhoneNumber(hardCodeNumberToSend),
                new PhoneNumber(numberProvidedByTwilio),
                messageToSend).create().getStatus();


    }



}
